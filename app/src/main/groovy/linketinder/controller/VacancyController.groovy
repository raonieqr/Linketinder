package linketinder.controller

import linketinder.model.dao.impl.VacancyImpl
import linketinder.model.entities.Vacancy

class VacancyController {

    static void addVacancy(Vacancy vacancy, ArrayList<Vacancy> vacancies,
                           VacancyImpl vacancyImpl) {

        vacancies.add(vacancy)
        vacancyImpl.insertVacancy(vacancy)
                           }

}
