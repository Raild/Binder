package ru.eltech.csa.kaas.binder.query.checker;

public class MoreOrEqualConditionChecker implements ConditionChecker {

    @Override
    public boolean checkCondition(double checkedValue, double checkingValue) {
        return checkedValue >= checkingValue;
    }
}
