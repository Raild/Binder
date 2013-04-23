package ru.eltech.csa.kaas.binder.model.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import ru.eltech.csa.kaas.binder.model.Knowledge;


@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractKnowledge implements Knowledge {
    
    @XmlID
    private String id;
    private String URI;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getURI() {
        return URI;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

}
