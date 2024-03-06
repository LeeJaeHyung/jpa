package me.loy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import me.loy.dao.AccountDAO;
import me.loy.vo.vo.AccountVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JDBCTest {

  @Test
  @DisplayName("테이블 생성 테스트 실습")
  void jdbcTest(){
    String url = "jdbc:postgresql://localhost:5432/messenger";
    String username = "lee";
    String password = "pass";

    //when
    try{
      Connection con = DriverManager.getConnection(url,username,password);
      String creatSql = "CREATE TABLE ACCOUNT (id SERIAL PRIMARY KEY, username varchar(255), password varchar(255))";
      PreparedStatement stmt = con.prepareStatement(creatSql);
      stmt.execute();
      stmt.close();
      con.close();
    }catch(SQLException e){
      e.printStackTrace();
    }
  }

  @Test
  @DisplayName("JDBC 삽입/조회 실습")
  void jdbcInsertSelectTest() throws SQLException {
    // given
    String url = "jdbc:postgresql://localhost:5432/messenger";
    String username = "lee";
    String password = "pass";

    // when
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      System.out.println("Connection created: " + connection);

      String insertSql = "INSERT INTO ACCOUNT (id, username, password) VALUES ((SELECT coalesce(MAX(ID), 0) + 1 FROM ACCOUNT A), 'user1', 'pass1')";
      try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
        statement.execute();
      }

      // then
      String selectSql = "SELECT * FROM ACCOUNT";
      try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
        var rs = statement.executeQuery();
        while (rs.next()) {
          System.out.printf("%d, %s, %s", rs.getInt("id"), rs.getString("username"),
              rs.getString("password"));
        }
      }
    }
  }


  @Test
  @DisplayName("JDBC DAO 삽입/조회 실습")
  void jdbcDAOInsertSelectTest() throws SQLException {
    // given
    AccountDAO accountDAO = new AccountDAO();

    // when
    Integer id = accountDAO.insertAccount(new AccountVO("new user", "new password"));

    // then
    var account = accountDAO.selectAccount(id);
    assert account.getUsername().equals("new user");
  }
}