package ru.eltech.csa.kaas.binder.engine.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import ru.eltech.csa.kaas.binder.model.Criterion;
import ru.eltech.csa.kaas.binder.platform.config.BinderConfig;
import ru.eltech.csa.kaas.binder.platform.config.CriterionUsingStrategy;
import ru.eltech.csa.kaas.binder.query.CriterionImportance;
import ru.eltech.csa.kaas.binder.query.Query;

/**
 * Contains all data for engine reasoning.
 */
public class WorkingMemory {

    private KnowledgeBaseAdapter knowledgeBase;
    private Query query;
    private List<Criterion> allCriterions;

    public WorkingMemory(Query query, KnowledgeBaseAdapter knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
        this.query = query;
        allCriterions = orderCriterions(query, knowledgeBase);
    }

    public BinderConfig getConfig() {
        return query.getConfig();
    }

    public List<Criterion> getAllCriterions() {
        return allCriterions;
    }

    private List<Criterion> orderCriterions(Query query, KnowledgeBaseAdapter knowledgeBase) {
        CriterionUsingStrategy criterionUsingStrategy = query.getConfig().getCriterionUsingStrategy();
        List<Criterion> result = null;
        switch (criterionUsingStrategy) {
            case ONLY_QUERY_CRITERIONS:
                result = queryCriterionsList(query);
                break;
            case ALL_KNOWLEDGE_BASE_CRITERIONS:
                result = knowledgeBaseCriterionsList(query);
        }
        if (result == null || result.isEmpty()) {
            throw new IllegalArgumentException("Criterions for reasoning haven't been found. "
                    + "Set at least one criterion or change criterion using strategy.");
        }
        return result;
    }

    private List<Criterion> queryCriterionsList(Query query) {
        List<Criterion> result = new ArrayList<>();
        if (query.getCriterionImportances() != null) {
            for (CriterionImportance i : query.getCriterionImportances()) {
                Criterion crit = knowledgeBase.findCriterion(i.getCriterionId());
                if (i.getImportanceValue() != null) {
                    crit.setImportance(i.getImportanceValue());
                }
                result.add(crit);
            }
            Collections.sort(result, Collections.reverseOrder());
        }
        return result;
    }

    private List<Criterion> knowledgeBaseCriterionsList(Query query) {
        List<Criterion> result = new ArrayList<>();
        if (knowledgeBase.getCriterions() != null) {
            result = knowledgeBase.getCriterions();
            if (query.getCriterionImportances() != null) {
                for (CriterionImportance i : query.getCriterionImportances()) {
                    Criterion crit = knowledgeBase.findCriterion(i.getCriterionId());
                    if (i.getImportanceValue() != null) {
                        crit.setImportance(i.getImportanceValue());
                    }
                }
            }
            Collections.sort(result, Collections.reverseOrder());
        }
        return result;
    }
}
