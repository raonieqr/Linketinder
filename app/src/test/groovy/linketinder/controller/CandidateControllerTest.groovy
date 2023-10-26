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
        ArrayList<String> expectedSkills = new ArrayList<>()
        expectedSkills.add("C")

        candidates.add(new Candidate("Beto", "beto@gmail.com", expectedSkills, 18, "RJ",
          "Desenvolvedor", "12344566701", 22785055, "batatinha"))

        candidates.add(new Candidate("Maria Santos",
                "maria.santos@example.com", ["JavaScript", "HTML", "CSS"] as
          ArrayList, 32, "RJ", "Desenvolvedora Front-End", "98765432100",
          54321098, "batatinha"))
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

        ArrayList<String> expectedSkills = new ArrayList<>()
        expectedSkills.add("C")

        Candidate candidate = new Candidate("Beto", "beto@gmail.com", expectedSkills, 18, "RJ",
          "Desenvolvedor", "12344566701", 22785055, "batatinha")

        doNothing().when(candidateImpl).insertCandidate(any(Candidate.class))

        CandidateController.addCandidate(candidate, candidateImpl)

        verify(candidateImpl, times(1))
                .insertCandidate(candidate)
    }

    @Test
    public void testExecuteCandidateDeletion() {
        CandidateDAOImpl candidateImpl = mock(CandidateDAOImpl.class)

        ArrayList<String> expectedSkills = new ArrayList<>()
        expectedSkills.add("C")

        Candidate candidate = new Candidate("Beto", "beto@gmail.com", expectedSkills, 18, "RJ",
          "Desenvolvedor", "12344566701", 22785055, "batatinha")

        doNothing().when(candidateImpl).deleteCandidate(any(Candidate.class))

        CandidateController.executeCandidateDeletion(candidate, candidateImpl)

        verify(candidateImpl, times(1))
                .deleteCandidate(candidate)
    }
}
