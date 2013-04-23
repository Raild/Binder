package ru.eltech.csa.kaas.binder.query;

import ru.eltech.csa.kaas.binder.model.Criterion;

public interface Condition {

    Criterion getCriterion();

    Operator getOperator();

    int getValue();
}
