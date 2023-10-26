package linketinder.controller

import linketinder.dao.impl.CompanyDAOImpl
import linketinder.model.entities.Company
import org.junit.jupiter.api.*

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.*


class CompanyControllerTest {
	static ArrayList<Company> companies
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream()
	private final PrintStream originalOut = System.out

	@BeforeAll
	public static void createList(){
		companies = new ArrayList<>()

		companies.add(new Company("TechCat","cat@tech.com", "92839402183649",
				"BR","Empresa de gatos dev" ,"RJ",22096255, "batatinha"))

		companies.add(new Company("GloboTech","contact@globo.tech", "12345678901234",
				"BR","Inovação Global Ltda." ,"SP",12345678, "batatinha"))
	}

	@BeforeEach
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent))
	}

	@AfterEach
	public void restoreStreams() {
		System.setOut(originalOut)
	}

//	@Test
//	public void testListCompanies() {
//		CompanyView.showCompanies(companies)
//
//		String expectedOutput = "ID: 1\n" +
//			"Nome: TechCat\n" +
//			"Email: cat@tech.com\n" +
//			"CNPJ: 92839402183649\n" +
//			"País: BR\n" +
//			"Estado: RJ\n" +
//			"CEP: 22096255\n" +
//			"Descrição: Empresa de gatos dev\n" +
//			"---------------------------------------------------------------\n" +
//			"ID: 2\n" +
//			"Nome: GloboTech\n" +
//			"Email: contact@globo.tech\n" +
//			"CNPJ: 12345678901234\n" +
//			"País: BR\n" +
//			"Estado: SP\n" +
//			"CEP: 12345678\n" +
//			"Descrição: Inovação Global Ltda.\n" +
//			"---------------------------------------------------------------\n"
//
//		assertEquals(expectedOutput, outContent.toString())
//	}


	@Test
	public void testAddCompany() {
		CompanyDAOImpl companyImpl = mock(CompanyDAOImpl.class)

		Company company = new Company("InovaTech", "info@inovatech.com",
				"12345678901234",
				"US", "Company of Innovation", "CA", 98765432, "batatinha")

		doNothing().when(companyImpl).insertCompany(any(Company.class))

		CompanyController.addCompany(company, companyImpl)

		verify(companyImpl, times(1))
				.insertCompany(company)
	}

	@Test
	public void testExecuteCompanyDeletion() {
		CompanyDAOImpl companyImpl = mock(CompanyDAOImpl.class)

		Company company =new Company("InovaTech", "info@inovatech.com", "12345678901234",
				"US", "Company of Innovation", "CA", 98765432, "batatinha")

		doNothing().when(companyImpl).deleteCompany(any(Company.class))

		CompanyController.executeCompanyDeletion(company, companyImpl)

		verify(companyImpl, times(1))
				.deleteCompany(company)
	}

}