package seedu.saveit.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.saveit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.saveit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.saveit.logic.commands.CommandTestUtil.showIssueAtIndex;
import static seedu.saveit.testutil.TypicalIndexes.INDEX_FIRST_ISSUE;
import static seedu.saveit.testutil.TypicalIndexes.INDEX_SECOND_ISSUE;
import static seedu.saveit.testutil.TypicalIndexes.INDEX_THIRD_ISSUE;
import static seedu.saveit.testutil.TypicalIssues.getTypicalSaveIt;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import seedu.saveit.commons.core.Messages;
import seedu.saveit.commons.core.index.Index;
import seedu.saveit.commons.events.ui.JumpToListRequestEvent;
import seedu.saveit.logic.CommandHistory;
import seedu.saveit.model.Model;
import seedu.saveit.model.ModelManager;
import seedu.saveit.model.UserPrefs;
import seedu.saveit.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalSaveIt(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSaveIt(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    @Ignore
    public void execute_validIndexUnfilteredList_success() {
        Index lastIssueIndex = Index.fromOneBased(model.getFilteredIssueList().size());
        assertExecutionSuccess(INDEX_FIRST_ISSUE);
        assertExecutionSuccess(INDEX_THIRD_ISSUE);
        assertExecutionSuccess(lastIssueIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredIssueList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_ISSUE_DISPLAYED_INDEX);
    }

    @Test
    @Ignore
    public void execute_validIndexFilteredList_success() {
        showIssueAtIndex(model, INDEX_FIRST_ISSUE);
        showIssueAtIndex(expectedModel, INDEX_FIRST_ISSUE);

        assertExecutionSuccess(INDEX_FIRST_ISSUE);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showIssueAtIndex(model, INDEX_FIRST_ISSUE);
        showIssueAtIndex(expectedModel, INDEX_FIRST_ISSUE);

        Index outOfBoundsIndex = INDEX_SECOND_ISSUE;
        // ensures that outOfBoundIndex is still in bounds of saveit book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getSaveIt().getIssueList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_ISSUE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_ISSUE);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_ISSUE);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_ISSUE);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different issue -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_ISSUE_SUCCESS, index.getOneBased());

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
