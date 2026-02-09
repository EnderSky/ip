package glados.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import glados.command.Command;
import glados.command.CommandAddTask;
import glados.command.CommandBye;
import glados.command.CommandList;
import glados.command.CommandMark;
import glados.utils.Ui;
import glados.utils.GladosException;

/**
 * Test class for InputParser.
 */
public class InputParserTest {

    @Test
    public void parseInput_byeCommand_success() throws GladosException {
        Command command = InputParser.parseInput("bye");
        assertTrue(command instanceof CommandBye);
    }

    @Test
    public void parseInput_listCommand_success() throws GladosException {
        Command command = InputParser.parseInput("list");
        assertTrue(command instanceof CommandList);
    }

    @Test
    public void parseInput_markCommand_success() throws GladosException {
        Command command = InputParser.parseInput("mark 2");
        assertTrue(command instanceof CommandMark);
    }

    @Test
    public void parseInput_markCommand_incorrectFormat_throwsException() {
        String input = "mark two";
        try {
            InputParser.parseInput(input);
        } catch (GladosException e) {
            assertEquals(Ui.getErrorIncorrectNumberFormat("mark"), e.getMessage());
        }
    }

    @Test
    public void parseInput_unmarkCommand_success() throws GladosException {
        Command command = InputParser.parseInput("unmark 3");
        assertTrue(command instanceof CommandMark);
    }

    @Test
    public void parseInput_todoCommand_success() throws GladosException {
        Command command = InputParser.parseInput("todo Read book");
        assertTrue(command instanceof CommandAddTask);
    }

    @Test
    public void parseInput_todoCommand_emptyDescription_throwsException() {
        String input = "todo   ";
        try {
            InputParser.parseInput(input);
        } catch (GladosException e) {
            assertEquals(Ui.getErrorEmpty("Description of a todo"), e.getMessage());
        }
    }

    @Test
    public void parseInput_todoCommand_incorrectFormat_throwsException() {
        String input = "todo";
        try {
            InputParser.parseInput(input);
        } catch (GladosException e) {
            assertEquals(Ui.getErrorIncorrectCommandFormat("todo", "todo <description>"), e.getMessage());
        }
    }

    @Test
    public void parseInput_unknownCommand_throwsException() {
        String input = "unknown command";
        try {
            InputParser.parseInput(input);
        } catch (GladosException e) {
            assertEquals(Ui.getErrorUnknownCommand(), e.getMessage());
        }
    }
}
