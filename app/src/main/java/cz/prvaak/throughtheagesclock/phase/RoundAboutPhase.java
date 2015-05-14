package cz.prvaak.throughtheagesclock.phase;

import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.phase.switcher.PlayerSwitcher;
import cz.prvaak.throughtheagesclock.phase.switcher.transition.PlayerTransition;
import cz.prvaak.throughtheagesclock.phase.switcher.transition.UpkeepTransition;

/**
 * Clock switching logic during standard round about phase. Players are playing
 * one after another.
 */
public class RoundAboutPhase implements GamePhase {

	private final PlayerSwitcher playerSwitcher;
	private final PlayerTransition transition = new UpkeepTransition();

	public RoundAboutPhase(List<? extends PlayerClock> allPlayers, PlayerClock currentPlayer) {
		this.playerSwitcher = new PlayerSwitcher(allPlayers, currentPlayer);
	}

	/**
	 * Terminates turn of current player.
	 *
	 * @param when Time in milliseconds when the turn is terminated.
	 */
	public void turnDone(long when) {
		playerSwitcher.switchPlayers(transition, when);
	}

	@Override
	public PlayerClock getCurrentPlayer() {
		return playerSwitcher.getCurrentPlayer();
	}

	@Override
	public List<PlayerClock> getNextPlayers() {
		return playerSwitcher.getNextPlayers();
	}

	@Override
	public List<PlayerClock> getAllPlayers() {
		return playerSwitcher.getAllPlayers();
	}
}
