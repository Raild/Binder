package ru.eltech.csa.kaas.binder.platform.stub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import ru.eltech.csa.kaas.binder.model.Criterion;
import ru.eltech.csa.kaas.binder.model.Estimate;
import ru.eltech.csa.kaas.binder.model.KnowledgeBase;
import ru.eltech.csa.kaas.binder.model.ServiceType;
import ru.eltech.csa.kaas.binder.platform.KnowledgeDAO;
import ru.eltech.csa.kaas.binder.query.Query;

public class KnowledgeDAOStub implements KnowledgeDAO {

    private KnowledgeBase knowledgeBase;

    public KnowledgeDAOStub() {
        knowledgeBase = new KnowledgeBase();
        stubInit();
    }

    @Override
    public KnowledgeBase select(Query query) {
        return knowledgeBase;
    }

    private void stubInit() {
        ServiceType st = new ServiceType();
        st.setId("0");
        st.setURI("http://example.com/0/");
        
        List<ServiceType> serviceTypes = Arrays.asList((ServiceType) st);
        
        Criterion crit = new Criterion();
        crit.setId("1");
        crit.setURI("http://example.com/1/");
        
        Estimate es = new Estimate();
        es.setId("2");
        es.setCriterion(crit);
        es.setServiceTypes(serviceTypes);

        knowledgeBase.setServiceTypes(serviceTypes);
        knowledgeBase.setCriterions(Arrays.asList((Criterion) crit));
        knowledgeBase.setEstimates(Arrays.asList((Estimate) es));
    }

    public static void main(String[] args) {
        {
            KnowledgeBase kb = (KnowledgeBase) new KnowledgeDAOStub().select(null);
            try {
                JAXBContext context = JAXBContext.newInstance(KnowledgeBase.class);
                File of = new File("book.xml");
                
                OutputStream os = new FileOutputStream(of);             
                Marshaller m = context.createMarshaller();
                m.marshal(kb, os);
                os.close();
                
                StreamSource is = new StreamSource(of);
                Unmarshaller um = context.createUnmarshaller();
                KnowledgeBase kb2 = um.unmarshal(is, KnowledgeBase.class).getValue();
                System.out.println(kb2);
                
                
                
            } catch (IOException ex) {
                Logger.getLogger(KnowledgeDAOStub.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JAXBException ex) {
                Logger.getLogger(KnowledgeDAOStub.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
