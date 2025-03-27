package com.Game.view;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CommandPromptViewTest {
    
    @Test
    public void testGetCommand() {
        String simulatedInput = "test command\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));
        CommandPromptView view = new CommandPromptView();
        String command = view.getCommand();
        assertEquals("test command", command);
        view.closeScanner();
    }
    
    @Test
    public void testGetPlayerOrder() {
        String simulatedInput = "deploy A 5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));
        CommandPromptView view = new CommandPromptView();
        String order = view.getPlayerOrder("Alice", 10);
        assertEquals("deploy A 5", order);
        view.closeScanner();
    }
    
    @Test
    public void testGetConfirmation() {
        String simulatedInput = "yes\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));
        CommandPromptView view = new CommandPromptView();
        boolean confirmed = view.getConfirmation("Are you sure");
        assertTrue(confirmed);
        view.closeScanner();
    }
    
    @Test
    public void testGetFilename() {
        String simulatedInput = "map.txt\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));
        CommandPromptView view = new CommandPromptView();
        String filename = view.getFilename("Enter filename");
        assertEquals("map.txt", filename);
        view.closeScanner();
    }
    
    @Test
    public void testGetIntegerAndGetString() {
        String simulatedInput = "42\nhello world\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));
        CommandPromptView view = new CommandPromptView();
        int intValue = view.getInteger("Enter integer");
        assertEquals(42, intValue);
        String strValue = view.getString("Enter string");
        assertEquals("hello world", strValue);
        view.closeScanner();
    }
}
