package ru.eltech.csa.kaas.binder.model.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import ru.eltech.csa.kaas.binder.model.ServiceImplementation;
import ru.eltech.csa.kaas.binder.model.ServiceProvider;
import ru.eltech.csa.kaas.binder.model.ServiceType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceImplementationImpl extends AbstractEstimatedKnowledge implements ServiceImplementation {

    @XmlAnyElement
    private ServiceType serviceType;
    @XmlAnyElement
    private ServiceProvider serviceProvider;

    @Override
    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    @Override
    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}
