package linketinder.db

import linketinder.dao.impl.CandidateImpl
import linketinder.dao.impl.CompanyImpl
import linketinder.dao.impl.VacancyImpl
import linketinder.model.entities.Candidate
import linketinder.model.entities.Company
import linketinder.model.entities.Vacancy

class DatabaseRefresh {

        CompanyImpl companyImpl
        VacancyImpl vacancyImpl
        CandidateImpl candidateImpl

        DatabaseRefresh(CompanyImpl companyImpl, VacancyImpl vacancyImpl,
                        CandidateImpl candidateImpl) {

            this.companyImpl = companyImpl
            this.vacancyImpl = vacancyImpl
            this.candidateImpl = candidateImpl
        }

        void updateCompanies(ArrayList<Company> companies) {
            companyImpl.getAllCompanies(companies)
        }

        void updateVacancies(ArrayList<Vacancy> vacancies,
                             ArrayList<Company> companies) {

            vacancyImpl.getAllVacancy(vacancies, companies)
        }

        void updateCandidates(ArrayList<Candidate> candidates,
                              ArrayList<Vacancy> vacancies) {

           candidateImpl.getAllCandidates(candidates, vacancies)
        }

    static void updateDataFromDatabase(
            ArrayList<Company> companies, ArrayList<Vacancy> vacancies,
            ArrayList<Candidate> candidates, DatabaseRefresh updater) {

        companies.clear()
        vacancies.clear()
        candidates.clear()

        updater.updateCompanies(companies)
        updater.updateVacancies(vacancies, companies)
        updater.updateCandidates(candidates, vacancies)

        Company.getMatch(candidates, companies)
    }
}
