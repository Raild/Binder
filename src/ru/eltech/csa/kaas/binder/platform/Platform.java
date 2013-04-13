package ru.eltech.csa.kaas.binder.platform;

import java.util.List;
import ru.eltech.csa.kaas.binder.query.Query;

public class Platform {

    private KnowledgeDAO knowledgeDAO;
    private ExpertEngine engine;

    public List<Proposal> propose(Query query) {
        return engine.propose(query, knowledgeDAO.select(query));
    }

    public void setKnowledgeDAO(KnowledgeDAO knowledgeDAO) {
        this.knowledgeDAO = knowledgeDAO;
    }

    public void setEngine(ExpertEngine engine) {
        this.engine = engine;
    }
}
