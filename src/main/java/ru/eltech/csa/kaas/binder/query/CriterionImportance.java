package ru.eltech.csa.kaas.binder.query;

public class CriterionImportance {

    private String criterionId;
    private Double importanceValue;

    public String getCriterion() {
        return criterionId;
    }

    public void setCriterion(String criterion) {
        this.criterionId = criterion;
    }

    public Double getImportanceValue() {
        return importanceValue;
    }

    public void setImportanceValue(Double importanceValue) {
        this.importanceValue = importanceValue;
    }
}
