package com.epam.esm.service;

import java.util.List;

public interface Service<T> {

  /**
   * Get all objects
   *
   * @param sort - field by which the sorting will be. If sort is null sorting will be by id
   * @return all objects
   */
  List<T> getAll(String sort);

  /**
   * Get object by id
   *
   * @param id - object id
   * @return object
   */
  T getById(long id);

  /**
   * Create object
   *
   * @param t - object
   * @return created object. If it's {@code null} the object was not created
   */
  T create(T t);

  /**
   * Delete object by id
   *
   * @param id - object id
   */
  void deleteById(long id);
}
