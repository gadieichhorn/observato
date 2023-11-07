package com.rds.observato.persistence.users;

import com.rds.observato.model.User;

public record LoginView(long id, String name, byte[] salt, byte[] secret) implements User {}
