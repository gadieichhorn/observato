package com.rd.observato.api;

public interface Rule {

  boolean test(Task task, Resource resource);
}
