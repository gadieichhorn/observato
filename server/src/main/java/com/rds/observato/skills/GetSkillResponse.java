package com.rds.observato.skills;

public record GetSkillResponse(
    long id, long account, int revision, String name, String description) {

  public static GetSkillResponse from(SkillView view) {
    return new GetSkillResponse(
        view.id(), view.account(), view.revision(), view.name(), view.description());
  }
}
