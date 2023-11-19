// package com.rds.observato.assignments;
//
// import com.rds.observato.json.ResponseMapper;
//
// public class GetAssignmentConverter
//    implements ResponseMapper<AssignmentView, GetAssignmentResponse> {
//
//  @Override
//  public GetAssignmentResponse convert(AssignmentView view) {
//    return new GetAssignmentResponse(
//        view.id(),
//        view.revision(),
//        view.account(),
//        view.task(),
//        view.resource(),
//        view.start(),
//        view.end());
//  }
// }
