package cz.prvaak.throughtheagesclock;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.AuctionPhase} class.
 */
public class AuctionPhaseTest extends InstrumentationTestCase {

	private ArrayList<PlayerClock> createPlayerClocks(int howMany) {
		ArrayList<PlayerClock> playerClocks = new ArrayList<>(howMany);
		for (int i = 0; i < howMany; i++) {
			playerClocks.add(new PlayerClock(10000, 1000));
		}
		playerClocks.get(0).start(0L);
		return playerClocks;
	}

	public void testBid() throws Exception {
		List<PlayerClock> playerClocks = createPlayerClocks(3);
		AuctionPhase auctionPhase = new AuctionPhase(playerClocks, playerClocks.get(0));

		assertEquals(playerClocks.get(0), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(0L);
		assertEquals(playerClocks.get(1), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(1000L);
		assertEquals(playerClocks.get(2), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(2000L);
		assertEquals(playerClocks.get(0), auctionPhase.getCurrentPlayer());
		assertEquals(3, auctionPhase.getAllPlayers().size());
	}

	public void testPass() throws Exception {
		List<PlayerClock> playerClocks = createPlayerClocks(3);
		AuctionPhase auctionPhase = new AuctionPhase(playerClocks, playerClocks.get(0));

		assertEquals(playerClocks.get(0), auctionPhase.getCurrentPlayer());
		auctionPhase.pass(0L);
		assertEquals(playerClocks.get(1), auctionPhase.getCurrentPlayer());
		auctionPhase.pass(1000L);
		assertEquals(playerClocks.get(2), auctionPhase.getCurrentPlayer());
		assertEquals(1, auctionPhase.getAllPlayers().size());
	}

	public void testLastPlayerCannotBid() throws Exception {
		List<PlayerClock> playerClocks = createPlayerClocks(2);
		AuctionPhase auctionPhase = new AuctionPhase(playerClocks, playerClocks.get(0));

		auctionPhase.pass(0L);
		try {
			auctionPhase.bid(1000L);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testLastPlayerCannotPass() throws Exception {
		List<PlayerClock> playerClocks = createPlayerClocks(2);
		AuctionPhase auctionPhase = new AuctionPhase(playerClocks, playerClocks.get(0));

		auctionPhase.pass(0L);
		try {
			auctionPhase.pass(1000L);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

}
