package ch.aaap.harvestclient.domain.reference.dto;

import javax.annotation.Nullable;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import ch.aaap.harvestclient.domain.ExpenseCategory;
import ch.aaap.harvestclient.domain.reference.Reference;

@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface ExpenseCategoryReferenceDto extends BaseReferenceDto, Reference<ExpenseCategory> {
    @Nullable
    Double getUnitPrice();

    @Nullable
    String getUnitName();

}
