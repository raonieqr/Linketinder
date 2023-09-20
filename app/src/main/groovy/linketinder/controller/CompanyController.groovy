package linketinder.controller

import linketinder.model.entities.Company
import linketinder.view.CompanyView

class CompanyController {
    static void listCompanies(ArrayList<Company> companies) {
        CompanyView.showCompanies(companies)
    }
}
