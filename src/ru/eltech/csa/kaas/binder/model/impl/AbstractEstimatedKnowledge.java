package ru.eltech.csa.kaas.binder.model.impl;

import java.util.List;
import ru.eltech.csa.kaas.binder.model.Estimate;
import ru.eltech.csa.kaas.binder.model.Estimated;

public abstract class AbstractEstimatedKnowledge extends AbstractKnowledge implements Estimated {

    private List<Estimate> estimates;

    @Override
    public List<Estimate> getEstimates() {
        return estimates;
    }

    public void setEstimates(List<Estimate> estimates) {
        this.estimates = estimates;
    }
}
