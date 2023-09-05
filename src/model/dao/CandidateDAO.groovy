package model.dao

import model.entities.Candidate
import model.entities.Vacancy

interface CandidateDAO {
    void getAllCandidates(ArrayList<Candidate> candidates,  ArrayList<Vacancy> vacancies)
    void insertCandidate(Candidate candidate)

}