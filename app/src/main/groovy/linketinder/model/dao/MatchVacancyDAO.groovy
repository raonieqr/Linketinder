package linketinder.model.dao

import linketinder.model.entities.Candidate
import linketinder.model.entities.MatchVacancy
import linketinder.model.entities.Vacancy

interface MatchVacancyDAO {
    void updateLikedCompany(MatchVacancy matchVacancy)
    void insertCandidateLiked(Candidate candidate, Vacancy vacancy)
}