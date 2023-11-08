package com.rds.observato.persistence.users;

public record LoginView(long id, String name, byte[] salt, byte[] secret) {}
