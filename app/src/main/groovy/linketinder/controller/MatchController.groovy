package linketinder.controller

import linketinder.model.dao.impl.MatchVacancyImpl
import linketinder.model.entities.Candidate
import linketinder.model.entities.MatchVacancy
import linketinder.model.entities.Vacancy
import linketinder.utils.InputValidator
import linketinder.view.MatchView
import linketinder.view.VacancyView

class MatchController {

    static void listAvailableVacancies(Candidate candidate, ArrayList<Vacancy> vacancies,
                                       MatchVacancyImpl matchVacancyImpl,
                                       int idMatch) {
        if (vacancies.isEmpty()) {
            VacancyView.displayNoVacancies()
        }
        else {
            ArrayList<Integer> printedVacancyIds = []
            Set<Integer> idsLiked = candidate.getMatchVacancies().findAll { it.getCompanyLiked() }

            displayUnlikedVacancies(candidate, vacancies, printedVacancyIds)

            if (idsLiked.size() == vacancies.size()) {
                MatchView.displayAllVacanciesLiked()
            }
            else {
                Vacancy selectedVacancy = likeVacancy(candidate, vacancies,
                        idsLiked, matchVacancyImpl)

                MatchVacancy match = new MatchVacancy(idMatch, selectedVacancy, candidate)

                matchVacancyImpl.insertCandidateLiked(candidate, selectedVacancy)

                candidate.getMatchVacancies().add(match)
                MatchView.displayVacancyLiked()
            }
        }
                                       }

    static void displayUnlikedVacancies(Candidate candidate, ArrayList<Vacancy> vacancies,
                                        ArrayList<Integer> printedVacancyIds) {
        vacancies.each { vacancy ->
            boolean containsVacancy = false

            candidate.getMatchVacancies().each { matchingVacancy ->
                if (matchingVacancy.getVacancy().getId() == vacancy.getId()) {
                    containsVacancy = true
                    return
                }
            }

            if (!containsVacancy && !printedVacancyIds.contains(vacancy.getId())) {
                VacancyView.displayVacancy(vacancy)
                printedVacancyIds.add(vacancy.getId())
            }
        }
                                        }

    static Vacancy likeVacancy(Candidate candidate, ArrayList<Vacancy> vacancies,
                            Set<Integer> idsLiked, MatchVacancyImpl matchVacancyImpl) {
        boolean containsNumber = true
        Vacancy selectedVacancy = null

        while (containsNumber) {
            containsNumber = false
            selectedVacancy = InputValidator
                    .findVacancyByID(vacancies)

            if (idsLiked.contains(selectedVacancy.getId())) {
                containsNumber = true
                MatchView.displayAlreadyLiked()
            }
        }

        return selectedVacancy
                            }

}
