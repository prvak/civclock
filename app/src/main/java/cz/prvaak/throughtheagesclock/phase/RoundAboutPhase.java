package cz.prvaak.throughtheagesclock.phase;

import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.phase.switcher.PlayerSwitcher;
import cz.prvaak.throughtheagesclock.phase.switcher.transition.PlayerTransition;
import cz.prvaak.throughtheagesclock.phase.switcher.transition.UpkeepTransition;

/**
 * Clock switching logic during standard round about phase. Players are switching
 * one after another.
 */
public class RoundAboutPhase implements Phase {

	private final PlayerSwitcher playerSwitcher;
	private final PlayerTransition transition = new UpkeepTransition();

	public RoundAboutPhase(List<PlayerClock> allPlayers, PlayerClock currentPlayer) {
		this.playerSwitcher = new PlayerSwitcher(allPlayers, currentPlayer);
	}

	public void turnDone(long when) {
		playerSwitcher.switchPlayers(transition, when);
	}

	@Override
	public List<PlayerClock> getRemainingPlayers() {
		return playerSwitcher.getRemainingPlayers();
	}

	@Override
	public PlayerClock getCurrentPlayer() {
		return playerSwitcher.getCurrentPlayer();
	}
}
