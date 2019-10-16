package com.epam.esm.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {

  @Override
  public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
    int columnNumber = 1;
    Long id = resultSet.getLong(columnNumber++);
    String name = resultSet.getString(columnNumber);
    return new Tag(id, name);
  }
}
