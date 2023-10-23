package linketinder.controller

import linketinder.dao.impl.CompanyDAOImpl
import linketinder.model.entities.Company
import linketinder.view.CompanyView

class CompanyController {

    // TODO: modificar para que o view chame o controller

    static void listCompanies(ArrayList<Company> companies) {

        CompanyView.showCompanies(companies)
    }

    static void addCompany(ArrayList<Company> companies,
                           Company company, CompanyDAOImpl companyImpl) {

        companies.add(company)
        companyImpl.insertCompany(company)
    }

    static void executeCompanyDeletion(Company company,
                                       CompanyDAOImpl companyImpl) {

        companyImpl.deleteCompany(company)
    }

}
