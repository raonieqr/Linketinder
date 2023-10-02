package linketinder.view

import linketinder.model.entities.Candidate
import linketinder.model.entities.Company
import linketinder.utils.InputValidator
import linketinder.utils.MatchValidator

class CompanyView {

    static showCompanies(ArrayList<Company> companies) {
        if (companies.isEmpty()) {
            println('Não há empresas registradas')
        }
        else {
            companies.each { companie ->
                println('ID: ' + companie.getId())

                companie.showInfo()

                println('-------------------------------------' +
                        '--------------------------')
            }
        }

    static Company createCompany(int idCompany,
                                 ArrayList<Company> companies) {
        Company company = new Company()

        String name = InputValidator.promptForUserInput('Nome: ')
        String email = InputValidator.promptForUserInput('E-mail: ')

        email = InputValidator.ensureUniqueEmail(companies, email)

        String description = InputValidator
                .promptForUserInput('Descrição: ')
        String state = InputValidator.promptForUserInput('Estado: ')
        String cnpj = InputValidator.promptForUserInput('Cnpj: ')

        while (cnpj.length() != 14)
            cnpj = InputValidator
                    .promptForUserInput('Error: O cnpj deve ter 14 ' +
                    'caracteres. Tente novamente: ')
        cnpj = InputValidator.ensureUniqueCnpj(companies, cnpj)

        String country = InputValidator.promptForUserInput('País: ')

        while (country.length() < 2 || country.length() > 3)
            country = InputValidator
                    .promptForUserInput('Error: O País deve ter 2 a 3 ' +
                    'caracteres. Tente novamente: ')

        Integer cep = InputValidator.promptForIntegerInput('CEP: ')

        return company.createCompanyFactory(idCompany, name, email, cnpj, country,
          description, state, cep) as Company
                                 }

    static Candidate processCompanyMatches(Company comp,
                                      ArrayList<Candidate> candidates) {

        ArrayList<Integer> idsCandidates = []

        comp.getMatchVacancies().each { match ->
            if (!match.getCompanyLiked()) {
                idsCandidates.add(match.getCandidate().getId())

                println('Id da vaga: ' + match.getId())
                println('Id do candidato: ' + match.getCandidate().getId())
                println('Descrição: ' + match.getCandidate().getDescription())
                println('Skills:')
                println(match.getCandidate().getSkills().join(', '))

                println('------------------------------')
            }
        }

        return MatchValidator.findMatchingCandidate(idsCandidates, candidates)
                                      }

    static Company getCompanyToDeleteByID(ArrayList<Company> companies) {
        println('Siga os comandos abaixo para deletar')

        return InputValidator.findCompanyByID(companies)
    }

    }
