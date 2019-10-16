package com.epam.esm.specification.tag;

import com.epam.esm.specification.Specification;

public class GetTagByIdSpecification implements Specification {

  private static final String GET_TAG_BY_ID = "SELECT id, name FROM Tag WHERE id = ?;";

  private long id;

  public GetTagByIdSpecification(long id) {
    this.id = id;
  }

  @Override
  public String getSql() {
    return GET_TAG_BY_ID;
  }

  @Override
  public Object[] getArguments() {
    return new Object[] {id};
  }
}
