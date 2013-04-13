package ru.eltech.csa.kaas.binder.query.impl;

import ru.eltech.csa.kaas.binder.model.Criterion;
import ru.eltech.csa.kaas.binder.query.CriterionImportance;

public class CriterionImportanceImpl implements CriterionImportance {

    private Criterion criterion;
    private int importanceValue;

    @Override
    public Criterion getCriterion() {
        return criterion;
    }

    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
    }

    @Override
    public int getImportanceValue() {
        return importanceValue;
    }

    public void setImportanceValue(int importanceValue) {
        this.importanceValue = importanceValue;
    }
}
