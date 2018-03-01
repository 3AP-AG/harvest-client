package ch.aaap.harvestclient.domain.reference.dto;

import ch.aaap.harvestclient.domain.Invoice;
import ch.aaap.harvestclient.domain.reference.Reference;

public class InvoiceReferenceDto extends BaseReferenceDto implements Reference<Invoice> {
    public InvoiceReferenceDto() {
    }

    public InvoiceReferenceDto(long id) {
        super(id);
    }
}
