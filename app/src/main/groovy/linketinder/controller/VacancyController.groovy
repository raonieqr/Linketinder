package linketinder.controller

import linketinder.dao.impl.VacancyDAOImpl
import linketinder.model.entities.Vacancy

class VacancyController {

    static void addVacancy(Vacancy vacancy, ArrayList<Vacancy> vacancies,
                           VacancyDAOImpl vacancyImpl) {

        vacancies.add(vacancy)
        vacancyImpl.insertVacancy(vacancy)
    }
}
