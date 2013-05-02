package ru.eltech.csa.kaas.binder.query;

import java.util.List;
import ru.eltech.csa.kaas.binder.platform.config.BinderConfig;

public class Query {

    private List<String> serviceTypesId;
    private List<CriterionImportance> criterionImportances;
    private List<Condition> conditions;
    private BinderConfig config;

    public List<String> getServiceTypesId() {
        return serviceTypesId;
    }

    public void setServiceTypesId(List<String> serviceTypesId) {
        this.serviceTypesId = serviceTypesId;
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

    public BinderConfig getConfig() {
        return config;
    }

    public void setConfig(BinderConfig config) {
        this.config = config;
    }
}
