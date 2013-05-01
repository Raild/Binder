package ru.eltech.csa.kaas.binder.query;

import java.util.List;

public class Query {

    private List<String> serviceTypesId;
    private List<CriterionImportance> criterionImportances;
    private List<Condition> conditions;

    public List<String> getServiceTypes() {
        return serviceTypesId;
    }

    public void setServiceTypes(List<String> serviceTypes) {
        this.serviceTypesId = serviceTypes;
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
