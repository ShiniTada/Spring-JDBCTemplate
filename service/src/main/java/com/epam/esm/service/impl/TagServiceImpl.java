package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.WrongIdException;
import com.epam.esm.exception.WrongInputDataException;
import com.epam.esm.mapper.TagModelMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.specification.tag.GetAllTagsSpecification;
import com.epam.esm.specification.tag.GetTagByIdSpecification;
import com.epam.esm.specification.tag.GetTagByNameSpecification;
import com.epam.esm.specification.tag.GetTagsByGiftCertificateIdSpecification;
import com.epam.esm.specification.tag.IsTagAddedToGiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

  @Autowired private TagRepository tagRepository;

  @Autowired private TagModelMapper tagModelMapper;

  @Override
  public List<TagDto> getAll(String sort) {
    List<Tag> tags = tagRepository.getListBySpecification(new GetAllTagsSpecification(sort));
    return tagModelMapper.listToDto(tags);
  }

  @Override
  public TagDto getById(long id) {
    Tag tag = tagRepository.getBySpecification(new GetTagByIdSpecification(id));
    return tagModelMapper.toDto(tag);
  }

  @Override
  public TagDto getByName(String name) {
    Tag tag = tagRepository.getBySpecification(new GetTagByNameSpecification(name));
    return tagModelMapper.toDto(tag);
  }

  @Override
  public List<TagDto> getTagsByGiftCertificateId(long id, String sort) {
    List<Tag> tags =
        tagRepository.getListBySpecification(new GetTagsByGiftCertificateIdSpecification(id, sort));
    return tagModelMapper.listToDto(tags);
  }

  @Override
  public TagDto create(TagDto tagDto) {
    Tag tag = tagModelMapper.toEntity(tagDto);
    validate(tag);
    Tag outTag = tagRepository.create(tag);
    if (outTag == null) {
      throw new ServiceException("Server error. Tag not added.");
    }
    return tagModelMapper.toDto(outTag);
  }

  @Transactional
  @Override
  public void createTags(GiftCertificateDto giftCertificateDto) {
    List<Tag> oldTags =
        tagRepository.getListBySpecification(
            new GetTagsByGiftCertificateIdSpecification(giftCertificateDto.getId(), null));
    if (giftCertificateDto.getListTagDto() == null) {
      deleteTagsFromGiftCertificate(oldTags, giftCertificateDto.getId());
    } else {
      List<Tag> newTags = tagModelMapper.listToEntity(giftCertificateDto.getListTagDto());

      List<Tag> tagsShouldBeRemove = new ArrayList<>(oldTags);
      tagsShouldBeRemove.removeAll(newTags);
      deleteTagsFromGiftCertificate(tagsShouldBeRemove, giftCertificateDto.getId());

      List<Tag> tagsShouldBeAdded = new ArrayList<>(newTags);
      tagsShouldBeAdded.removeAll(oldTags);
      addNewTagsToGiftCertificate(tagsShouldBeAdded, giftCertificateDto.getId());
    }
  }

  private void deleteTagsFromGiftCertificate(List<Tag> tagsShouldBeRemove, Long giftCertificateId) {
    for (Tag tag : tagsShouldBeRemove) {
      Tag oldOutTag;
      if (tag.getId() != null) {
        oldOutTag = tagRepository.getBySpecification(new GetTagByIdSpecification(tag.getId()));
      } else {
        oldOutTag = tagRepository.getBySpecification(new GetTagByNameSpecification(tag.getName()));
      }
      tagRepository.deleteTagFromGiftCertificate(giftCertificateId, oldOutTag.getId());
    }
  }

  private void addNewTagsToGiftCertificate(List<Tag> tagsShouldBeAdded, Long giftCertificateId) {
    for (Tag tag : tagsShouldBeAdded) {
      validate(tag);
      Tag outTag;
      if (tag.getId() != null) {
        outTag = tagRepository.getBySpecification(new GetTagByIdSpecification(tag.getId()));
        if (outTag == null) {
          throw new WrongIdException("Tag with id " + tag.getId() + " does not exist.");
        }
      } else {
        outTag = tagRepository.getBySpecification(new GetTagByNameSpecification(tag.getName()));
        if (outTag == null) {
          outTag = tagRepository.create(tag);
        }
      }
      Tag connTag =
          tagRepository.getBySpecification(
              new IsTagAddedToGiftCertificate(giftCertificateId, outTag.getId()));
      if (connTag == null) {
        tagRepository.addTagToGiftCertificate(giftCertificateId, outTag.getId());
      }
    }
  }

  private void validate(Tag tag) {
    List<String> exceptionMessages = new ArrayList<>();
    if (tag.getId() == null && tag.getName() == null) {
      exceptionMessages.add("Tag must contain id or name.");
    }
    if (tag.getName().length() < 2 || tag.getName().length() > 30) {
      exceptionMessages.add("Name must be between 3 and 100 characters.");
    }
    if (!exceptionMessages.isEmpty()) {
      throw new WrongInputDataException(exceptionMessages);
    }
  }

  @Override
  public void deleteById(long id) {
    tagRepository.delete(new Tag(id));
  }
}
