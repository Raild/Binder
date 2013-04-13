package ru.eltech.csa.kaas.binder.model;

import java.util.List;

public interface KnowledgeBase {

    List<Criterion> getCriterions();

    List<Estimate> getEstimates();

    List<ServiceType> getServiceTypes();

    List<ServiceProvider> getServiceProviders();

    List<ServiceImplementation> getServiceImplementations();
}
