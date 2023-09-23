package linketinder.controller

import linketinder.dao.impl.MatchVacancyImpl
import linketinder.model.entities.Candidate
import linketinder.model.entities.Company
import linketinder.model.entities.MatchVacancy
import linketinder.model.entities.Vacancy
import linketinder.utils.InputValidator
import linketinder.utils.MatchValidator
import linketinder.view.MatchView
import linketinder.view.VacancyView

class MatchController {

static void listAvailableVacancies(Candidate candidate, ArrayList<Vacancy> vacancies,
                                       MatchVacancyImpl matchVacancyImpl,
                                       int idMatch) {

    ArrayList<Integer> printedVacancyIds = new ArrayList<>()
    Set<Integer> idsLiked =  new HashSet<>()
    boolean allVacanciesLiked = true

        if (vacancies.isEmpty())
            VacancyView.displayNoVacancies()
        else {

            vacancies.each { vacancie ->
                boolean containsVacancie = false

                candidate.getMatchVacancies().each { matchingVacancy ->

                    if (matchingVacancy.getVacancy().
                            getId() == vacancie.getId()) {

                        idsLiked.add(vacancie.getId())

                        containsVacancie = true

                        return
                    }
                }

                allVacanciesLiked = MatchView
                        .displayUnlikedVacancies(containsVacancie,
                        printedVacancyIds, vacancie, allVacanciesLiked)

            }
        }

        if (allVacanciesLiked)
            MatchView.displayAllVacanciesLiked()
        else {
                MatchVacancy match = likeVacancy(candidate, vacancies,
                        idsLiked, idMatch)

                if (match != null) {

                    matchVacancyImpl
                            .insertCandidateLiked(candidate,
                                    match.getVacancy().getId())

                    candidate.getMatchVacancies().add(match)

                    MatchView.showLikedMsg()
                }
        }

    }

    static void handleCompanyMatchResults(Candidate candidate,
                                      ArrayList<Candidate> candidates, Company company,
                                      MatchVacancyImpl matchVacancyImpl) {

        if (candidate != null) {

            MatchVacancy matchVacancy =
                    MatchValidator.findMatchVacancyForCandidate(candidate,
                            candidates)

            if (matchVacancy != null) {

                company.getMatchVacancies()
                        .find {
                            it.getId() == matchVacancy
                                    .getId()
                        }?.setCompanyLiked(true)

                matchVacancyImpl.updateLikedCompany(matchVacancy)
            }
        }
    }


    static MatchVacancy likeVacancy(Candidate candidate, ArrayList<Vacancy> vacancies,
                            Set<Integer> idsLiked, int idMatch) {

        boolean containsNumber = true

        Vacancy vacancy
        while (containsNumber) {
            containsNumber = false

            vacancy = InputValidator.findVacancyByID(vacancies)

            for (Integer id : idsLiked) {

                if (id == vacancy.getId()) {

                    containsNumber = true

                    MatchView.displayAlreadyLiked()

                    break
                }
            }
        }
        if (vacancy != null && !containsNumber) {

            MatchVacancy match = new MatchVacancy(++idMatch, vacancy, candidate)

            return match
        }
        return null
    }

}