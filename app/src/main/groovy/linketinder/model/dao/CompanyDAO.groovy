package linketinder.model.dao

import linketinder.model.entities.Company

interface CompanyDAO {

    void getAllCompanies(ArrayList<Company> companies)

    void insertCompany(Company company)

}
