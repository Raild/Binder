package ru.eltech.csa.kaas.binder.query;

public class CriterionImportance {

    private String criterionId;
    private double importanceValue;

    public String getCriterion() {
        return criterionId;
    }

    public void setCriterion(String criterion) {
        this.criterionId = criterion;
    }

    public double getImportanceValue() {
        return importanceValue;
    }

    public void setImportanceValue(double importanceValue) {
        this.importanceValue = importanceValue;
    }
}
