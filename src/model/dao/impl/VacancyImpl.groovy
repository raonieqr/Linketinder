package model.dao.impl

import db.DBHandler
import model.dao.VacancyDAO
import model.entities.Vacancy

class VacancyImpl implements VacancyDAO{

    def dbHandler = DBHandler.getInstance()
    def sql = dbHandler.getSql()

    @Override
    void insertVacancy(Vacancy vacancy) {
        sql.executeInsert("""
            INSERT INTO roles (NAME, DESCRIPTION, ID_COMPANY,
            DATE)
            VALUES (${vacancy.getName()}, ${vacancy.getDescription()},
            ${vacancy.getCompany().getId()}, current_date)
        """)

        vacancy.getSkills().each { skill ->
            def containsSkill = sql.firstRow("""
                SELECT id, COUNT(*)
                FROM skills
                WHERE description = $skill
                GROUP BY id
            """)

            def idSkill

            if (containsSkill != null && containsSkill.count > 0) {
                idSkill = containsSkill.id
            } else {
                def result = sql.firstRow("""
                                INSERT INTO skills (DESCRIPTION) VALUES ($skill) RETURNING id
                            """)
                idSkill = result.id
            }

            sql.executeInsert("""
                            INSERT INTO roles_skills (ID_ROLE, ID_SKILL)
                            VALUES (${vacancy.getId()}, $idSkill)
            """)
        }
    }
}
