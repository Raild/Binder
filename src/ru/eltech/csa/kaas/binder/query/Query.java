package ru.eltech.csa.kaas.binder.query;

import java.util.List;
import ru.eltech.csa.kaas.binder.model.ServiceType;

/**
 * Describes user requirements for binding services.
 */
public interface Query {

    List<ServiceType> getServiceTypes();

    List<CriterionImportance> getCriterionImportances();

    List<Condition> getConditions();
}
