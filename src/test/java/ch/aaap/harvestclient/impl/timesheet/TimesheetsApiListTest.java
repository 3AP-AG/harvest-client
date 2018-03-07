package ch.aaap.harvestclient.impl.timesheet;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.api.TimesheetsApi;
import ch.aaap.harvestclient.api.filter.TimeEntryFilter;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.*;
import ch.aaap.harvestclient.domain.pagination.Pagination;
import ch.aaap.harvestclient.domain.param.*;
import ch.aaap.harvestclient.domain.reference.Reference;
import ch.aaap.harvestclient.exception.NotFoundException;
import util.ExistingData;
import util.TestSetupUtil;

@HarvestTest
class TimesheetsApiListTest {

    private static final Harvest harvest = TestSetupUtil.getAdminAccess();
    private static final TimesheetsApi api = harvest.timesheets();
    private static final Reference<Project> project = ExistingData.getInstance().getProjectReference();
    private static final Reference<Project> anotherProject = ExistingData.getInstance().getAnotherProjectReference();
    private static final Reference<Task> existingTask = ExistingData.getInstance().getTaskReference();
    private static final Reference<User> user = ExistingData.getInstance().getUserReference();
    private static final Reference<User> anotherUser = harvest.users().getSelf();

    private static final Reference<TimeEntry> fixEntryReference = ExistingData.getInstance().getTimeEntryReference();

    private TimeEntry timeEntry;
    private TimeEntry anotherTimeEntry;
    private TaskAssignment taskAssignment;
    private Task task;
    private Invoice invoice;

    @AfterEach
    void AfterEach() {
        if (timeEntry != null) {
            api.delete(timeEntry);
            timeEntry = null;
        }
        if (anotherTimeEntry != null) {
            api.delete(anotherTimeEntry);
            anotherTimeEntry = null;
        }
        if (taskAssignment != null) {
            // TODO taskassignment should have a link to the project
            try {
                harvest.taskAssignments().delete(project, taskAssignment);
            } catch (NotFoundException e) {
                // ignore
                try {
                    harvest.taskAssignments().delete(anotherProject, taskAssignment);
                } catch (NotFoundException ex) {
                    // ignore
                }
            }
            taskAssignment = null;
        }
        if (invoice != null) {
            harvest.invoices().delete(invoice);
            invoice = null;
        }
        if (task != null) {
            harvest.tasks().delete(task);
            task = null;
        }
    }

    @Test
    void testList() {

        List<TimeEntry> timeEntries = api.list(TimeEntryFilter.emptyFilter());

        assertTrue(timeEntries.size() > 0);
    }

    @Test
    void testListFilterByUser() {

        TimeEntryFilter filter = new TimeEntryFilter();
        filter.setUserReference(harvest.users().getSelf());

        List<TimeEntry> entries = api.list(filter);

        assertTrue(entries.size() > 0);

        assertTrue(entries.stream().map(TimeEntry::getId).anyMatch(id -> id.equals(fixEntryReference.getId())));
    }

    @Test
    void listPaginated() {

        Pagination<TimeEntry> pagination = api.list(new TimeEntryFilter(), 1, 1);

        List<TimeEntry> result = pagination.getList();

        assertThat(result).hasSize(1);
        assertThat(pagination.getTotalPages()).isGreaterThanOrEqualTo(2);
        assertThat(pagination.getNextPage()).isEqualTo(2);
        assertThat(pagination.getPreviousPage()).isNull();
        assertThat(pagination.getPerPage()).isEqualTo(1);
        assertThat(pagination.getTotalPages()).isGreaterThanOrEqualTo(2);

    }

