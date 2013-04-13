package ru.eltech.csa.kaas.binder.model;

import java.util.List;

public interface ServiceType extends Knowledge, Estimated {

    ServiceType getParent();
    List<ServiceType> getDependencies();
}