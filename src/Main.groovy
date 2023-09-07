import db.DBHandler
import model.dao.impl.CandidateImpl
import model.dao.impl.CompanyImpl
import model.dao.impl.MatchVacancyImpl
import model.dao.impl.VacancyImpl
import model.entities.*
import org.junit.jupiter.engine.discovery.predicates.IsNestedTestClass

import java.sql.SQLException

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


            int idCompany = resultIdCompany.max == null? 0: resultIdCompany.max
            int idCandidate = resultIdCandidate.max == null? 0:
                    resultIdCandidate.max
            int idVacancy = resultIdVacancy.max == null? 0: resultIdVacancy.max
            int idMatch = resultIdMatch.max == null? 0: resultIdMatch.max
            int option;

            CandidateImpl candidateImpl = new CandidateImpl()
            CompanyImpl companyImpl = new CompanyImpl()
            VacancyImpl vacancyImpl = new VacancyImpl()
            MatchVacancyImpl matchVacancyImpl = new MatchVacancyImpl()

            ArrayList<Candidate> candidates = new ArrayList<>()
            ArrayList<Company> companies = new ArrayList<>()
            ArrayList<Vacancy> vacancies = new ArrayList<>()

            println("Bem vindo ao LinkeTinder")

            BufferedReader reader = new BufferedReader(new InputStreamReader
                    (System.in))
            while (option != 8) {

                companies.clear()
                vacancies.clear()
                candidates.clear()

                companyImpl.getAllCompanies(companies)
                vacancyImpl.getAllVacancy(vacancies, companies)
                candidateImpl.getAllCandidates(candidates, vacancies)

                Company.getMatch(candidates, companies)

                println("Escolha um dos comandos abaixo:")
                println("1 - Listar empresas")
                println("2 - Listar candidatos")
                println("3 - Adicionar novo candidato")
                println("4 - Adicionar nova empresa")
                println("5 - Criar vaga para empresa");
                println("6 - Curtir");
                println("7 - Visualizar matches");
                println("8 - Sair")

                option = Integer.parseInt(reader.readLine())

                if (option == 1) {
                    if (companies.isEmpty())
                        println("Não há empresas registradas")
                    else
                        companies.each {companie ->
                            companie.showInfo()
                            println("-------------------------------------" +
                                    "--------------------------")
                        }
                }
                else if (option == 2) {
                        if (candidates.isEmpty())
                            println("Não há candidatos cadastrados")
                        else {
                            candidates.each { candidate ->
                                candidate.showInfo()
                                println("-----------------------------------" +
                                        "----------------------------")
                            }
                        }
                }
                else if (option == 3) {

                    def name = getUserInput("Nome: ")
                    def email = getUserInput("E-mail: ")

                    email = checkEmail(candidates, email)

                    def skills = getUserInput("Habilidades (separadas por vírgula): ")
                    def age = getUserInputInt("Idade: ")
                    def state = getUserInput("Estado: ")
                    def description = getUserInput("Descrição: ")
                    def cpf = getUserInput("CPF: ")

                    while(cpf.length() != 11) {
                        cpf = getUserInput("Error: O cpf deve ter 11 " +
                                "caracteres. Tente novamente: ")
                    }

                    cpf = checkCpf(candidates, cpf)

                    def cep = getUserInputInt("CEP: ")

                    ArrayList<String> skillsList = skills.split("[,;\\s]+")
                    skillsList = skillsList.collect { it.toLowerCase() }
                    skillsList = skillsList.collect { it.capitalize() }



                    Candidate candidate = new Candidate(++idCandidate, name,
                            email, skillsList, age, state, description, cpf, cep)
                    candidates.add(candidate)

                    candidateImpl.insertCandidate(candidate)

                    println("Cadastrado com sucesso")
                }
                else if (option == 4) {
                    def name = getUserInput("Nome: ")
                    def email = getUserInput("E-mail: ")

                    email = checkEmail(companies, email)

                    def cnpj = getUserInput("Cnpj: ")

                    while(cnpj.length() != 14) {
                        cnpj = getUserInput("Error: O cnpj deve ter 14 " +
                                "caracteres. Tente novamente: ")
                    }

                    cnpj = checkCnpj(companies, cnpj)

                    def country = getUserInput("País: ")

                    while(country.length() >= 2 &&
                            country.length() <= 3) {
                        country = getUserInput("Error: O País deve ter 2 a 3 " +
                                "caracteres. Tente novamente: ")
                    }

                    def description = getUserInput("Descrição: ")
                    def state = getUserInput("Estado: ")
                    def cep = getUserInputInt("CEP: ")

                    Company company = new Company(++idCompany, name, email,
                            cnpj, country, description, state, cep)

                    companies.add(company)

                    companyImpl.insertCompany(company)

                    println("Cadastrado com sucesso")
                }
                else if(option == 5) {
                    Company comp =  checkCompanyID(companies)

                    String name = getUserInput("Qual nome da vaga? ")
                    String description = getUserInput("Qual descrição da vaga? ")
                    String skills = getUserInput("Quais skills necessárias? ")

                    ArrayList<String> skillsList = skills.split("[,;\\s]+")
                    skillsList = skillsList.collect { it.toLowerCase() }
                    skillsList = skillsList.collect { it.capitalize() }

                    Vacancy vacancy = new Vacancy(++idVacancy, name, description,
                            comp, skillsList)

                    vacancies.add(vacancy)
                    vacancyImpl.insertVacancy(vacancy)

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

                            ArrayList<Integer> idsCandidates = new ArrayList<>()

                            if (comp.getMatchVacancies().isEmpty())
                                println("Ainda não há candidatos")
                            else {
                                comp.getMatchVacancies().each {match ->
                                    if (!match.getCompanyLiked()) {
                                        idsCandidates.add(match
                                           .getCandidate().getId())
                                        println("Id da vaga: " + match.getId())
                                        println("Id do candidato: " + match.
                                                getCandidate().getId())
                                        println("Descrição: " + match.
                                                getCandidate().
                                                getDescription())
                                        println("Skills:")
                                        println(match.getCandidate().getSkills()
                                                .join(", "))
                                        println("------------------------------")
                                    }
                                }
                                Candidate candi

                                boolean isCandidate = false
                                while (!isCandidate) {
                                    if (idsCandidates.isEmpty()) {
                                        println("Não há candidatos")
                                        break
                                    }
                                     candi = checkCandidateID(candidates)
                                    if (idsCandidates.contains(candi.getId())) {
                                        isCandidate = true
                                        break
                                    }
                                }


                                if (candi != null && isCandidate) {
                                    MatchVacancy matchVacancy =
                                            checkMatchVacancyID(candi.
                                                    getMatchVacancies(), candidates)

                                    comp.getMatchVacancies().find { it.getId() == matchVacancy.getId() }?.setCompanyLiked(true)

                                    matchVacancyImpl.updateLikedCompany(matchVacancy)

                                    println("Match realizado!")
                                }
                            }
                            break

                        case "2":
                            Candidate candi = checkCandidateID(candidates)

                            if (vacancies.isEmpty())
                                println("Não existem vagas no momento")
                            else {

                                boolean allVacanciesLiked = true
                                ArrayList<Integer> printedVacancyIds = new ArrayList<>()
                                Set<Integer> idsLikeds = new HashSet<>();

                                vacancies.each { vacancie ->
                                    boolean containsVacancie = false

                                    candi.getMatchVacancies().each { matchingVacancy ->
                                        if (matchingVacancy.getVacancy().
                                                getId() == vacancie.getId()) {
                                            idsLikeds.add(vacancie.getId())
                                            containsVacancie = true
                                            return
                                        }
                                    }

                                    if (!containsVacancie && !printedVacancyIds.contains(vacancie.getId())) {
                                        allVacanciesLiked = false
                                        printedVacancyIds.add(vacancie.getId())
                                        println("Id da vaga: " + vacancie.getId())
                                        println("Titulo: " + vacancie.getName())
                                        println("Descrição: " + vacancie.getDescription())
                                        println("Skills:")
                                        println(vacancie.getSkills().join(", "))
                                        println("------------------------------")
                                    }
                                }

                                if (allVacanciesLiked)
                                    println("Você já curtiu todas as vagas disponíveis.")
                                else {

                                    boolean containsNumber = true

                                    Vacancy vacancy
                                    while (containsNumber) {
                                        containsNumber = false
                                        vacancy = checkVacancyID(vacancies)
                                        for (Integer id : idsLikeds) {
                                            if (id.equals(vacancy.getId())) {
                                                containsNumber = true
                                                println("Você só pode curtir " +
                                                        "as vagas que estão " +
                                                        "na lista")
                                                break
                                            }
                                        }
                                    }

                                    if (vacancy != null && !containsNumber) {
                                        MatchVacancy match = new MatchVacancy(++idMatch, vacancy, candi)

                                        matchVacancyImpl
                                           .insertCandidateLiked(candi, vacancy)

                                        candi.getMatchVacancies().add(match)
                                        println("Vaga curtida!")
                                    }
                                }
                            }

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
                                            print("Vaga: " + matches.
                                                    getVacancy().
                                                    getName() + ", ")
                                            matches.getCandidate().showInfo()
                                        }
                            break

                        case "2":
                            Candidate candi = checkCandidateID(candidates)
                            boolean isMatch = false

                            candi.getMatchVacancies().each {match ->
                                if (match.companyLiked) {
                                    print("Vaga: " + match.
                                            getVacancy().
                                            getName() + ", ")
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

    static String checkEmail(ArrayList<?> object, String email) {
        boolean exist = true
        while (exist) {
            exist = false

            object.each {obj ->
                if (obj.getEmail().equals(email)) {
                    exist = true
                    email = getUserInput("Error: o email já " +
                            "existe. Tente novamente outro email: ")
                }
            }
        }
        return email
    }

    static String checkCnpj(ArrayList<?> object, String cnpj) {
        boolean exist = true
        while (exist) {
            exist = false

            object.each { obj ->
                if (obj.getCnpj().equals(cnpj)) {
                    exist = true
                    cnpj = getUserInput("Error: o cnpj já " +
                            "existe. Tente novamente outro cnpj: ")
                }
            }
        }
        return cnpj
    }

    static String checkCpf(ArrayList<?> object, String cpf) {
        boolean exist = true
        while (exist) {
            exist = false

            object.each {obj ->
                if (obj.getCpf().equals(cpf)) {
                    exist = true
                    cpf = getUserInput("Error: o cpf já " +
                            "existe. Tente novamente outro cpf: ")
                }
            }
        }
        return cpf
    }

    static MatchVacancy checkMatchVacancyID(ArrayList<MatchVacancy> matches,
                                            ArrayList<Candidate> candidates) {
        int index
        MatchVacancy matc

        boolean idFind = true
        boolean isCandidate = true
        while (idFind) {
            if (!isCandidate) {
                def candi = checkCandidateID(candidates)
                matches = candi.getMatchVacancies()
            }
            index = getUserInputInt("Digite o id da vaga: ")
            matches.each { match ->
                if (match.getId() == index) {
                    matc = match
                    idFind = false
                }
            }
            if (matc == null) {
                println("Error: Match não encontrado. Tente " +
                        "novamente")
                isCandidate = false
            }
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

    static Company checkCompanyID(ArrayList<Company> companies) {
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
