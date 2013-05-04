package ru.eltech.csa.kaas.binder.engine.memory;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.eltech.csa.kaas.binder.model.Criterion;
import ru.eltech.csa.kaas.binder.model.KnowledgeBase;
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
    private WorkingMemory memory;
    private Query query;
    private KnowledgeBaseAdapter adapter;
    private Criterion crit1;
    private Criterion crit2;
    private Criterion crit3;
    private Criterion crit4;

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

        KnowledgeBase base = new KnowledgeBase();
        base.setCriterions(Arrays.asList(crit1, crit2, crit3, crit4));

        query = new Query();
        query.setCriterionImportances(Arrays.asList(ci1, ci2, ci3));

        adapter = new KnowledgeBaseAdapter(base);
    }

    @Test
    public void getAllCriterionsTestQuery() {
        memory = new WorkingMemory(query, adapter);
        assertCriterionsOrder(Arrays.asList(crit1, crit3, crit2), memory.getAllCriterions());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllCriterionsTestWithoutCriterions() {
        memory = new WorkingMemory(new Query(), adapter);
    }

    @Test
    public void getAllCriterionsTestFull() {
        query.getConfig().setParameter(ConfigParameter.CRITERION_USING_STRATEGY,
                CriterionUsingStrategy.ALL_KNOWLEDGE_BASE_CRITERIONS);
        memory = new WorkingMemory(query, adapter);
        assertCriterionsOrder(Arrays.asList(crit1, crit3, crit4, crit2), memory.getAllCriterions());
    }

    private void assertCriterionsOrder(List<Criterion> expected, List<Criterion> actual) {
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }
}
