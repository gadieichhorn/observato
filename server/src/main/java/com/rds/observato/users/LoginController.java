// package com.rds.observato.controller.users;
//
// import com.codahale.metrics.annotation.Timed;
// import com.rds.observato.db.Repository;
// import com.rds.observato.users.UserLoginRequest;
// import com.rds.observato.users.UserView;
// import jakarta.ws.rs.*;
// import jakarta.ws.rs.core.MediaType;
// import java.util.Optional;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
//
// @Timed
// @Path("login")
// @Produces(MediaType.APPLICATION_JSON)
// public record LoginController(Repository repository) {
//  private static final Logger log = LoggerFactory.getLogger(LoginController.class);
//
//  @POST
//  public Optional<UserView> login(UserLoginRequest request) {
//    return null;
//  }
// }
