package linketinder

import linketinder.controller.CandidateController
import linketinder.controller.CompanyController
import linketinder.controller.MatchController
import linketinder.controller.VacancyController
import linketinder.db.DBHandler
import linketinder.db.DatabaseRefresh
import linketinder.db.IDGenerator
import linketinder.model.dao.impl.CandidateImpl
import linketinder.model.dao.impl.CompanyImpl
import linketinder.model.dao.impl.MatchVacancyImpl
import linketinder.model.dao.impl.VacancyImpl
import linketinder.model.entities.Candidate
import linketinder.model.entities.Company
import linketinder.model.entities.Vacancy
import linketinder.utils.InputValidator
import linketinder.view.CandidateView
import linketinder.view.CompanyView
import linketinder.view.MatchView
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

                DatabaseRefresh updater = new DatabaseRefresh(companyImpl,
                        vacancyImpl, candidateImpl)

                DatabaseRefresh.updateDataFromDatabase(companies, vacancies,
                        candidates, updater)

                // TODO: delete company and candidate

                option = InputValidator.displayMenuAndGetOption()

                switch (option) {

                    case 1:
                        CompanyController.listCompanies(companies)
                        break

                    case 2:
                        CandidateController.listCandidates(candidates)
                        break

                    case 3:
                        Candidate candidate = CandidateView
                                .createCandidate(++idCandidate, candidates)

                        CandidateController
                                .addCandidate(candidates, candidate, candidateImpl)

                        println("Cadastrado com sucesso")

                        break

                    case 4:
                        Company company = CompanyView
                                .createCompany(++idCompany, companies)

                        CompanyController
                                .addCompany(companies, company, companyImpl)

                        println("Cadastrado com sucesso")

                        break

                    case 5:
                        Company company =  InputValidator.findCompanyByID(companies)

                        Vacancy vacancy = VacancyView.createVacancy(++idVacancy,
                                company)

                        VacancyController.addVacancy(vacancy, vacancies,
                                vacancyImpl)

                        println("Vaga criada com sucesso!")

                        break

                    case 6:
                        int choose = InputValidator.getUserTypeChoice()

                        switch (choose){
                            case 1:
                                Company company =  InputValidator
                                        .findCompanyByID(companies)

                                Candidate candidate = CompanyView
                                        .processCompanyMatches(company, candidates)

                                if (candidate != null)
                                    CompanyController.processCompanyMatches(candidate,
                                    candidates, company,matchVacancyImpl)

                                println("Match realizado!")

                                break

                            case 2:
                                Candidate candidate = InputValidator
                                        .findCandidateByID(candidates)

                                MatchController.listAvailableVacancies(candidate,
                                        vacancies, matchVacancyImpl, ++idMatch)

                                println("Vaga curtida!")

                                break
                        }

                        break

                    case 7:
                        int choose = InputValidator.getUserTypeChoice()

                        switch (choose) {
                            case 1:
                                Company company = InputValidator
                                        .findCompanyByID(companies)

                                MatchView.displayCompanyMatches(company)

                                break

                            case 2:
                                Candidate candidate = InputValidator
                                        .findCandidateByID(candidates)

                                MatchView.displayCandidateMatches(candidate)

                                break
                        }

                        break

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
}