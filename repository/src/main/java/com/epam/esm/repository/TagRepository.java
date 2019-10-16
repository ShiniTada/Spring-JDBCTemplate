package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

/** Implementation of {@link IRepository} for working with {@link Tag} */
public interface TagRepository extends IRepository<Tag> {

  /**
   * Add tag to gift certificate
   *
   * @param giftCertificateId - gift certificate id
   * @param tagId - tag id
   */
  void addTagToGiftCertificate(long giftCertificateId, long tagId);

  /**
   * Delete tag from gift certificate
   *
   * @param giftCertificateId - gift certificate id
   * @param tagId - tag id
   */
  void deleteTagFromGiftCertificate(long giftCertificateId, long tagId);
}
