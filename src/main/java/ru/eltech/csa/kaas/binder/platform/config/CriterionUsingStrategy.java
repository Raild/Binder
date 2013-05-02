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
     * All the knowledge base criterions is used. Sorted by importance.
     */
    FULL_SORTING_CRITERIONS,
    /**
     * All the knowledge base criterions is used. At first, criterions from
     * query. Then criterions from conditions. After that other knowledge base
     * criterions. Within the group criterions are sorted by importance.
     */
    GROUP_SORTING_CRITERIONS;
}
