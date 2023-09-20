package linketinder.view

import linketinder.model.entities.Candidate
import linketinder.model.entities.Company
import linketinder.utils.InputValidator

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

    static Company createCompany(int idCompany,
                                 ArrayList<Company> companies) {
        String name = InputValidator.promptForUserInput("Nome: ")
        String email = InputValidator.promptForUserInput("E-mail: ")

        email = InputValidator.ensureUniqueEmail(companies, email)

        String description = InputValidator
                .promptForUserInput("Descrição: ")
        String state = InputValidator.promptForUserInput("Estado: ")
        String cnpj = InputValidator.promptForUserInput("Cnpj: ")

        while(cnpj.length() != 14)
            cnpj = InputValidator
                    .promptForUserInput("Error: O cnpj deve ter 14 " +
                    "caracteres. Tente novamente: ")
        cnpj = InputValidator.ensureUniqueCnpj(companies, cnpj)

        String country = InputValidator.promptForUserInput("País: ")

        while(country.length() < 2 || country.length() > 3)
            country = InputValidator
                    .promptForUserInput("Error: O País deve ter 2 a 3 " +
                    "caracteres. Tente novamente: ")

        Integer cep = InputValidator.promptForIntegerInput("CEP: ")

        return new Company(idCompany, name, email, cnpj, country,
                description, state, cep)
    }
}
