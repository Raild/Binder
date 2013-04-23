package ru.eltech.csa.kaas.binder.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
public class Estimate extends AbstractKnowledge {

    @XmlIDREF
    private Criterion criterion;
    @XmlIDREF
    private List<ServiceType> serviceTypes;
    @XmlIDREF
    private List<ServiceProvider> serviceProviders;
    @XmlIDREF
    private List<ServiceImplementation> serviceImplementations;
    private int value;

    public Criterion getCriterion() {
        return criterion;
    }

    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
    }

    public List<ServiceType> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<ServiceType> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public List<ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }

    public void setServiceProviders(List<ServiceProvider> serviceProviders) {
        this.serviceProviders = serviceProviders;
    }

    public List<ServiceImplementation> getServiceImplementations() {
        return serviceImplementations;
    }

    public void setServiceImplementations(List<ServiceImplementation> serviceImplementations) {
        this.serviceImplementations = serviceImplementations;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
