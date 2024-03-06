package me.loy.template;

import me.loy.vo.vo.AccountVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class AccountTemplateDAO {

  private final JdbcTemplate jdbcTemplate;

  private final String ACCOUNT_INSERT = "INSERT INTO ACCOUNT (id, username, password) VALUES ((SELECT coalesce(MAX(ID), 0) + 1 FROM ACCOUNT A), ?, ?)";
  private final String ACCOUNT_SELECT = "SELECT * FROM ACCOUNT WHERE id = ? ";

  public AccountTemplateDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Integer insertAccount(AccountVO vo){
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(con -> {
      var ps = con.prepareStatement(ACCOUNT_INSERT, new String[] {"id"});
      ps.setString(1, vo.getUsername());
      ps.setString(2, vo.getPassword());
      return ps;
    },keyHolder);

    return (Integer) keyHolder.getKey();
  }

  public AccountVO selectAccount(Integer id){
    return jdbcTemplate.queryForObject(ACCOUNT_SELECT,new AccountRowMapper(), id);
  }
}
