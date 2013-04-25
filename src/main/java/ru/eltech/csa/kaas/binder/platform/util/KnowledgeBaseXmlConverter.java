package ru.eltech.csa.kaas.binder.platform.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import ru.eltech.csa.kaas.binder.model.KnowledgeBase;

public class KnowledgeBaseXmlConverter {
    
    private static Marshaller marshaller;
    private static Unmarshaller unmarshaller;
    
    static {
        try {
            JAXBContext context = JAXBContext.newInstance(KnowledgeBase.class);
            marshaller = context.createMarshaller();
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException ex) {
            Logger.getLogger(KnowledgeBaseXmlConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void marshall(KnowledgeBase base, OutputStream os) throws JAXBException {        
        marshaller.marshal(base, os);
    }
    
    public static KnowledgeBase unmarshall(InputStream is) throws JAXBException {
        return (KnowledgeBase) unmarshaller.unmarshal(is);
    }
}
