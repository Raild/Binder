package ru.eltech.csa.kaas.binder.query;

import ru.eltech.csa.kaas.binder.model.Criterion;

public class Condition {

    private Criterion criterion;
    private Operator operator;
    private int value;


    public Criterion getCriterion() {
        return criterion;
    }

    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
    }


    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
