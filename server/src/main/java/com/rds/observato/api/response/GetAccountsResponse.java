package com.rds.observato.api.response;

import com.rds.observato.api.model.Account;
import java.util.Set;

public record GetAccountsResponse(Set<Account> accounts) {}
