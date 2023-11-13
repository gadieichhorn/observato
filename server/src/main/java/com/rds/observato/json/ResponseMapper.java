package com.rds.observato.json;

public interface ResponseMapper<V, R> {

  R convert(V view);
}
