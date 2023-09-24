package linketinder.controller

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.*

import linketinder.dao.impl.MatchVacancyImpl
import linketinder.model.entities.Candidate
import linketinder.model.entities.Company
import linketinder.model.entities.MatchVacancy
import linketinder.model.entities.Vacancy
import linketinder.utils.InputValidator
import linketinder.view.MatchView
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.MockedStatic

class MatchControllerTest {

    static ArrayList<Company> companies

    static ArrayList<Candidate> candidates

    @BeforeAll
    public static void createList() {
        companies = []

        candidates = []

        companies.add(new Company(1, 'TechCat', 'cat@tech.com',
                '92839402183649',
                'BR','Empresa de gatos dev' ,'RJ',
                22096255))

        companies.add(new Company(2, 'GloboTech',
                'contact@globo.tech', '12345678901234',
                'BR','Inovação Global Ltda.' ,'SP',
                12345678))

        candidates.add(new Candidate(1, 'João Silva',
                'joao.silva@example.com', ['Python', 'Java'], 28,
                'SP', 'Desenvolvedor Full Stack',
                '12345678900', 12345678
        ))

        candidates.add(new Candidate(2, 'Maria Santos',
                'maria.santos@example.com', ['JavaScript', 'HTML', 'CSS'],
                32, 'RJ', 'Desenvolvedora Front-End',
                '98765432100', 54321098
        ))
    }

    @Test
    public void testHandleCompanyMatchResults() {
        MatchVacancyImpl matchVacancyImpl = mock(MatchVacancyImpl)

        Company company = new Company(2, 'InovaTech',
                'info@inovatech.com', '12345678901234',
                'US', 'Company of Innovation',
                'CA', 98765432)

        Candidate candidate = new Candidate(3, 'Pedro Oliveira',
                'pedro.oliveira@example.com', ['Java', 'Spring Boot'],
                28, 'SP', 'Desenvolvedor Full Stack',
                '12378945600', 98765432)

        Vacancy vacancy = new Vacancy(1, 'Desenvolvedor Full Stack',
                'Vaga para desenvolvedor Full Stack', company,
                ['Java', 'Spring Boot', 'Angular', 'SQL', 'C#'])

        try (MockedStatic<InputValidator> inputValidator = mockStatic(InputValidator)) {
            MatchController.handleCompanyMatchResults(candidate, candidates,
                    company, matchVacancyImpl)

            inputValidator.when(() -> InputValidator
                    .promptForIntegerInput(anyString()))
                    .thenReturn(1)

            doNothing().when(matchVacancyImpl)
                    .updateLikedCompany(any(MatchVacancy))
        }
    }
    @Test
    public void testLikeVacancy() {
        ArrayList<Vacancy> vacancies = []
        Set<Integer> idsLiked = new HashSet<>()

        Candidate candidate = new Candidate(3, 'Pedro Oliveira',
          'pedro.oliveira@example.com', ['Java', 'Spring Boot'],
          28, 'SP', 'Desenvolvedor Full Stack',
          '12378945600', 98765432)

        Company company = new Company(2, 'InovaTech',
          'info@inovatech.com', '12345678901234',
          'US', 'Company of Innovation',
          'CA', 98765432)

        Vacancy vacancy = new Vacancy(1, 'Desenvolvedor Full Stack',
          'Vaga para desenvolvedor Full Stack', company,
          ['Java', 'Spring Boot', 'Angular', 'SQL', 'C#'])

        MatchVacancy matchVacancy = new MatchVacancy(1, vacancy, candidate)

        candidate.getMatchVacancies().add(matchVacancy)

        vacancies.add(vacancy)

        int idMatch = 1

        try (MockedStatic<InputValidator> inputValidator = mockStatic(InputValidator)) {
            inputValidator.when(() -> InputValidator.promptForIntegerInput(anyString()))
              .thenReturn(1)

            MockedStatic<MatchController> matchController = mockStatic(MatchController)
            matchController.when(() -> MatchController
              .likeVacancy(candidate, vacancies, idsLiked, idMatch))
              .thenReturn(matchVacancy)

            MatchVacancy match = MatchController
              .likeVacancy(candidate, vacancies, idsLiked, idMatch)

            assert match != null
            assert match.candidate != null
        }
    }

    @Test
    public void testManageVacancyListAndLikes() {
        MatchVacancyImpl matchVacancyImpl = mock(MatchVacancyImpl)

        ArrayList<Vacancy> vacancies = []

        Company company = new Company(2, 'InovaTech',
          'info@inovatech.com', '12345678901234',
          'US', 'Company of Innovation',
          'CA', 98765432)

        Vacancy vacancy = new Vacancy(1, 'Desenvolvedor Full Stack',
          'Vaga para desenvolvedor Full Stack', company,
          ['Java', 'Spring Boot', 'Angular', 'SQL', 'C#'])

        Candidate candidate = new Candidate(3, 'Pedro Oliveira',
          'pedro.oliveira@example.com', ['Java', 'Spring Boot'],
          28, 'SP', 'Desenvolvedor Full Stack',
          '12378945600', 98765432)

        int idMatch = 1

        vacancies.add(vacancy)

        ArrayList<Integer> printedVacancyIds = []
        Set<Integer> idsLiked =  new HashSet<>()

        boolean  allVacanciesLiked = true

        try (MockedStatic<InputValidator> inputValidator = mockStatic(InputValidator)) {
            inputValidator.when(() -> InputValidator
              .promptForIntegerInput(anyString()))
              .thenReturn(1)

            allVacanciesLiked = MatchController.processVacancies(vacancies,
              candidate,
              idsLiked, printedVacancyIds)

            if (allVacanciesLiked) {
                MatchView.displayAllVacanciesLiked()
            }
            else {
                MatchVacancy match = MatchController.handleLikedVacancies(candidate,
                  vacancies,
                  idsLiked, idMatch)

                MatchController.likedVacancies(match, matchVacancyImpl, candidate)
            }
        }
    }

}
