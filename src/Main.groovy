import entities.*

class Main {
    static void main(String[] args) {
        println("Bem vindo ao LinkeTinder")
        int idCompany = 5
        int idCandidate = 5
        int idVacancy = 0
        int idMatch = 0
        int option;

        ArrayList<Candidate> candidates = new ArrayList<>()
        ArrayList<Company> companies = new ArrayList<>()
        ArrayList<Vacancy> vacancies = new ArrayList<>()

        candidates.add(new Candidate(1, "Raoni", "raoniestevan@gmail.com",
                ["C", "C++", "Java", "Python"], 25, "RJ", "Desenvolvedor", "12344566701", 22785055))
        candidates.add(new Candidate(2, "Alice", "alice@example.com",
                ["Python", "Java", "HTML", "CSS"], 28, "SP", "Web Developer", "987654321", 12345678))
        candidates.add(new Candidate(3,"Bob", "bob@example.com", ["Java",
                                                                 "JavaScript", "SQL", "React"], 30, "MG", "Full Stack Developer", "555555555", 54321098))
        candidates.add(new Candidate(4, "Eva", "eva@example.com", ["Ruby",
                                                                 "PHP", "Swift", "Kotlin"], 22, "RS", "Mobile Developer", "111111111", 98765432))
        candidates.add(new Candidate(5, "Carlos", "carlos@example.com",
                ["Python", "C#", "Unity", "Game Development"], 26, "BA", "Game Developer", "222222222", 11223344))

        companies.add(new Company(1, "ZG", "zg@zg.com.br", "12345678945567",
                "Brasil", "Desenvolvedor Java", "GO", ["Java", "Groovy"], 70806000))
        companies.add(new Company(2, "TechCorp", "info@techcorp.com",
                "98765432101234", "USA", "Web Developer", "CA", ["JavaScript", "HTML", "CSS"], 90210))
        companies.add(new Company(3, "InnovateTech", "contact@innovatetech." +
                "com", "55555555556789", "Canada", "Full Stack Developer", "ON", ["Java", "Python", "Angular"], 54321098))
        companies.add(new Company(4, "GameWorld", "info@gameworld.com",
                "11111111234567", "Brazil", "Game Developer", "SP", ["Unity", "C#", "Game Development"], 12345678))
        companies.add(new Company(15,"SoftSys", "contact@softsys.com",
                "22222222345678", "USA", "Software Engineer", "NY", ["Java", "Python", "SQL"], 10001))

        BufferedReader reader = new BufferedReader(new InputStreamReader
                (System.in))
        while (option != 8) {

            println("Escolha um dos comandos abaixo:")
            println("1 - Listar empresas")
            println("2 - Listar candidatos")
            println("3 - Adicionar novo candidato")
            println("4 - Adicionar nova empresa")
            println("5 - Criar vaga para empresa");
            println("6 - Curtir vaga");
            println("7 - Visualizar matches");
            println("8 - Sair")

            option = Integer.parseInt(reader.readLine())

            if (option == 1) {
                companies.each { companie ->
                    companie.showInfo()
                }
            }
            else if (option == 2) {
                candidates.each { candidate ->
                    candidate.showInfo()
                }
            }
            else if (option == 3) {
                def name = getUserInput("Nome: ")
                def email = getUserInput("E-mail: ")
                def skills = getUserInput("Habilidades (separadas por vírgula): ")
                def age = getUserInputInt("Idade: ")
                def state = getUserInput("Estado: ")
                def description = getUserInput("Descrição: ")
                def cpf = getUserInput("CPF: ")
                def cep = getUserInputInt("CEP: ")

                ArrayList<String> skillsList = skills.split(',').collect { it.trim() }
                candidates.add(new Candidate(++idCandidate, name, email,
                        skillsList, age,
                        state, description, cpf, cep))

                println("Cadastrado com sucesso")
            }
            else if (option == 4) {
                def name = getUserInput("Nome: ")
                def email = getUserInput("E-mail: ")
                def cnpj = getUserInput("Cnpj: ")
                def country = getUserInput("País: ")
                def description = getUserInput("Descrição: ")
                def state = getUserInput("Estado: ")
                def skills = getUserInput("Habilidades (separadas por vírgula): ")
                def cep = getUserInputInt("CEP: ")

                ArrayList<String> skillsList = skills.split(',').collect { it.trim() }

                companies.add(new Company(++idCompany, name, email, cnpj,
                        country,
                        description, state, skillsList, cep))

                println("Cadastrado com sucesso")
            }
            else if(option == 5) {
                Company comp =  checkCompanyID(companies)

                String name = getUserInput("Qual nome da vaga? ")
                String description = getUserInput("Qual descrição da vaga? ")
                String skills = getUserInput("Quais skills necessárias? ")

                vacancies.add(new Vacancy(++idVacancy, name, description,
                        comp, skills.split("[,;]+") as
                        ArrayList<String>))

                println("Vaga criada com sucesso!")
            }
            else if (option == 6) {
                String choose
                boolean checkChoose = true

                while(checkChoose) {
                   choose = getUserInput("Você é empresa ou candidato?\n" +
                           "1 - Empresa\n" + "2 - Candidato\n")
                    if (!choose.matches("^[1-2]\$"))
                        println("Error: opção inválida. Tente novamente")
                    else
                        checkChoose = false
                }

                switch (choose){
                    case "1":
                        Company comp =  checkCompanyID(companies)
                        if (comp.getMatchVacancies().isEmpty())
                            println("Ainda não há candidatos")
                        else {
                            comp.getMatchVacancies().each {match ->
                                println("Id da vaga " + match.getVacancy().getId())
                                println("Id do candidato " + match.
                                        getCandidate().getId())
                                println("Descrição " + match.getCandidate().
                                        getDescription())
                                println("Skills:")
                                match.getCandidate().getSkills().each
                                {skill ->
                                    print(skill + " ")
                                }
                                println()
                                println("------------------------------")
                            }
                            Candidate candi = checkCandidateID(candidates)

                            MatchVacancy matchVacancy = checkMatchVacancyID(candi.getMatchVacancies())

                            comp.getMatchVacancies().find { it.getId() == matchVacancy.getId() }?.setCompanyLiked(true)
                            println("Match realizado!")
                        }
                        break

                    case "2":
                        Candidate candi = checkCandidateID(candidates)

                        if (vacancies.isEmpty())
                            println("Não existem vagas no momento")
                        else {
                            vacancies.each {vacancie ->
                                println("Id da vaga: " + vacancie.getId())
                                println("Titulo: " + vacancie.getName())
                                println("Descrição: " + vacancie.getDescription())
                                println("Skills:")
                                vacancie.getSkills().each {skill ->
                                    print(skill + " ")
                                }
                                println()
                                println("------------------------------")
                            }

                            Vacancy vacancy = checkVacancyID(vacancies)
                            MatchVacancy match = new MatchVacancy(++idMatch,
                                    vacancy, candi, true)

                            candi.getMatchVacancies().add(match)

                            vacancy.getCompany().getMatchVacancies().add(match)
                            println("Vaga curtida!")
                        }
                        break
                }
            }
            else if (option == 7) {
                String choose
                boolean checkChoose = true

                while(checkChoose) {
                    choose = getUserInput("Você é empresa ou candidato?\n" +
                            "1 - Empresa\n" + "2 - Candidato\n")

                    if (!choose.matches("^[1-2]\$"))
                        println("Error: opção inválida. Tente novamente")
                    else
                        checkChoose = false
                }

                switch (choose) {
                    case "1":
                        Company company = checkCompanyID(companies)

                        if (company.getMatchVacancies().isEmpty())
                            println("A sua empresa ainda não deu match")
                        else
                            company.getMatchVacancies().
                                    each {matches ->
                                        matches.getCandidate().showInfo()
                                    }
                        break

                    case "2":
                        Candidate candi = checkCandidateID(candidates)
                        boolean isMatch = false

                        candi.getMatchVacancies().each {match ->
                            if (match.companyLiked) {
                                match.getVacancy().getCompany().showInfo()
                                isMatch = true
                            }
                        }

                        if (!isMatch)
                            println("Ainda não houve match")
                        break
                }
            }
        }

    }

