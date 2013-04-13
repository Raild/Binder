package ru.eltech.csa.kaas.binder.platform;

import java.util.List;
import ru.eltech.csa.kaas.binder.model.ServiceImplementation;

public interface Proposal {

    List<ServiceImplementation> getServiceImplementations();
}
