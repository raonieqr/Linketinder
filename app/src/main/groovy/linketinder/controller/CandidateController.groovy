package linketinder.controller

import linketinder.dao.impl.CandidateImpl
import linketinder.dao.impl.CompanyImpl
import linketinder.model.entities.Candidate
import linketinder.model.entities.Company
import linketinder.view.CandidateView

class CandidateController {

    static void listCandidates(ArrayList<Candidate> candidates) {

        CandidateView.showCandidates(candidates)
    }

    static void addCandidate(ArrayList<Candidate> candidates,
                             Candidate candidate, CandidateImpl candidateImpl) {

        candidates.add(candidate)
        candidateImpl.insertCandidate(candidate)
    }

    static void executeCandidateDeletion(Candidate candidate) {
        CandidateImpl candidateImpl = new CandidateImpl()

        candidateImpl.deleteCandidate(candidate)
    }

}
