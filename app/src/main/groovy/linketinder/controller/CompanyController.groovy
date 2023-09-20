package linketinder.controller

import linketinder.model.dao.impl.CandidateImpl
import linketinder.model.dao.impl.CompanyImpl
import linketinder.model.entities.Candidate
import linketinder.model.entities.Company
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
}
