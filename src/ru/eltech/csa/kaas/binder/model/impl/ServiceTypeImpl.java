package ru.eltech.csa.kaas.binder.model.impl;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import ru.eltech.csa.kaas.binder.model.ServiceType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceTypeImpl extends AbstractEstimatedKnowledge implements ServiceType {

    @XmlAnyElement
    private ServiceType parent;
    @XmlAnyElement
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
