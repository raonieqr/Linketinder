package linketinder.controller

import linketinder.dao.impl.CandidateDAOImpl
import linketinder.model.entities.Candidate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.mockito.Mockito.*

class CandidateControllerTest {
    static ArrayList<Candidate> candidates

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream()
    private final PrintStream originalOut = System.out

    @BeforeAll
    public static void createList(){
        candidates = new ArrayList<>()

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

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent))
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut)
    }

    @Test
    public void testAddCandidate() {
        CandidateDAOImpl candidateImpl = mock(CandidateDAOImpl.class)

        Candidate candidate = new Candidate(3, "Pedro Oliveira",
                "pedro.oliveira@example.com", ["Java", "Spring Boot"],
                28, "SP", "Desenvolvedor Full Stack",
                "12378945600", 98765432)

        doNothing().when(candidateImpl).insertCandidate(any(Candidate.class))

        CandidateController.addCandidate(candidates, candidate, candidateImpl)

        verify(candidateImpl, times(1))
                .insertCandidate(candidate)
    }

    @Test
    public void testExecuteCandidateDeletion() {
        CandidateDAOImpl candidateImpl = mock(CandidateDAOImpl.class)

        Candidate candidate = new Candidate(3, "Pedro Oliveira",
                "pedro.oliveira@example.com", ["Java", "Spring Boot"],
                28, "SP", "Desenvolvedor Full Stack",
                "12378945600", 98765432)

        doNothing().when(candidateImpl).deleteCandidate(any(Candidate.class))

        CandidateController.executeCandidateDeletion(candidate, candidateImpl)

        verify(candidateImpl, times(1))
                .deleteCandidate(candidate)
    }
}