    @Test
    void listByUser() {

        TimeEntryCreationInfoDuration creationInfo = ImmutableTimeEntryCreationInfoDuration.builder()
                .projectReference(project)
                .taskReference(existingTask)
                .spentDate(LocalDate.now())
                .userReference(user)
                .build();

        timeEntry = api.create(creationInfo);

        creationInfo = ImmutableTimeEntryCreationInfoDuration.builder()
                .projectReference(project)
                .taskReference(existingTask)
                .spentDate(LocalDate.now())
                .userReference(anotherUser)
                .build();
        anotherTimeEntry = api.create(creationInfo);

        TimeEntryFilter filter = new TimeEntryFilter();
        filter.setUserReference(user);

        List<TimeEntry> timeEntries = api.list(filter);
        assertThat(timeEntries).contains(timeEntry);
        assertThat(timeEntries).doesNotContain(anotherTimeEntry);

    }

    @Test
    void listByProject() {
        TimeEntryCreationInfoDuration creationInfo = ImmutableTimeEntryCreationInfoDuration.builder()
                .projectReference(project)
                .taskReference(existingTask)
                .spentDate(LocalDate.now())
                .userReference(user)
                .build();
        timeEntry = api.create(creationInfo);

        taskAssignment = harvest.taskAssignments()
                .create(anotherProject, ImmutableTaskAssignment.builder()
                        .taskReference(existingTask)
                        .build());

        creationInfo = ImmutableTimeEntryCreationInfoDuration.builder()
                .projectReference(anotherProject)
                .taskReference(existingTask)
                .spentDate(LocalDate.now())
                .userReference(anotherUser)
                .build();
        anotherTimeEntry = api.create(creationInfo);

        TimeEntryFilter filter = new TimeEntryFilter();
        filter.setProjectReference(project);

        List<TimeEntry> timeEntries = api.list(filter);
        assertThat(timeEntries).contains(timeEntry);
        assertThat(timeEntries).doesNotContain(anotherTimeEntry);

    }

    @Test
    void listByClient() {

        TimeEntryCreationInfoDuration creationInfo = ImmutableTimeEntryCreationInfoDuration.builder()
                .projectReference(project)
                .taskReference(existingTask)
                .spentDate(LocalDate.now())
                .userReference(user)
                .build();
        timeEntry = api.create(creationInfo);

        taskAssignment = harvest.taskAssignments()
                .create(anotherProject, ImmutableTaskAssignment.builder()
                        .taskReference(existingTask)
                        .build());

        creationInfo = ImmutableTimeEntryCreationInfoDuration.builder()
                .projectReference(anotherProject)
                .taskReference(existingTask)
                .spentDate(LocalDate.now())
                .userReference(anotherUser)
                .build();
        anotherTimeEntry = api.create(creationInfo);

        TimeEntryFilter filter = new TimeEntryFilter();
        Project fullProject = harvest.projects().get(project);
        filter.setClientReference(fullProject.getClient());

        List<TimeEntry> timeEntries = api.list(filter);
        assertThat(timeEntries).contains(timeEntry);
        assertThat(timeEntries).doesNotContain(anotherTimeEntry);

    }

    @Test
    void listByBilled(TestInfo testInfo) {

        TimeEntryFilter filter = new TimeEntryFilter();
        filter.setBilled(true);
        List<TimeEntry> timeEntries = api.list(filter);

        task = harvest.tasks().create(ImmutableTask.builder()
                .name("Task for " + testInfo.getDisplayName())
                .build());
        taskAssignment = harvest.taskAssignments().create(project, ImmutableTaskAssignment.builder()
                .taskReference(task)
                .billable(true)
                .hourlyRate(100.)
                .build());

        TimeEntryCreationInfoDuration creationInfo = ImmutableTimeEntryCreationInfoDuration.builder()
                .projectReference(project)
                .taskReference(task)
                .spentDate(LocalDate.now())
                .userReference(user)
                .hours(2.)
                .build();
        timeEntry = api.create(creationInfo);

        assertThat(timeEntries).isEmpty();

        // mark the timeEntry as billed
        Project fullProject = harvest.projects().get(project);

        invoice = harvest.invoices().createFrom(ImmutableInvoiceImportInfo.builder()
                .client(fullProject.getClient())
                .lineItemsImport(ImmutableInvoiceItemImport.builder()
                        .addProject(project)
                        .invoiceTimeImport(ImmutableInvoiceTimeImport.builder()
                                .summaryType(InvoiceTimeImport.SummaryType.PROJECT)
                                .fromDate(LocalDate.now().minusDays(1))
                                .to(LocalDate.now().plusDays(1))
                                .build())
                        .build())
                .build());

        timeEntries = api.list(filter);
        timeEntry = api.get(timeEntry);
        assertThat(timeEntries).contains(timeEntry);

    }

