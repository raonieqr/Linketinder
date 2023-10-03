package linketinder.db

import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.*

public class DBConnectionTest {

	@Test
	public void testConnectionCreation() {
		DBConnection factory = DBConnection.getInstance()

    String dbUrl = "jdbc:postgresql://localhost/linketinder"
    String dbUser = "postgres"
    String dbPass = ""

		DBHandler dbHandler1 = factory
			.createConnection(dbUrl, dbUser, dbPass)

		assertThrows(Exception.class, () -> {
			factory.createConnection("jdbc:postgresql://localhost/db2",
					"user2", "password2")
		})

		assertNotNull(dbHandler1)
	}

	@Test
	public void testSameConnectionReuse() {

		DBConnection factory = DBConnection.getInstance()

    String dbUrl = "jdbc:postgresql://localhost/linketinder"
    String dbUser = "postgres"
    String dbPass = ""

		DBHandler dbHandler1 = factory
      .createConnection(dbUrl, dbUser, dbPass)

		DBHandler dbHandler2 = factory
      .createConnection(dbUrl, dbUser, dbPass)

		assertSame(dbHandler1, dbHandler2)
	}
}
