package ch.aaap.harvestclient.domain;

import java.time.LocalDate;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import ch.aaap.harvestclient.domain.reference.dto.ClientReferenceDto;
import ch.aaap.harvestclient.domain.reference.dto.ProjectReferenceDto;
import ch.aaap.harvestclient.domain.reference.dto.UserReferenceDto;

@Value.Immutable
public interface Expense extends BaseObject<Expense> {
    @Nullable
    ClientReferenceDto getClientReferenceDto();

    ProjectReferenceDto getProjectReferenceDto();

    ExpenseCategory getExpenseCategory();

    @Nullable
    UserReferenceDto getUserReferenceDto();

    @Nullable
    UserAssignment getUserAssignment();

    @Nullable
    Receipt getReceipt();

    @Nullable
    Invoice getInvoice();

    @Nullable
    String getNotes();

    @Nullable
    Boolean getBillable();

    @Nullable
    Boolean getClosed();

    @Nullable
    Boolean getLocked();

    @Nullable
    Boolean getBilled();

    @Nullable
    String getLockedReason();

    LocalDate getSpentDate();

}
