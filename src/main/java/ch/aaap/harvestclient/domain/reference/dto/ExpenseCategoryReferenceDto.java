package ch.aaap.harvestclient.domain.reference.dto;

import ch.aaap.harvestclient.domain.ExpenseCategory;
import ch.aaap.harvestclient.domain.reference.Reference;

public class ExpenseCategoryReferenceDto extends BaseReferenceDto implements Reference<ExpenseCategory> {

    public ExpenseCategoryReferenceDto() {
    }

    public ExpenseCategoryReferenceDto(long id) {
        super(id);
    }
}
