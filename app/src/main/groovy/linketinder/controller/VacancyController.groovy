package linketinder.controller

import linketinder.dao.impl.VacancyDAOImpl
import linketinder.model.entities.Vacancy

class VacancyController {

    static void addVacancy(Vacancy vacancy, VacancyDAOImpl vacancyImpl) {

        vacancyImpl.insertVacancy(vacancy)
    }
}
