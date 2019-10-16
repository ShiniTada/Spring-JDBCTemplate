package com.epam.esm.specification;

public interface Specification {

  /** @return sql */
  String getSql();

  /** @return arguments for sql */
  Object[] getArguments();
}
