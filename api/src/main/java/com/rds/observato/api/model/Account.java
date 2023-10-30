package com.rds.observato.api.model;

/**
 * Represent a single customer account using the system. Account may have many users, agents and
 * tasks but is isolated from the rest of the accounts.
 */
public record Account(long id, String key, String name) {}
