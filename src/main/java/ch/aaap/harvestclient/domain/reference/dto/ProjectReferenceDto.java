package ch.aaap.harvestclient.domain.reference.dto;

import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.reference.Reference;

public class ProjectReferenceDto extends BaseReferenceDto implements Reference<Project> {

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
