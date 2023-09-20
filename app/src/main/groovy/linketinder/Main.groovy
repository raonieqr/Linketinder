import linketinder.controller.CandidateController
import linketinder.controller.CompanyController
import linketinder.controller.VacancyController
import linketinder.db.DBHandler
import linketinder.model.IDGenerator
import linketinder.model.dao.impl.CandidateImpl
import linketinder.model.dao.impl.CompanyImpl
import linketinder.model.dao.impl.MatchVacancyImpl
import linketinder.model.dao.impl.VacancyImpl
import linketinder.model.entities.Candidate
import linketinder.model.entities.Company
import linketinder.model.entities.MatchVacancy
import linketinder.model.entities.Vacancy
import linketinder.view.CandidateView
import linketinder.view.CompanyView
import linketinder.view.VacancyView

class Main {
    static void main(String[] args) {

        DBHandler dbHandler = DBHandler.getInstance()

        try {
            int idCompany = IDGenerator.getNextCompanyID()
            int idCandidate = IDGenerator.getNextCandidateID()
            int idVacancy = IDGenerator.getNextVacancyID()
            int idMatch = IDGenerator.getNextMatchID()
            int option

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

                // TODO: delete company and candidate

                println("Escolha um dos comandos abaixo:")
                println("1 - Listar empresas")
                println("2 - Listar candidatos")
                println("3 - Adicionar novo candidato")
                println("4 - Adicionar nova empresa")
                println("5 - Criar vaga para empresa");
                println("6 - Curtir");
                println("7 - Visualizar matches");
                println("8 - Sair")

                String getInput = reader.readLine()
                option = Integer.parseInt(getInput)

                if (option == 1)
                    CompanyController.listCompanies(companies)

                else if (option == 2)
                    CandidateController.listCandidates(candidates)

                else if (option == 3) {

                    Candidate candidate = CandidateView
                            .createCandidate(++idCandidate, candidates)

                    CandidateController
                            .addCandidate(candidates, candidate, candidateImpl)

                    println("Cadastrado com sucesso")
                }
                else if (option == 4) {

                    Company company = CompanyView
                            .createCompany(++idCompany, companies)

                    CompanyController
                            .addCompany(companies, company, companyImpl)

                    println("Cadastrado com sucesso")
                }
                else if(option == 5) {
                    Company company =  checkCompanyID(companies)

                    Vacancy vacancy = VacancyView.createVacancy(++idVacancy,
                            company)

                    VacancyController.addVacancy(vacancy, vacancies,
                            vacancyImpl)

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
                            Company company =  checkCompanyID(companies)

                            Candidate candidate = CompanyView
                                    .processCompanyMatches(company, candidates)

                            if (candidate != null)
                                CompanyController.processCompanyMatches(candidate,
                                candidates, company,matchVacancyImpl)

                            println("Match realizado!")

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
                                            if (id == vacancy.getId()) {
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
