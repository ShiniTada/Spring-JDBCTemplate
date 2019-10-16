package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.WrongIdException;

import java.util.List;

public interface GiftCertificateService extends Service<GiftCertificateDto> {

  /**
   * Gives gift certificate dtos that match the criteria.
   *
   * @param giftCertificateDto - gift certificate dto which have various search criteria: name,
   *     description, tags
   * @param sort - field by which the sorting will be. If sort is null sorting will be by id
   * @return gift certificate dtos that match the criteria.
   */
  List<GiftCertificateDto> getGiftCertificates(GiftCertificateDto giftCertificateDto, String sort);

  /**
   * @param id - gift certificate id
   * @param giftCertificateDto - gift certificate dto with fields which should be changed
   * @return modified gift certificate dto
   * @throws WrongIdException throws if gift certificate with {@code id} does not exist.
   */
  GiftCertificateDto update(long id, GiftCertificateDto giftCertificateDto);
}
