package ru.eltech.csa.kaas.binder.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbstractDependentKnowledge extends AbstractEstimatedKnowledge {

    @XmlIDREF
    private List<ServiceType> typeDependencies;
    @XmlIDREF
    private List<ServiceImplementation> implementationDependencies;

    public List<ServiceType> getTypeDependencies() {
        return typeDependencies;
    }

    public void setTypeDependencies(List<ServiceType> typeDependencies) {
        this.typeDependencies = typeDependencies;
    }

    public List<ServiceImplementation> getImplementationDependencies() {
        return implementationDependencies;
    }

    public void setImplementationDependencies(List<ServiceImplementation> implementationDependencies) {
        this.implementationDependencies = implementationDependencies;
    }
}
