package ru.eltech.csa.kaas.binder.platform.stub;

import ru.eltech.csa.kaas.binder.model.KnowledgeBase;
import ru.eltech.csa.kaas.binder.model.impl.KnowledgeBaseImpl;
import ru.eltech.csa.kaas.binder.platform.KnowledgeDAO;
import ru.eltech.csa.kaas.binder.query.Query;


public class KnowledgeDAOStub implements KnowledgeDAO{
    
    private KnowledgeBase knowledgeBase;
    
    public KnowledgeDAOStub(){
        knowledgeBase = new KnowledgeBaseImpl();
    }

    @Override
    public KnowledgeBase select(Query query) {
        return knowledgeBase;
    }

}
