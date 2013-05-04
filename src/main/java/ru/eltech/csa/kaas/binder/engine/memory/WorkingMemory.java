package ru.eltech.csa.kaas.binder.engine.memory;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import ru.eltech.csa.kaas.binder.engine.memory.proposal.ProposalInWork;
import ru.eltech.csa.kaas.binder.model.Criterion;
import ru.eltech.csa.kaas.binder.model.Estimate;
import ru.eltech.csa.kaas.binder.model.ServiceImplementation;
import ru.eltech.csa.kaas.binder.model.ServiceType;
import ru.eltech.csa.kaas.binder.platform.config.BinderConfig;
import ru.eltech.csa.kaas.binder.platform.config.CriterionUsingStrategy;
import ru.eltech.csa.kaas.binder.query.Condition;
import ru.eltech.csa.kaas.binder.query.CriterionImportance;
import ru.eltech.csa.kaas.binder.query.Query;
import ru.eltech.csa.kaas.binder.query.checker.ConditionCheckerUtil;

/**
 * Contains all data for engine reasoning.
 */
public class WorkingMemory {

    private KnowledgeBaseAdapter knowledgeBase;
    private Query query;
    /**
     * Set of criterions which used for reasoning.
     */
    private Set<Criterion> criterions;
    private Double maxCriterionImportance;
    /**
     * Required service types and set of alternative implementations
     */
    private Map<ServiceType, Set<ServiceImplementation>> alternatives;
    /**
     * Service types queried by the user.
     */
    private Set<ServiceType> requiredTypes = new HashSet<>();
    /**
     * Set of corrupt estimates. If even one of them is satisfied,
     * implementation proposition will break the query conditions.
     */
    private Set<Estimate> corruptEstimates;
    /**
     * Cache of criterion conditions.
     */
    private Map<Criterion, Set<Condition>> criterionConditions;
    
    /**
     * Built proposals.
     */
    private Set<ProposalInWork> proposals = new HashSet<>();

    public WorkingMemory(Query query, KnowledgeBaseAdapter knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
        this.query = query;
        criterions = orderCriterions(query, knowledgeBase);
        criterionConditions = initCriterionConditions(query, knowledgeBase);
        alternatives = initAlternatives(query, knowledgeBase);

        proposals.add(new ProposalInWork(requiredTypes));
    }

    public BinderConfig getConfig() {
        return query.getConfig();
    }

    public Set<Criterion> getCriterions() {
        return criterions;
    }

    public Map<ServiceType, Set<ServiceImplementation>> getAlternatives() {
        return alternatives;
    }

    public Set<ProposalInWork> getProposals() {
        return proposals;
    }

    public Set<Estimate> getCorruptEstimates() {
        return corruptEstimates;
    }

    private Set<Criterion> orderCriterions(Query query, KnowledgeBaseAdapter knowledgeBase) {
        CriterionUsingStrategy criterionUsingStrategy = query.getConfig().getCriterionUsingStrategy();
        Set<Criterion> result = null;
        switch (criterionUsingStrategy) {
            case ONLY_QUERY_CRITERIONS:
                result = queryCriterionsList(query, knowledgeBase);
                break;
            case ALL_KNOWLEDGE_BASE_CRITERIONS:
                result = knowledgeBaseCriterionsList(query, knowledgeBase);
        }
        if (result == null || result.isEmpty()) {
            throw new IllegalArgumentException("Criterions for reasoning haven't been found. "
                    + "Set at least one criterion or change criterion using strategy.");
        }
        initMaxCriterionImportance(result);
        return result;
    }

    private Set<Criterion> queryCriterionsList(Query query, KnowledgeBaseAdapter knowledgeBase) {
        Set<Criterion> result = new HashSet<>();
        if (query.getCriterionImportances() != null) {
            for (CriterionImportance i : query.getCriterionImportances()) {
                Criterion crit = knowledgeBase.findCriterion(i.getCriterionId());
                if (i.getImportanceValue() != null) {
                    crit.setImportance(i.getImportanceValue());
                }
                result.add(crit);
            }
        }
        return result;
    }

