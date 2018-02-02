package ch.aaap.harvestclient.domain.reference.dto;

import ch.aaap.harvestclient.domain.reference.TaskReference;

public class TaskReferenceDto extends BaseReferenceDto implements TaskReference {

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
