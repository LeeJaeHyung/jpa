package me.loy.vo.vo;

import java.sql.PreparedStatement;

public class AccountVO {

  private Integer id;

  private String username;

  private String password;

  public AccountVO(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public AccountVO() {

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
