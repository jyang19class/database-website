package selenium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class runMain {

	
	static final String JDBC_Driver = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/webcrawl";
	
	static final String USER = "root";
	static final String PASS = null/*your pass*/;
	
	public static void main(String args[]) {
		ArrayList<Website> articles = new ArrayList<Website>();
		articles.addAll(USATodayScraper.getArticles());
		articles.addAll(NYTScraper.getArticles());
		articles.addAll(FoxScraper.getArticles());
		
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement myInsert = null;	
		
		 try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			stmt = conn.createStatement();
			String clear = "TRUNCATE website";
			String insert = "INSERT INTO website(website, url, title, author, dateposted) VALUES(?,?,?,?,?)";
			myInsert = conn.prepareStatement(insert);
			stmt.execute(clear);
			
			for (Website article: articles) {
				myInsert.setString(1, article.getWebsite());
				myInsert.setString(2, article.getUrl());
				myInsert.setString(3, article.getTitle());
				myInsert.setString(4, article.getAuthor());
				myInsert.setString(5, article.getDatePosted());
				myInsert.executeUpdate();
			}
			conn.close();
			stmt.close();
			myInsert.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
	}
}