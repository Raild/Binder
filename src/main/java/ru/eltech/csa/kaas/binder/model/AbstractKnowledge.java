package ru.eltech.csa.kaas.binder.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractKnowledge {

    @XmlID
    private String id;
    private String URI;

    public String getId() {
        return id;
    }

    public String getURI() {
        return URI;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }
    
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
