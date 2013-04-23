package ru.eltech.csa.kaas.binder.platform;

import ru.eltech.csa.kaas.binder.model.KnowledgeBase;
import ru.eltech.csa.kaas.binder.query.Query;

/**
 * Data access object interface for knowledge selecting.
 */
public interface KnowledgeDAO {

    /**
     * @param query - query object with user requirements
     * @return Knowledge base selection with data that relates to required
     * services and can be useful in expert inference.
     */
    KnowledgeBase select(Query query);
}
