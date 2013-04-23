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
import ru.eltech.csa.kaas.binder.model.impl.CriterionImpl;
import ru.eltech.csa.kaas.binder.model.impl.EstimateImpl;
import ru.eltech.csa.kaas.binder.model.impl.KnowledgeBaseImpl;
import ru.eltech.csa.kaas.binder.model.impl.ServiceTypeImpl;
import ru.eltech.csa.kaas.binder.platform.KnowledgeDAO;
import ru.eltech.csa.kaas.binder.query.Query;

public class KnowledgeDAOStub implements KnowledgeDAO {

    private KnowledgeBaseImpl knowledgeBase;

    public KnowledgeDAOStub() {
        knowledgeBase = new KnowledgeBaseImpl();
        stubInit();
    }

    @Override
    public KnowledgeBase select(Query query) {
        return knowledgeBase;
    }

    private void stubInit() {
        ServiceTypeImpl st = new ServiceTypeImpl();
        st.setId("0");
        st.setURI("http://example.com/0/");
        
        List<ServiceType> serviceTypes = Arrays.asList((ServiceType) st);
        
        CriterionImpl crit = new CriterionImpl();
        crit.setId("1");
        crit.setURI("http://example.com/1/");
        
        EstimateImpl es = new EstimateImpl();
        es.setCriterion(crit);
        es.setServiceTypes(serviceTypes);

        knowledgeBase.setServiceTypes(serviceTypes);
        knowledgeBase.setCriterions(Arrays.asList((Criterion) crit));
        knowledgeBase.setEstimates(Arrays.asList((Estimate) es));
    }

    public static void main(String[] args) {
        {
            KnowledgeBaseImpl kb = (KnowledgeBaseImpl) new KnowledgeDAOStub().select(null);
            try {
                JAXBContext context = JAXBContext.newInstance(KnowledgeBaseImpl.class);
                File of = new File("book.xml");
                
                OutputStream os = new FileOutputStream(of);             
                Marshaller m = context.createMarshaller();
                m.marshal(kb, os);
                os.close();
                
                StreamSource is = new StreamSource(of);
                Unmarshaller um = context.createUnmarshaller();
                KnowledgeBaseImpl kb2 = um.unmarshal(is, KnowledgeBaseImpl.class).getValue();
                System.out.println(kb2);
                
                
                
            } catch (IOException ex) {
                Logger.getLogger(KnowledgeDAOStub.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JAXBException ex) {
                Logger.getLogger(KnowledgeDAOStub.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
