package model.dao

import model.entities.Vacancy

interface VacancyDAO {
    void insertVacancy(Vacancy vacancy)
}