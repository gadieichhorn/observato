package com.rd.observato.api;

public interface Rule {

  boolean test(Task task, Resource resource);

  boolean test(Task task, Availability availability);

  boolean accept(RuleVisitor visitor);
}
