package ru.eltech.csa.kaas.binder.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
public class Estimate extends AbstractKnowledge {

    @XmlIDREF
    private Criterion criterion;
    
    @XmlElementWrapper
    @XmlElement( name="serviceType" )
    @XmlIDREF
    private List<ServiceType> serviceTypes;
    
    @XmlElementWrapper
    @XmlElement( name="serviceProvider" )
    @XmlIDREF
    private List<ServiceProvider> serviceProviders;
    
    @XmlElementWrapper
    @XmlElement( name="serviceImplementation" )
    @XmlIDREF
    private List<ServiceImplementation> serviceImplementations;
    private double value;

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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
