package ch.aaap.harvestclient.domain;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import ch.aaap.harvestclient.domain.reference.dto.ProjectReferenceDto;

@Value.Immutable
public interface InvoiceLineItem {
    @Nullable
    Long getId();

    @Nullable
    ProjectReferenceDto getProjectReferenceDto();

    String getKind(); // Invoice Item Category

    @Nullable
    String getDescription();

    @Nullable
    Long getQuantity();

    @Nullable
    Double getUnitPrice();

    @Nullable
    Double getAmount();

    @Nullable
    Boolean getTaxed();

    @Nullable
    Boolean getTaxed2();
}
