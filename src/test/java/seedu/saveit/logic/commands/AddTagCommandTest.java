package seedu.saveit.logic.commands;

import static seedu.saveit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.saveit.logic.commands.CommandTestUtil.VALID_TAG_PYTHON;
import static seedu.saveit.logic.commands.CommandTestUtil.VALID_TAG_SYNTAX;
import static seedu.saveit.logic.commands.CommandTestUtil.VALID_TAG_UI;
import static seedu.saveit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.saveit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.saveit.testutil.TypicalIndexes.INDEX_FIRST_ISSUE;
import static seedu.saveit.testutil.TypicalIndexes.INDEX_SECOND_ISSUE;
import static seedu.saveit.testutil.TypicalIndexes.INDEX_THIRD_ISSUE;
import static seedu.saveit.testutil.TypicalIssues.getTypicalSaveIt;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.saveit.commons.core.index.Index;
import seedu.saveit.logic.CommandHistory;
import seedu.saveit.model.Issue;
import seedu.saveit.model.Model;
import seedu.saveit.model.ModelManager;
import seedu.saveit.model.UserPrefs;
import seedu.saveit.model.issue.Tag;

public class AddTagCommandTest {

    private Model model = new ModelManager(getTypicalSaveIt(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void execute_addValidTagValidIndex_success() throws Exception {
        Set<Tag> tagSet = new HashSet<>();
        Set<Index> indexSet = new HashSet<>();
        Tag tagToAdd = new Tag(VALID_TAG_UI);
        tagSet.add(tagToAdd);
        indexSet.add(INDEX_FIRST_ISSUE);

        AddTagCommand addTagCommand = new AddTagCommand(indexSet, tagSet);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getSaveIt(), new UserPrefs());

        Set<Issue> issueToEdit = getIssueToEdit(indexSet, expectedModel);

        expectedModel.addTag(issueToEdit, tagSet);
        expectedModel.commitSaveIt();

        assertCommandSuccess(addTagCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addValidTagDiscreteIndexes_success() throws Exception {
        Set<Tag> tagSet = new HashSet<>();
        Set<Index> indexSet = new HashSet<>();
        Tag tagToAdd = new Tag(VALID_TAG_UI);
        tagSet.add(tagToAdd);
        indexSet.add(INDEX_FIRST_ISSUE);
        indexSet.add(INDEX_THIRD_ISSUE);

        AddTagCommand addTagCommand = new AddTagCommand(indexSet, tagSet);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getSaveIt(), new UserPrefs());

        Set<Issue> issueToEdit = getIssueToEdit(indexSet, expectedModel);

        expectedModel.addTag(issueToEdit, tagSet);
        expectedModel.commitSaveIt();

        assertCommandSuccess(addTagCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_addMultipleValidTagsRangeIndexes_success() throws Exception {
        Tag tagToAdd1 = new Tag(VALID_TAG_PYTHON);
        Tag tagToAdd2 = new Tag(VALID_TAG_UI);
        Tag tagToAdd3 = new Tag(VALID_TAG_SYNTAX);
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd1);
        tagSet.add(tagToAdd2);
        tagSet.add(tagToAdd3);
        indexSet.add(INDEX_FIRST_ISSUE);
        indexSet.add(INDEX_SECOND_ISSUE);
        indexSet.add(INDEX_THIRD_ISSUE);

        AddTagCommand addTagCommand = new AddTagCommand(indexSet, tagSet);

        String expectedMessage = AddTagCommand.MESSAGE_ADD_TAG_SUCCESS;
        ModelManager expectedModel = new ModelManager(model.getSaveIt(), new UserPrefs());
        Set<Issue> issueToEdit = getIssueToEdit(indexSet, expectedModel);
        expectedModel.addTag(issueToEdit, tagSet);
        expectedModel.commitSaveIt();

        assertCommandSuccess(addTagCommand, model, commandHistory, expectedMessage, expectedModel);
    }


    @Test
    public void execute_addMultipleValidTagsDiscreteIndexes_success() throws Exception {
        Tag tagToAdd1 = new Tag(VALID_TAG_PYTHON);
        Tag tagToAdd2 = new Tag(VALID_TAG_UI);
        Tag tagToAdd3 = new Tag(VALID_TAG_SYNTAX);
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd1);
        tagSet.add(tagToAdd2);
        tagSet.add(tagToAdd3);
        indexSet.add(INDEX_FIRST_ISSUE);
        indexSet.add(INDEX_THIRD_ISSUE);

        AddTagCommand addTagCommand = new AddTagCommand(indexSet, tagSet);
        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS);
        ModelManager expectedModel = new ModelManager(model.getSaveIt(), new UserPrefs());
        Set<Issue> issueToEdit = getIssueToEdit(indexSet, expectedModel);
        expectedModel.addTag(issueToEdit, tagSet);
        expectedModel.commitSaveIt();

        assertCommandSuccess(addTagCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addMultipleValidTagsValidIndexes_success() throws Exception {
        Tag tagToAdd1 = new Tag(VALID_TAG_PYTHON);
        Tag tagToAdd2 = new Tag(VALID_TAG_UI);
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd1);
        tagSet.add(tagToAdd2);
        indexSet.add(INDEX_FIRST_ISSUE);

        AddTagCommand addTagCommand = new AddTagCommand(indexSet, tagSet);
        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS);
        ModelManager expectedModel = new ModelManager(model.getSaveIt(), new UserPrefs());
        Set<Issue> issueToEdit = getIssueToEdit(indexSet, expectedModel);
        expectedModel.addTag(issueToEdit, tagSet);
        expectedModel.commitSaveIt();

        assertCommandSuccess(addTagCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    // failure
    @Test
    public void execute_addDuplicateTag_throwsCommandException() throws Exception {
        Tag tagToAdd = new Tag(VALID_TAG_SYNTAX);
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd);
        indexSet.add(INDEX_FIRST_ISSUE);

        AddTagCommand addTagCommand = new AddTagCommand(indexSet, tagSet);

        assertCommandFailure(addTagCommand, model, commandHistory, String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_DUPLICATE_TAG));

    }

    @Test
    public void execute_addValidTagBiggerThanHigherBoundIndex_throwsCommandException() throws Exception {
        Set<Tag> tagSet = new HashSet<>();
        Set<Index> indexSet = new HashSet<>();
        Tag tagToAdd = new Tag(VALID_TAG_UI);
        tagSet.add(tagToAdd);
        indexSet.add(Index.fromOneBased(model.getFilteredAndSortedIssueList().size() + 1));

        AddTagCommand addTagCommand = new AddTagCommand(indexSet, tagSet);

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE);

        assertCommandFailure(addTagCommand, model, commandHistory, expectedMessage);
    }


    private Set<Issue> getIssueToEdit(Set<Index> indexSet, ModelManager expectedModel) {
        Set<Issue> issueToEdit = new HashSet<>();
        List<Issue> lastShownList = expectedModel.getFilteredAndSortedIssueList();
        indexSet.forEach(issueIndex -> {
            issueToEdit.add(lastShownList.get(issueIndex.getZeroBased()));
        });
        return issueToEdit;
    }
}
