package com.epam.esm.specification.giftcertficate;

import com.epam.esm.specification.Specification;

public class GetAllGiftCertificatesSpecification implements Specification {

  private static final String GET_ALL_GIFT_CERTIFICATES_SORT =
      "SELECT id, name, description, price, creation_date, last_modified, duration_in_days "
          + "FROM gift_certificate ORDER BY "
          + "CASE WHEN ? ='name' THEN name END ASC, "
          + "CASE WHEN ?='description' THEN description END ASC, "
          + "CASE WHEN ? ='last_modified' THEN TO_CHAR(last_modified, 'YYYY-MM-DD') END DESC, "
          + "CASE WHEN ? ='creation_date' THEN TO_CHAR(creation_date, 'YYYY-MM-DD') END DESC, "
          + "id ASC;";

  private String sort;

  public GetAllGiftCertificatesSpecification(String sort) {
    this.sort = sort;
  }

  @Override
  public String getSql() {
    return GET_ALL_GIFT_CERTIFICATES_SORT;
  }

  @Override
  public Object[] getArguments() {
    return new Object[] {sort, sort, sort, sort};
  }
}
