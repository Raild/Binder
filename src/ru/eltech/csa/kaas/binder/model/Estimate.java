package ru.eltech.csa.kaas.binder.model;

import java.util.List;

public interface Estimate extends Knowledge {

    Criterion getCriterion();

    List<ServiceType> getServiceTypes();

    List<ServiceProvider> getServiceProviders();

    List<ServiceImplementation> getServiceImplementations();

    int getValue();
}