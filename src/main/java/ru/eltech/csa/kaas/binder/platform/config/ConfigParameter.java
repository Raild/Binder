package ru.eltech.csa.kaas.binder.platform.config;

/**
 * Configuration parameters for the Binder. Used or ignored depending on the
 * engine implementation. Contain default value;
 */
public enum ConfigParameter {

    /**
     * Boolean value. If true, Binder proposes implementations only from single
     * provider.
     */
    ONLY_ONE_PROVIDER(Boolean.FALSE),
    CRITERION_USING_STRATEGY(CriterionUsingStrategy.ONLY_QUERY_CRITERIONS);
    private Object defaultValue;

    private ConfigParameter(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }
}
