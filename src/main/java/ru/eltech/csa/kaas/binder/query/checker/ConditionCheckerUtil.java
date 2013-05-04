package ru.eltech.csa.kaas.binder.query.checker;

import java.util.EnumMap;
import java.util.Map;
import ru.eltech.csa.kaas.binder.query.Condition;
import ru.eltech.csa.kaas.binder.query.Operator;

/**
 * Util for {@link Condition} checking.
 */
public final class ConditionCheckerUtil {

    private static final Map<Operator, ConditionChecker> checkers = new EnumMap(Operator.class);

    static {
        checkers.put(Operator.MORE, new MoreConditionChecker());
        checkers.put(Operator.MORE_OR_EQUAL, new MoreOrEqualConditionChecker());
    }

    /**
     * Compares given value with value from the condition using the condition
     * operator.
     *
     * @param condition
     * @param checkedValue
     * @return true if the values satisfy the condition
     */
    public static boolean check(double checkedValue, Condition condition) {
        return checkers.get(condition.getOperator()).checkCondition(checkedValue, condition.getValue());
    }
}
