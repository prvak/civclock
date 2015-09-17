package cz.prvaak.civclock.phase;

import java.util.ArrayList;
import java.util.List;

import cz.prvaak.civclock.TimeInstant;
import cz.prvaak.civclock.clock.EpochId;
import cz.prvaak.civclock.clock.PlayerClock;
import cz.prvaak.civclock.phase.switcher.PlayerSwitcher;
import cz.prvaak.civclock.phase.switcher.transition.NormalTransition;
import cz.prvaak.civclock.phase.switcher.transition.PlayerTransition;

/**
 * Phase in which only two players.
 */
public class OneOnOnePhase implements GamePhase {

	private final PlayerSwitcher playerSwitcher;
	private final PlayerTransition transition = new NormalTransition();

	public OneOnOnePhase(TimeInstant when, EpochId epoch, PlayerClock targetPlayer,
				PlayerClock currentPlayer) {
		ArrayList<PlayerClock> allPlayers = new ArrayList<>(2);
		allPlayers.add(currentPlayer);
		allPlayers.add(targetPlayer);
		this.playerSwitcher = new PlayerSwitcher(allPlayers, currentPlayer);
		playerSwitcher.switchPlayers(transition, when, epoch);
	}

	/**
	 * Terminates turn of current player.
	 *
	 * @param when Time in milliseconds when the turn is terminated.
	 * @param epoch Current game epoch.
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
