package ru.eltech.csa.kaas.binder.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ru.eltech.csa.kaas.binder.engine.memory.KnowledgeBaseAdapter;
import ru.eltech.csa.kaas.binder.engine.memory.WorkingMemory;
import ru.eltech.csa.kaas.binder.engine.memory.proposal.ProposalInWork;
import ru.eltech.csa.kaas.binder.engine.reasoner.Reasoner;
import ru.eltech.csa.kaas.binder.model.KnowledgeBase;
import ru.eltech.csa.kaas.binder.platform.ExpertEngine;
import ru.eltech.csa.kaas.binder.platform.Proposal;
import ru.eltech.csa.kaas.binder.query.Query;

/**
 * Build result proposals using injected reasoner implementation.
 */
public class ReasonerBasedEngine implements ExpertEngine {

    private Reasoner reasoner;

    @Override
    public List<Proposal> propose(Query query, KnowledgeBase knowledgeBase) {
        WorkingMemory memory = new WorkingMemory(query, new KnowledgeBaseAdapter(knowledgeBase));
        return doPropose(memory);
    }

    /**
     * While there are non-ready proposals in working memory, ask reasoner to
     * propose their extending. Follow to avoid duplicates proposals.
     *
     * @param memory
     * @return
     */
    private List<Proposal> doPropose(WorkingMemory memory) {
        List<Proposal> resultProposals = new ArrayList<>();
        while (!memory.getProposals().isEmpty()) {
            Set<ProposalInWork> nextStepProposals = new HashSet<>();
            for (ProposalInWork currentStepProposal : memory.getProposals()) {
                Set<ProposalInWork> nextStepCandidates = reasoner.propose(currentStepProposal, memory);
                for (ProposalInWork candidate : nextStepCandidates) {
                    if (!memory.isInProposalsLog(candidate)) {
                        if (candidate.hasEmptySlots()) {
                            nextStepProposals.add(candidate);
                            memory.addToProposalsLog(candidate);
                        } else {
                            resultProposals.add(prepareAnswer(candidate));
                        }
                    }
                }
            }
            memory.setProposals(nextStepProposals);
        }
        return resultProposals;
    }

    private Proposal prepareAnswer(ProposalInWork piw) {
        Proposal result = new Proposal();
        result.setServiceImplementations(piw.getSlots().getResultImplementations());
        return result;
    }

    public void setReasoner(Reasoner reasoner) {
        this.reasoner = reasoner;
    }
}
