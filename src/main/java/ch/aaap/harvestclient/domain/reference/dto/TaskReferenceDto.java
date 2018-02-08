package ch.aaap.harvestclient.domain.reference.dto;

import ch.aaap.harvestclient.domain.Task;
import ch.aaap.harvestclient.domain.reference.Reference;

public class TaskReferenceDto extends BaseReferenceDto implements Reference<Task> {

    public TaskReferenceDto() {
    }

    public TaskReferenceDto(long id) {
        super(id);
    }

    @Override
    public String toString() {
        return "TaskReferenceDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
