package linketinder.controller

import linketinder.dao.impl.CompanyDAOImpl
import linketinder.model.entities.Company
import linketinder.view.CompanyView

class CompanyController {

    static void listCompanies(ArrayList<Company> companies) {

        CompanyView.showCompanies(companies)
    }

    static void addCompany(Company company, CompanyDAOImpl companyImpl) {

        companyImpl.insertCompany(company)
    }

    static void executeCompanyDeletion(Company company,
                                       CompanyDAOImpl companyImpl) {

        companyImpl.deleteCompany(company)
    }

}
