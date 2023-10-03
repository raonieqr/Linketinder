package linketinder.db

import groovy.sql.Sql
import org.junit.jupiter.api.Test
import static org.mockito.Mockito.*
import static org.junit.jupiter.api.Assertions.*

public class DBConnectionTest {

	@Test
	public void testConnectionCreation() {
		DBConnection factory = DBConnection.getInstance()
		DBHandler dbHandlerMock = mock(DBHandler.class)
		Sql sqlMock = mock(Sql.class)

		String dbUrl = "jdbc:postgresql://localhost/linketinder"
		String dbUser = "postgres"
		String dbPass = ""

		when(dbHandlerMock.getSql()).thenReturn(sqlMock)

		DBHandler dbHandler1 = factory.createConnection(sqlMock, dbUrl, dbUser)

		assertNotNull(dbHandler1)
	}

	@Test
	public void testSameConnectionReuse() {
		DBConnection factory = DBConnection.getInstance()
		DBHandler dbHandlerMock = mock(DBHandler.class)
		Sql sqlMock = mock(Sql.class)

		String dbUrl = "jdbc:postgresql://localhost/linketinder"
		String dbUser = "postgres"

		when(dbHandlerMock.getSql()).thenReturn(sqlMock)

		DBHandler dbHandler1 = factory.createConnection(sqlMock, dbUrl, dbUser)

		DBHandler dbHandler2 = factory.createConnection(sqlMock, dbUrl, dbUser)

		assertSame(dbHandler1, dbHandler2)
	}
}
