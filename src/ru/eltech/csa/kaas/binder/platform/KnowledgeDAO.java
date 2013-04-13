package ru.eltech.csa.kaas.binder.platform;

import ru.eltech.csa.kaas.binder.model.KnowledgeBase;
import ru.eltech.csa.kaas.binder.query.Query;

public interface KnowledgeDAO {

    KnowledgeBase select(Query query);
}
