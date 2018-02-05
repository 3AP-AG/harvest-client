package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import ch.aaap.harvestclient.domain.reference.RoleReference;
import ch.aaap.harvestclient.domain.reference.UserReference;
import ch.aaap.harvestclient.domain.reference.dto.UserReferenceDto;

public class Role implements RoleReference {

    private Long id;

    private String name;

    /**
     * The IDs of the users assigned to this role.
     */
    private List<Long> userIds;

    private Instant createdAt;

    private Instant updatedAt;

    public List<UserReference> getUserIds() {
        return userIds.stream().map(UserReferenceDto::new).collect(Collectors.toList());
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userIds=" + userIds +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
