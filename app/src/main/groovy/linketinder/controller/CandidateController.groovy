package linketinder.controller

import linketinder.dao.impl.CandidateDAOImpl
import linketinder.model.entities.Candidate
import linketinder.view.CandidateView

class CandidateController {

    static void listCandidates(ArrayList<Candidate> candidates) {

        CandidateView.showCandidates(candidates)
    }

    static void addCandidate(ArrayList<Candidate> candidates,
                             Candidate candidate, CandidateDAOImpl candidateImpl) {

        candidates.add(candidate)

        candidateImpl.insertCandidate(candidate)
    }

    static void executeCandidateDeletion(Candidate candidate,
                                         CandidateDAOImpl candidateImpl) {

        candidateImpl.deleteCandidate(candidate)
    }

}
