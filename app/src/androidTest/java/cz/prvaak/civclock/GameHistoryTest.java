package cz.prvaak.civclock;

import android.test.InstrumentationTestCase;

import java.util.ArrayList;

import cz.prvaak.civclock.gui.Player;
import cz.prvaak.civclock.gui.PlayerColor;
import cz.prvaak.civclock.gui.PlayerSettings;

/**
 * Tests of {@link GameHistory} class.
 */
public class GameHistoryTest extends InstrumentationTestCase {

	private static Game createGame() {
		PlayerSettings playerSettings = new PlayerSettings(3);
		ArrayList<Player> playerClocks = new ArrayList<>(PlayerColor.values().length);
		for (PlayerColor color: playerSettings) {
			playerClocks.add(new Player(color, playerSettings.getPlayerData(color)));
		}
		return new Game(playerClocks);
	}

	public void testEmptyHistory() throws Exception {
		GameHistory history = new GameHistory();
		assertFalse(history.canRedo());
		assertFalse(history.canUndo());
	}

	public void testAdd() throws Exception {
		GameHistory history = new GameHistory();
		Game game = createGame();

		history.add(new TimeInstant(1000L), game);
		assertTrue(history.canUndo());
	}

	public void testUndo() throws Exception {
		GameHistory history = new GameHistory();
		Game game = createGame();

		game.start(new TimeInstant(0L));
		TimeAmount remainingTime = game.getCurrentPhase().getCurrentPlayer()
				.getRemainingReserveTime(new TimeInstant(1000L));
		history.add(new TimeInstant(1000L), game);
		Game previousGame = history.undo(new TimeInstant(2000L), game);
		TimeAmount previousRemainingTime = previousGame.getCurrentPhase().getCurrentPlayer()
				.getRemainingReserveTime(new TimeInstant(3000L));

		assertFalse(game == previousGame); // not the same instance
		assertTrue(previousGame.isPaused());
		assertEquals(remainingTime, previousRemainingTime);
	}

	public void testRedo() throws Exception {
		GameHistory history = new GameHistory();
		Game game = createGame();
		game.start(new TimeInstant(0L));
		TimeAmount remainingTime1 = game.getCurrentPhase().getCurrentPlayer()
				.getRemainingReserveTime(new TimeInstant(1000L));
		history.add(new TimeInstant(1000L), game);

		TimeAmount remainingTime2 = game.getCurrentPhase().getCurrentPlayer()
				.getRemainingReserveTime(new TimeInstant(2000L));
		game = history.undo(new TimeInstant(2000L), game);

		TimeAmount previousRemainingTime = game.getCurrentPhase().getCurrentPlayer()
				.getRemainingReserveTime(new TimeInstant(3000L));

		assertEquals(remainingTime1, previousRemainingTime);
		assertTrue(history.canRedo());

		game.resume(new TimeInstant(4000L));
		game = history.redo(new TimeInstant(5000L), game);
		TimeAmount nextRemainingTime = game.getCurrentPhase().getCurrentPlayer()
				.getRemainingReserveTime(new TimeInstant(6000L));

		assertEquals(remainingTime2, nextRemainingTime);
	}

	public void testPausedGame() throws Exception {
		GameHistory history = new GameHistory();
		Game game = createGame();
		game.start(new TimeInstant(0L));
		game.pause(new TimeInstant(1000L));

		assertTrue(game.isPaused());

		history.add(new TimeInstant(2000L), game);

		game = history.undo(new TimeInstant(3000L), game);

		assertTrue(game.isPaused());

		game = history.redo(new TimeInstant(4000L), game);

		assertTrue(game.isPaused());
	}

	public void testUndoRedo() throws Exception {
		GameHistory history = new GameHistory();
		Game game = createGame();
		game.start(new TimeInstant(0L));
		history.add(new TimeInstant(0L), game);
		history.add(new TimeInstant(1000L), game);
		game.nextPlayer(new TimeInstant(1000L));
		history.add(new TimeInstant(2000L), game);
		game.nextPlayer(new TimeInstant(2000L));
		history.add(new TimeInstant(3000L), game);
		game.nextPlayer(new TimeInstant(3000L));

		game = history.undo(new TimeInstant(4000L), game);
		game = history.undo(new TimeInstant(4000L), game);

		int turnBefore = game.getTurnCounter();
		game = history.undo(new TimeInstant(4000L), game);
		game = history.redo(new TimeInstant(4000L), game);
		game = history.undo(new TimeInstant(4000L), game);
		game = history.redo(new TimeInstant(4000L), game);
		int turnAfter = game.getTurnCounter();

		assertEquals(turnBefore, turnAfter);
	}
}
