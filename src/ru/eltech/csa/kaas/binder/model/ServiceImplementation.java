package ru.eltech.csa.kaas.binder.model;

public interface ServiceImplementation extends Knowledge, Estimated {

    ServiceType getServiceType();

    ServiceProvider getServiceProvider();
}