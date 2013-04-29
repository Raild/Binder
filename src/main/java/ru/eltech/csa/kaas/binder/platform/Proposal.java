package ru.eltech.csa.kaas.binder.platform;

import java.util.List;
import ru.eltech.csa.kaas.binder.model.ServiceImplementation;

public class Proposal {

    private List<ServiceImplementation> serviceImplementations;

    public List<ServiceImplementation> getServiceImplementations() {
        return serviceImplementations;
    }

    public void setServiceImplementations(List<ServiceImplementation> serviceImplementations) {
        this.serviceImplementations = serviceImplementations;
    }
}
