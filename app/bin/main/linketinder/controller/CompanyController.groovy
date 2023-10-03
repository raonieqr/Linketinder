package linketinder.controller

import linketinder.dao.impl.CompanyImpl
import linketinder.dao.impl.MatchVacancyImpl
import linketinder.model.entities.Candidate
import linketinder.model.entities.Company
import linketinder.model.entities.MatchVacancy
import linketinder.utils.MatchValidator
import linketinder.view.CompanyView

class CompanyController {

    // TODO: modificar para que o view chame o controller

    static void listCompanies(ArrayList<Company> companies) {

        CompanyView.showCompanies(companies)
    }

    static void addCompany(ArrayList<Company> companies,
                           Company company, CompanyImpl companyImpl) {

        companies.add(company)
        companyImpl.insertCompany(company)
    }

    static void executeCompanyDeletion(Company company,
                                       CompanyImpl companyImpl) {

        companyImpl.deleteCompany(company)
    }

}
