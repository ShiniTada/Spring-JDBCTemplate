package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository {

  private static final String RETURNING = "RETURNING id, name;";

  private static final String INSERT_TAG = "INSERT INTO tag (name) VALUES (?) " + RETURNING;

  private static final String UPDATE_TAG = "UPDATE tag SET name = ? WHERE id = ? " + RETURNING;

  private static final String ADD_TAG_TO_GIFT_CERTIFICATE =
      "INSERT INTO gift_certificate_to_tag (gift_certificate_id, tag_id)  VALUES (?, ?);";

  private static final String DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id = ?;";

  private static final String DELETE_TAG_FROM_GIFT_CERTIFICATE =
      "DELETE FROM gift_certificate_to_tag WHERE gift_certificate_id = ? AND tag_id = ?;";

  private static final String DELETE_TAG_FROM_GIFT_CERTIFICATES =
          "DELETE FROM gift_certificate_to_tag WHERE tag_id = ?;";

  @Autowired private JdbcTemplate jdbcTemplate;

  private TagMapper tagMapper = new TagMapper();

  @Override
  public Tag getBySpecification(Specification specification) {
    try {
      return jdbcTemplate.queryForObject(
          specification.getSql(), specification.getArguments(), new TagMapper());
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public List<Tag> getListBySpecification(Specification specification) {
    return jdbcTemplate.query(
        specification.getSql(), specification.getArguments(), new TagMapper());
  }

  @Override
  public Tag create(Tag tag) {
    try {
      return jdbcTemplate.queryForObject(INSERT_TAG, new Object[] {tag.getName()}, tagMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public Tag update(Tag tag) {
    try {
      return jdbcTemplate.queryForObject(
          UPDATE_TAG, new Object[] {tag.getId(), tag.getName()}, tagMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public void addTagToGiftCertificate(long giftCertificateId, long tagId) {
    jdbcTemplate.update(ADD_TAG_TO_GIFT_CERTIFICATE, giftCertificateId, tagId);
  }

  @Override
  public void deleteTagFromGiftCertificate(long giftCertificateId, long tagId) {
    jdbcTemplate.update(DELETE_TAG_FROM_GIFT_CERTIFICATE, giftCertificateId, tagId);
  }

  @Override
  public void delete(Tag tag) {
    jdbcTemplate.update(DELETE_TAG_FROM_GIFT_CERTIFICATES, tag.getId());
    jdbcTemplate.update(DELETE_TAG_BY_ID, tag.getId());
  }
}
