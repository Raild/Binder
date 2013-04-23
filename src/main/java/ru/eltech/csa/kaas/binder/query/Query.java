package ru.eltech.csa.kaas.binder.query;

import java.util.List;
import ru.eltech.csa.kaas.binder.model.ServiceType;

/**
 * Describes user requirements for binding services.
 */
public interface Query {

    /**
     * @return Required service types.
     */
    List<ServiceType> getServiceTypes();

    /**
     * @return List of criterion importance values.
     */
    List<CriterionImportance> getCriterionImportances();

    /**
     * @return List of conditions needed to be applied.
     */
    List<Condition> getConditions();
}
