
package ru.eltech.csa.kaas.binder.engine.memory;

import java.util.Arrays;
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
    private ServiceImplementation serviceImplementation;
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

        serviceImplementation = new ServiceImplementation();
        serviceImplementation.setId(SERVICE_IMPLEMENTATION_ID);
        serviceImplementationList = Arrays.asList(serviceImplementation);

        criterion = new Criterion();
        criterion.setId(CRITERION_ID);
        criterionList = Arrays.asList(criterion);

        estimate = new Estimate();
        estimate.setId(ESTIMATE_ID);
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
        Assert.assertEquals(serviceImplementation, adapter.findServiceImplementation(SERVICE_IMPLEMENTATION_ID));
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
}
