package model.dao

import model.entities.Company

interface CompanyDAO {
    void getAllCompanies(ArrayList<Company> companies)
    void insertCompany(Company company)
}