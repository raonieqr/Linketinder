package linketinder.controller

import static org.junit.Assert.assertEquals
import static org.mockito.Mockito.*

import linketinder.dao.impl.CandidateImpl
import linketinder.model.entities.Candidate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CandidateControllerTest {

    static ArrayList<Candidate> candidates

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream()
    private final PrintStream originalOut = System.out

    @BeforeAll
    public static void createList() {
        candidates = []

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

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent))
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut)
    }

    @Test
    public void testListCandidates() {
        CandidateController.listCandidates(candidates)

        String expectedOutput = 'ID: 1\n' +
        'Nome: João Silva\n' +
        'Email: joao.silva@example.com\n' +
        'CPF: 12345678900\n' +
        'Estado: SP\n' +
        'CEP: 12345678\n' +
        'Descrição: Desenvolvedor Full Stack\n' +
        'Competências: Python, Java\n' +
        '---------------------------------------------------------------\n' +
        'ID: 2\n' +
        'Nome: Maria Santos\n' +
        'Email: maria.santos@example.com\n' +
        'CPF: 98765432100\n' +
        'Estado: RJ\n' +
        'CEP: 54321098\n' +
        'Descrição: Desenvolvedora Front-End\n' +
        'Competências: JavaScript, HTML, CSS\n' +
        '---------------------------------------------------------------\n'

        assertEquals(expectedOutput, outContent.toString())
    }

    @Test
    public void testAddCandidate() {
        CandidateImpl candidateImpl = mock(CandidateImpl)

        Candidate candidate = new Candidate(3, 'Pedro Oliveira',
                'pedro.oliveira@example.com', ['Java', 'Spring Boot'],
                28, 'SP', 'Desenvolvedor Full Stack',
                '12378945600', 98765432)

        doNothing().when(candidateImpl).insertCandidate(any(Candidate))

        CandidateController.addCandidate(candidates, candidate, candidateImpl)

        verify(candidateImpl, times(1))
                .insertCandidate(candidate)
    }

    @Test
    public void testExecuteCandidateDeletion() {
        CandidateImpl candidateImpl = mock(CandidateImpl)

        Candidate candidate = new Candidate(3, 'Pedro Oliveira',
                'pedro.oliveira@example.com', ['Java', 'Spring Boot'],
                28, 'SP', 'Desenvolvedor Full Stack',
                '12378945600', 98765432)

        doNothing().when(candidateImpl).deleteCandidate(any(Candidate))

        CandidateController.executeCandidateDeletion(candidate, candidateImpl)

        verify(candidateImpl, times(1))
                .deleteCandidate(candidate)
    }

}
