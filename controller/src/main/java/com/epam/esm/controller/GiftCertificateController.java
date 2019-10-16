package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/giftcertificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

  @Autowired private GiftCertificateService giftCertificateService;

  @GetMapping
  public List<GiftCertificateDto> getAllGiftCertificates(
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "description", required = false) String description,
      @RequestParam(value = "tags", required = false) List<String> tagNames,
      @RequestParam(value = "sort", required = false) String sort) {

    if (name == null && description == null && tagNames == null) {
      return giftCertificateService.getAll(sort);
    } else {
      GiftCertificateDto giftCertificateDto = new GiftCertificateDto(name, description);
      if (tagNames != null) {
        List<TagDto> tagDtos = new ArrayList<>();
        for (String tagName : tagNames) {
          tagDtos.add(new TagDto(tagName));
        }
        giftCertificateDto.setListTagDto(tagDtos);
      }
      return giftCertificateService.getGiftCertificates(giftCertificateDto, sort);
    }
  }

  @GetMapping(value = "/{id}")
  public GiftCertificateDto getGiftCertificate(@PathVariable("id") Long id) {
    GiftCertificateDto giftCertificateDto = giftCertificateService.getById(id);
    if (giftCertificateDto == null) {
      throw new ResourceNotFoundException("Gift certificate with id " + id + " does not exist.");
    }
    return giftCertificateDto;
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.OK)
  public GiftCertificateDto addGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {

    giftCertificateDto = giftCertificateService.create(giftCertificateDto);
    return giftCertificateDto;
  }

  @PutMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GiftCertificateDto updateGiftCertificate(
      @PathVariable("id") Long id, @RequestBody GiftCertificateDto giftCertificateDto) {
    giftCertificateDto = giftCertificateService.update(id, giftCertificateDto);
    return giftCertificateDto;
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") Long id) {
    GiftCertificateDto giftCertificateDto = giftCertificateService.getById(id);
    if (giftCertificateDto == null) {
      throw new ResourceNotFoundException("Gift certificate with id " + id + " does not exist.");
    }
    giftCertificateService.deleteById(id);
  }
}
