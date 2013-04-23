package ru.eltech.csa.kaas.binder.model.impl;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import ru.eltech.csa.kaas.binder.model.Estimate;
import ru.eltech.csa.kaas.binder.model.Estimated;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractEstimatedKnowledge extends AbstractKnowledge implements Estimated {

    @XmlAnyElement
    private List<Estimate> estimates;

    @Override
    public List<Estimate> getEstimates() {
        return estimates;
    }

    public void setEstimates(List<Estimate> estimates) {
        this.estimates = estimates;
    }
}
