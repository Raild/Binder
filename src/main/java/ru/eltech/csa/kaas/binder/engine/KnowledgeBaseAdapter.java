package ru.eltech.csa.kaas.binder.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public KnowledgeBaseAdapter(KnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
        init(criterions, knowledgeBase.getCriterions());
        init(estimates, knowledgeBase.getEstimates());
        init(serviceTypes, knowledgeBase.getServiceTypes());
        init(serviceProviders, knowledgeBase.getServiceProviders());
        init(serviceImplementations, knowledgeBase.getServiceImplementations());
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

    private <T extends AbstractKnowledge> void init(Map<String, ? super T> map, List<? extends T> list) {
        for (T item : list) {
            map.put(item.getId(), item);
        }
    }
}
