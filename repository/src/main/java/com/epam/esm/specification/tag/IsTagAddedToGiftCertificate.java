package com.epam.esm.specification.tag;

import com.epam.esm.specification.Specification;

public class IsTagAddedToGiftCertificate implements Specification {

  private static final String FIND_TAG_IN_GIFT_CERTIFICATE =
      "SELECT gctt.tag_id, t.name FROM gift_certificate_to_tag gctt"
          + " INNER JOIN tag t ON t.id = gctt.tag_id WHERE gctt.gift_certificate_id = ? AND gctt.tag_id = ?;";

  private long giftCertificateId;
  private long tagId;

  public IsTagAddedToGiftCertificate(long giftCertificateId, long tagId) {
    this.giftCertificateId = giftCertificateId;
    this.tagId = tagId;
  }

  @Override
  public String getSql() {
    return FIND_TAG_IN_GIFT_CERTIFICATE;
  }

  @Override
  public Object[] getArguments() {
    return new Object[] {giftCertificateId, tagId};
  }
}
