package ru.eltech.csa.kaas.binder.engine.memory.proposal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ru.eltech.csa.kaas.binder.model.ServiceProvider;
import ru.eltech.csa.kaas.binder.model.ServiceType;

/**
 * Contains working data for reasoning. Can become an answer.
 */
public class ProposalInWork {

    private Map<ServiceType, ProposalEntry> entries = new HashMap<>();
    private Map<ServiceProvider, Integer> usedProviders = new HashMap<>();
    private Set<ServiceProvider> requiredProviders = new HashSet<>();

    public ProposalInWork(Set<ServiceType> requiredTypes) {
        for (ServiceType type : requiredTypes) {
            addRequirement(type);
        }
    }

    public ProposalInWork(ProposalInWork piw) {
        entries.putAll(piw.entries);
        usedProviders.putAll(piw.usedProviders);
        requiredProviders.addAll(piw.requiredProviders);
    }

    public final void addRequirement(ServiceType type) {
        ProposalEntry pe = new ProposalEntry();
        pe.setType(type);
        entries.put(type, pe);
    }

    public final void addRequirement(ServiceProvider provider) {
        if (!usedProviders.containsKey(provider)) {
            requiredProviders.add(provider);
        }
    }

    public void addProviderUsing(ServiceProvider provider) {
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

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
