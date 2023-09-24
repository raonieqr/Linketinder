package linketinder.controller

import static org.junit.Assert.assertEquals
import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.*

import linketinder.dao.impl.CompanyImpl
import linketinder.model.entities.Company
import org.junit.jupiter.api.*

class CompanyControllerTest {

    static ArrayList<Company> companies
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream()
    private final PrintStream originalOut = System.out

    @BeforeAll
    public static void createList() {
        companies = []

        companies.add(new Company(1, 'TechCat', 'cat@tech.com', '92839402183649',
                'BR','Empresa de gatos dev' ,'RJ',22096255))

        companies.add(new Company(2, 'GloboTech', 'contact@globo.tech', '12345678901234',
                'BR','Inovação Global Ltda.' ,'SP',12345678))
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
    public void testListCompanies() {
        CompanyController.listCompanies(companies)

        String expectedOutput = 'ID: 1\n' +
            'Nome: TechCat\n' +
            'Email: cat@tech.com\n' +
            'CNPJ: 92839402183649\n' +
            'País: BR\n' +
            'Estado: RJ\n' +
            'CEP: 22096255\n' +
            'Descrição: Empresa de gatos dev\n' +
            '---------------------------------------------------------------\n' +
            'ID: 2\n' +
            'Nome: GloboTech\n' +
            'Email: contact@globo.tech\n' +
            'CNPJ: 12345678901234\n' +
            'País: BR\n' +
            'Estado: SP\n' +
            'CEP: 12345678\n' +
            'Descrição: Inovação Global Ltda.\n' +
            '---------------------------------------------------------------\n'

        assertEquals(expectedOutput, outContent.toString())
    }

    @Test
    public void testAddCompany() {
        CompanyImpl companyImpl = mock(CompanyImpl)

        Company company = new Company(2, 'InovaTech', 'info@inovatech.com',
                '12345678901234',
                'US', 'Company of Innovation', 'CA', 98765432)

        doNothing().when(companyImpl).insertCompany(any(Company))

        CompanyController.addCompany(companies, company, companyImpl)

        verify(companyImpl, times(1))
                .insertCompany(company)
    }

    @Test
    public void testExecuteCompanyDeletion() {
        CompanyImpl companyImpl = mock(CompanyImpl)

        Company company = new Company(2, 'InovaTech', 'info@inovatech.com', '12345678901234',
                'US', 'Company of Innovation', 'CA', 98765432)

        doNothing().when(companyImpl).deleteCompany(any(Company))

        CompanyController.executeCompanyDeletion(company, companyImpl)

        verify(companyImpl, times(1))
                .deleteCompany(company)
    }

}
