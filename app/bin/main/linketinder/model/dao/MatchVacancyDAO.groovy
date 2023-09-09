package model.dao

import model.entities.Candidate
import model.entities.MatchVacancy
import model.entities.Vacancy

interface MatchVacancyDAO {
    void updateLikedCompany(MatchVacancy matchVacancy)
    void insertCandidateLiked(Candidate candidate, Vacancy vacancy)
}