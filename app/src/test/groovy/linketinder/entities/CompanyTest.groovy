package linketinder.entities;

import linketinder.model.entities.Company;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {
	@Test
	public void testAddCompany() {

		//Given:
		Company company = new Company("abc", "abc@gmail.com", "01234567891234", "Brasil",
				"Desenvolvedor", "RJ", 22785055, "batatinha");
		ArrayList<Company> companys = new ArrayList<>();

		//When:
		companys.add(company);

		//Then:
		assertTrue(companys.size() == 1);
	}

	@Test
	void testCompanyAttributes() {

		Company company = new Company("abc", "abc@gmail.com", "01234567891234", "Brasil",
				"Desenvolvedor", "RJ", 22785055, "batatinha");

//		assertEquals(1, company.getId());
		assertEquals("abc", company.getName());
		assertEquals("abc@gmail.com", company.getEmail());
		assertEquals("01234567891234", company.getCnpj());
		assertEquals("Brasil", company.getCountry());
		assertEquals("Desenvolvedor", company.getDescription());
		assertEquals("RJ", company.getState());
		assertEquals(22785055, company.getCep());
	}
}