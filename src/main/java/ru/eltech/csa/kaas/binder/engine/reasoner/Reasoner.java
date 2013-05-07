package ru.eltech.csa.kaas.binder.engine.reasoner;

import java.util.Set;
import ru.eltech.csa.kaas.binder.engine.memory.WorkingMemory;
import ru.eltech.csa.kaas.binder.engine.memory.proposal.ProposalInWork;

/**
 * Interface for proposal analyzing.
 */
public interface Reasoner {

    /**
     * Analyze the given propolsal and try to extend it using data from working
     * memory.
     *
     * @param proposal the given proposal
     * @param memory the working memory
     * @return set of created proposals. can be empty.
     */
    Set<ProposalInWork> propose(ProposalInWork proposal, WorkingMemory memory);
}
