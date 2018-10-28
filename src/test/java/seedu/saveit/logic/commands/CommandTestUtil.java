package seedu.saveit.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_SOLUTION_LINK;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_STATEMENT;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.saveit.commons.core.index.Index;
import seedu.saveit.logic.CommandHistory;
import seedu.saveit.logic.commands.exceptions.CommandException;
import seedu.saveit.logic.parser.exceptions.ParseException;
import seedu.saveit.model.Issue;
import seedu.saveit.model.Model;
import seedu.saveit.model.SaveIt;
import seedu.saveit.model.issue.IssueContainsKeywordsPredicate;
import seedu.saveit.testutil.EditIssueDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_STATEMENT_JAVA = "Jave Issue";
    public static final String VALID_STATEMENT_C = "C Issue";
    public static final String VALID_DESCRIPTION_JAVA = "syntax error";
    public static final String VALID_DESCRIPTION_C = "94351253";
    public static final String VALID_SOLUTION_JAVA = "http://www.oracle.com RemarkJava";
    public static final String VALID_SOLUTION_C = "https://stackoverflow.com/ RemarkC";
    public static final String VALID_SOLUTION_STACKOVERLOW = "https://stackoverflow.com/ newSol";
    public static final String VALID_TAG_UI = "ui";
    public static final String VALID_TAG_SYNTAX = "syntax";

    public static final String STATEMENT_DESC_JAVA = " " + PREFIX_STATEMENT + VALID_STATEMENT_JAVA;
    public static final String STATEMENT_DESC_C = " " + PREFIX_STATEMENT + VALID_STATEMENT_C;
    public static final String DESCRIPTION_DESC_JAVA = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_JAVA;
    public static final String DESCRIPTION_DESC_C = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_C;
    public static final String SOLUTION_DESC_JAVA = " " + PREFIX_SOLUTION_LINK + VALID_SOLUTION_JAVA;
    public static final String SOLUTION_DESC_C = " " + PREFIX_SOLUTION_LINK + VALID_SOLUTION_C;
    public static final String TAG_DESC_SYNTAX = " " + PREFIX_TAG + VALID_TAG_SYNTAX;
    public static final String TAG_DESC_UI = " " + PREFIX_TAG + VALID_TAG_UI;

    public static final String INVALID_STATEMENT_DESC = " " + PREFIX_STATEMENT + "James&"; // '&' not allowed in names
    public static final String INVALID_DESCRIPTION_DESC =
        " " + PREFIX_DESCRIPTION + " "; // 'a' not allowed in descriptionss
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditIssueDescriptor DESC_JAVA_PROGRAMMING;
    public static final EditCommand.EditIssueDescriptor DESC_C_PROGRAMMING;

    static {
        DESC_JAVA_PROGRAMMING = new EditIssueDescriptorBuilder().withStatement(VALID_STATEMENT_JAVA)
            .withDescription(VALID_DESCRIPTION_JAVA)
            .withSolutions(VALID_SOLUTION_JAVA)
            .withTags(VALID_TAG_SYNTAX).build();
        DESC_C_PROGRAMMING = new EditIssueDescriptorBuilder().withStatement(VALID_STATEMENT_C)
            .withDescription(VALID_DESCRIPTION_C)
            .withSolutions(VALID_SOLUTION_C)
            .withTags(VALID_TAG_UI, VALID_TAG_SYNTAX).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br> - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br> - the {@code actualCommandHistory} remains
     * unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
        String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
            try {
                CommandResult result = command.execute(actualModel, actualCommandHistory);

                System.out.println("check model diff filtered " + actualModel.getFilteredIssueList().equals(expectedModel.getFilteredIssueList()));
                System.out.println("check model diff sortedlist " + actualModel.getSortedIssueList().equals(expectedModel.getSortedIssueList()));

                assertEquals(actualModel.getFilteredIssueList(), expectedModel.getFilteredIssueList());
                assertEquals(actualModel.getSortedIssueList(), expectedModel.getSortedIssueList());
                assertEquals(actualModel.getSaveIt(), expectedModel.getSaveIt());


                assertEquals(expectedMessage, result.feedbackToUser);
                assertEquals(expectedModel, actualModel);
                assertEquals(expectedCommandHistory, actualCommandHistory);
            } catch (CommandException | ParseException ce) {
                throw new AssertionError("Execution of command should not fail.", ce);
            }
    }

    /**
     * Executes the given {@code command}, confirms that <br> - a {@code CommandException} is thrown <br> - the
     * CommandException message matches {@code expectedMessage} <br> - the saveit book and the filtered issue list in
     * the {@code actualModel} remain unchanged <br> - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
        String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        SaveIt expectedSaveIt = new SaveIt(actualModel.getSaveIt());
        List<Issue> expectedFilteredList = new ArrayList<>(actualModel.getFilteredIssueList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
            try {
                command.execute(actualModel, actualCommandHistory);
                throw new AssertionError("The expected CommandException was not thrown.");
            } catch (CommandException | ParseException e) {
                assertEquals(expectedMessage, e.getMessage());
                assertEquals(expectedSaveIt, actualModel.getSaveIt());
                assertEquals(expectedFilteredList, actualModel.getFilteredIssueList());
                assertEquals(expectedCommandHistory, actualCommandHistory);
            }
    }

    /**
     * Updates {@code model}'s filtered list to show only the issue at the given {@code targetIndex} in the {@code
     * model}'s saveit book.
     */
    public static void showIssueAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredIssueList().size());

        Issue issue = model.getFilteredIssueList().get(targetIndex.getZeroBased());
        final String[] splitName = issue.getStatement().issue.split("\\s+");
        model.updateFilteredIssueList(new IssueContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredIssueList().size());
    }

    /**
     * Deletes the first issue in {@code model}'s filtered list from {@code model}'s saveit book.
     */
    public static void deleteFirstIssue(Model model) {
        Issue firstIssue = model.getFilteredIssueList().get(0);
        model.deleteIssue(firstIssue);
        model.commitSaveIt();
    }

}
