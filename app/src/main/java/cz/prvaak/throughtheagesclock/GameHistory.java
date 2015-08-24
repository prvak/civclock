package cz.prvaak.throughtheagesclock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Previous states of the {@link Game} instances.
 *
 * This is used to implement undo functionality.
 */
public class GameHistory {
	private List<Game> history = new LinkedList<>();
	private List<Game> future = new LinkedList<>();

	public GameHistory() {

	}

	public boolean canUndo() {
		return !history.isEmpty();
	}

	public boolean canRedo() {
		return !future.isEmpty();
	}

	public void add(TimeInstant now, Game game) {
		Game clonedGame = pauseAndClone(now, game);
		history.add(clonedGame);
		future.clear();
	}

	public Game undo(TimeInstant now, Game game) {
		Game clonedGame = pauseAndClone(now, game);
		future.add(clonedGame);
		return history.remove(history.size() - 1);
	}

	public Game redo(TimeInstant now, Game game) {
		Game clonedGame = pauseAndClone(now, game);
		history.add(clonedGame);
		return future.remove(0);
	}

	private Game pauseAndClone(TimeInstant now, Game game) {
		Game clonedGame = clone(game);
		if (!clonedGame.isPaused()) {
			clonedGame.pause(now);
		}
		return clonedGame;
	}

	private Game clone(Game game) {
		Game clonedGame = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutput out = null;
			try {
				out = new ObjectOutputStream(bos);
				out.writeObject(game);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				bos.close();
				if (out != null) {
					out.close();
				}
			}
			byte[] gameAsBytes = bos.toByteArray();

			ByteArrayInputStream bis = new ByteArrayInputStream(gameAsBytes);
			ObjectInput in = null;
			try {
				in = new ObjectInputStream(bis);
				clonedGame = (Game) in.readObject();
			} catch (ClassNotFoundException e) {
				// Ignore... we are recreating an object that was serialized moments ago.
			} finally {
				bis.close();
				if (in != null) {
					in.close();
				}
			}
		} catch (IOException e) {
			throw new IllegalStateException("Failed to clone the game!", e);
		}

		if (clonedGame == null) {
			throw new IllegalStateException("Failed to clone the game!");
		}
		return clonedGame;
	}
}
