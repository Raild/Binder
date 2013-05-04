package ru.eltech.csa.kaas.binder.query;

public class CriterionImportance {

    private String criterionId;
    private Double importanceValue;

    public String getCriterionId() {
        return criterionId;
    }

    public void setCriterionId(String criterionId) {
        this.criterionId = criterionId;
    }

    public Double getImportanceValue() {
        return importanceValue;
    }

    public void setImportanceValue(Double importanceValue) {
        this.importanceValue = importanceValue;
    }
}
