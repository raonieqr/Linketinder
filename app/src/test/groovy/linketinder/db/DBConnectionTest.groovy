package linketinder.db

import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.*

public class DBConnectionTest {

	@Test
	public void testConnectionCreation() {
		DBConnection factory = DBConnection.getInstance()

		DBHandler dbHandler1 = factory
			.createConnection("jdbc:postgresql://localhost/linketinder",
				"postgres", "")

		assertThrows(Exception.class, () -> {
			factory.createConnection("jdbc:postgresql://localhost/db2",
					"user2", "password2")
		})

		assertNotNull(dbHandler1)
	}

	@Test
	public void testSameConnectionReuse() {

		DBConnection factory = DBConnection.getInstance()

		DBHandler dbHandler1 = factory
			.createConnection("jdbc:postgresql://localhost/linketinder",
				"postgres", "")

		DBHandler dbHandler2 = factory
			.createConnection("jdbc:postgresql://localhost/linketinder",
				"postgres", "")

		assertSame(dbHandler1, dbHandler2)
	}
}
