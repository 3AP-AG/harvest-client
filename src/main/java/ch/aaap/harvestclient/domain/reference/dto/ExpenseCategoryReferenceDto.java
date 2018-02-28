package ch.aaap.harvestclient.domain.reference.dto;

import ch.aaap.harvestclient.domain.Expense;
import ch.aaap.harvestclient.domain.reference.Reference;

public class ExpenseCategoryReferenceDto extends BaseReferenceDto implements Reference<Expense> {

    public ExpenseCategoryReferenceDto() {
    }

    public ExpenseCategoryReferenceDto(long id) {
        super(id);
    }
}
