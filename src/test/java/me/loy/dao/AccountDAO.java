package me.loy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import me.loy.vo.vo.AccountVO;

public class AccountDAO {

  private Connection con = null;
  private PreparedStatement stmt = null;
  private ResultSet rs = null;
  private static final String url = "jdbc:postgresql://localhost:5432/messenger";
  private static final String username = "lee";
  private static final String password = "pass";

  private final String ACCOUNT_INSERT = "INSERT INTO ACCOUNT (id, username, password) VALUES ((SELECT coalesce(MAX(ID), 0) + 1 FROM ACCOUNT A), ?, ?)";
  private final String ACCOUNT_SELECT = "SELECT * FROM ACCOUNT WHERE id = ? ";
  
  //CRUD 기능 메소드
  public Integer insertAccount(AccountVO vo){
    var id = -1;
    try{
      String[] returnId = {"id"};
      con = DriverManager.getConnection(url,username,password);
      stmt = con.prepareStatement(ACCOUNT_INSERT, returnId);
      stmt.setString(1, vo.getUsername());
      stmt.setString(2, vo.getPassword());
      stmt.executeUpdate();

      try(ResultSet rs = stmt.getGeneratedKeys()){
        if(rs.next()){
          id = rs.getInt(1);
        }
      }
    }catch(SQLException e){
      e.printStackTrace();
    }
    return id;
  }

  public AccountVO selectAccount(Integer id){
    AccountVO vo = null;
    try{
      con = DriverManager.getConnection(url,username,password);
      stmt = con.prepareStatement(ACCOUNT_SELECT);
      stmt.setInt(1, id);
      var rs = stmt.executeQuery();
      
      if(rs.next()){
        vo = new AccountVO();
        vo.setId(rs.getInt("id"));
        vo.setUsername(rs.getString("username"));
        vo.setPassword(rs.getString("password"));
      }
    }catch(SQLException e){
      e.printStackTrace();
    }
    return vo;
    
  }
  
}
