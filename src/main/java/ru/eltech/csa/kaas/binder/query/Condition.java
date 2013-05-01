package ru.eltech.csa.kaas.binder.query;

public class Condition {

    private String criterionId;
    private Operator operator;
    private double value;

    public String getCriterion() {
        return criterionId;
    }

    public void setCriterion(String criterion) {
        this.criterionId = criterion;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
