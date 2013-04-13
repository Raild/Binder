package ru.eltech.csa.kaas.binder.model.impl;

import ru.eltech.csa.kaas.binder.model.Knowledge;


public abstract class AbstractKnowledge implements Knowledge {
    
    private int id;
    private String URI;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getURI() {
        return URI;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

}
