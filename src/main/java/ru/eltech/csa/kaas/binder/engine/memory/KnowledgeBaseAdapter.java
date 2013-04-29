package ru.eltech.csa.kaas.binder.engine.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ru.eltech.csa.kaas.binder.model.AbstractEstimatedKnowledge;
import ru.eltech.csa.kaas.binder.model.AbstractKnowledge;
import ru.eltech.csa.kaas.binder.model.Criterion;
import ru.eltech.csa.kaas.binder.model.Estimate;
import ru.eltech.csa.kaas.binder.model.KnowledgeBase;
import ru.eltech.csa.kaas.binder.model.ServiceImplementation;
import ru.eltech.csa.kaas.binder.model.ServiceProvider;
import ru.eltech.csa.kaas.binder.model.ServiceType;

public class KnowledgeBaseAdapter {

    private KnowledgeBase knowledgeBase;
    private Map<String, Criterion> criterions = new HashMap<>();
    private Map<String, Estimate> estimates = new HashMap<>();
    private Map<String, ServiceType> serviceTypes = new HashMap<>();
    private Map<String, ServiceProvider> serviceProviders = new HashMap<>();
    private Map<String, ServiceImplementation> serviceImplementations = new HashMap<>();
    private Map<ServiceType, List<ServiceImplementation>> typeImplementationCache = new HashMap<>();
    private Map<ServiceProvider, List<ServiceImplementation>> providerImplementationCache = new HashMap<>();
    private Map<ServiceImplementation, Map<Criterion, List<Estimate>>> implementationEstimates = new HashMap<>();

    public KnowledgeBaseAdapter(KnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
        initIdMap(criterions, knowledgeBase.getCriterions());
        initIdMap(serviceTypes, knowledgeBase.getServiceTypes());
        initIdMap(serviceProviders, knowledgeBase.getServiceProviders());
        initIdMap(serviceImplementations, knowledgeBase.getServiceImplementations());

        initEstimates(knowledgeBase.getEstimates());
    }

    public List<ServiceImplementation> getTypeImplementations(ServiceType serviceType, boolean useCache) {
        return doGetImplementations(typeImplementationCache, serviceType, SERVICE_TYPE, useCache);
    }

    public List<ServiceImplementation> getTypeImplementations(ServiceType serviceType) {
        return getTypeImplementations(serviceType, true);
    }

    public List<ServiceImplementation> getProviderImplementations(ServiceProvider serviceProvider, boolean useCache) {
        return doGetImplementations(providerImplementationCache, serviceProvider, SERVICE_PROVIDER, useCache);
    }

    public List<ServiceImplementation> getProviderImplementations(ServiceProvider serviceProvider) {
        return getProviderImplementations(serviceProvider, true);
    }

    public Criterion findCriterion(String id) {
        return criterions.get(id);
    }

    public Estimate findEstimate(String id) {
        return estimates.get(id);
    }

    public ServiceType findServiceType(String id) {
        return serviceTypes.get(id);
    }

    public ServiceProvider findServiceProvider(String id) {
        return serviceProviders.get(id);
    }

    public ServiceImplementation findServiceImplementation(String id) {
        return serviceImplementations.get(id);
    }

    public List<Criterion> getCriterions() {
        return knowledgeBase.getCriterions();
    }

    public List<Estimate> getEstimates() {
        return knowledgeBase.getEstimates();
    }

    public List<ServiceType> getServiceTypes() {
        return knowledgeBase.getServiceTypes();
    }

    public List<ServiceProvider> getServiceProviders() {
        return knowledgeBase.getServiceProviders();
    }

    public List<ServiceImplementation> getServiceImplementations() {
        return knowledgeBase.getServiceImplementations();
    }

    private <T extends AbstractKnowledge> void initIdMap(Map<String, ? super T> map, List<? extends T> list) {
        if (list != null) {
            for (T item : list) {
                if (map.containsKey(item.getId())) {
                    throw new IllegalArgumentException("Knowledge base contains item with duplicate id: " + item.getId());
                }
                map.put(item.getId(), item);
            }
        }
    }

    /**
     * Copy of initIdMap method with additional initialization for each estimate
     *
     * @param list
     */
    private void initEstimates(List<Estimate> list) {
        if (list != null) {
            for (Estimate estimate : list) {
                if (estimates.containsKey(estimate.getId())) {
                    throw new IllegalArgumentException("Knowledge base contains item with duplicate id: " + estimate.getId());
                }
                estimates.put(estimate.getId(), estimate);
                doInitEstimates(estimate);
            }
        }
    }

    private void doInitEstimates(Estimate estimate) {
        Set<ServiceImplementation> implSet = new HashSet<>();
        List<ServiceImplementation> implementations = estimate.getServiceImplementations();
        if (implementations != null) {
            implSet.addAll(implementations);
        }

        List<ServiceProvider> providers = estimate.getServiceProviders();
        if (providers != null) {
            for (ServiceProvider provider : providers) {
                implSet.addAll(getProviderImplementations(provider));
            }
        }

        List<ServiceType> types = estimate.getServiceTypes();
        if (types != null) {
            for (ServiceType type : types) {
                implSet.addAll(getTypeImplementations(type));
            }
        }

        for (ServiceImplementation impl : implSet) {
            putImplementationEstimate(impl, estimate.getCriterion(), estimate);
        }
    }

    private void putImplementationEstimate(ServiceImplementation serviceImplementation, Criterion criterion, Estimate estimate) {
        Map<Criterion, List<Estimate>> innerMap = implementationEstimates.get(serviceImplementation);
        if (innerMap == null) {
            innerMap = new HashMap<>();
            implementationEstimates.put(serviceImplementation, innerMap);
        }
        List<Estimate> innerList = innerMap.get(criterion);
        if (innerList == null) {
            innerList = new ArrayList<>();
            innerMap.put(criterion, innerList);
        }
        innerList.add(estimate);
    }

    private <T extends AbstractEstimatedKnowledge> List<ServiceImplementation> doGetImplementations(
            Map<T, List<ServiceImplementation>> destination, T target,
            ComparedField comparedField, boolean useCache) {
        List<ServiceImplementation> result;
        if (useCache) {
            result = destination.get(target);
            if (result != null) {
                return result;
            }
        }
        result = new ArrayList<>();
        for (ServiceImplementation impl : knowledgeBase.getServiceImplementations()) {
            if (comparedField.compare(impl, target)) {
                result.add(impl);
            }
        }
        destination.put(target, result);
        return result;
    }

    private interface ComparedField {

        boolean compare(ServiceImplementation impl, AbstractEstimatedKnowledge target);
    }
    private static final ComparedField SERVICE_TYPE = new ComparedField() {
        @Override
        public boolean compare(ServiceImplementation impl, AbstractEstimatedKnowledge target) {
            return target.equals(impl.getServiceType());
        }
    };
    private static final ComparedField SERVICE_PROVIDER = new ComparedField() {
        @Override
        public boolean compare(ServiceImplementation impl, AbstractEstimatedKnowledge target) {
            return target.equals(impl.getServiceProvider());
        }
    };
}
