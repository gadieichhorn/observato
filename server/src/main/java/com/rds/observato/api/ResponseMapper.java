package com.rds.observato.api;

public interface ResponseMapper<V, R> {

  R convert(V view);
}
