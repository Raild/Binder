package ru.eltech.csa.kaas.binder.query.impl;

import java.util.List;
import ru.eltech.csa.kaas.binder.model.ServiceType;
import ru.eltech.csa.kaas.binder.query.Condition;
import ru.eltech.csa.kaas.binder.query.CriterionImportance;
import ru.eltech.csa.kaas.binder.query.Query;

public class QueryImpl implements Query {

    private List<ServiceType> serviceTypes;
    private List<CriterionImportance> criterionImportances;
    private List<Condition> conditions;

    @Override
    public List<ServiceType> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<ServiceType> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    @Override
    public List<CriterionImportance> getCriterionImportances() {
        return criterionImportances;
    }

    public void setCriterionImportances(List<CriterionImportance> criterionImportances) {
        this.criterionImportances = criterionImportances;
    }

    @Override
    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
}
