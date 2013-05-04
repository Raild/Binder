package ru.eltech.csa.kaas.binder.engine.memory.proposal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ru.eltech.csa.kaas.binder.model.ServiceImplementation;
import ru.eltech.csa.kaas.binder.model.ServiceType;

/**
 * Internal class for reasoning. Contains service type and its implementation.
 */
class ProposalEntry {

    private ServiceType type;
    private ServiceImplementation implementation;

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public ServiceImplementation getImplementation() {
        return implementation;
    }

    public void setImplementation(ServiceImplementation implementation) {
        if (type != null && !type.equals(implementation.getServiceType())) {
            throw new IllegalArgumentException("Argument do not implement current entry type.");
        }
        this.implementation = implementation;
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
