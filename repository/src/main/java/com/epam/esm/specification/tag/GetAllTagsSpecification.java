package com.epam.esm.specification.tag;

import com.epam.esm.specification.Specification;

public class GetAllTagsSpecification implements Specification {

  private static final String GET_TAGS_SORT =
      "SELECT id, name FROM Tag " + "ORDER BY CASE WHEN ? ='name' THEN name END ASC, id ASC;";

  private String sort;

  public GetAllTagsSpecification(String sort) {
    this.sort = sort;
  }

  @Override
  public String getSql() {
    return GET_TAGS_SORT;
  }

  @Override
  public Object[] getArguments() {
    return new Object[] {sort};
  }
}
