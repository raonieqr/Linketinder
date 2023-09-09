package linketinder.model.dao.impl

import linketinder.db.DBHandler
import linketinder.model.dao.VacancyDAO
import linketinder.model.entities.Company
import linketinder.model.entities.Vacancy

class VacancyImpl implements VacancyDAO{

    def dbHandler = DBHandler.getInstance()
    def sql = dbHandler.getSql()

    @Override
    void getAllVacancy(ArrayList<Vacancy> vacancies, ArrayList<Company> companies) {
        def viewAllVancacy = sql.rows("""
            SELECT * FROM roles
        """)

        if (viewAllVancacy != null) {

            viewAllVancacy.each {vacancy ->
                int id = vacancy.id as int
                def viewAllSkill = sql.rows("""
                    SELECT
                        skills.description
                    FROM
                        roles_skills
                    JOIN
                        skills
                    ON
                        roles_skills.id_skill = skills.id
                    WHERE
                        roles_skills.id_role = $id;
                """)

                Company comp

                companies.each { company ->
                    if (company.getId() == vacancy.id_company as int) {
                        comp = new Company(company.id as int, company.name as String, company.email as String, company.cnpj as String, company.country as String, company.description as String, company.state as String, company.cep as int)
                    }
                }

                if (comp != null && comp) {
                    vacancies.add(new Vacancy(vacancy.id as int, vacancy.name as String, vacancy.description as String, comp, viewAllSkill.description as ArrayList<String>))
                }
            }
        }
    }

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
