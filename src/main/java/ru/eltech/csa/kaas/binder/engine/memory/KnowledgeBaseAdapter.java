package ru.eltech.csa.kaas.binder.engine.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ru.eltech.csa.kaas.binder.model.AbstractKnowledge;
import ru.eltech.csa.kaas.binder.model.Criterion;
import ru.eltech.csa.kaas.binder.model.Estimate;
import ru.eltech.csa.kaas.binder.model.KnowledgeBase;
import ru.eltech.csa.kaas.binder.model.ServiceImplementation;
import ru.eltech.csa.kaas.binder.model.ServiceProvider;
import ru.eltech.csa.kaas.binder.model.ServiceType;

/**
 * Provides easy access to knowledge via additional methods.
 */
public class KnowledgeBaseAdapter {

    private KnowledgeBase knowledgeBase;
    private Map<String, Criterion> criterions = new HashMap<>();
    private Map<String, Estimate> estimates = new HashMap<>();
    private Map<String, ServiceType> serviceTypes = new HashMap<>();
    private Map<String, ServiceProvider> serviceProviders = new HashMap<>();
    private Map<String, ServiceImplementation> serviceImplementations = new HashMap<>();
    private Map<ServiceType, Set<ServiceImplementation>> typeImplementations = new HashMap<>();
    private Map<ServiceProvider, List<ServiceImplementation>> providerImplementationCache = new HashMap<>();
    private Map<ServiceImplementation, Map<Criterion, List<Estimate>>> implementationEstimates = new HashMap<>();
    private Map<Criterion, List<Estimate>> criterionEstimates = new HashMap<>();
    private Map<ServiceType, Set<ServiceType>> typeChilds = new HashMap<>();

    public KnowledgeBaseAdapter(KnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
        initIdMap(criterions, knowledgeBase.getCriterions(), EMPTY_INIT_OPERATIONS);
        initIdMap(serviceTypes, knowledgeBase.getServiceTypes(), TYPE_INIT_OPERATIONS);
        initIdMap(serviceProviders, knowledgeBase.getServiceProviders(), EMPTY_INIT_OPERATIONS);
        initIdMap(serviceImplementations, knowledgeBase.getServiceImplementations(), IMPL_INIT_OPERATIONS);
        initIdMap(estimates, knowledgeBase.getEstimates(), ESTIMATE_INIT_OPERATIONS);
    }

    /**
     * Finds all implementations of the given service type in knowledge base.
     *
     * @param serviceProvider the service type
     * @return see description
     */
    public Set<ServiceImplementation> findTypeImplementations(ServiceType serviceType) {
        Set<ServiceImplementation> set = typeImplementations.get(serviceType);
        if (set != null) {
            return set;
        }
        return Collections.emptySet();
    }

    /**
     * Finds all implementations of the given service provider in knowledge
     * base.
     *
     * @param serviceProvider the service provider
     * @param useCache if false - the knowledge base is processed for each
     * method call
     * @return see description
     */
    public List<ServiceImplementation> findProviderImplementations(ServiceProvider serviceProvider, boolean useCache) {
        List<ServiceImplementation> result;
        if (useCache) {
            result = providerImplementationCache.get(serviceProvider);
            if (result != null) {
                return result;
            }
        }
        result = new ArrayList<>();
        for (ServiceImplementation impl : knowledgeBase.getServiceImplementations()) {
            if (serviceProvider.equals(impl.getServiceProvider())) {
                result.add(impl);
            }
        }
        providerImplementationCache.put(serviceProvider, result);
        return result;
    }

    /**
     * Finds all implementations of the given service provider in knowledge
     * base. Uses cache.
     *
     * @param serviceProvider the service provider
     * @return see description
     */
    public List<ServiceImplementation> findProviderImplementations(ServiceProvider serviceProvider) {
        return findProviderImplementations(serviceProvider, true);
    }

