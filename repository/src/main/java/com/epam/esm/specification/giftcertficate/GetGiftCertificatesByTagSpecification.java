package com.epam.esm.specification.giftcertficate;

import com.epam.esm.entity.Tag;
import com.epam.esm.specification.Specification;

public class GetGiftCertificatesByTagSpecification implements Specification {

  private static final String GET_GIFT_CERTIFICATE_BY_TAG =
      "SELECT gc.id, gc.name, gc.description, gc.price, gc.creation_date, gc.last_modified, gc.duration_in_days "
          + "FROM gift_certificate gc "
          + "INNER JOIN gift_certificate_to_tag gctt ON gc.id = gctt.gift_certificate_id "
          + "INNER JOIN tag t   ON t.id = gctt.tag_id "
          + "WHERE t.name = ? "
          + "ORDER BY "
              + "CASE WHEN ? ='name' THEN gc.name END ASC, "
              + "CASE WHEN ?='description' THEN gc.description END ASC, "
              + "CASE WHEN ? ='last_modified' THEN TO_CHAR(gc.last_modified, 'YYYY-MM-DD') END DESC, "
              + "CASE WHEN ? ='creation_date' THEN TO_CHAR(gc.creation_date, 'YYYY-MM-DD') END DESC, "
              + "id ASC;";

  private String name;
  private String sort;

  public GetGiftCertificatesByTagSpecification(Tag tag, String sort) {
    this.name = tag.getName();
    this.sort = sort;
  }

  @Override
  public String getSql() {
    return GET_GIFT_CERTIFICATE_BY_TAG;
  }

  @Override
  public Object[] getArguments() {
    return new Object[] {name, sort, sort, sort, sort};
  }
}
