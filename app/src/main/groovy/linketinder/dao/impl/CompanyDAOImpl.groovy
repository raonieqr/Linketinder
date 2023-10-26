package linketinder.dao.impl

import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import linketinder.dao.CompanyDAO
import linketinder.db.DBHandler
import linketinder.model.entities.Company

class CompanyDAOImpl implements CompanyDAO{
    static CompanyDAOImpl instance

    DBHandler dbHandler = DBHandler.getInstance()
    Sql sql = dbHandler.getSql()

    static CompanyDAOImpl getInstance() {
        if (instance == null)
            instance = new CompanyDAOImpl()
        return instance
    }

    @Override
    void getAllCompanies(ArrayList<Company> companies) {

        try {

            List<GroovyRowResult> viewAllCompanies = sql
                    .rows("SELECT * FROM companies")

            if (!viewAllCompanies.isEmpty()) {

                viewAllCompanies.each {companie ->

                    if (companie.name != null)

                        companies.add(new Company(companie.name as String, companie.email as String,
                            companie.cnpj as String, companie.country as String,
                            companie.description as String,
                            companie.state as String, companie.cep as int,
                          companies.password as String)
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
                ${company.getCountry()}, ${company.getPassword()})
            """)
        }
        catch (Exception e) {
            e.printStackTrace()
        }
    }

    @Override
    void deleteCompany(Company company) {
        sql.executeInsert("""
            DELETE FROM companies WHERE id = ${company.getId()}
        """)
    }
}
