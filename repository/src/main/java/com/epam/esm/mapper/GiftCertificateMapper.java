package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

  @Override
  public GiftCertificate mapRow(ResultSet resultSet, int i) throws SQLException {
    int columnNumber = 1;
    Long id = resultSet.getLong(columnNumber++);
    String name = resultSet.getString(columnNumber++);
    String description = resultSet.getString(columnNumber++);
    BigDecimal price = resultSet.getBigDecimal(columnNumber++);
    LocalDate creationDate = resultSet.getDate(columnNumber++).toLocalDate();
    LocalDate lastModified = resultSet.getDate(columnNumber++).toLocalDate();
    int durationInDays = resultSet.getInt(columnNumber);
    return new GiftCertificate(
        id, name, description, price, creationDate, lastModified, durationInDays);
  }
}
