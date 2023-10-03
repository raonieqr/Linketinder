package linketinder.db

import groovy.sql.Sql
import linketinder.db.DBHandler

class DBConnection {

    static DBConnection instance
    Map<String, DBHandler> connectionMap

    DBConnection() {
        connectionMap = [:]
    }

    static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection()
        }

        return instance
    }

    DBHandler createConnection(Sql sql, String databaseUrl, String username) {
        String key = "$databaseUrl:$username"

        if (!connectionMap.containsKey(key)) {
            DBHandler dbHandler = new DBHandler(sql)
            connectionMap[key] = dbHandler
        }
        return connectionMap[key]
    }

}
