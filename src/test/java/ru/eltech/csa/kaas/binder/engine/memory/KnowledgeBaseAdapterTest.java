package ru.eltech.csa.kaas.binder.engine.memory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
    
    private static final String SERVICE_PROVIDER_ID = "1";
    private static final String SERVICE_TYPE_ID = "2";
    private static final String SERVICE_IMPLEMENTATION_ID = "1";
    private static final String SERVICE_IMPLEMENTATION_ID2 = "2";
    private static final String SERVICE_IMPLEMENTATION_ID3 = "3";
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
    private ServiceType serviceType;
    private ServiceImplementation serviceImplementation1;
    private ServiceImplementation serviceImplementation2;
    private ServiceImplementation serviceImplementation3;
    private Criterion criterion;
    private Estimate estimate;
    
    @Before
    public void setUp() {
        serviceProvider = new ServiceProvider();
        serviceProvider.setId(SERVICE_PROVIDER_ID);
        serviceProviderList = Arrays.asList(serviceProvider);
        
        serviceType = new ServiceType();
        serviceType.setId(SERVICE_TYPE_ID);
        serviceTypeList = Arrays.asList(serviceType);
        
        serviceImplementation1 = new ServiceImplementation();
        serviceImplementation1.setId(SERVICE_IMPLEMENTATION_ID);
        serviceImplementation1.setServiceProvider(serviceProvider);
        serviceImplementation1.setServiceType(serviceType);
        serviceImplementation2 = new ServiceImplementation();
        serviceImplementation2.setId(SERVICE_IMPLEMENTATION_ID2);
        serviceImplementation2.setServiceType(serviceType);
        serviceImplementation3 = new ServiceImplementation();
        serviceImplementation3.setId(SERVICE_IMPLEMENTATION_ID3);
        serviceImplementation3.setServiceProvider(serviceProvider);
        serviceImplementationList = Arrays.asList(serviceImplementation1, serviceImplementation2, serviceImplementation3);
        
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
        Assert.assertEquals(serviceType, adapter.findServiceType(SERVICE_TYPE_ID));
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

        // second check - for cached value
        for (int i = 0; i < 2; i++) {
            List<ServiceImplementation> typeImplementations = adapter.findTypeImplementations(serviceType);
            Assert.assertEquals(Arrays.asList(serviceImplementation1, serviceImplementation2), typeImplementations);
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
    public void findImplementationEstimatesTest() {
        adapter = new KnowledgeBaseAdapter(knowledgeBase);
        Assert.assertEquals(estimateList, adapter.findImplementationEstimates(serviceImplementation1, criterion));
        Assert.assertEquals(Collections.emptyList(), adapter.findImplementationEstimates(serviceImplementation2, criterion));
        Assert.assertEquals(estimateList, adapter.findImplementationEstimates(serviceImplementation3, criterion));
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
}
