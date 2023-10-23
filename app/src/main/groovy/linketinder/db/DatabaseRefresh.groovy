package linketinder.db

import linketinder.dao.impl.CandidateDAOImpl
import linketinder.dao.impl.CompanyDAOImpl
import linketinder.dao.impl.VacancyDAOImpl
import linketinder.model.entities.Candidate
import linketinder.model.entities.Company
import linketinder.model.entities.Vacancy

class DatabaseRefresh {

        CompanyDAOImpl companyImpl
        VacancyDAOImpl vacancyImpl
        CandidateDAOImpl candidateImpl

        DatabaseRefresh(CompanyDAOImpl companyImpl, VacancyDAOImpl vacancyImpl,
                        CandidateDAOImpl candidateImpl) {

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
