package com.epam.esm.entity;

import java.util.Objects;

public class Tag extends Entity {

  private String name;

  public Tag(Long id, String name) {
    super(id);
    this.name = name;
  }

  public Tag(Long id) {
    this(id, null);
  }

  public Tag(String name) {
    this(null, name);
  }

  public Tag() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Tag{" + "id=" + id + ", name='" + name + '\'' + '}';
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
    Tag tag = (Tag) o;
    return Objects.equals(name, tag.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name);
  }
}