    @Test
    void listByRunning() {

        TimeEntryCreationInfoDuration creationInfo = ImmutableTimeEntryCreationInfoDuration.builder()
                .projectReference(project)
                .taskReference(existingTask)
                .spentDate(LocalDate.now())
                .userReference(user)
                .build();
        timeEntry = api.create(creationInfo);

        creationInfo = ImmutableTimeEntryCreationInfoDuration.copyOf(creationInfo).withHours(2.);
        anotherTimeEntry = api.create(creationInfo);

        TimeEntryFilter filter = new TimeEntryFilter();
        filter.setRunning(true);
        List<TimeEntry> timeEntries = api.list(filter);

        assertThat(timeEntries).contains(timeEntry);
        assertThat(timeEntries).doesNotContain(anotherTimeEntry);

    }

    @Test
    void listByUpdatedSince() {

        TimeEntryCreationInfoDuration creationInfo = ImmutableTimeEntryCreationInfoDuration.builder()
                .projectReference(project)
                .taskReference(existingTask)
                .spentDate(LocalDate.now())
                .userReference(user)
                .build();
        timeEntry = api.create(creationInfo);
        Instant creationTime = timeEntry.getUpdatedAt();

        TimeEntryFilter filter = new TimeEntryFilter();
        filter.setUpdatedSince(creationTime.minusSeconds(1));
        List<TimeEntry> timeEntries = api.list(filter);

        assertThat(timeEntries).containsExactly(timeEntry);

        filter.setUpdatedSince(creationTime);
        timeEntries = api.list(filter);

        assertThat(timeEntries).doesNotContain(timeEntry);

    }

    @Test
    void listByDateRange() {

        TimeEntryCreationInfoDuration creationInfo = ImmutableTimeEntryCreationInfoDuration.builder()
                .projectReference(project)
                .taskReference(existingTask)
                .spentDate(LocalDate.of(2000, 1, 5))
                .userReference(user)
                .build();
        timeEntry = api.create(creationInfo);

        creationInfo = ImmutableTimeEntryCreationInfoDuration.builder()
                .projectReference(project)
                .taskReference(existingTask)
                .spentDate(LocalDate.of(2000, 1, 10))
                .userReference(anotherUser)
                .build();
        anotherTimeEntry = api.create(creationInfo);

        TimeEntryFilter filter = new TimeEntryFilter();
        filter.setFrom(LocalDate.of(2000, 1, 4));
        List<TimeEntry> timeEntries = api.list(filter);

        assertThat(timeEntries).contains(timeEntry);
        assertThat(timeEntries).contains(anotherTimeEntry);

        filter.setFrom(LocalDate.of(2000, 1, 6));
        timeEntries = api.list(filter);
        assertThat(timeEntries).doesNotContain(timeEntry);
        assertThat(timeEntries).contains(anotherTimeEntry);

        filter.setTo(LocalDate.of(2000, 1, 9));
        timeEntries = api.list(filter);
        assertThat(timeEntries).doesNotContain(timeEntry);
        assertThat(timeEntries).doesNotContain(anotherTimeEntry);

        filter.setTo(LocalDate.of(2000, 1, 11));
        timeEntries = api.list(filter);
        assertThat(timeEntries).doesNotContain(timeEntry);
        assertThat(timeEntries).contains(anotherTimeEntry);

    }
}