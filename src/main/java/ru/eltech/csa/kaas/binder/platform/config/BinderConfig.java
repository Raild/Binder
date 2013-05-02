package ru.eltech.csa.kaas.binder.platform.config;

import java.util.EnumMap;
import java.util.Map;

/**
 * Contains the Binder configuration parameters values.
 */
public class BinderConfig {

    private Map<ConfigParameter, Object> parameters = new EnumMap(ConfigParameter.class);

    public void setParameter(ConfigParameter parameter, String value) {
        parameters.put(parameter, value);
    }

    public Object getParameter(ConfigParameter parameter) {
        Object value = parameters.get(parameter);
        if (value == null) {
            value = parameter.getDefaultValue();
        }
        return value;
    }

    public Double getParameterAsDouble(ConfigParameter parameter) {
        return (Double) getParameter(parameter);
    }

    public Boolean getParameterAsBoolean(ConfigParameter parameter) {
        return (Boolean) getParameter(parameter);
    }

    public CriterionUsingStrategy getCriterionUsingStrategy() {
        return (CriterionUsingStrategy) getParameter(ConfigParameter.CRITERION_USING_STRATEGY);
    }
}
