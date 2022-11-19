package fr.niavlys.api.db;

public class DatabaseManager {

	private DbConnection dbConnection;
	
	public DatabaseManager(String url, String username, String password, String dbName, Integer port) {
		this.dbConnection = new DbConnection(new DbCredentials(url, username, password, dbName, port));
	}
	
	public DbConnection getDbConnection() {
		return dbConnection;
	}
}
