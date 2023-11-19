package com.rds.observato.accounts;

public record GetAccountResponse(long id, int revision, String name, long owner) {}
