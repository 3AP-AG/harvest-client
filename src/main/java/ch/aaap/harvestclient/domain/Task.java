package ch.aaap.harvestclient.domain;

import java.time.Instant;

import com.google.gson.annotations.SerializedName;

public class Task {

    private Long id;
    private String name;
    private Boolean billableByDefault;
    private Double defaultHourlyRate;

    @SerializedName("default")
    private Boolean defaultAddToFutureProjects;
    private Boolean active;

    private Instant createdAt;
    private Instant updatedAt;

}
