package ru.eltech.csa.kaas.binder.model.impl;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import ru.eltech.csa.kaas.binder.model.Criterion;
import ru.eltech.csa.kaas.binder.model.Estimate;
import ru.eltech.csa.kaas.binder.model.KnowledgeBase;
import ru.eltech.csa.kaas.binder.model.ServiceImplementation;
import ru.eltech.csa.kaas.binder.model.ServiceProvider;
import ru.eltech.csa.kaas.binder.model.ServiceType;

/**
 * Container for knowledge entries.
 */
@XmlRootElement(name="knowledgeBase")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({CriterionImpl.class, EstimateImpl.class, ServiceTypeImpl.class,
        ServiceProviderImpl.class, ServiceImplementationImpl.class})
public class KnowledgeBaseImpl implements KnowledgeBase {

    @XmlElementRef(type=CriterionImpl.class)
    private List<Criterion> criterions;
    @XmlElementRef(type=EstimateImpl.class)
    private List<Estimate> estimates;
    @XmlElementRef(type=ServiceTypeImpl.class)
    private List<ServiceType> serviceTypes;
    @XmlElementRef(type=ServiceProviderImpl.class)
    private List<ServiceProvider> serviceProviders;
    @XmlElementRef(type=ServiceImplementationImpl.class)
    private List<ServiceImplementation> serviceImplementations;

    @Override
    public List<Criterion> getCriterions() {
        return criterions;
    }

    public void setCriterions(List<Criterion> criterions) {
        this.criterions = criterions;
    }

    @Override
    public List<Estimate> getEstimates() {
        return estimates;
    }

    public void setEstimates(List<Estimate> estimates) {
        this.estimates = estimates;
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
    public String toString() {
        return "KnowledgeBaseImpl{" + "criterions=" + criterions + ", estimates=" + estimates + ", serviceTypes=" + serviceTypes + ", serviceProviders=" + serviceProviders + ", serviceImplementations=" + serviceImplementations + '}';
    }
}
