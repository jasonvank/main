package seedu.saveit.logic.commands;

import static seedu.saveit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.saveit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.saveit.logic.commands.CommandTestUtil.deleteFirstIssue;
import static seedu.saveit.testutil.TypicalIssues.getTypicalSaveIt;

import org.junit.Before;
import org.junit.Test;

import seedu.saveit.logic.CommandHistory;
import seedu.saveit.model.Model;
import seedu.saveit.model.ModelManager;
import seedu.saveit.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalSaveIt(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalSaveIt(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstIssue(model);
        deleteFirstIssue(model);
        model.undoSaveIt();
        model.undoSaveIt();

        deleteFirstIssue(expectedModel);
        deleteFirstIssue(expectedModel);
        expectedModel.undoSaveIt();
        expectedModel.undoSaveIt();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoSaveIt();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoSaveIt();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
