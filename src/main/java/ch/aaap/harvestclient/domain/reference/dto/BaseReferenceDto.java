package ch.aaap.harvestclient.domain.reference.dto;

/**
 * Used to deserialize objects references in other APIs, e.g. from the TimeEntry
 * list
 */
public interface BaseReferenceDto {

    Long getId();

    String getName();

}
