package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class GiftCertificate extends Entity {

  private String name;
  private String description;
  private BigDecimal price;
  private LocalDate creationDate;
  private LocalDate lastModified;
  private int durationInDays;

  public GiftCertificate(
      Long id,
      String name,
      String description,
      BigDecimal price,
      LocalDate creationDate,
      LocalDate lastModified,
      int durationInDays) {
    super(id);
    this.name = name;
    this.description = description;
    this.price = price;
    this.creationDate = creationDate;
    this.lastModified = lastModified;
    this.durationInDays = durationInDays;
  }

  public GiftCertificate(Long id) {
    this(id, null, null, new BigDecimal("0.0"), null, null, 0);
  }

  public GiftCertificate() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDate getLastModified() {
    return lastModified;
  }

  public void setLastModified(LocalDate lastModified) {
    this.lastModified = lastModified;
  }

  public int getDurationInDays() {
    return durationInDays;
  }

  public void setDurationInDays(int durationInDays) {
    this.durationInDays = durationInDays;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    GiftCertificate that = (GiftCertificate) o;
    return durationInDays == that.durationInDays
        && Objects.equals(name, that.name)
        && Objects.equals(description, that.description)
        && Objects.equals(price, that.price)
        && Objects.equals(creationDate, that.creationDate)
        && Objects.equals(lastModified, that.lastModified);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        super.hashCode(), name, description, price, creationDate, lastModified, durationInDays);
  }

  @Override
  public String toString() {
    return "GiftCertificate{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + ", price="
        + price
        + ", creationDate="
        + creationDate
        + ", lastModified="
        + lastModified
        + ", durationInDays="
        + durationInDays
        + '}';
  }
}
