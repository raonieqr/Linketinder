// package linketinder.entities;
//
// import linketinder.controller.CompanyController;
// import linketinder.model.entities.Company;
// import org.junit.After;
// import org.junit.Before;
// import org.junit.Test;
// import org.junit.jupiter.api.BeforeAll;
//
// import java.io.ByteArrayOutputStream;
// import java.io.PrintStream;
// import java.util.ArrayList;
//
// import static org.junit.Assert.assertEquals;
//
// class CompanyControllerTest {
//	private final ByteArrayOutputStream outContent = new
//ByteArrayOutputStream(); 	private final PrintStream originalOut = System.out;
//	ArrayList<Company> companies;
//
//	@BeforeAll
//	public void createList(){
//		companies = new ArrayList<>();
//		companies.add(new Company("Company 1"));
//		companies.add(new Company("Company 2"));
//	}
//
//	@Before
//	public void setUpStreams() {
//		System.setOut(new PrintStream(outContent));
//	}
//
//	@After
//	public void restoreStreams() {
//		System.setOut(originalOut);
//	}
//
//	@Test
//	public void testListCompanies() {
//
//		CompanyController.listCompanies(companies);
//
//		String expectedOutput = "Company Name: Company 1\n" +
//				"--------------------------------------------\n"
//+ 				"Company Name: Company 2\n" +
//				"--------------------------------------------\n";
//		assertEquals(expectedOutput, outContent.toString());
//	}
// }