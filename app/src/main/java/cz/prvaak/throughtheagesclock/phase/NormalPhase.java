package cz.prvaak.throughtheagesclock.phase;

import java.util.List;

import cz.prvaak.throughtheagesclock.TimeInstant;
import cz.prvaak.throughtheagesclock.clock.EpochId;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.phase.switcher.PlayerSwitcher;
import cz.prvaak.throughtheagesclock.phase.switcher.transition.PlayerTransition;
import cz.prvaak.throughtheagesclock.phase.switcher.transition.UpkeepTransition;

/**
 * Clock switching logic during standard round about phase. Players are playing
 * one after another.
 */
public class NormalPhase implements GamePhase {

	private final PlayerSwitcher playerSwitcher;
	private final PlayerTransition transition = new UpkeepTransition();

	public NormalPhase(List<? extends PlayerClock> allPlayers, PlayerClock currentPlayer) {
		this.playerSwitcher = new PlayerSwitcher(allPlayers, currentPlayer);
	}

	/**
	 * Terminates turn of current player.
	 *
	 * @param when Time in milliseconds when the turn is terminated.
	 * @param epoch Current epoch.
	 */
	public void turnDone(TimeInstant when, EpochId epoch) {
		playerSwitcher.switchPlayers(transition, when, epoch);
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
