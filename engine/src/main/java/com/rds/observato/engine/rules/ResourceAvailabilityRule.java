package com.rds.observato.engine.rules;

import com.rd.observato.api.*;

public record ResourceAvailabilityRule() implements Rule {

  //  private static final Logger log = LoggerFactory.getLogger(ResourceAvailabilityRule.class);

  @Override
  public boolean test(Task task, Resource resource) {
    return true;
  }

  @Override
  public boolean test(Task task, Availability availability) {
    return availability.duration().compareTo(task.duration()) >= 0;
  }

  @Override
  public boolean accept(RuleVisitor visitor) {
    return visitor.visit(this);
  }
}
