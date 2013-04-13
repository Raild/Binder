package ru.eltech.csa.kaas.binder.model.impl;

import java.util.List;
import ru.eltech.csa.kaas.binder.model.ServiceType;


public class ServiceTypeImpl extends AbstractEstimatedKnowledge implements ServiceType {

    private ServiceType parent;
    private List<ServiceType> dependencies;

    @Override
    public ServiceType getParent() {
        return parent;
    }

    public void setParent(ServiceType parent) {
        this.parent = parent;
    }

    @Override
    public List<ServiceType> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<ServiceType> dependencies) {
        this.dependencies = dependencies;
    }
    
    



}
