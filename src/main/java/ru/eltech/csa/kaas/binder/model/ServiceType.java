package ru.eltech.csa.kaas.binder.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceType extends AbstractDependentKnowledge {

    @XmlIDREF
    private ServiceType parent;

    public ServiceType getParent() {
        return parent;
    }

    public void setParent(ServiceType parent) {
        this.parent = parent;
    }
}
