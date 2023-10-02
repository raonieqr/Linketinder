package linketinder.db

class DBConnection {

    private static DBConnection instance
    private Map<String, DBHandler> connectionMap

    private DBConnection() {
        connectionMap = [:]
    }

    static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection()
        }

        return instance
    }

    DBHandler createConnection(String databaseUrl, String username, String password) {
        String key = "$databaseUrl:$username"

        if (!connectionMap.containsKey(key)) {
            DBHandler dbHandler = new DBHandler(databaseUrl, username, password)

            connectionMap[key] = dbHandler
        }
        return connectionMap[key]
    }

}

