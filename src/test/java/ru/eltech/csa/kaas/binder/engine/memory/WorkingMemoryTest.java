package ru.eltech.csa.kaas.binder.engine.memory;

import java.util.Arrays;
import java.util.HashSet;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import ru.eltech.csa.kaas.binder.model.Criterion;
import ru.eltech.csa.kaas.binder.model.KnowledgeBase;
import ru.eltech.csa.kaas.binder.model.ServiceType;
import ru.eltech.csa.kaas.binder.platform.config.ConfigParameter;
import ru.eltech.csa.kaas.binder.platform.config.CriterionUsingStrategy;
import ru.eltech.csa.kaas.binder.query.CriterionImportance;
import ru.eltech.csa.kaas.binder.query.Query;

public class WorkingMemoryTest {

    private static final String CRITERION_ID1 = "cr1";
    private static final String CRITERION_ID2 = "cr2";
    private static final String CRITERION_ID3 = "cr3";
    private static final String CRITERION_ID4 = "cr4";
    private static final Double IMPORTANCE1 = 0.9;
    private static final Double IMPORTANCE2 = 0.6;
    private static final Double IMPORTANCE3_1 = 0.53;
    private static final Double IMPORTANCE3_2 = 0.83;
    private static final Double IMPORTANCE4 = 0.7;
    private static final String TYPE_ID1 = "t1";
    private WorkingMemory memory;
    private Query query;
    private KnowledgeBaseAdapter adapter;
    private Criterion crit1;
    private Criterion crit2;
    private Criterion crit3;
    private Criterion crit4;
    private ServiceType type1;

    @Before
    public void setUp() {
        crit1 = new Criterion();
        crit1.setId(CRITERION_ID1);
        crit1.setImportance(IMPORTANCE1);
        CriterionImportance ci1 = new CriterionImportance();
        ci1.setCriterionId(CRITERION_ID1);

        crit2 = new Criterion();
        crit2.setId(CRITERION_ID2);
        crit2.setImportance(IMPORTANCE2);
        CriterionImportance ci2 = new CriterionImportance();
        ci2.setCriterionId(CRITERION_ID2);

        crit3 = new Criterion();
        crit3.setId(CRITERION_ID3);
        crit3.setImportance(IMPORTANCE3_1);
        CriterionImportance ci3 = new CriterionImportance();
        ci3.setCriterionId(CRITERION_ID3);
        ci3.setImportanceValue(IMPORTANCE3_2);

        crit4 = new Criterion();
        crit4.setId(CRITERION_ID4);
        crit4.setImportance(IMPORTANCE4);

        type1 = new ServiceType();
        type1.setId(TYPE_ID1);

        KnowledgeBase base = new KnowledgeBase();
        base.setCriterions(Arrays.asList(crit1, crit2, crit3, crit4));
        base.setServiceTypes(Arrays.asList(type1));

        query = new Query();
        query.setCriterionImportances(Arrays.asList(ci1, ci2, ci3));
        query.setServiceTypesId(Arrays.asList(TYPE_ID1));

        adapter = new KnowledgeBaseAdapter(base);
    }

    @Test
    public void getCriterionsTestQuery() {
        memory = new WorkingMemory(query, adapter);
        assertEquals(new HashSet<>(Arrays.asList(crit1, crit2, crit3)), memory.getCriterions());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCriterionsTestQueryWithoutCriterions() {
        memory = new WorkingMemory(new Query(), adapter);
    }

    @Test
    public void getCriterionsTestBase() {
        query.getConfig().setParameter(ConfigParameter.CRITERION_USING_STRATEGY,
                CriterionUsingStrategy.ALL_KNOWLEDGE_BASE_CRITERIONS);
        memory = new WorkingMemory(query, adapter);
        assertEquals(new HashSet<>(Arrays.asList(crit1, crit2, crit3, crit4)), memory.getCriterions());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCriterionsTestBaseWithoutCriterions() {
        Query q = new Query();
        q.getConfig().setParameter(ConfigParameter.CRITERION_USING_STRATEGY,
                CriterionUsingStrategy.ALL_KNOWLEDGE_BASE_CRITERIONS);
        memory = new WorkingMemory(q, new KnowledgeBaseAdapter(new KnowledgeBase()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void duplicateServiceTypes() {
        Query q = new Query();
        q.setServiceTypesId(Arrays.asList(TYPE_ID1, TYPE_ID1));
        memory = new WorkingMemory(q, new KnowledgeBaseAdapter(new KnowledgeBase()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyServiceTypes() {
        Query q = new Query();
        memory = new WorkingMemory(q, new KnowledgeBaseAdapter(new KnowledgeBase()));
    }
}
