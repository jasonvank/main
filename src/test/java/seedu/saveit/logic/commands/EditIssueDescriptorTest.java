package seedu.saveit.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.saveit.logic.commands.CommandTestUtil.DESC_C_PROGRAMMING;
import static seedu.saveit.logic.commands.CommandTestUtil.DESC_JAVA_PROGRAMMING;
import static seedu.saveit.logic.commands.CommandTestUtil.VALID_DESCRIPTION_C;
import static seedu.saveit.logic.commands.CommandTestUtil.VALID_SOLUTION_C;
import static seedu.saveit.logic.commands.CommandTestUtil.VALID_STATEMENT_C;
import static seedu.saveit.logic.commands.CommandTestUtil.VALID_TAG_UI;

import org.junit.Test;

import seedu.saveit.logic.commands.EditCommand.EditIssueDescriptor;
import seedu.saveit.testutil.EditIssueDescriptorBuilder;

public class EditIssueDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditIssueDescriptor descriptorWithSameValues = new EditCommand.EditIssueDescriptor(DESC_JAVA_PROGRAMMING);
        assertTrue(DESC_JAVA_PROGRAMMING.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_JAVA_PROGRAMMING.equals(DESC_JAVA_PROGRAMMING));

        // null -> returns false
        assertFalse(DESC_JAVA_PROGRAMMING.equals(null));

        // different types -> returns false
        assertFalse(DESC_JAVA_PROGRAMMING.equals(5));

        // different values -> returns false
        assertFalse(DESC_JAVA_PROGRAMMING.equals(DESC_C_PROGRAMMING));

        // different name -> returns false
        EditIssueDescriptor editedAmy = new EditIssueDescriptorBuilder(DESC_JAVA_PROGRAMMING)
                .withStatement(VALID_STATEMENT_C).build();
        assertFalse(DESC_JAVA_PROGRAMMING.equals(editedAmy));

        // different description -> returns false
        editedAmy = new EditIssueDescriptorBuilder(DESC_JAVA_PROGRAMMING).withDescription(VALID_DESCRIPTION_C).build();
        assertFalse(DESC_JAVA_PROGRAMMING.equals(editedAmy));

        // different solutions -> returns false
        editedAmy = new EditIssueDescriptorBuilder(DESC_JAVA_PROGRAMMING).withSolutions(VALID_SOLUTION_C).build();
        assertFalse(DESC_JAVA_PROGRAMMING.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditIssueDescriptorBuilder(DESC_JAVA_PROGRAMMING).withTags(VALID_TAG_UI).build();
        assertFalse(DESC_JAVA_PROGRAMMING.equals(editedAmy));
    }
}
