package model.dao.impl

import db.DBHandler
import model.dao.CandidateDAO
import model.entities.Candidate

class CandidateImpl implements  CandidateDAO{
    def dbHandler = DBHandler.getInstance()
    def sql = dbHandler.getSql()

    @Override
    void getAllCandidates(ArrayList<Candidate> candidates) {
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

                    // TODO: implement get match in bd
//                    candi.getMatchVacancies().add()
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

            candidate.getSkills().each { skill ->
                def containsSkill = sql.firstRow("""
                    SELECT id, COUNT(*)
                    FROM skills 
                    WHERE description = $skill
                    GROUP BY id
                """)

                def idSkill

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
