package linketinder.model.dao

import linketinder.model.entities.Company
import linketinder.model.entities.Vacancy

interface VacancyDAO {

    void getAllVacancy(ArrayList<Vacancy> vacancies,
                       ArrayList<Company> companies)

    void insertVacancy(Vacancy vacancy)
}