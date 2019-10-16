package com.epam.esm.repository;

import com.epam.esm.entity.Entity;
import com.epam.esm.specification.Specification;

import java.util.List;

public interface IRepository<T extends Entity> {

  /**
   * Create object
   *
   * @param t - object
   * @return created object. If it's {@code null} the object was not created
   */
  T create(T t);

  /**
   * Update object
   *
   * @param t - object
   * @return updated object. If it's {@code null} the object was not updated
   */
  T update(T t);

  /**
   * Delete object and all links to object
   *
   * @param t - object
   */
  void delete(T t);

  /**
   * Get object by some specification
   *
   * @param specification - specification for search
   * @return object
   */
  T getBySpecification(Specification specification);

  /**
   * Get objects by some specification
   *
   * @param specification - specification for search
   * @return list objects
   */
  List<T> getListBySpecification(Specification specification);
}
