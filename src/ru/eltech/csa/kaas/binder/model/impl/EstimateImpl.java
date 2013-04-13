package ru.eltech.csa.kaas.binder.model.impl;

import java.util.List;
import ru.eltech.csa.kaas.binder.model.Criterion;
import ru.eltech.csa.kaas.binder.model.Estimate;
import ru.eltech.csa.kaas.binder.model.ServiceImplementation;
import ru.eltech.csa.kaas.binder.model.ServiceProvider;
import ru.eltech.csa.kaas.binder.model.ServiceType;

public class EstimateImpl extends AbstractKnowledge implements Estimate {

    private Criterion criterion;
    private List<ServiceType> serviceTypes;
    private List<ServiceProvider> serviceProviders;
    private List<ServiceImplementation> serviceImplementations;
    private int value;

    @Override
    public Criterion getCriterion() {
        return criterion;
    }

    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
    }

    @Override
    public List<ServiceType> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<ServiceType> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    @Override
    public List<ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }

    public void setServiceProviders(List<ServiceProvider> serviceProviders) {
        this.serviceProviders = serviceProviders;
    }

    @Override
    public List<ServiceImplementation> getServiceImplementations() {
        return serviceImplementations;
    }

    public void setServiceImplementations(List<ServiceImplementation> serviceImplementations) {
        this.serviceImplementations = serviceImplementations;
    }

    @Override
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
