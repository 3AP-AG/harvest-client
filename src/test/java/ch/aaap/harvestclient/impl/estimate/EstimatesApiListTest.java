package ch.aaap.harvestclient.impl.estimate;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.EstimatesApi;
import ch.aaap.harvestclient.api.filter.EstimateFilter;
import ch.aaap.harvestclient.domain.Client;
import ch.aaap.harvestclient.domain.Estimate;
import ch.aaap.harvestclient.domain.ImmutableEstimate;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class EstimatesApiListTest {

    private static final EstimatesApi estimatesApi = TestSetupUtil.getAdminAccess().estimates();
    private Estimate anotherEstimate;
    private Estimate estimate;
    private Reference<Client> clientReference = ExistingData.getInstance().getClientReference();

    @AfterEach
    void afterEach() {
        if (estimate != null) {
            estimatesApi.delete(estimate);
            estimate = null;
        }
        if (anotherEstimate != null) {
            estimatesApi.delete(anotherEstimate);
            anotherEstimate = null;
        }
    }

    @Test
    void list() {

        Estimate creationInfo = ImmutableEstimate.builder()
                .client(clientReference)
                .build();
        estimate = estimatesApi.create(creationInfo);

        List<Estimate> estimates = estimatesApi.list(new EstimateFilter());

        assertThat(estimates).isNotEmpty();

    }

    @Test
    void listPaginated() {

        Estimate creationInfo = ImmutableEstimate.builder()
                .client(clientReference)
                .build();
        anotherEstimate = estimatesApi.create(creationInfo);
        estimate = estimatesApi.create(creationInfo);

        Pagination<Estimate> estimates = estimatesApi.list(new EstimateFilter(), 1, 1);

        List<Estimate> contacts = estimates.getList();

        assertThat(contacts).hasSize(1);
        assertThat(estimates.getNextPage()).isEqualTo(2);
        assertThat(estimates.getPreviousPage()).isNull();
        assertThat(estimates.getPerPage()).isEqualTo(1);
        assertThat(estimates.getTotalPages()).isGreaterThanOrEqualTo(2);

    }

    @Test
    void listByClient() {

        Reference<Client> anotherClientReference = ExistingData.getInstance().getAnotherClientReference();

        Estimate creationInfo = ImmutableEstimate.builder()
                .client(anotherClientReference)
                .build();
        estimate = estimatesApi.create(creationInfo);

        EstimateFilter filter = new EstimateFilter();
        filter.setClientReference(anotherClientReference);

        List<Estimate> estimates = estimatesApi.list(filter);

        assertThat(estimates).usingFieldByFieldElementComparator().contains(estimate);

    }

    @Test
    void listByUpdatedSince() {

        Instant creationTime = Instant.now().minusSeconds(1);
        Estimate creationInfo = ImmutableEstimate.builder()
                .client(clientReference)
                .build();
        estimate = estimatesApi.create(creationInfo);

        EstimateFilter filter = new EstimateFilter();
        filter.setUpdatedSince(creationTime);

        List<Estimate> estimates = estimatesApi.list(filter);

        assertThat(estimates).usingFieldByFieldElementComparator().containsExactly(estimate);

    }

}
