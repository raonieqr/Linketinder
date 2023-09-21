package linketinder.db

import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import linketinder.db.DBHandler

class IDGenerator {
    static int getNextCompanyID() {
        DBHandler dbHandler = DBHandler.getInstance()

        Sql sql = dbHandler.getSql()

        GroovyRowResult result = sql.firstRow("SELECT max(id) FROM companies")

        return result.max == null ? 0 : result.max
    }

    static int getNextCandidateID() {
        DBHandler dbHandler = DBHandler.getInstance()

        Sql sql = dbHandler.getSql()

        GroovyRowResult result = sql.firstRow("SELECT max(id) FROM candidates")

        return result.max == null ? 0 : result.max
    }

    static int getNextVacancyID() {
        DBHandler dbHandler = DBHandler.getInstance()

        Sql sql = dbHandler.getSql()

        GroovyRowResult result = sql.firstRow("SELECT max(id) FROM roles")

        return result.max == null ? 0 : result.max
    }

    static int getNextMatchID() {
        DBHandler dbHandler = DBHandler.getInstance()

        Sql sql = dbHandler.getSql()

        GroovyRowResult result = sql.firstRow("SELECT max(id) FROM role_matching")
        
        return result.max == null ? 0 : result.max
    }
}