    /**
     * Finds all estimates of the given service implementation by the given
     * criterion in knowledge base.
     *
     * @param impl the service implementation
     * @param crit the criterion
     * @return see description
     */
    public List<Estimate> findImplementationEstimates(ServiceImplementation impl, Criterion crit) {
        Map<Criterion, List<Estimate>> innerMap = implementationEstimates.get(impl);
        if (innerMap != null) {
            List<Estimate> list = innerMap.get(crit);
            if (list != null) {
                return list;
            }
        }
        return Collections.emptyList();
    }

    public List<Estimate> findCriterionEstimates(Criterion criterion) {
        List<Estimate> list = criterionEstimates.get(criterion);
        if (list != null) {
            return list;
        }
        return Collections.emptyList();
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

    private <T extends AbstractKnowledge> void initIdMap(Map<String, ? super T> map, List<? extends T> list, PostInitOperations post) {
        if (list != null) {
            for (T item : list) {
                if (map.containsKey(item.getId())) {
                    throw new IllegalArgumentException("Knowledge base contains item with duplicate id: " + item.getId());
                }
                map.put(item.getId(), item);
                post.doOperations(item);
            }
        }
    }

    private void initImplementationEstimates(Estimate estimate) {
        Set<ServiceImplementation> implSet = new HashSet<>();
        List<ServiceImplementation> implementations = estimate.getServiceImplementations();
        if (implementations != null) {
            implSet.addAll(implementations);
        }

        List<ServiceProvider> providers = estimate.getServiceProviders();
        if (providers != null) {
            for (ServiceProvider provider : providers) {
                implSet.addAll(findProviderImplementations(provider));
            }
        }

        List<ServiceType> types = estimate.getServiceTypes();
        if (types != null) {
            for (ServiceType type : types) {
                implSet.addAll(findTypeImplementations(type));
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
        putCriterionEstimate(innerMap, criterion, estimate);

    }

    private void putCriterionEstimate(Map<Criterion, List<Estimate>> map, Criterion criterion, Estimate estimate) {
        List<Estimate> innerList = map.get(criterion);
        if (innerList == null) {
            innerList = new ArrayList<>();
            map.put(criterion, innerList);
        }
        innerList.add(estimate);
    }

    private void initChilds(ServiceType type) {
        ServiceType parent = type.getParent();
        if (parent != null) {
            Set<ServiceType> set = typeChilds.get(parent);
            if (set == null) {
                set = new HashSet<>();
                typeChilds.put(parent, set);
            }
            set.add(type);
        }
    }

    private void initTypeImplementations(ServiceImplementation impl) {
        ServiceType type = impl.getServiceType();
        if (type != null) {
            doInitTypeImplementations(impl, type);
        }
    }

    private void doInitTypeImplementations(ServiceImplementation impl, ServiceType type) {
        Set<ServiceImplementation> set = typeImplementations.get(type);
        if (set == null) {
            set = new HashSet<>();
            typeImplementations.put(type, set);
        }
        set.add(impl);
        ServiceType parent = type.getParent();
        if (parent != null) {
            doInitTypeImplementations(impl, parent);
        }
    }

    private interface PostInitOperations {

        void doOperations(AbstractKnowledge item);
    }
    private static final PostInitOperations EMPTY_INIT_OPERATIONS = new PostInitOperations() {
        @Override
        public void doOperations(AbstractKnowledge item) {
        }
    };
    private final PostInitOperations ESTIMATE_INIT_OPERATIONS = new PostInitOperations() {
        @Override
        public void doOperations(AbstractKnowledge item) {
            Estimate estimate = (Estimate) item;
            putCriterionEstimate(criterionEstimates, estimate.getCriterion(), estimate);
            initImplementationEstimates(estimate);
        }
    };
    private final PostInitOperations TYPE_INIT_OPERATIONS = new PostInitOperations() {
        @Override
        public void doOperations(AbstractKnowledge item) {
            ServiceType type = (ServiceType) item;
            initChilds(type);
        }
    };
    private final PostInitOperations IMPL_INIT_OPERATIONS = new PostInitOperations() {
        @Override
        public void doOperations(AbstractKnowledge item) {
            ServiceImplementation impl = (ServiceImplementation) item;
            initTypeImplementations(impl);
        }
    };
}
