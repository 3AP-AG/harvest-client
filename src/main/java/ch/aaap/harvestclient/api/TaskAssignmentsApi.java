package ch.aaap.harvestclient.api;

import java.util.List;

import ch.aaap.harvestclient.api.filter.TaskAssignmentListFilter;
import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.TaskAssignment;
import ch.aaap.harvestclient.domain.param.TaskAssignmentCreationInfo;
import ch.aaap.harvestclient.domain.param.TaskAssignmentUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;

public interface TaskAssignmentsApi {

    List<TaskAssignment> list(Reference<Project> projectReference, TaskAssignmentListFilter filter);

    TaskAssignment get(Reference<Project> projectReference, Reference<TaskAssignment> taskAssignmentReference);

    TaskAssignment create(Reference<Project> projectReference, TaskAssignmentCreationInfo creationInfo);

    TaskAssignment update(Reference<Project> projectReference, Reference<TaskAssignment> taskAssignmentReference,
            TaskAssignmentUpdateInfo updateInfo);

    void delete(Reference<Project> projectReference, Reference<TaskAssignment> taskAssignmentReference);
}
