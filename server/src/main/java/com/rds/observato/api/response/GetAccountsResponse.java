package com.rds.observato.api.response;

import com.rds.observato.accounts.GetAccountResponse;
import java.util.Set;

public record GetAccountsResponse(Set<GetAccountResponse> accounts) {}
