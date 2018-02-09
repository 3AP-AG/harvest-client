package ch.aaap.harvestclient.impl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.CompanyApi;
import ch.aaap.harvestclient.domain.Company;
import util.TestSetupUtil;

@HarvestTest
class CompanyApiImplTest {

    @Test
    void testGet() {

        CompanyApi api = TestSetupUtil.getAdminAccess().company();

        Company company = api.get();

        assertThat(company.getActive()).isNotNull();

    }
}