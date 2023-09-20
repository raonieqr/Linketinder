package linketinder.controller

import linketinder.model.dao.impl.CompanyImpl
import linketinder.model.dao.impl.MatchVacancyImpl
import linketinder.model.entities.Candidate
import linketinder.model.entities.Company
import linketinder.model.entities.MatchVacancy
import linketinder.utils.MatchValidator
import linketinder.view.CompanyView

class CompanyController {

    static void listCompanies(ArrayList<Company> companies) {

        CompanyView.showCompanies(companies)
    }

    static void addCompany(ArrayList<Company> companies,
                           Company company, CompanyImpl companyImpl) {

        companies.add(company)
        companyImpl.insertCompany(company)
    }

    static void processCompanyMatches(Candidate candidate,
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
}
