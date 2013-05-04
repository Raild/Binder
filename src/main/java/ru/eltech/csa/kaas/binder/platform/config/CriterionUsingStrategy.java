package ru.eltech.csa.kaas.binder.platform.config;

/**
 * Permitted values of CRITERION_USING_STRATEGY config parameter.
 */
public enum CriterionUsingStrategy {

    /**
     * Only criterions from the user query is used.
     */
    ONLY_QUERY_CRITERIONS,
    /**
     * All the knowledge base criterions is used.
     */
    ALL_KNOWLEDGE_BASE_CRITERIONS;
}
