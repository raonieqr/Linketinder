package linketinder.model

import linketinder.db.DBHandler

class IDGenerator {
    static int getNextCompanyID() {
        def dbHandler = DBHandler.getInstance()
        def sql = dbHandler.getSql()
        def result = sql.firstRow("SELECT max(id) FROM companies")
        return result.max == null ? 0 : result.max
    }

    static int getNextCandidateID() {
        def dbHandler = DBHandler.getInstance()
        def sql = dbHandler.getSql()
        def result = sql.firstRow("SELECT max(id) FROM candidates")
        return result.max == null ? 0 : result.max
    }

    static int getNextVacancyID() {
        def dbHandler = DBHandler.getInstance()
        def sql = dbHandler.getSql()
        def result = sql.firstRow("SELECT max(id) FROM roles")
        return result.max == null ? 0 : result.max
    }

    static int getNextMatchID() {
        def dbHandler = DBHandler.getInstance()
        def sql = dbHandler.getSql()
        def result = sql.firstRow("SELECT max(id) FROM role_matching")
        return result.max == null ? 0 : result.max
    }
}
