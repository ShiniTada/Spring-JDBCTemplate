package com.epam.esm.specification.tag;

import com.epam.esm.specification.Specification;

public class GetTagsByGiftCertificateIdSpecification implements Specification {

  private static final String GET_TAG_BY_GIFT_CERTIFICATE_ID =
      "SELECT t.id, t.name FROM tag t INNER JOIN gift_certificate_to_tag gctt"
          + " ON t.id = gctt.tag_id WHERE gctt.gift_certificate_id = ? "
          + "ORDER BY CASE WHEN ? ='name' THEN name END ASC, id ASC;";

  private long id;
  private String sort;

  public GetTagsByGiftCertificateIdSpecification(long id, String sort) {
    this.id = id;
    this.sort = sort;
  }

  @Override
  public String getSql() {
    return GET_TAG_BY_GIFT_CERTIFICATE_ID;
  }

  @Override
  public Object[] getArguments() {
    return new Object[] {id, sort};
  }
}
