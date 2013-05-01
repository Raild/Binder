package ru.eltech.csa.kaas.binder.query.checker;

/**
 * Provides ability to check condition. Instance must be get from
 * {@link ConditionCheckerFactory} class.
 */
public interface ConditionChecker {

    /**
     * Compares given value with value from the condition, which was used for
     * {@link ConditionChecker} instance creation.
     * 
     * <i>(checkedValue) Operator (checkingValue)</i>
     * 
     * e.g. 5 > 3 == true
     *
     * @param checkedValue the checked value
     * @param checkingValue the checking value
     * @return true if the values satisfy the condition
     */
    boolean checkCondition(double checkedValue, double checkingValue);
}
