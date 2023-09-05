package model.dao

import model.entities.Candidate

interface CandidateDAO {
    void getAllCandidates(ArrayList<Candidate> candidates)
    void insertCandidate(Candidate candidate)

}