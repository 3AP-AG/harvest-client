package ch.aaap.harvestclient.domain;

import java.time.Instant;

import ch.aaap.harvestclient.domain.reference.dto.TaskReferenceDto;

public class TaskAssignment {

    private Long id;
    private TaskReferenceDto taskReferenceDto;
    private Boolean active;
    private Boolean billable;
    private Boolean hourlyRate;
    private Boolean budget;

    private Instant createdAt;
    private Instant updatedAt;
}
