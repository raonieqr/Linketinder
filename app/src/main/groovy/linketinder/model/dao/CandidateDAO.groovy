package linketinder.model.dao

import linketinder.model.entities.Candidate
import linketinder.model.entities.Vacancy

interface CandidateDAO {
    void getAllCandidates(ArrayList<Candidate> candidates, ArrayList<Vacancy> vacancies)
    void insertCandidate(Candidate candidate)

}