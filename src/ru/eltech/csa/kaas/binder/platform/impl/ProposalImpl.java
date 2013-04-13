package ru.eltech.csa.kaas.binder.platform.impl;

import java.util.List;
import ru.eltech.csa.kaas.binder.model.ServiceImplementation;
import ru.eltech.csa.kaas.binder.platform.Proposal;

public class ProposalImpl implements Proposal {

    private List<ServiceImplementation> serviceImplementations;

    @Override
    public List<ServiceImplementation> getServiceImplementations() {
        return serviceImplementations;
    }

    public void setServiceImplementations(List<ServiceImplementation> serviceImplementations) {
        this.serviceImplementations = serviceImplementations;
    }
}
