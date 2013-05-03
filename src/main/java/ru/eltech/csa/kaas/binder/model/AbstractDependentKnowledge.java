package ru.eltech.csa.kaas.binder.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbstractDependentKnowledge extends AbstractKnowledge {

    @XmlElementWrapper
    @XmlElement( name="dependency" )
    @XmlIDREF
    private List<ServiceType> dependencies;

    public List<ServiceType> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<ServiceType> dependencies) {
        this.dependencies = dependencies;
    }

}
