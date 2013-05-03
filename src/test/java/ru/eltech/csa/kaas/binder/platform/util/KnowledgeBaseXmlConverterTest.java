package ru.eltech.csa.kaas.binder.platform.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.util.Arrays.asList;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.eltech.csa.kaas.binder.model.Criterion;
import ru.eltech.csa.kaas.binder.model.Estimate;
import ru.eltech.csa.kaas.binder.model.KnowledgeBase;
import ru.eltech.csa.kaas.binder.model.ServiceImplementation;
import ru.eltech.csa.kaas.binder.model.ServiceProvider;
import ru.eltech.csa.kaas.binder.model.ServiceType;

public class KnowledgeBaseXmlConverterTest {

    private static final String ESTIMATE_ID = "5";
    private static final String TEST_FILE_NAME = "KnowledgeBaseXmlConverterTest_tmp.xml";
    private IMocksControl control;
    private OutputStream outputStreamMock;
    private File file;
    private KnowledgeBase knowledgeBase;
    private OutputStream outputStream;
    private InputStream inputStream;

    @Before
    public void setUp() {
        control = EasyMock.createNiceControl();
        outputStreamMock = control.createMock(OutputStream.class);
        file = new File(TEST_FILE_NAME);
        knowledgeBase = initKnowledgeBase();
    }

    @After
    public void tearDown() throws IOException {
        file.delete();
        if (inputStream != null) {
            inputStream.close();
        }
        if (outputStream != null) {
            outputStream.close();
        }
    }

    @Test
    public void convertionTest() throws FileNotFoundException, JAXBException {
        outputStream = new FileOutputStream(file);
        KnowledgeBaseXmlConverter.marshall(knowledgeBase, outputStream);
        inputStream = new FileInputStream(file);
        Assert.assertEquals(knowledgeBase, KnowledgeBaseXmlConverter.unmarshall(inputStream));
    }

    @Test(expected = IllegalArgumentException.class)
    public void duplicateIdTest() throws JAXBException {
        Estimate estimate1 = new Estimate();
        estimate1.setId(ESTIMATE_ID);
        Estimate estimate2 = new Estimate();
        estimate2.setId(ESTIMATE_ID);
        List<Estimate> estimates = asList(estimate1, estimate2);
        KnowledgeBase base = new KnowledgeBase();
        base.setEstimates(estimates);

        KnowledgeBaseXmlConverter.marshall(base, null);
    }

    @Test
    public void emptyBaseTest() throws JAXBException {
        KnowledgeBase base = new KnowledgeBase();

        control.replay();
        KnowledgeBaseXmlConverter.marshall(base, outputStreamMock);

        control.verify();
    }

    private KnowledgeBase initKnowledgeBase() {
        ServiceProvider provider = new ServiceProvider();
        provider.setId("sp1");
        provider.setURI("http://example.com/provider");

        ServiceType serviceType1 = new ServiceType();
        serviceType1.setId("st1");
        serviceType1.setURI("http://example.com/serviceType1");
        ServiceType serviceType2 = new ServiceType();
        serviceType2.setId("st2");
        serviceType2.setURI("http://example.com/serviceType2");

        serviceType2.setDependencies(asList(serviceType1));
        serviceType1.setParent(serviceType2);

        ServiceImplementation impl1 = new ServiceImplementation();
        impl1.setId("si1");
        impl1.setServiceProvider(provider);
        impl1.setServiceType(serviceType1);
        impl1.setURI("http://example.com/impl1");
        impl1.setDependencies(asList(serviceType1));
        ServiceImplementation impl2 = new ServiceImplementation();
        impl2.setId("si2");
        impl2.setServiceProvider(provider);
        impl2.setServiceType(serviceType2);
        impl2.setURI("http://example.com/impl2");

        Criterion criterion = new Criterion();
        criterion.setId("cr1");
        criterion.setURI("http://example.com/criterion");
        criterion.setImportance(0.78);

        Estimate es1 = new Estimate();
        es1.setId("es1");
        es1.setCriterion(criterion);
        es1.setServiceProviders(asList(provider));
        es1.setServiceTypes(asList(serviceType1, serviceType2));
        es1.setServiceImplementations(asList(impl1, impl2));
        es1.setValue(0.9);
        es1.setURI("http://example.com/estimate");

        KnowledgeBase base = new KnowledgeBase();
        base.setServiceProviders(asList(provider));
        base.setServiceTypes(asList(serviceType1, serviceType2));
        base.setServiceImplementations(asList(impl1, impl2));
        base.setCriterions(asList(criterion));
        base.setEstimates(asList(es1));
        return base;
    }
}
