package com.epam.esm.specification.giftcertficate;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.specification.Specification;

public class GetGiftCertificatesSpecification implements Specification {

  private static final String GET_GIFT_CERTIFICATE_SORT =
      "SELECT id, name, description, price, creation_date, "
          + "last_modified, duration_in_days FROM get_gift_certificate(?,?,?)";

  private String name;
  private String description;
  private String sort;

  public GetGiftCertificatesSpecification(GiftCertificate giftCertificate, String sort) {
    this.name = giftCertificate.getName();
    this.description = giftCertificate.getDescription();
    this.sort = sort;
  }

  @Override
  public String getSql() {
    return GET_GIFT_CERTIFICATE_SORT;
  }

  @Override
  public Object[] getArguments() {
    return new Object[] {name, description, sort};
  }
}
