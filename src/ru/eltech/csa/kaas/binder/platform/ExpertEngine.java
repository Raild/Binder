package ru.eltech.csa.kaas.binder.platform;

import java.util.List;
import ru.eltech.csa.kaas.binder.model.KnowledgeBase;
import ru.eltech.csa.kaas.binder.query.Query;

public interface ExpertEngine {

    List<Proposal> propose(Query query, KnowledgeBase knowledgeBase);
}
