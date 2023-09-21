package linketinder.model.dao.impl

import groovy.sql.Sql
import linketinder.db.DBHandler
import linketinder.model.dao.MatchVacancyDAO
import linketinder.model.entities.Candidate
import linketinder.model.entities.MatchVacancy
import linketinder.model.entities.Vacancy

class MatchVacancyImpl implements MatchVacancyDAO {

    DBHandler dbHandler = DBHandler.getInstance()
    Sql sql = dbHandler.getSql()

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
