package linketinder.utils

import linketinder.model.entities.Candidate
import linketinder.model.entities.Company
import linketinder.model.entities.MatchVacancy
import linketinder.model.entities.Vacancy

class InputValidator {

    static String ensureUniqueEmail(ArrayList<?> objects, String email) {

        boolean exists = true

        while (exists) {
            exists = false

            objects.each { obj ->
                if (obj.getEmail().equals(email)) {

                    exists = true

                    email = promptForUserInput("Error: o email já existe" +
                            ". Tente novamente outro email: ")
                }
            }
        }
        return email
    }

    static String ensureUniqueCnpj(ArrayList<?> objects, String cnpj) {

        boolean exists = true

        while (exists) {
            exists = false

            objects.each { obj ->
                if (obj.getCnpj().equals(cnpj)) {

                    exists = true

                    cnpj = promptForUserInput("Error: o CNPJ já existe." +
                            " Tente novamente outro CNPJ: ")
                }
            }
        }
        return cnpj
    }

    static String ensureUniqueCpf(ArrayList<?> objects, String cpf) {

        boolean exists = true

        while (exists) {
            exists = false

            objects.each { obj ->
                if (obj.getCpf().equals(cpf)) {

                    exists = true

                    cpf = promptForUserInput("Error: o CPF já existe." +
                            " Tente novamente outro CPF: ")
                }
            }
        }
        return cpf
    }

    static findMatchVacancyByID(ArrayList<MatchVacancy> matches,
                                ArrayList<Candidate> candidates) {

        int index
        MatchVacancy match

        boolean idFound = true
        boolean isCandidate = true

        while (idFound) {

            if (!isCandidate) {

                Candidate selectedCandidate = findCandidateByID(candidates)
                matches = selectedCandidate.getMatchVacancies()
            }

            index = promptForIntegerInput("Digite o ID da vaga: ")

            matches.each { m ->

                if (m.getId() == index) {
                    match = m

                    idFound = false
                }
            }
            if (match == null) {
                println("Error: Match não encontrado. Tente novamente")

                isCandidate = false
            }
        }
        return match
    }

    static Vacancy findVacancyByID(ArrayList<Vacancy> vacancies) {

        int index
        Vacancy vacancy

        boolean idFound = true

        while (idFound) {

            index = promptForIntegerInput("Digite o ID da vaga: ")

            vacancies.each { v ->

                if (v.getId() == index) {
                    vacancy = v

                    idFound = false
                }
            }
            if (vacancy == null)
                println("Error: Vaga não encontrada. Tente novamente")
        }
        return vacancy
    }

    static Candidate findCandidateByID(ArrayList<Candidate> candidates) {

        int index
        Candidate candidate

        boolean idFound = true

        while (idFound) {

            index = promptForIntegerInput("Digite o ID do candidato: ")

            candidates.each { c ->

                if (c.getId() == index) {
                    candidate = c

                    idFound = false
                }
            }
            if (candidate == null)
                println("Error: Candidato não encontrado. Tente novamente")
        }
        return candidate
    }

    static Company findCompanyByID(ArrayList<Company> companies) {

        int index
        Company company

        boolean idFound = true

        while (idFound) {

            index = promptForIntegerInput("Digite o ID da sua empresa: ")

            companies.each { comp ->

                if (comp.getId() == index) {
                    company = comp

                    idFound = false
                }
            }
            if (company == null)
                println("Error: Empresa não encontrada. Tente novamente")
        }
        return company
    }

    static String promptForUserInput(String prompt) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System
                .in))

        print(prompt)

        String input = reader.readLine()
        while (input.trim().isEmpty()) {

            println("Error: O campo não pode estar vazio. Tente novamente.")
            print(prompt)

            input = reader.readLine()
        }
        return input
    }

    static int promptForIntegerInput(String prompt) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System
                .in))

        print(prompt)

        String input = reader.readLine()
        while (!input.isInteger()) {

            println("Error: Entrada inválida. Tente novamente.")
            print(prompt)

            input = reader.readLine()
        }
        return Integer.parseInt(input)
    }

    static int getUserTypeChoice() {

        boolean validChoice = false
        int choice = 0

        while (!validChoice) {

            choice = promptForIntegerInput("Você é empresa ou " +
                    "candidato?\n" +
                    "1 - Empresa\n" + "2 - Candidato\n")

            if (choice == 1 || choice == 2)
                validChoice = true
            else
                println("Opção inválida. Tente novamente")
        }

        return choice
    }

    static int displayMenuAndGetOption() {
        println("1 - Listar empresas")
        println("2 - Listar candidatos")
        println("3 - Adicionar novo candidato")
        println("4 - Adicionar nova empresa")
        println("5 - Criar vaga para empresa")
        println("6 - Curtir")
        println("7 - Visualizar matches")
        println("8 - Deletar")
        println("9 - Sair")

        int input = promptForIntegerInput("Escolha um dos comandos: ")
        return input
    }


}
