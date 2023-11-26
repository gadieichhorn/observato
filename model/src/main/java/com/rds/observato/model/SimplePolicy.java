package com.rds.observato.model;

import com.rd.observato.api.Policy;
import com.rd.observato.api.Rule;
import java.util.Set;

public record SimplePolicy(Set<Rule> rules) implements Policy {}
