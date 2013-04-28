package ru.eltech.csa.kaas.binder.platform.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import ru.eltech.csa.kaas.binder.model.AbstractKnowledge;
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
        if (duplicateValuesCheck(base)) {
            throw new IllegalArgumentException("Knowledge base must contain entries with unique ids only.");
        }
        marshaller.marshal(base, os);
    }

    public static KnowledgeBase unmarshall(InputStream is) throws JAXBException {
        return (KnowledgeBase) unmarshaller.unmarshal(is);
    }

    private static boolean duplicateValuesCheck(KnowledgeBase base) {
        Set<String> idSet = new HashSet<>();

        return checkIdList(base.getServiceProviders(), idSet) ||
        checkIdList(base.getServiceTypes(), idSet) ||
        checkIdList(base.getServiceImplementations(), idSet) ||
        checkIdList(base.getCriterions(), idSet) ||
        checkIdList(base.getEstimates(), idSet);
    }

    private static <T extends AbstractKnowledge> boolean checkIdList(List<? extends T> entryList, Set<String> idSet) {
        boolean result = false;
        if (entryList != null) {
            for (T item : entryList) {
                if (!idSet.add(item.getId())) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
