package ru.eltech.csa.kaas.binder.engine.memory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.eltech.csa.kaas.binder.model.Criterion;
import ru.eltech.csa.kaas.binder.model.Estimate;
import ru.eltech.csa.kaas.binder.model.KnowledgeBase;
import ru.eltech.csa.kaas.binder.model.ServiceImplementation;
import ru.eltech.csa.kaas.binder.model.ServiceProvider;
import ru.eltech.csa.kaas.binder.model.ServiceType;

public class KnowledgeBaseAdapterTest {

    private static final String SERVICE_PROVIDER_ID = "p1";
    private static final String SERVICE_TYPE_ID1 = "t1";
    private static final String SERVICE_TYPE_ID2 = "t2";
    private static final String SERVICE_TYPE_ID3 = "t3";
    private static final String SERVICE_IMPLEMENTATION_ID = "i1";
    private static final String SERVICE_IMPLEMENTATION_ID2 = "i2";
    private static final String SERVICE_IMPLEMENTATION_ID3 = "i3";
    private static final String SERVICE_IMPLEMENTATION_ID4 = "i4";
    private static final String CRITERION_ID = "4";
    private static final String ESTIMATE_ID = "5";
    private KnowledgeBaseAdapter adapter;
    private KnowledgeBase knowledgeBase;
    private List<ServiceProvider> serviceProviderList;
    private List<ServiceType> serviceTypeList;
    private List<ServiceImplementation> serviceImplementationList;
    private List<Criterion> criterionList;
    private List<Estimate> estimateList;
    private ServiceProvider serviceProvider;
    private ServiceType serviceType1;
    private ServiceType serviceType2;
    private ServiceType serviceType3;
    private ServiceImplementation serviceImplementation1;
    private ServiceImplementation serviceImplementation2;
    private ServiceImplementation serviceImplementation3;
    private ServiceImplementation serviceImplementation4;
    private Criterion criterion;
    private Estimate estimate;

    @Before
    public void setUp() {
        serviceProvider = new ServiceProvider();
        serviceProvider.setId(SERVICE_PROVIDER_ID);
        serviceProviderList = Arrays.asList(serviceProvider);

        serviceType1 = new ServiceType();
        serviceType1.setId(SERVICE_TYPE_ID1);
        serviceType2 = new ServiceType();
        serviceType2.setId(SERVICE_TYPE_ID2);
        serviceType2.setParent(serviceType1);
        serviceType3 = new ServiceType();
        serviceType3.setId(SERVICE_TYPE_ID3);
        serviceType3.setParent(serviceType2);
        serviceTypeList = Arrays.asList(serviceType1, serviceType2, serviceType3);

        serviceImplementation1 = new ServiceImplementation();
        serviceImplementation1.setId(SERVICE_IMPLEMENTATION_ID);
        serviceImplementation1.setServiceProvider(serviceProvider);
        serviceImplementation1.setServiceType(serviceType1);
        serviceImplementation2 = new ServiceImplementation();
        serviceImplementation2.setId(SERVICE_IMPLEMENTATION_ID2);
        serviceImplementation2.setServiceType(serviceType1);
        serviceImplementation3 = new ServiceImplementation();
        serviceImplementation3.setId(SERVICE_IMPLEMENTATION_ID3);
        serviceImplementation3.setServiceProvider(serviceProvider);
        serviceImplementation4 = new ServiceImplementation();
        serviceImplementation4.setId(SERVICE_IMPLEMENTATION_ID4);
        serviceImplementation4.setServiceType(serviceType3);
        serviceImplementationList = Arrays.asList(serviceImplementation1,
                serviceImplementation2, serviceImplementation3, serviceImplementation4);

        criterion = new Criterion();
        criterion.setId(CRITERION_ID);
        criterionList = Arrays.asList(criterion);

        estimate = new Estimate();
        estimate.setId(ESTIMATE_ID);
        estimate.setCriterion(criterion);
        estimate.setServiceImplementations(Arrays.asList(serviceImplementation3));
        estimate.setServiceProviders(serviceProviderList);
        estimateList = Arrays.asList(estimate);

        knowledgeBase = new KnowledgeBase();
        knowledgeBase.setServiceProviders(serviceProviderList);
        knowledgeBase.setServiceTypes(serviceTypeList);
        knowledgeBase.setServiceImplementations(serviceImplementationList);
        knowledgeBase.setCriterions(criterionList);
        knowledgeBase.setEstimates(estimateList);
    }

