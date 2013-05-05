package ru.eltech.csa.kaas.binder.engine.memory.proposal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import ru.eltech.csa.kaas.binder.model.Estimate;
import ru.eltech.csa.kaas.binder.model.ServiceImplementation;
import ru.eltech.csa.kaas.binder.model.ServiceProvider;
import ru.eltech.csa.kaas.binder.model.ServiceType;

public class ProposalInWorkTest {

    private static final String TYPE_ID1 = "t1";
    private static final String TYPE_ID2 = "t2";
    private static final String PROVIDER_ID1 = "p2";
    private ProposalInWork proposal;
    private ServiceType type1;
    private ServiceType type2;
    private ServiceImplementation impl1;
    private ServiceImplementation impl2;
    private ServiceProvider provider1;
    private Estimate estimate;

    @Before
    public void setUp() {
        type1 = new ServiceType();
        type1.setId(TYPE_ID1);
        type2 = new ServiceType();
        type2.setId(TYPE_ID2);

        provider1 = new ServiceProvider();
        provider1.setId(PROVIDER_ID1);

        impl1 = new ServiceImplementation();
        impl1.setServiceType(type1);
        impl1.setServiceProvider(provider1);
        impl2 = new ServiceImplementation();
        impl2.setServiceType(type2);
        impl2.setServiceProvider(provider1);

        estimate = new Estimate();

        Set<ServiceType> typeSet = new HashSet<>(Arrays.asList(type1, type2));

        proposal = new ProposalInWork(typeSet);
    }

    @Test
    public void getEntriesTest() {
        Map<ServiceType, ProposalEntry> entries = proposal.getEntries();
        doGetEntriesTest(entries, type1);
        doGetEntriesTest(entries, type2);
    }

    @Test
    public void addImplementationTest() {
        doAddImplementationTest(impl1);
        doAddImplementationTest(impl2);
        assertFalse(proposal.hasEmptySlots());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addImplementationTestWithException() {
        proposal.addImplementation(impl1);
        impl2.setServiceType(type1);
        proposal.addImplementation(impl2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullProviderTest() {
        ServiceImplementation i = new ServiceImplementation();
        i.setServiceType(type1);
        proposal.addImplementation(i);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullTypeTest() {
        ServiceImplementation i = new ServiceImplementation();
        i.setServiceProvider(provider1);
        proposal.addImplementation(i);
    }

    @Test
    public void requiredProvidersTest() {
        proposal.addRequirement(provider1);
        assertFalse(proposal.getRequiredProviders().isEmpty());
        proposal.addImplementation(impl1);
        assertTrue(proposal.getRequiredProviders().isEmpty());
        proposal.addRequirement(provider1);
        assertTrue(proposal.getRequiredProviders().isEmpty());
    }

    @Test
    public void logFiredEstimateTest() {
        assertTrue(proposal.getFiredEstimates().isEmpty());
        proposal.logFiredEstimate(estimate);
        assertFalse(proposal.getFiredEstimates().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void logFiredEstimateTestWithException() {
        proposal.logFiredEstimate(estimate);
        proposal.logFiredEstimate(estimate);
    }

    private void doGetEntriesTest(Map<ServiceType, ProposalEntry> entries, ServiceType type) {
        assertTrue(entries.containsKey(type));
        assertEquals(type, entries.get(type).getType());
        assertNull(entries.get(type).getImplementation());
    }

    private void doAddImplementationTest(ServiceImplementation impl) {
        assertTrue(proposal.hasEmptySlots());
        proposal.addImplementation(impl);
    }
}
