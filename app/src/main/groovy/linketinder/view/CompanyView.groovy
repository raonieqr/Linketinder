package linketinder.view

import linketinder.model.entities.Company

class CompanyView {
    static showCompanies(ArrayList<Company> companies) {
        if (companies.isEmpty())
            println("Não há empresas registradas")
        else
            companies.each {companie ->
                companie.showInfo()
                println("-------------------------------------" +
                        "--------------------------")
            }
    }
}
