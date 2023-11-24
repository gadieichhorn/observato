package com.rds.observato.accounts;

import java.util.Set;

public record GetAccountsResponse(Set<GetAccountResponse> accounts) {}
