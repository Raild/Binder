package ru.eltech.csa.kaas.binder.query.impl;

import ru.eltech.csa.kaas.binder.model.Criterion;
import ru.eltech.csa.kaas.binder.query.Condition;
import ru.eltech.csa.kaas.binder.query.Operator;

public class ConditionImpl implements Condition {

    private Criterion criterion;
    private Operator operator;
    private int value;

    @Override
    public Criterion getCriterion() {
        return criterion;
    }

    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
    }

    @Override
    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    @Override
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
