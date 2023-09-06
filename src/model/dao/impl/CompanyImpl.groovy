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
            if (!viewAllCompanies.isEmpty()) {
                viewAllCompanies.each {companie ->
                    if (companie.name != null)
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
        try {
            sql.executeInsert("""
                INSERT INTO companies (NAME, CEP, CNPJ, STATE, 
                DESCRIPTION, EMAIL, COUNTRY, PASSWORD) 
                VALUES (${company.getName()}, ${company.getCep()},
                ${company.getCnpj()}, ${company.getState()},
                ${company.getDescription()}, ${company.getEmail()},
                ${company.getCountry()}, 'batatinha')
            """)
        }
        catch (Exception e) {
            e.printStackTrace()
        }
    }
}