    private Set<Criterion> knowledgeBaseCriterionsList(Query query, KnowledgeBaseAdapter knowledgeBase) {
        Set<Criterion> result = new HashSet<>();
        if (knowledgeBase.getCriterions() != null) {
            result.addAll(knowledgeBase.getCriterions());
            if (query.getCriterionImportances() != null) {
                for (CriterionImportance i : query.getCriterionImportances()) {
                    Criterion crit = knowledgeBase.findCriterion(i.getCriterionId());
                    if (i.getImportanceValue() != null) {
                        crit.setImportance(i.getImportanceValue());
                    }
                }
            }
        }
        return result;
    }

    private void initMaxCriterionImportance(Set<Criterion> set) {
        maxCriterionImportance = Collections.max(set).getImportance();
    }

    private HashMap<Criterion, Set<Condition>> initCriterionConditions(Query query, KnowledgeBaseAdapter knowledgeBase) {
        HashMap<Criterion, Set<Condition>> result = new HashMap<>();
        if (query.getConditions() != null) {
            for (Condition condition : query.getConditions()) {
                Criterion crit = knowledgeBase.findCriterion(condition.getCriterionId());
                Set<Condition> innerSet = result.get(crit);
                if (innerSet == null) {
                    innerSet = new HashSet<>();
                    result.put(crit, innerSet);
                }
                innerSet.add(condition);
            }
        }
        return result;
    }

    private Map<ServiceType, Set<ServiceImplementation>> initAlternatives(Query query, KnowledgeBaseAdapter knowledgeBase) {
        Map<ServiceType, Set<ServiceImplementation>> result = new HashMap<>();
        if (query.getServiceTypesId() == null || query.getServiceTypesId().isEmpty()) {
            throw new IllegalArgumentException("At least one service type must be queried.");
        }
        for (String typeId : query.getServiceTypesId()) {
            ServiceType type = knowledgeBase.findServiceType(typeId);
            if (result.containsKey(type)) {
                throw new IllegalArgumentException("Query contains item with duplicate id: " + typeId);
            }
            Set<ServiceImplementation> typeImplementations = knowledgeBase.findTypeImplementations(type);
            result.put(type, filter(typeImplementations, query, knowledgeBase));
            requiredTypes.add(type);
        }
        return result;
    }

    /**
     * Removes implementations which nerer cannot satisfy current conditions.
     * Removes corrupt estimates from the knowledge base cache and sets them to
     * utility set.
     *
     * @param filtered filtered implementation set
     * @param query the query
     * @param knowledgeBase the knowledge base adapter
     * @return set of implementations which have a chance to satisfy conditions
     */
    private Set<ServiceImplementation> filter(Set<ServiceImplementation> filtered, Query query, KnowledgeBaseAdapter knowledgeBase) {
        if (query.getConditions() == null) {
            return new HashSet<>(filtered);
        }
        Set<ServiceImplementation> result = new HashSet<>();
        for (ServiceImplementation impl : filtered) {
            Set<Estimate> wrk = new HashSet<>();
            Set<Estimate> implementationEstimates = knowledgeBase.findImplementationEstimates(impl);
            // move corrupted estimates to wrk
            for (Estimate estimate : implementationEstimates) {
                Set<Condition> estimateConditions = criterionConditions.get(estimate.getCriterion());
                for (Condition condition : estimateConditions) {
                    if (!ConditionCheckerUtil.check(estimate.getValue(), condition)) {
                        wrk.add(estimate);
                        implementationEstimates.remove(estimate);
                        break;
                    }
                }
            }
            if (implementationEstimates.size() > 0) {
                result.add(impl);
                corruptEstimates.addAll(wrk);
            }
        }
        return result;
    }
}
