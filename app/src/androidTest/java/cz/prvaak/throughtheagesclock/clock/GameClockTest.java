package cz.prvaak.throughtheagesclock.clock;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests of {@link GameClock} class.
 */
public class GameClockTest {

	private GameClock createGameClock() {
		PlayerClock playerClock0 = new PlayerClock(60000L, 30000L);
		PlayerClock playerClock1 = new PlayerClock(60000L, 30000L);
		return new GameClock(new PlayerClock[]{playerClock0, playerClock1});
	}

	public void testStart() throws Exception {
		GameClock gameClock = createGameClock();
		gameClock.nextPlayer(1000L);
		assertEquals(59000L, gameClock.getRemainingTime(2000L, 0));
		assertEquals(60000L, gameClock.getRemainingTime(2000L, 1));
	}


}
