package ch.aaap.harvestclient.domain;

import java.time.Instant;
import java.time.LocalDate;

import ch.aaap.harvestclient.domain.reference.dto.ClientReferenceDto;
import ch.aaap.harvestclient.domain.reference.dto.ProjectReferenceDto;
import ch.aaap.harvestclient.domain.reference.dto.UserReferenceDto;

public class Expense {

    private Long id;
    private ClientReferenceDto clientReferenceDto;
    private ProjectReferenceDto projectReferenceDto;
    private ExpenseCategory expenseCategory;
    private UserReferenceDto userReferenceDto;

    private UserAssignment userAssignment;

    private Receipt receipt;
    private Invoice invoice;
    private String notes;
    private Boolean billable;
    private Boolean closed;
    private Boolean locked;
    private Boolean billed;

    private String lockedReason;
    private LocalDate spentDate;

    private Instant createdAt;
    private Instant updatedAt;

}
