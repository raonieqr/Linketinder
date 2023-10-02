package linketinder.dao

import linketinder.model.entities.Candidate
import linketinder.model.entities.Vacancy

// TODO: package DAOInterface
interface CandidateDAO {

    void getAllCandidates(ArrayList<Candidate> candidates,
                          ArrayList<Vacancy> vacancies)

    void insertCandidate(Candidate candidate)

    void deleteCandidate(Candidate candidate)

}
