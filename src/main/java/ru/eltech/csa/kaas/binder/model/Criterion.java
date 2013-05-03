package ru.eltech.csa.kaas.binder.model;

public class Criterion extends AbstractKnowledge implements Comparable<Criterion> {

    private Double importance;

    public Double getImportance() {
        return importance;
    }

    public void setImportance(Double importance) {
        this.importance = importance;
    }
    
    @Override
    public int compareTo(Criterion crit) {
        return importance.compareTo(crit.importance);
    }
}