    @Test
    public void adapterInitTest() {
        adapter = new KnowledgeBaseAdapter(knowledgeBase);
        Assert.assertEquals(serviceProviderList, adapter.getServiceProviders());
        Assert.assertEquals(serviceTypeList, adapter.getServiceTypes());
        Assert.assertEquals(serviceImplementationList, adapter.getServiceImplementations());
        Assert.assertEquals(criterionList, adapter.getCriterions());
        Assert.assertEquals(estimateList, adapter.getEstimates());

        Assert.assertEquals(serviceProvider, adapter.findServiceProvider(SERVICE_PROVIDER_ID));
        Assert.assertEquals(serviceType1, adapter.findServiceType(SERVICE_TYPE_ID1));
        Assert.assertEquals(serviceImplementation1, adapter.findServiceImplementation(SERVICE_IMPLEMENTATION_ID));
        Assert.assertEquals(criterion, adapter.findCriterion(CRITERION_ID));
        Assert.assertEquals(estimate, adapter.findEstimate(ESTIMATE_ID));
    }

    @Test(expected = IllegalArgumentException.class)
    public void duplicateIdTest() {
        Estimate estimate1 = new Estimate();
        estimate1.setId(ESTIMATE_ID);
        Estimate estimate2 = new Estimate();
        estimate2.setId(ESTIMATE_ID);
        List<Estimate> estimates = Arrays.asList(estimate1, estimate2);
        KnowledgeBase base = new KnowledgeBase();
        base.setEstimates(estimates);

        adapter = new KnowledgeBaseAdapter(base);
    }

    @Test
    public void emptyBaseTest() {
        KnowledgeBase base = new KnowledgeBase();

        adapter = new KnowledgeBaseAdapter(base);
    }

    @Test
    public void getTypeImplementationsTest() {
        adapter = new KnowledgeBaseAdapter(knowledgeBase);
        HashSet<ServiceImplementation> expectedSet = new HashSet<>();
        expectedSet.add(serviceImplementation1);
        expectedSet.add(serviceImplementation2);
        expectedSet.add(serviceImplementation4);

        // second check - for cached value
        for (int i = 0; i < 2; i++) {
            Set<ServiceImplementation> typeImplementations = adapter.findTypeImplementations(serviceType1);
            Assert.assertEquals(expectedSet, typeImplementations);
        }
    }

    @Test
    public void getProviderImplementationsTest() {
        adapter = new KnowledgeBaseAdapter(knowledgeBase);

        // second check - for cached value
        for (int i = 0; i < 2; i++) {
            List<ServiceImplementation> providerImplementations = adapter.findProviderImplementations(serviceProvider);
            Assert.assertEquals(Arrays.asList(serviceImplementation1, serviceImplementation3), providerImplementations);
        }
    }

    @Test
    public void findImplementationCriterionEstimatesTest() {
        adapter = new KnowledgeBaseAdapter(knowledgeBase);
        Assert.assertEquals(estimateList, adapter.findImplementationCriterionEstimates(serviceImplementation1, criterion));
        Assert.assertEquals(Collections.emptyList(), adapter.findImplementationCriterionEstimates(serviceImplementation2, criterion));
        Assert.assertEquals(estimateList, adapter.findImplementationCriterionEstimates(serviceImplementation3, criterion));
    }

    @Test
    public void findImplementationEstimatesTest() {
        adapter = new KnowledgeBaseAdapter(knowledgeBase);
        Assert.assertEquals(new HashSet<>(estimateList), adapter.findImplementationEstimates(serviceImplementation1));
    }

    @Test
    public void findCriterionEstimatesTest() {
        Criterion crit = new Criterion();
        crit.setId("1");
        Estimate es1 = new Estimate();
        es1.setId("2");
        es1.setCriterion(crit);
        Estimate es2 = new Estimate();
        es2.setId("3");
        es2.setCriterion(crit);
        Estimate es3 = new Estimate();
        es3.setId("4");
        es3.setCriterion(criterion);

        KnowledgeBase base = new KnowledgeBase();
        base.setCriterions(Arrays.asList(crit));
        base.setEstimates(Arrays.asList(es1, es2, es3));

        adapter = new KnowledgeBaseAdapter(base);
        Assert.assertEquals(Arrays.asList(es1, es2), adapter.findCriterionEstimates(crit));
    }

    @Test(expected = IllegalArgumentException.class)
    public void unexistCriterionIdSearch() {
        adapter = new KnowledgeBaseAdapter(new KnowledgeBase());
        adapter.findCriterion("unexist criterion");
    }

    @Test(expected = IllegalArgumentException.class)
    public void unexistEstimateIdSearch() {
        adapter = new KnowledgeBaseAdapter(new KnowledgeBase());
        adapter.findEstimate("unexist criterion");
    }

    @Test(expected = IllegalArgumentException.class)
    public void unexistImplementationIdSearch() {
        adapter = new KnowledgeBaseAdapter(new KnowledgeBase());
        adapter.findServiceImplementation("unexist criterion");
    }

    @Test(expected = IllegalArgumentException.class)
    public void unexistTypeIdSearch() {
        adapter = new KnowledgeBaseAdapter(new KnowledgeBase());
        adapter.findServiceType("unexist criterion");
    }

    @Test(expected = IllegalArgumentException.class)
    public void unexistProviderIdSearch() {
        adapter = new KnowledgeBaseAdapter(new KnowledgeBase());
        adapter.findServiceProvider("unexist criterion");
    }
}
