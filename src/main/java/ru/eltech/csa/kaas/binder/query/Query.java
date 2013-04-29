package ru.eltech.csa.kaas.binder.query;

import java.util.List;
import ru.eltech.csa.kaas.binder.model.ServiceType;

public class Query{

    private List<ServiceType> serviceTypes;
    private List<CriterionImportance> criterionImportances;
    private List<Condition> conditions;

    public List<ServiceType> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<ServiceType> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public List<CriterionImportance> getCriterionImportances() {
        return criterionImportances;
    }

    public void setCriterionImportances(List<CriterionImportance> criterionImportances) {
        this.criterionImportances = criterionImportances;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
}
