package linketinder

import linketinder.controller.CandidateController
import linketinder.controller.CompanyController
import linketinder.controller.MatchController
import linketinder.controller.VacancyController
import linketinder.db.DBHandler
import linketinder.db.DatabaseRefresh
import linketinder.db.IDGenerator
import linketinder.dao.impl.CandidateImpl
import linketinder.dao.impl.CompanyImpl
import linketinder.dao.impl.MatchVacancyImpl
import linketinder.dao.impl.VacancyImpl
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
            int option = 0

            CandidateImpl candidateImpl = CandidateImpl.getInstance()
            CompanyImpl companyImpl = CompanyImpl.getInstance()
            VacancyImpl vacancyImpl = VacancyImpl.getInstance()
            MatchVacancyImpl matchVacancyImpl = MatchVacancyImpl.getInstance()

            ArrayList<Candidate> candidates = []
            ArrayList<Company> companies = []
            ArrayList<Vacancy> vacancies = []

            println('Bem vindo ao LinkeTinder')

            while (option != 9) {
                DatabaseRefresh updater = new DatabaseRefresh(companyImpl,
                        vacancyImpl, candidateImpl)

                DatabaseRefresh.updateDataFromDatabase(companies, vacancies,
                        candidates, updater)

                option = InputValidator.displayMenuAndGetOption()

                switch (option) {
                    // TODO: a view chama o controller e o controller chama o
                    //  service

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

                        println('Cadastrado com sucesso')

                        break

                    case 4:
                        Company company = CompanyView
                                .createCompany(++idCompany, companies)

                        CompanyController
                                .addCompany(companies, company, companyImpl)

                        println('Cadastrado com sucesso')

                        break

                    case 5:
                        Company company =  InputValidator.findCompanyByID(companies)

                        Vacancy vacancy = VacancyView.createVacancy(++idVacancy,
                                company)

                        VacancyController.addVacancy(vacancy, vacancies,
                                vacancyImpl)

                        println('Vaga criada com sucesso!')

                        break

                    case 6:
                        int choose = InputValidator.getUserTypeChoice()

                        switch (choose) {
                            case 1:
                                Company company =  InputValidator
                                        .findCompanyByID(companies)

                                Candidate candidate = CompanyView
                                        .processCompanyMatches(company, candidates)

                                if (candidate != null) {
                                    MatchController
                                            .handleCompanyMatchResults(candidate,
                                            candidates, company,matchVacancyImpl)

                                    println('Match realizado!')
                                }

                                break

                            case 2:
                                Candidate candidate = InputValidator
                                        .findCandidateByID(candidates)

                                MatchController.manageVacancyListAndLikes(candidate,
                                        vacancies, matchVacancyImpl, ++idMatch)

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

                    case 8:
                        int choose = InputValidator.getUserTypeChoice()

                        switch (choose) {
                            case 1:
                                CompanyController.listCompanies(companies)

                                Company company = CompanyView
                                        .getCompanyToDeleteByID(companies)

                                CompanyController
                                        .executeCompanyDeletion(company,
                                                companyImpl)

                                println("Empresa ${company.getName()} deletada")

                                break
                            case 2:
                                CandidateController.listCandidates(candidates)

                                Candidate candidate = CandidateView
                                        .getCandidateToDeleteByID(candidates)

                                CandidateController
                                        .executeCandidateDeletion(candidate,
                                                candidateImpl)

                                println("Candidato ${candidate.getName()} " +
                                        'deletado')
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

}
