package com.epam.esm.entity;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public abstract class Entity {

  protected Long id;

  public Entity(Long id) {
    this.id = id;
  }

  public Entity() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Entity entity = (Entity) o;
    return Objects.equals(id, entity.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Entity{" + "id=" + id + '}';
  }
}
