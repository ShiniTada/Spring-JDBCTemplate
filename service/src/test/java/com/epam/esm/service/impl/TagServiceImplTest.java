package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagModelMapper;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagServiceImplTest {

  @Mock TagModelMapper mapper;
  @Mock TagRepositoryImpl repository;
  @InjectMocks TagServiceImpl service;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void createTag_allParamsValid_created() {
    // given
    TagDto newTagDto = new TagDto(null, "best");
    Tag newTag = new Tag(null, "best");
    when(mapper.toEntity(newTagDto)).thenReturn(newTag);

    Tag expectedTag = new Tag(1L, "best");
    when(repository.create(newTag)).thenReturn(expectedTag);

    TagDto expectedTagDto = new TagDto(1L, "best");
    when(mapper.toDto(expectedTag)).thenReturn(expectedTagDto);

    // when
    TagDto actualTagDto = service.create(newTagDto);
    // then
    verify(repository, times(1)).create(newTag);
    assertEquals(expectedTagDto, actualTagDto);
  }

  @Test
  public void deleteTag_deleted() {
    service.deleteById(1L);
    Tag tag = new Tag(1L);
    verify(repository, times(1)).delete(tag);
  }
}
