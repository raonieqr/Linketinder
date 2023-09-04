import db.DBHandler
import entities.*

class Main {
    static void main(String[] args) {
        def dbHandler = DBHandler.getInstance()
        try {
            def sql = dbHandler.getSql()
            def resultIdCompany = sql.firstRow("SELECT max(id) FROM companies")
            def resultIdCandidate = sql.firstRow("SELECT max(id) FROM " +
                    "candidates")
            def resultIdVacancy = sql.firstRow("SELECT max(id) FROM " +
                    "roles")
            def resultIdMatch = sql.firstRow("SELECT max(id) FROM " +
                    "role_matching")

            println("Bem vindo ao LinkeTinder")
            int idCompany = resultIdCompany.max == null? 0: resultIdCompany.max
            int idCandidate = resultIdCandidate.max == null? 0:
                    resultIdCandidate.max
            int idVacancy = resultIdVacancy.max == null? 0: resultIdVacancy.max
            int idMatch = resultIdMatch.max == null? 0: resultIdMatch.max
            int option;

            ArrayList<Candidate> candidates = new ArrayList<>()
            ArrayList<Company> companies = new ArrayList<>()
            ArrayList<Vacancy> vacancies = new ArrayList<>()

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
                    def viewAllCompanies = sql.rows("SELECT * FROM " +
                            "companies")
                    viewAllCompanies.each {companie ->
                        println("Id:" + companie.id + " Nome:" + companie.
                                name +
                                " E-mail:" +
                                companie.email + " Estado:" + companie.state +
                                " País:" +
                                companie.country)
                        println("---------------------------------------------------------------")
                    }
                }
                else if (option == 2) {
                    def viewAllCandidates = sql.rows("SELECT * FROM " +
                            "candidates")
                    viewAllCandidates.each {candidate ->
                        def viewCandidateSkill = sql.rows("SELECT " +
                                "DISTINCT skills.description AS skill\n" +
                                "FROM candidates\n" +
                                "JOIN candidate_skills ON $candidate.id = " +
                                "candidate_skills.id_candidate\n" +
                                "JOIN skills ON candidate_skills.id_skill = skills.id")
                        println("Id:" + candidate.id + " Nome:" + candidate.name +
                                " E-mail:" +
                                candidate.email + " Descrição: " + candidate.
                                description + " " +
                                "Estado:" +
                                candidate.state +
                                " Skills:" + viewCandidateSkill.skill.join("," +
                                " "))
                        println("---------------------------------------------------------------")
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
                    candidates.add(new Candidate(++idCandidate, name, email, age,
                            state, description, cpf, cep))
                    sql.executeInsert(""" 
                            INSERT INTO candidates (NAME, CEP, CPF, STATE, AGE, DESCRIPTION, EMAIL, PASSWORD) VALUES ($name, $cep, $cpf, $state, $age, $description, $email, 'batatinha')
                    """);
                    println("Cadastrado com sucesso")
                }
                else if (option == 4) {
                    def name = getUserInput("Nome: ")
                    def email = getUserInput("E-mail: ")
                    def cnpj = getUserInput("Cnpj: ")
                    def country = getUserInput("País: ")
                    def description = getUserInput("Descrição: ")
                    def state = getUserInput("Estado: ")
//                    def skills = getUserInput("Habilidades (separadas por vírgula): ")
                    def cep = getUserInputInt("CEP: ")

//                    ArrayList<String> skillsList = skills.split(',').collect { it.trim() }

                    companies.add(new Company(++idCompany, name, email, cnpj,
                            country,
                            description, state, cep))
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
        catch (Exception e) {
            e.printStackTrace()
        }
        finally {
            dbHandler.close()
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
