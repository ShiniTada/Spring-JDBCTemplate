package com.epam.esm.specification.tag;

import com.epam.esm.specification.Specification;

public class GetTagByNameSpecification implements Specification {

  private static final String GET_TAG_BY_ID = "SELECT id, name FROM Tag WHERE name = ?;";

  private String name;

  public GetTagByNameSpecification(String name) {
    this.name = name;
  }

  @Override
  public String getSql() {
    return GET_TAG_BY_ID;
  }

  @Override
  public Object[] getArguments() {
    return new Object[] {name};
  }
}
