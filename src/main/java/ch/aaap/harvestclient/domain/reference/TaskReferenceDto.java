package ch.aaap.harvestclient.domain.reference;

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
