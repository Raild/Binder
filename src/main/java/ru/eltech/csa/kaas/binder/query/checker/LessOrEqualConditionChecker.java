package ru.eltech.csa.kaas.binder.query.checker;

public class LessOrEqualConditionChecker implements ConditionChecker {

    @Override
    public boolean checkCondition(double checkedValue, double checkingValue) {
        return checkedValue <= checkingValue;
    }
}
