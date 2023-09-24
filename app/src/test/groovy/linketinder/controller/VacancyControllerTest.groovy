package linketinder.controller

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.*
import static org.mockito.Mockito.times

import linketinder.dao.impl.VacancyImpl
import linketinder.model.entities.Company
import linketinder.model.entities.Vacancy
import org.junit.jupiter.api.*

class VacancyControllerTest {

    static ArrayList<Vacancy> vacancies

    @BeforeAll
    public static void createList() {
        vacancies = []
    }

    @Test
    public void testAddVacancy() {
        VacancyImpl vacancyImpl = mock(VacancyImpl)

        Company company = new Company(1, 'TechCat', 'cat@tech.com',
                '92839402183649',
                'BR','Empresa de gatos dev' ,'RJ',22096255)

        Vacancy vacancy = new Vacancy(1, 'Dev Java JR', 'Desenvolver em Java',
                company, ['Java', 'Spring boot'])

        vacancies.add(vacancy)

        doNothing().when(vacancyImpl).insertVacancy(any(Vacancy))

        VacancyController.addVacancy(vacancy, vacancies, vacancyImpl)

        verify(vacancyImpl, times(1))
                .insertVacancy(vacancy)
    }

}
