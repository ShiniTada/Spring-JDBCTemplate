package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.AlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

  @Autowired private TagService tagService;

  @GetMapping
  public List<TagDto> getAllTags(@RequestParam(value = "sort", required = false) String sort) {
    return tagService.getAll(sort);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public TagDto getTag(@PathVariable("id") long id) {
    TagDto tagDto = tagService.getById(id);
    if (tagDto == null) {
      throw new ResourceNotFoundException("Tag with id " + id + " does not exist.");
    }
    return tagDto;
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.OK)
  public TagDto addTag(@RequestBody TagDto tagDto) {
    TagDto existedTag = tagService.getByName(tagDto.getName());
    if (existedTag != null) {
      throw new AlreadyExistException(
          "Tag with the same name already created. Id: " + existedTag.getId());
    }
    tagDto = tagService.create(tagDto);
    return tagDto;
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") Long id) {
    TagDto tagDto = tagService.getById(id);
    if (tagDto == null) {
      throw new ResourceNotFoundException("Tag with id " + id + " does not exist.");
    }
    tagService.deleteById(id);
  }
}