    static MatchVacancy checkMatchVacancyID(ArrayList<MatchVacancy> matches) {
        int index
        MatchVacancy matc

        boolean idFind = true
        while (idFind) {
            index = getUserInputInt("Digite o id da vaga: ")
            matches.each { match ->
                if (match.getId() == index) {
                    matc = match
                    idFind = false
                }
            }
            if (matc == null)
                println("Error: Match não encontrado. Tente " +
                        "novamente")
        }
        return matc
    }

    static Vacancy checkVacancyID(ArrayList<Vacancy> vacancies) {
        int index
        Vacancy vacan

        boolean idFind = true
        while (idFind) {
            index = getUserInputInt("Digite o id da vaga: ")
            vacancies.each { vacancy ->
                if (vacancy.getId() == index) {
                    vacan = vacancy
                    idFind = false
                }
            }
            if (vacan == null)
                println("Error: Vaga não encontrada. Tente " +
                        "novamente")
        }
        return vacan
    }

    static Candidate checkCandidateID(ArrayList<Candidate> candidates) {
        int index
        Candidate candi

        boolean idFind = true
        while (idFind) {
            index = getUserInputInt("Digite o id do candidato: ")
            candidates.each { candidate ->
                if (candidate.getId() == index) {
                    candi = candidate
                    idFind = false
                }
            }
            if (candi == null)
                println("Error: Candidato não encontrado. Tente " +
                        "novamente")
        }
        return candi
    }

    static Company checkCompanyID(ArrayList<Company>companies) {
        int index
        Company comp

        boolean idFind = true
        while (idFind) {
            index = getUserInputInt("Digite o id da sua empresa: ")
            companies.each { companie ->
                if (companie.getId() == index) {
                    comp = companie
                    idFind = false
                }
            }
            if (comp == null)
                println("Error: Empresa não encontrada. Tente " +
                        "novamente")
        }
        return comp
    }

    static String getUserInput(String prompt) {
        BufferedReader reader = new BufferedReader(new InputStreamReader
                (System.in))
        print(prompt)
        def input = reader.readLine()
        while (input.trim().isEmpty()) {
            println("Error: O campo não pode estar vazio. Tente novamente.")
            print(prompt)
            input = reader.readLine()
        }
        return input
    }

    static int getUserInputInt(String prompt) {
        BufferedReader reader = new BufferedReader(new InputStreamReader
                (System.in))
        print(prompt)
        def input = reader.readLine()
        while (!input.isInteger()) {
            println("Error: Entrada inválida. " +
                    "Tente novamente.")
            print(prompt)
            input = reader.readLine()
        }
        return Integer.parseInt(input)
    }
}
