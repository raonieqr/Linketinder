package linketinder.controller

import linketinder.dao.impl.MatchVacancyImpl
import linketinder.model.entities.Candidate
import linketinder.model.entities.Company
import linketinder.model.entities.MatchVacancy
import linketinder.model.entities.Vacancy
import linketinder.utils.InputValidator
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.Spy

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.*

class MatchControllerTest {
    static ArrayList<Company> companies

    static ArrayList<Candidate> candidates

    @BeforeAll
    public static void createList(){

        companies = new ArrayList<>()

        candidates = new ArrayList<>()

        companies.add(new Company(1, "TechCat","cat@tech.com",
                "92839402183649",
                "BR","Empresa de gatos dev" ,"RJ",
                22096255))

        companies.add(new Company(2, "GloboTech",
                "contact@globo.tech", "12345678901234",
                "BR","Inovação Global Ltda." ,"SP",
                12345678))

        candidates.add(new Candidate(1, "João Silva",
                "joao.silva@example.com", ["Python", "Java"], 28,
                "SP", "Desenvolvedor Full Stack",
                "12345678900", 12345678
        ))

        candidates.add(new Candidate(2, "Maria Santos",
                "maria.santos@example.com", ["JavaScript", "HTML", "CSS"],
                32, "RJ", "Desenvolvedora Front-End",
                "98765432100", 54321098
        ))
    }

    @Test
    public void testHandleCompanyMatchResults() {
        MatchVacancyImpl matchVacancyImpl = mock(MatchVacancyImpl.class)

        Company company = new Company(2, "InovaTech",
                "info@inovatech.com", "12345678901234",
                "US", "Company of Innovation",
                "CA", 98765432)

        Candidate candidate = new Candidate(3, "Pedro Oliveira",
                "pedro.oliveira@example.com", ["Java", "Spring Boot"],
                28, "SP", "Desenvolvedor Full Stack",
                "12378945600", 98765432);

        Vacancy vacancy = new Vacancy(1, "Desenvolvedor Full Stack",
                "Vaga para desenvolvedor Full Stack", company,
                ["Java", "Spring Boot", "Angular", "SQL", "C#"])

        try (MockedStatic<InputValidator> inputValidator = mockStatic(InputValidator.class)) {
            MatchController.handleCompanyMatchResults(candidate, candidates,
                    company, matchVacancyImpl)

            inputValidator.when(() -> InputValidator
                    .promptForIntegerInput(anyString()))
                    .thenReturn(1)

            doNothing().when(matchVacancyImpl)
                    .updateLikedCompany(any(MatchVacancy.class))

        }
    }
}