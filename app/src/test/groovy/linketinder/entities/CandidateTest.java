package linketinder.entities;

import linketinder.model.entities.Candidate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CandidateTest {
	@Test
	public void testAddCandidate() {

		//Given:
		ArrayList<String> expectedSkills = new ArrayList<>();
		expectedSkills.add("C");

		Candidate candidate = new Candidate(1, "Beto", "beto@gmail.com", expectedSkills, 18, "RJ",
				"Desenvolvedor", "12344566701", 22785055);
		ArrayList<Candidate> candidates = new ArrayList<>();

		//When:
		candidates.add(candidate);

		//Then:
		assertTrue(candidates.size() == 1);
	}

	@Test
	void testCandidateAttributes() {
		ArrayList<String> expectedSkills = new ArrayList<>();
		expectedSkills.add("C");
		expectedSkills.add("C++");
		expectedSkills.add("Java");
		Candidate candidate = new Candidate(1, "Beto", "beto@gmail.com", expectedSkills, 18, "RJ",
				"Desenvolvedor", "12344566701", 22785055);

		assertEquals(1, candidate.getId());
		assertEquals("Beto", candidate.getName());
		assertEquals("beto@gmail.com", candidate.getEmail());
		assertArrayEquals(expectedSkills.toArray(), candidate.getSkills().toArray());
		assertEquals(18, candidate.getAge());
		assertEquals("RJ", candidate.getState());
		assertEquals("Desenvolvedor", candidate.getDescription());
		assertEquals("12344566701", candidate.getCpf());
		assertEquals(22785055, candidate.getCep());
	}
}