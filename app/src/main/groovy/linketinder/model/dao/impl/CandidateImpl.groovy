package linketinder.model.dao.impl

import linketinder.db.DBHandler
import linketinder.model.dao.CandidateDAO
import linketinder.model.entities.Candidate
import linketinder.model.entities.MatchVacancy
import linketinder.model.entities.Vacancy

class CandidateImpl implements  CandidateDAO {
    def dbHandler = DBHandler.getInstance()
    def sql = dbHandler.getSql()

    @Override
    void getAllCandidates(ArrayList<Candidate> candidates,
                          ArrayList<Vacancy> vacancies) {
        try {

            def viewAllCandidates =
                    sql.rows("SELECT * FROM candidates")
            if (viewAllCandidates) {
                viewAllCandidates.each { candidate ->
                    def viewCandidateSkill = sql.rows("""
                        SELECT DISTINCT skills.description AS skill
                        FROM candidates
                        JOIN candidate_skills ON $candidate.id = 
                        candidate_skills.id_candidate
                        JOIN skills ON candidate_skills.id_skill = skills.id
                    """)

                    Candidate candi = new Candidate(candidate.id as int,
                            candidate.name as String, candidate.email as
                            String, viewCandidateSkill.skill as ArrayList<String>,
                            candidate.age as int, candidate.state as String,
                            candidate.description as String, candidate.
                            cpf as String, candidate.cep as int)

                    // TODO: gerar outro metodo, e retornar a lista

                    def getMatchCandidate = sql.rows("""
                        SELECT rm.id, rm.id_role, rm.id_candidate, rm.companymatched
                        FROM role_matching AS rm
                        JOIN roles on rm.id_role = roles.id
                        WHERE id_candidate = ${candidate.id}
                    """)

                    getMatchCandidate.each {row ->
                        vacancies.each {vacancy ->
                            if (vacancy.getId() == row.id_role) {
                                MatchVacancy match = new MatchVacancy
                                        (row.id as int, vacancy,
                                                candi)
                                match.setCompanyLiked(row
                                        .companymatched as boolean)
                                candi.getMatchVacancies().add(match)
                            }
                        }
                    }
                    candidates.add(candi)
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace()
        }
    }

    @Override
    void insertCandidate(Candidate candidate) {
        try {

            int idCandidate = candidate.getId()

            sql.executeInsert("""
                INSERT INTO candidates (NAME, CEP, CPF, STATE, AGE, DESCRIPTION, EMAIL, PASSWORD) 
                VALUES (${candidate.getName()}, ${candidate.getCep()},
                ${candidate.getCpf()}, ${candidate.getState()}, 
                ${candidate.getAge()}, ${candidate.getDescription()},
                ${candidate.getEmail()}, 'batatinha')
            """)

            // TODO: evitar utilizar muito o def
            candidate.getSkills().each { skill ->
                def containsSkill = sql.firstRow("""
                    SELECT id, COUNT(*)
                    FROM skills 
                    WHERE description = $skill
                    GROUP BY id
                """)

                def idSkill

                // TODO: criar a classe skills
                if (containsSkill != null && containsSkill.count > 0)
                    idSkill = containsSkill.id
                else {
                    def result = sql.firstRow("""
                       INSERT INTO skills (DESCRIPTION) VALUES ($skill) RETURNING id
                    """)
                    idSkill = result.id
                }

                sql.executeInsert("""
                    INSERT INTO candidate_skills (ID_CANDIDATE, ID_SKILL) 
                    VALUES ($idCandidate, $idSkill)
                """)
            }
        }
        catch (Exception e) {
            e.printStackTrace()
        }
    }
}
