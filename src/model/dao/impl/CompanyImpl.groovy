package model.dao.impl

import db.DBHandler
import model.dao.CompanyDAO
import model.entities.Company

class CompanyImpl implements CompanyDAO{
    def dbHandler = DBHandler.getInstance()
    def sql = dbHandler.getSql()

    @Override
    void getAllCompanies(ArrayList<Company> companies) {
        try {

            def viewAllCompanies = sql.rows("SELECT * FROM companies")
            if (viewAllCompanies) {
                viewAllCompanies.each {companie ->
                    companies.add(new Company(companie.id as int,
                        companie.name as String, companie.email as String,
                        companie.cnpj as String, companie.country as String,
                        companie.description as String,
                        companie.state as String, companie.cep as int)
                    )
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace()
        }
    }

    @Override
    void insertCompany(Company company) {
    }
}
