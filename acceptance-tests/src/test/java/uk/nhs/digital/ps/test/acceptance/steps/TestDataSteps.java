package uk.nhs.digital.ps.test.acceptance.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import uk.nhs.digital.ps.test.acceptance.data.TestDataRepo;
import uk.nhs.digital.ps.test.acceptance.pages.ContentPage;

import static org.slf4j.LoggerFactory.getLogger;

public class TestDataSteps extends AbstractSpringSteps {

    private final static Logger log = getLogger(TestDataSteps.class);

    @Autowired
    private ContentPage contentPage;

    @Autowired
    private TestDataRepo testDataRepo;

    /**
     * Resets the test data repository before every scenario to prevent data leaking between scenarios, unless given
     * scenario is tagged with {@code @NeedsExistingTestData}.
     */
    @Before(value = "~@NeedsExistingTestData")
    public void clearTestData() {
        log.debug("Disposing of test data.");
        testDataRepo.clear();
    }

    /**
     * <p>
     * Closes the current publication discarding current changes.
     * </p><p>
     * Only applicable to scenarios that leave the document in editable state; implemented as an {@linkplain After}
     * hook rather than a step because this is really a test cleanup/tear-down activity that doesn't warrant expressing
     * explicitly as a scenario step.
     * </p><p>
     * To ensure that this method gets called at the end of your scenario, tag the scenario with
     * {@code @DiscardAfter}.
     * </p>
     */
    @After(value = "@DiscardAfter", order = 500)
    public void discardEditedPublication() throws Throwable {
        final String currentPublicationName = testDataRepo.getCurrentPublication().getName();
        log.debug("Discarding and closing current publication: {}.", currentPublicationName);

        contentPage.discardUnsavedChanges(currentPublicationName);
    }

    /**
     * <p>
     * Takes current publication offline (un-publishes it).
     * </p><p>
     * Only applicable to scenarios leaving a published publication behind.
     * </p><p>
     * To ensure that this method gets called at the end of your scenario, tag the scenario with
     * {@code @TakeOfflineAfter}.
     * </p>
     */
    @After(value = "@TakeOfflineAfter", order = 500)
    public void takePublicationOffline() throws Throwable {
        final String currentPublicationName = testDataRepo.getCurrentPublication().getName();
        log.debug("Taking current publication offline: {}.", currentPublicationName);

        contentPage.openCms();
        contentPage.openContentTab();
        contentPage.unpublishDocument(currentPublicationName);
    }
}
