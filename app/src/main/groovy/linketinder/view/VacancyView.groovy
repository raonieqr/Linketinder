package linketinder.view

import linketinder.model.entities.Company
import linketinder.model.entities.Vacancy
import linketinder.utils.InputValidator

class VacancyView {

    static Vacancy createVacancy(int idVacancy, Company company) {

        String name = InputValidator
                .promptForUserInput("Qual nome da vaga? ")
        String description = InputValidator
                .promptForUserInput("Qual descrição da vaga? ")
        String skills = InputValidator
                .promptForUserInput("Quais skills necessárias? ")

        ArrayList<String> skillsList = skills.split("[,;\\s]+")
        skillsList = skillsList.collect { it.toLowerCase() }
        skillsList = skillsList.collect { it.capitalize() }

        return new Vacancy(idVacancy, name, description,
                company, skillsList)
    }
}
