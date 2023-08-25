package test.entities;

import entities.Company;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {
	@Test
	public void testAddCompany() {

		//Given:
		ArrayList<String> expectedSkills = new ArrayList<>();
		expectedSkills.add("C");

		Company company = new Company(1, "abc", "abc@gmail.com", "01234567891234", "Brasil",
				"Desenvolvedor", "RJ", expectedSkills,22785055);
		ArrayList<Company> companys = new ArrayList<>();

		//When:
		companys.add(company);

		//Then:
		assertTrue(companys.size() == 1);
	}

	@Test
	void testCompanyAttributes() {

		ArrayList<String> expectedSkills = new ArrayList<>();
		expectedSkills.add("C");
		expectedSkills.add("C++");
		expectedSkills.add("Java");

		Company company = new Company(1,"abc", "abc@gmail.com", "01234567891234", "Brasil",
				"Desenvolvedor", "RJ", expectedSkills,22785055);

		assertEquals(1, company.getId());
		assertEquals("abc", company.getName());
		assertEquals("abc@gmail.com", company.getEmail());
		assertEquals("01234567891234", company.getCnpj());
		assertEquals("Brasil", company.getCountry());
		assertEquals("Desenvolvedor", company.getDescription());
		assertEquals("RJ", company.getState());
		assertArrayEquals(expectedSkills.toArray(), company.getSkills().toArray());
		assertEquals(22785055, company.getCep());
	}
}