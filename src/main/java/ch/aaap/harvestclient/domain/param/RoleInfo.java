package ch.aaap.harvestclient.domain.param;

import java.util.List;

/**
 * Represents a creation or update request for a Role
 */
public class RoleInfo {

    private String name;

    private List<Long> userIds;

    public RoleInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    @Override
    public String toString() {
        return "RoleInfo{" +
                "name='" + name + '\'' +
                ", userIds=" + userIds +
                '}';
    }
}
