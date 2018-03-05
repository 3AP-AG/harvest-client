package ch.aaap.harvestclient.domain.reference.dto;

import ch.aaap.harvestclient.domain.ExpenseCategory;
import ch.aaap.harvestclient.domain.reference.Reference;

public class ExpenseCategoryReferenceDto extends BaseReferenceDto implements Reference<ExpenseCategory> {

    private Double unitPrice;
    private String unitName;

    public ExpenseCategoryReferenceDto() {
    }

    public ExpenseCategoryReferenceDto(long id) {
        super(id);
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
