package ch.aaap.harvestclient.domain.param;

import ch.aaap.harvestclient.domain.Project;
import ch.aaap.harvestclient.domain.reference.ClientReference;
import ch.aaap.harvestclient.domain.reference.dto.ClientReferenceDto;
import ch.aaap.harvestclient.exception.HarvestRuntimeException;

public class ProjectCreationInfo extends Project {

    private Long clientId;

    public ProjectCreationInfo(ClientReference clientReference, String name, boolean isBillable, BillingMethod billBy,
            BudgetMethod budgetBy) {
        super();
        clientId = clientReference.getId();
        setName(name);
        setBillable(isBillable);
        setBillBy(billBy);
        setBudgetBy(budgetBy);
    }

    public void setClientReference(ClientReference clientReference) {
        // we only need an id for creation
        setClientReference(new ClientReferenceDto(clientReference.getId()));
    }

    @Override
    public void setId(Long id) {
        throw new HarvestRuntimeException("Setting Id not allowed, Harvest will assign one");
    }
}
