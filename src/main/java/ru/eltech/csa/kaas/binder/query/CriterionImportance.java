package ru.eltech.csa.kaas.binder.query;

import ru.eltech.csa.kaas.binder.model.Criterion;

public class CriterionImportance {

    private Criterion criterion;
    private double importanceValue;

    public Criterion getCriterion() {
        return criterion;
    }

    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
    }

    public double getImportanceValue() {
        return importanceValue;
    }

    public void setImportanceValue(double importanceValue) {
        this.importanceValue = importanceValue;
    }
}
