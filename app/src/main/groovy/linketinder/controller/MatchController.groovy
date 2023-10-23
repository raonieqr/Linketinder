package linketinder.controller

import linketinder.dao.impl.MatchVacancyDAOImpl
import linketinder.model.entities.Candidate
import linketinder.model.entities.Company
import linketinder.model.entities.MatchVacancy
import linketinder.model.entities.Vacancy
import linketinder.utils.InputValidator
import linketinder.utils.MatchValidator

class MatchController {

static void manageVacancyListAndLikes(Candidate candidate, ArrayList<Vacancy> vacancies,
                                      MatchVacancyDAOImpl matchVacancyImpl,
                                      int idMatch) {

    ArrayList<Integer> printedVacancyIds = new ArrayList<>()
    Set<Integer> idsLiked =  new HashSet<>()

    boolean  allVacanciesLiked = true

        if (vacancies.isEmpty())
            return
//            VacancyView.displayNoVacancies()
        else
                allVacanciesLiked = processVacancies(vacancies, candidate,
                        idsLiked, printedVacancyIds)

//        if (allVacanciesLiked)
//            MatchView.displayAllVacanciesLiked()
        if(!allVacanciesLiked) {
           MatchVacancy match =  handleLikedVacancies(candidate, vacancies,
                    idsLiked, idMatch)

            likedVacancies(match, matchVacancyImpl, candidate)
        }

    }

    static boolean processVacancies(ArrayList<Vacancy> vacancies,
                                    Candidate candidate,
                                    Set<Integer>  idsLiked,
                                    ArrayList<Integer> printedVacancyIds) {

        boolean allVacanciesLiked = true

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

            allVacanciesLiked = isUnlikedVacancies(containsVacancie,
                            printedVacancyIds, vacancie, allVacanciesLiked)
        }
        return allVacanciesLiked
    }

    static boolean isUnlikedVacancies(boolean  containsVacancie,
                                           ArrayList<Integer> printedVacancyIds,
                                           Vacancy vacancy,
                                           boolean allVacanciesLiked) {

        if (!containsVacancie && !printedVacancyIds.contains(vacancy.getId())) {

            allVacanciesLiked = false

            printedVacancyIds.add(vacancy.getId())
        }

        return allVacanciesLiked
    }

    static MatchVacancy handleLikedVacancies(Candidate candidate,
                                     ArrayList<Vacancy> vacancies,
                                     Set<Integer> idsLiked, int idMatch) {
        return likeVacancy(candidate, vacancies,
                idsLiked, idMatch)

    }

    static void likedVacancies(MatchVacancy match,
                               MatchVacancyDAOImpl matchVacancyImpl,
                               Candidate candidate) {
        if (match != null) {

            matchVacancyImpl
                    .insertCandidateLiked(candidate,
                            match.getVacancy().getId())

            candidate.getMatchVacancies().add(match)

//            MatchView.showLikedMsg()
        }
    }

    static void handleCompanyMatchResults(Candidate candidate,
                                          ArrayList<Candidate> candidates, Company company,
                                          MatchVacancyDAOImpl matchVacancyImpl) {

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

//                    MatchView.displayAlreadyLiked()

                    break
                }
            }
        }
        if (vacancy != null && !containsNumber) {

            MatchVacancy match = new MatchVacancy(idMatch, vacancy, candidate)

            return match
        }
        return null
    }

}