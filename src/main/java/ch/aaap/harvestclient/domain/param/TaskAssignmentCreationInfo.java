package ch.aaap.harvestclient.domain.param;

import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.TaskAssignment;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.domain.reference.dto.TaskReferenceDto;

public class TaskAssignmentCreationInfo extends TaskAssignment {

    private Long taskId;

    public TaskAssignmentCreationInfo(Reference<Task> taskReference) {
        taskId = taskReference.getId();
    }

    @Override
    public void setTaskReferenceDto(TaskReferenceDto taskReferenceDto) {
        taskId = taskReferenceDto.getId();
    }
}
