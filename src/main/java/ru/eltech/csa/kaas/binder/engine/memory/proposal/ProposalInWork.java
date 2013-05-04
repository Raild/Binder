package ru.eltech.csa.kaas.binder.engine.memory.proposal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ru.eltech.csa.kaas.binder.model.Estimate;
import ru.eltech.csa.kaas.binder.model.ServiceImplementation;
import ru.eltech.csa.kaas.binder.model.ServiceProvider;
import ru.eltech.csa.kaas.binder.model.ServiceType;

/**
 * Contains working data for reasoning. Can become an answer.
 */
public class ProposalInWork {

    private Map<ServiceType, ProposalEntry> entries = new HashMap<>();
    private Map<ServiceProvider, Integer> usedProviders = new HashMap<>();
    private Set<ServiceProvider> requiredProviders = new HashSet<>();
    private Set<Estimate> firedEstimates = new HashSet<>();
    private int filledEntriesCounter = 0;

    public ProposalInWork(Set<ServiceType> requiredTypes) {
        for (ServiceType type : requiredTypes) {
            addRequirement(type);
        }
    }

    public ProposalInWork(ProposalInWork piw) {
        entries.putAll(piw.entries);
        usedProviders.putAll(piw.usedProviders);
        requiredProviders.addAll(piw.requiredProviders);
        firedEstimates.addAll(piw.firedEstimates);
        filledEntriesCounter = piw.filledEntriesCounter;
    }

    public final void addRequirement(ServiceType type) {
        if (!entries.containsKey(type)) {
            ProposalEntry pe = new ProposalEntry();
            pe.setType(type);
            entries.put(type, pe);
        }
    }

    public final void addRequirement(ServiceProvider provider) {
        if (!usedProviders.containsKey(provider)) {
            requiredProviders.add(provider);
        }
    }

    public void addImplementation(ServiceImplementation implementation) {
        ProposalEntry entry = entries.get(implementation.getServiceType());
        if (!entry.isImplementationEmpty()) {
            throw new IllegalArgumentException("Slot is not empty. Cannot add impl: " + implementation);
        }
        entry.setImplementation(implementation);
        addProviderUsing(implementation.getServiceProvider());
        filledEntriesCounter++;
    }
    
    public boolean hasEmptySlots() {
        return filledEntriesCounter < entries.size();
    }

    private void addProviderUsing(ServiceProvider provider) {
        Integer count = usedProviders.get(provider);
        if (count == null) {
            usedProviders.put(provider, 1);
            if (requiredProviders.contains(provider)) {
                requiredProviders.remove(provider);
            }
        } else {
            usedProviders.put(provider, ++count);
        }
    }

    public Map<ServiceType, ProposalEntry> getEntries() {
        return entries;
    }

    public Map<ServiceProvider, Integer> getUsedProviders() {
        return usedProviders;
    }

    public Set<ServiceProvider> getRequiredProviders() {
        return requiredProviders;
    }

    public Set<Estimate> getFiredEstimates() {
        return firedEstimates;
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
