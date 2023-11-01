package linketinder.dao.impl

import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import linketinder.dao.CandidateDAO
import linketinder.db.DBHandler
import linketinder.model.entities.Candidate
import linketinder.model.entities.MatchVacancy
import linketinder.model.entities.Vacancy

class CandidateDAOImpl implements  CandidateDAO {

    static CandidateDAOImpl instance

    DBHandler dbHandler = DBHandler.getInstance()
    Sql sql = dbHandler.getSql()

    static CandidateDAOImpl getInstance() {
        if (instance == null)
            instance = new CandidateDAOImpl()
        return instance
    }

    @Override
    void getAllCandidates(ArrayList<Candidate> candidates,
                          ArrayList<Vacancy> vacancies) {

        List<GroovyRowResult> viewAllCandidates = sql
                .rows("SELECT * FROM candidates")

        if (viewAllCandidates) {

            viewAllCandidates.each { candidate ->

                Candidate candi = createCandidateFromRow(candidate)

                populateCandidateMatches(candi, vacancies)

                candidates.add(candi)
            }
        }
    }

    Candidate createCandidateFromRow(GroovyRowResult candidateRow) {

        List<GroovyRowResult> viewCandidateSkill = sql.rows("""
            SELECT DISTINCT skills.description AS skill
            FROM candidates
            JOIN candidate_skills ON ${candidateRow
                .id} = candidate_skills.id_candidate
            JOIN skills ON candidate_skills.id_skill = skills.id
        """)

        return new Candidate(
                candidateRow.id as int,
                candidateRow.name as String,
                candidateRow.email as String,
                viewCandidateSkill.skill as ArrayList<String>,
                candidateRow.age as int,
                candidateRow.state as String,
                candidateRow.description as String,
                candidateRow.cpf as String,
                candidateRow.cep as int
        )
    }

    void populateCandidateMatches(Candidate candidate,
                                  ArrayList<Vacancy> vacancies) {

        List<GroovyRowResult> getMatchCandidate = sql.rows("""
            SELECT rm.id, rm.id_role, rm.id_candidate, rm.companymatched
            FROM role_matching AS rm
            JOIN roles on rm.id_role = roles.id
            WHERE id_candidate = ${candidate.getId()}
        """)

        getMatchCandidate.each { row ->

            vacancies.each { vacancy ->

                if (vacancy.getId() == row.id_role) {

                    MatchVacancy match = new MatchVacancy(
                            row.id as int, vacancy, candidate
                    )

                    match.setCompanyLiked(row.companymatched as boolean)

                    candidate.getMatchVacancies().add(match)
                }
            }
        }
    }

    @Override
    void insertCandidate(Candidate candidate) {

        try {

            sql.executeInsert("""
                INSERT INTO candidates (NAME, CEP, CPF, STATE,
                AGE, DESCRIPTION, EMAIL, PASSWORD) 
                VALUES (${candidate.getName()}, ${candidate.getCep()},
                ${candidate.getCpf()}, ${candidate.getState()}, 
                ${candidate.getAge()}, ${candidate.getDescription()},
                ${candidate.getEmail()}, ${candidate.getPassword()})
            """)

            int idCandidate = sql.firstRow('SELECT currval(\'candidates_id_seq\') as id')?.id as int

            candidate.getSkills().each { skill ->

                GroovyRowResult containsSkill = sql.firstRow("""
                    SELECT id, COUNT(*)
                    FROM skills 
                    WHERE description = $skill
                    GROUP BY id
                """)

                int idSkill

                if (containsSkill != null && containsSkill.count > 0)
                    idSkill = containsSkill.id as int
                else {

                    GroovyRowResult result = sql.firstRow("""
                       INSERT INTO skills (DESCRIPTION) VALUES (
                        $skill) RETURNING id
                    """)
                    idSkill = result.id as int

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

    @Override
    void deleteCandidate(Candidate candidate) {
        sql.executeInsert("""
            DELETE FROM candidates WHERE id = ${candidate.getId()}
        """)
    }
}
