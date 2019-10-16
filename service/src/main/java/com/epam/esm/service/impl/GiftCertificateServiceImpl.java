package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.WrongIdException;
import com.epam.esm.exception.WrongInputDataException;
import com.epam.esm.mapper.GiftCertificateModelMapper;
import com.epam.esm.mapper.TagModelMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.specification.giftcertficate.GetAllGiftCertificatesSpecification;
import com.epam.esm.specification.giftcertficate.GetGiftCertificateByIdSpecification;
import com.epam.esm.specification.giftcertficate.GetGiftCertificatesByTagSpecification;
import com.epam.esm.specification.giftcertficate.GetGiftCertificatesSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

  @Autowired private GiftCertificateRepository giftCertificateRepository;

  @Autowired private GiftCertificateModelMapper giftCertificateModelMapper;

  @Autowired private TagModelMapper tagModelMapper;

  @Autowired private TagService tagService;

  @Override
  public List<GiftCertificateDto> getAll(String sort) {
    List<GiftCertificate> giftCertificates =
        giftCertificateRepository.getListBySpecification(
            new GetAllGiftCertificatesSpecification(sort));
    return convertGiftCertificateToDtoAndAddTags(giftCertificates);
  }

  @Override
  public GiftCertificateDto getById(long id) {
    GiftCertificate giftCertificate =
        giftCertificateRepository.getBySpecification(new GetGiftCertificateByIdSpecification(id));
    if (giftCertificate != null) {
      GiftCertificateDto giftCertificateDto = giftCertificateModelMapper.toDto(giftCertificate);

      List<TagDto> tagDtos = tagService.getTagsByGiftCertificateId(giftCertificate.getId(), null);
      giftCertificateDto.setListTagDto(tagDtos);
      return giftCertificateDto;
    } else {
      return null;
    }
  }

  @Transactional
  @Override
  public List<GiftCertificateDto> getGiftCertificates(
      GiftCertificateDto giftCertificateDto, String sort) {
    GiftCertificate giftCertificate = giftCertificateModelMapper.toEntity(giftCertificateDto);
    List<GiftCertificate> certificates = new ArrayList<>();
    if (giftCertificate.getName() != null || giftCertificate.getDescription() != null) {
      certificates =
          giftCertificateRepository.getListBySpecification(
              new GetGiftCertificatesSpecification(giftCertificate, sort));
    }

    if (giftCertificateDto.getListTagDto() != null) {
      List<Tag> tags = tagModelMapper.listToEntity(giftCertificateDto.getListTagDto());
      for (Tag tag : tags) {
        List<GiftCertificate> byTag =
            giftCertificateRepository.getListBySpecification(
                new GetGiftCertificatesByTagSpecification(tag, sort));
        if (!certificates.isEmpty()) {
          certificates.retainAll(byTag);
        } else {
          certificates = byTag;
        }
      }
    }
    return convertGiftCertificateToDtoAndAddTags(certificates);
  }

  private List<GiftCertificateDto> convertGiftCertificateToDtoAndAddTags(
      List<GiftCertificate> certificates) {
    List<GiftCertificateDto> giftCertificateDtos =
        giftCertificateModelMapper.listToDto(certificates);
    for (GiftCertificateDto giftCertificateDto : giftCertificateDtos) {
      List<TagDto> tagDtos =
          tagService.getTagsByGiftCertificateId(giftCertificateDto.getId(), null);
      giftCertificateDto.setListTagDto(tagDtos);
    }
    return giftCertificateDtos;
  }

  @Transactional
  @Override
  public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
    validate(giftCertificateDto);
    GiftCertificate giftCertificate =
        giftCertificateRepository.create(giftCertificateModelMapper.toEntity(giftCertificateDto));
    if (giftCertificate == null) {
      throw new ServiceException("Server error. Gift certificate not added.");
    }
    giftCertificateDto.setId(giftCertificate.getId());

    tagService.createTags(giftCertificateDto);

    giftCertificateDto = giftCertificateModelMapper.toDto(giftCertificate);
    List<TagDto> tagDtos = tagService.getTagsByGiftCertificateId(giftCertificate.getId(), null);
    giftCertificateDto.setListTagDto(tagDtos);
    return giftCertificateDto;
  }

  @Transactional
  @Override
  public GiftCertificateDto update(long id, GiftCertificateDto giftCertificateDto) {
    validate(giftCertificateDto);
    giftCertificateDto.setId(id);
    GiftCertificate oldG = giftCertificateModelMapper.toEntity(this.getById(id));
    if (oldG == null) {
      throw new WrongIdException("The gift certificate with id " + id + "does not exist.");
    }
    GiftCertificate newG = giftCertificateModelMapper.toEntity(giftCertificateDto);
    GiftCertificate outGiftCertificate = giftCertificateRepository.update(newG);
    if (outGiftCertificate == null) {
      throw new ServiceException("Server error. Gift certificate does not updated.");
    }
    tagService.createTags(giftCertificateDto);

    giftCertificateDto = giftCertificateModelMapper.toDto(outGiftCertificate);
    List<TagDto> tagDtos = tagService.getTagsByGiftCertificateId(outGiftCertificate.getId(), null);
    giftCertificateDto.setListTagDto(tagDtos);
    return giftCertificateDto;
  }

  private void validate(GiftCertificateDto giftCertificateDto) {

    List<String> exceptionMessages = new ArrayList<>();
    if (giftCertificateDto.getName() == null) {
      exceptionMessages.add("Name is required.");
    }
    if (giftCertificateDto.getName().length() < 2 || giftCertificateDto.getName().length() > 100) {
      exceptionMessages.add("Name must be between 3 and 100 characters.");
    }
    if (giftCertificateDto.getPrice() == null
        || giftCertificateDto.getPrice().doubleValue() < 0.0) {
      exceptionMessages.add("Wrong price value: " + giftCertificateDto.getPrice());
    }
    if (giftCertificateDto.getDurationInDays() < 1) {
      exceptionMessages.add("Wrong duration value: " + giftCertificateDto.getDurationInDays());
    }
    if (!exceptionMessages.isEmpty()) {
      throw new WrongInputDataException(exceptionMessages);
    }
  }

  @Override
  public void deleteById(long id) {
    giftCertificateRepository.delete(new GiftCertificate(id));
  }
}
