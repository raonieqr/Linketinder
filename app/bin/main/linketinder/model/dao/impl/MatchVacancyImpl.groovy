package model.dao.impl

import db.DBHandler
import model.dao.MatchVacancyDAO
import model.entities.Candidate
import model.entities.MatchVacancy
import model.entities.Vacancy

class MatchVacancyImpl implements MatchVacancyDAO{
    def dbHandler = DBHandler.getInstance()
    def sql = dbHandler.getSql()

    @Override
    void updateLikedCompany(MatchVacancy matchVacancy) {
        sql.executeInsert("""
            UPDATE role_matching
            SET companymatched = true
            WHERE id = ${matchVacancy.getId()}
        """)
    }

    @Override
    void insertCandidateLiked(Candidate candidate, Vacancy vacancy) {
        sql.executeInsert("""
            INSERT INTO role_matching (ID_CANDIDATE, ID_ROLE)
            VALUES (${candidate.getId()}, ${vacancy.getId()})
        """)
    }
}
