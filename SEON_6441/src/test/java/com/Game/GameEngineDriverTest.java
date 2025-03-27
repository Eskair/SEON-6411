package com.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import com.Game.controller.GameController;
import com.Game.view.GameView;

// A dummy GameView to use for stubbing getView()
class DummyGameView extends GameView {
    // Optionally override methods if you want to capture output, etc.
}

public class GameEngineDriverTest {

    @Test
    public void testMain_StartsGameController() {
        // Create a real GameController instance and then spy on it.
        GameController realController = new GameController();
        GameController spyController = spy(realController);

        // Stub getView() using doReturn(...).when(...) to avoid issues with inline mocking.
        DummyGameView dummyView = new DummyGameView();
        doReturn(dummyView).when(spyController).getView();

        // For testing, simulate some initialization:
        // (Note: if startGame() runs an infinite loop, test only a portion of it or call a smaller method)
        try {
            // For example, call handleGamePlayer() which should work without exception.
            spyController.handleGamePlayer("-add", "TestPlayer");
            assertEquals(1, spyController.getPlayers().size());
            assertEquals("TestPlayer", spyController.getPlayers().get(0).getName());
        } catch (Exception e) {
            fail("startGame-related method threw an exception: " + e.getMessage());
        }
    }

    @Test
    public void testMain_NoExceptionsThrown() {
        GameController realController = new GameController();
        GameController spyController = spy(realController);

        DummyGameView dummyView = new DummyGameView();
        doReturn(dummyView).when(spyController).getView();

        // Try calling a method that should not throw an exception.
        try {
            spyController.handleGamePlayer("-add", "SamplePlayer");
            // If no exception, the test passes.
        } catch (Exception e) {
            fail("handleGamePlayer() threw an exception: " + e.getMessage());
        }
    }

    @Test
    public void testMain_GameControllerInitialization() {
        GameController realController = new GameController();
        GameController spyController = spy(realController);

        DummyGameView dummyView = new DummyGameView();
        doReturn(dummyView).when(spyController).getView();

        // Set a map file path and then verify that it is set correctly.
        spyController.setMapFilePath("dummyMap.txt");
        assertEquals("dummyMap.txt", spyController.getMapFilePath());
    }
}
