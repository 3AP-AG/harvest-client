package util;

/**
 * Collection of id of test data used in tests
 */
public class TestData {

    private long timeEntryId;
    private long userId;
    private long clientId;
    private long anotherClientId;

    private long clientContactId;
    private long anotherClientContactId;

    private long projectId;
    private long anotherProjectId;
    private long taskId;
    private long anotherTaskId;
    private long taskAssignmentId;
    private long anotherUserId;

    private long expenseCategoryId;

    public long getTimeEntryId() {
        return timeEntryId;
    }

    public void setTimeEntryId(long timeEntryId) {
        this.timeEntryId = timeEntryId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getAnotherClientId() {
        return anotherClientId;
    }

    public void setAnotherClientId(long anotherClientId) {
        this.anotherClientId = anotherClientId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getAnotherProjectId() {
        return anotherProjectId;
    }

    public void setAnotherProjectId(long anotherProjectId) {
        this.anotherProjectId = anotherProjectId;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getAnotherTaskId() {
        return anotherTaskId;
    }

    public void setAnotherTaskId(long anotherTaskId) {
        this.anotherTaskId = anotherTaskId;
    }

    public long getTaskAssignmentId() {
        return taskAssignmentId;
    }

    public void setTaskAssignmentId(long taskAssignmentId) {
        this.taskAssignmentId = taskAssignmentId;
    }

    public long getClientContactId() {
        return clientContactId;
    }

    public void setClientContactId(long clientContactId) {
        this.clientContactId = clientContactId;
    }

    public long getAnotherClientContactId() {
        return anotherClientContactId;
    }

    public void setAnotherClientContactId(long anotherClientContactId) {
        this.anotherClientContactId = anotherClientContactId;
    }

    public long getAnotherUserId() {
        return anotherUserId;
    }

    public void setAnotherUserId(long anotherUserId) {
        this.anotherUserId = anotherUserId;
    }

    public long getExpenseCategoryId() {
        return expenseCategoryId;
    }

    public void setExpenseCategoryId(long expenseCategoryId) {
        this.expenseCategoryId = expenseCategoryId;
    }

    @Override
    public String toString() {
        return "TestData{" +
                "timeEntryId=" + timeEntryId +
                ", userId=" + userId +
                ", clientId=" + clientId +
                ", anotherClientId=" + anotherClientId +
                ", clientContactId=" + clientContactId +
                ", anotherClientContactId=" + anotherClientContactId +
                ", projectId=" + projectId +
                ", anotherProjectId=" + anotherProjectId +
                ", taskId=" + taskId +
                ", anotherTaskId=" + anotherTaskId +
                ", taskAssignmentId=" + taskAssignmentId +
                ", anotherUserId=" + anotherUserId +
                ", expenseCategoryId=" + expenseCategoryId +
                '}';
    }
}
