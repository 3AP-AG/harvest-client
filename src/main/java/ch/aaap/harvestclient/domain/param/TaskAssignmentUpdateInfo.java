package ch.aaap.harvestclient.domain.param;

import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.TaskAssignment;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.domain.reference.dto.TaskReferenceDto;

public class TaskAssignmentUpdateInfo extends TaskAssignment {

    /**
     * We cannot serialize the reference directly
     */
    private Long taskId;

    @Override
    public void setTaskReferenceDto(TaskReferenceDto taskReferenceDto) {
        taskId = taskReferenceDto.getId();
    }

    public void setTaskReference(Reference<Task> taskReference) {
        taskId = taskReference.getId();
    }
}
