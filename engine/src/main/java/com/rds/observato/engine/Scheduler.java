package com.rds.observato.engine;

import com.rd.observato.api.Assignment;
import com.rd.observato.api.Task;
import java.util.Set;

public interface Scheduler {

  Assignment valid(Task task, Set<Assignment> schedule);
}
