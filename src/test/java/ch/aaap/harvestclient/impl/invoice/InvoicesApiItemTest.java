package ch.aaap.harvestclient.impl.invoice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.harvestclient.api.InvoicesApi;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.domain.param.ImmutableInvoiceItemUpdateInfo;
import ch.aaap.harvestclient.domain.param.InvoiceItemUpdateInfo;
import ch.aaap.harvestclient.domain.reference.Reference;
import util.ExistingData;
import util.TestSetupUtil;

public class InvoicesApiItemTest {

    private static final Logger log = LoggerFactory.getLogger(InvoicesApiItemTest.class);

    private final InvoicesApi api = TestSetupUtil.getAdminAccess().invoices();

    private static final String kind = ExistingData.getInstance().getInvoiceItemCategory().getName();

    private static final String anotherKind = ExistingData.getInstance().getAnotherInvoiceItemCategory().getName();

    private final Reference<Client> clientReference = ExistingData.getInstance().getClientReference();

    private Invoice invoice;

    @BeforeEach
    void beforeEach() {
        invoice = api.create(ImmutableInvoice.builder()
                .client(clientReference)
                .build());
    }

    @AfterEach
    void afterEach() {
        if (invoice != null) {
            api.delete(invoice);
            invoice = null;
        }
    }

    @Test
    void createItem() {

        InvoiceItem creationInfo = ImmutableInvoiceItem.builder()
                .kind(kind)
                .unitPrice(1.)
                .build();
        invoice = api.addLineItem(invoice, creationInfo);

        assertThat(invoice.getInvoiceItems()).isNotEmpty();
        InvoiceItem invoiceItem = invoice.getInvoiceItems().get(0);

        assertThat(invoiceItem).isEqualToIgnoringNullFields(creationInfo);
    }

    @Test
    void createMultipleItems() {

        List<InvoiceItem> creationInfoList = new ArrayList<>();
        creationInfoList.add(ImmutableInvoiceItem.builder()
                .kind(kind)
                .unitPrice(2.)
                .build());
        creationInfoList.add(ImmutableInvoiceItem.builder()
                .kind(kind)
                .unitPrice(5.)
                .build());
        invoice = api.addLineItems(this.invoice, creationInfoList);

        for (int i = 0; i < creationInfoList.size(); i++) {
            assertThat(invoice.getInvoiceItems().get(i)).isEqualToIgnoringNullFields(creationInfoList.get(i));
        }
    }

    @Nested
    class UpdateTest {

        private InvoiceItem item;

        @BeforeEach
        void beforeEach() {
            InvoiceItem creationInfo = ImmutableInvoiceItem.builder()
                    .kind(kind)
                    .unitPrice(1.)
                    .build();
            invoice = api.addLineItem(invoice, creationInfo);
            item = invoice.getInvoiceItems().get(0);
        }

        @Test
        void updateKind() {

            InvoiceItemUpdateInfo updateInfo = ImmutableInvoiceItemUpdateInfo.builder()
                    .kind(anotherKind)
                    .build();
            invoice = api.updateLineItem(invoice, item, updateInfo);

            InvoiceItem updatedItem = invoice.getInvoiceItems().get(0);

            assertThat(updatedItem.getKind()).isEqualTo(anotherKind);

        }

        @Test
        void updateAll() {

            InvoiceItemUpdateInfo updateInfo = ImmutableInvoiceItemUpdateInfo.builder()
                    .kind(anotherKind)
                    .description("updated description")
                    .quantity(22L)
                    .unitPrice(33.)
                    .taxed(true)
                    .taxed2(true)
                    .build();
            invoice = api.updateLineItem(invoice, item, updateInfo);

            InvoiceItem updatedItem = invoice.getInvoiceItems().get(0);

            assertThat(updatedItem).isEqualToIgnoringNullFields(updateInfo);

        }

        @Test
        void updateMultiple() {

            InvoiceItem item2 = ImmutableInvoiceItem.builder()
                    .kind(kind)
                    .unitPrice(2.)
                    .build();
            invoice = api.addLineItem(invoice, item2);
            item2 = invoice.getInvoiceItems().get(1);

            List<InvoiceItemUpdateInfo> updateInfoList = new ArrayList<>();
            updateInfoList.add(ImmutableInvoiceItemUpdateInfo.builder()
                    .kind(anotherKind)
                    .description("updated description")
                    .quantity(22L)
                    .unitPrice(33.)
                    .taxed(true)
                    .taxed2(true)
                    .build());
            updateInfoList.add(ImmutableInvoiceItemUpdateInfo.builder()
                    .kind(anotherKind)
                    .description("updated description 2")
                    .quantity(2L)
                    .unitPrice(3.)
                    .taxed(false)
                    .taxed2(true)
                    .build());
            invoice = api.updateLineItems(invoice, Arrays.asList(item, item2), updateInfoList);

            for (int i = 0; i < updateInfoList.size(); i++) {
                InvoiceItem updatedItem = invoice.getInvoiceItems().get(i);
                assertThat(updatedItem).as("Check item " + i).isEqualToIgnoringNullFields(updateInfoList.get(i));
            }

        }

        @Nested
        class DeleteTest {

            @Test
            void deleteOne() {
                invoice = api.deleteLineItem(InvoicesApiItemTest.this.invoice, item);
                assertThat(invoice.getInvoiceItems()).isEmpty();
            }

            @Test
            void deleteMultiple() {

                InvoiceItem creationInfo = ImmutableInvoiceItem.builder()
                        .kind(kind)
                        .unitPrice(2.)
                        .build();
                invoice = api.addLineItem(invoice, creationInfo);

                invoice = api.deleteLineItems(invoice, invoice.getInvoiceItems());

                assertThat(invoice.getInvoiceItems()).isEmpty();
            }
        }
    }

}
