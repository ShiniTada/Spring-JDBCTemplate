package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

  private static final String RETURNING =
      "RETURNING id, name, description, price, creation_date, last_modified, duration_in_days;";

  private static final String INSERT_GIFT_CERTIFICATE =
      "INSERT INTO gift_certificate (name, description, price, creation_date, last_modified, "
          + "duration_in_days) VALUES (?,?,?,now(),now(),?) "
          + RETURNING;

  private static final String UPDATE_GIFT_CERTIFICATE =
      "UPDATE  gift_certificate SET (name, description, price, last_modified, duration_in_days) "
          + " = (?,?,?,now(),?) WHERE id = ? "
          + RETURNING;

  private static final String DELETE_GIFT_CERTIFICATE_BY_ID =
      "DELETE FROM  gift_certificate WHERE id = ?;";

  private static final String DELETE_TAGS_FROM_GIFT_CERTIFICATE =
      "DELETE FROM gift_certificate_to_tag WHERE gift_certificate_id = ?;";

  @Autowired private JdbcTemplate jdbcTemplate;

  private GiftCertificateMapper giftCertificateMapper = new GiftCertificateMapper();

  @Override
  public GiftCertificate getBySpecification(Specification specification) {
    try {
      return jdbcTemplate.queryForObject(
          specification.getSql(), specification.getArguments(), new GiftCertificateMapper());
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public List<GiftCertificate> getListBySpecification(Specification specification) {
    return jdbcTemplate.query(
        specification.getSql(), specification.getArguments(), new GiftCertificateMapper());
  }

  @Override
  public GiftCertificate create(GiftCertificate giftCertificate) {
    try {
      return jdbcTemplate.queryForObject(
          INSERT_GIFT_CERTIFICATE,
          new Object[] {
            giftCertificate.getName(),
            giftCertificate.getDescription(),
            giftCertificate.getPrice(),
            giftCertificate.getDurationInDays()
          },
          giftCertificateMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public GiftCertificate update(GiftCertificate giftCertificate) {
    try {
      return jdbcTemplate.queryForObject(
          UPDATE_GIFT_CERTIFICATE,
          new Object[] {
            giftCertificate.getName(),
            giftCertificate.getDescription(),
            giftCertificate.getPrice(),
            giftCertificate.getDurationInDays(),
            giftCertificate.getId()
          },
          giftCertificateMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public void delete(GiftCertificate giftCertificate) {
    jdbcTemplate.update(DELETE_TAGS_FROM_GIFT_CERTIFICATE, giftCertificate.getId());
    jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_BY_ID, giftCertificate.getId());
  }
}
