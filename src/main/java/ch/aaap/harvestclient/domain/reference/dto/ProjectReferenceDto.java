package ch.aaap.harvestclient.domain.reference.dto;

import ch.aaap.harvestclient.domain.reference.ProjectReference;

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
