package ru.eltech.csa.kaas.binder.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Container for knowledge entries.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(value=Criterion.class)
public class KnowledgeBase {

    private List<Criterion> criterions;
    private List<Estimate> estimates;
    private List<ServiceType> serviceTypes;
    private List<ServiceProvider> serviceProviders;
    private List<ServiceImplementation> serviceImplementations;

    public List<Criterion> getCriterions() {
        return criterions;
    }

    public void setCriterions(List<Criterion> criterions) {
        this.criterions = criterions;
    }

    public List<Estimate> getEstimates() {
        return estimates;
    }

    public void setEstimates(List<Estimate> estimates) {
        this.estimates = estimates;
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

    @Override
    public String toString() {
        return "KnowledgeBaseImpl{" + "criterions=" + criterions + ", estimates=" + estimates + ", serviceTypes=" + serviceTypes + ", serviceProviders=" + serviceProviders + ", serviceImplementations=" + serviceImplementations + '}';
    }
}
