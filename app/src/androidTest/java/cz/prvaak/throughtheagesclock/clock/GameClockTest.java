package cz.prvaak.throughtheagesclock.clock;

import cz.prvaak.throughtheagesclock.player.GameClock;
import cz.prvaak.throughtheagesclock.player.PlayerCountdown;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.player.GameClock} class.
 */
public class GameClockTest {

	private GameClock createGameClock() {
		PlayerCountdown playerCountdown0 = new PlayerCountdown(60000L, 30000L);
		PlayerCountdown playerCountdown1 = new PlayerCountdown(60000L, 30000L);
		return new GameClock(new PlayerCountdown[]{playerCountdown0, playerCountdown1});
	}

	public void testStart() throws Exception {
		GameClock gameClock = createGameClock();
		gameClock.nextPlayer(1000L);
		assertEquals(59000L, gameClock.getRemainingTime(2000L, 0));
		assertEquals(60000L, gameClock.getRemainingTime(2000L, 1));
	}


}
