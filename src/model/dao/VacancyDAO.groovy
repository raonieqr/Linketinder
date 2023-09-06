package model.dao

import model.entities.Candidate
import model.entities.Company
import model.entities.Vacancy

interface VacancyDAO {
    void getAllVacancy(ArrayList<Vacancy> vacancies, ArrayList<Company> companies)
    void insertVacancy(Vacancy vacancy)
}