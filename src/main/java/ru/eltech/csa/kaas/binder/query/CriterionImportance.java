package ru.eltech.csa.kaas.binder.query;

import ru.eltech.csa.kaas.binder.model.Criterion;

public interface CriterionImportance {

    Criterion getCriterion();

    int getImportanceValue();
}
