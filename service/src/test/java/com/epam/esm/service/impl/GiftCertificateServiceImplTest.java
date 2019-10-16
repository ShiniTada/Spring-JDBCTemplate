package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.WrongInputDataException;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GiftCertificateServiceImplTest {

  @Mock GiftCertificateRepositoryImpl repository;
  @InjectMocks GiftCertificateServiceImpl service;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void deleteTag_deleted() {
    service.deleteById(1L);
    GiftCertificate giftCertificate = new GiftCertificate(1L);
    verify(repository, times(1)).delete(giftCertificate);
  }

  @Test(expected = WrongInputDataException.class)
  public void validateName_exception() {
    GiftCertificateDto giftCertificateDto =
        new GiftCertificateDto(null, null, "1", new BigDecimal("23"), null, null, 2);
    service.create(giftCertificateDto);
  }

  @Test(expected = WrongInputDataException.class)
  public void validatePrice_exception() {
    GiftCertificateDto giftCertificateDto =
        new GiftCertificateDto(null, "1", null, new BigDecimal("-23"), null, null, 2);
    service.create(giftCertificateDto);
  }

  @Test(expected = WrongInputDataException.class)
  public void validateDuration_exception() {
    GiftCertificateDto giftCertificateDto =
        new GiftCertificateDto(null, "1", null, new BigDecimal("23"), null, null, -2);
    service.create(giftCertificateDto);
  }
}
