package ch.aaap.harvestclient.domain.reference;

public class ProjectReferenceDto extends BaseReferenceDto implements ProjectReference {

    public ProjectReferenceDto() {
    }

    public ProjectReferenceDto(long id) {
        super(id);
    }

    @Override
    public String toString() {
        return "ProjectReferenceDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
