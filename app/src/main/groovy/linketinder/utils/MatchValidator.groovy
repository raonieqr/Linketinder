package linketinder.utils

import linketinder.model.entities.Candidate
import linketinder.model.entities.MatchVacancy

class MatchValidator {

    static Candidate findMatchingCandidate(ArrayList<Integer> idsCandidates,
                                           ArrayList<Candidate> candidates) {
        Candidate candidate = null
        boolean isCandidate = false

        while (!isCandidate) {

            if (idsCandidates.isEmpty()) {
                println("Não há candidatos")
                break
            }

             candidate = InputValidator.findCandidateByID(candidates)

            if (idsCandidates.contains(candidate.getId())) {
                isCandidate = true
            }
        }

        return candidate
    }

    static MatchVacancy findMatchVacancyForCandidate(Candidate candidate,
                                            ArrayList<Candidate> candidates) {

        return InputValidator.findMatchVacancyByID(candidate
                .getMatchVacancies(), candidates)
    }
}