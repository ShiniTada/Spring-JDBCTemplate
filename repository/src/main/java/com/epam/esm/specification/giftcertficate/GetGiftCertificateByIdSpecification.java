package com.epam.esm.specification.giftcertficate;

import com.epam.esm.specification.Specification;

public class GetGiftCertificateByIdSpecification implements Specification {

  private static final String GET_GIFT_CERTIFICATE_BY_ID =
      "SELECT id, name, description, price, creation_date, "
          + "last_modified, duration_in_days FROM gift_certificate WHERE id = ?;";

  private long id;

  public GetGiftCertificateByIdSpecification(long id) {
    this.id = id;
  }

  @Override
  public String getSql() {
    return GET_GIFT_CERTIFICATE_BY_ID;
  }

  @Override
  public Object[] getArguments() {
    return new Object[] {id};
  }
}
