package cz.prvaak.throughtheagesclock;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import java.util.List;

import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.AuctionPhase} class.
 */
public class AuctionPhaseTest extends InstrumentationTestCase {

	public void testFirstPlayer() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		AuctionPhase auctionPhase = new AuctionPhase(allPlayers, allPlayers.get(2));

		assertEquals(allPlayers.get(2), auctionPhase.getCurrentPlayer());
	}

	public void testBid() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		AuctionPhase auctionPhase = new AuctionPhase(allPlayers, allPlayers.get(0));

		assertEquals(allPlayers.get(0), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(0L);
		assertEquals(allPlayers.get(1), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(1000L);
		assertEquals(allPlayers.get(2), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(2000L);
		assertEquals(allPlayers.get(0), auctionPhase.getCurrentPlayer());
		assertEquals(3, auctionPhase.getRemainingPlayers().size());
	}

	public void testPass() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		AuctionPhase auctionPhase = new AuctionPhase(allPlayers, allPlayers.get(0));

		assertEquals(allPlayers.get(0), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(1000L);
		assertEquals(allPlayers.get(1), auctionPhase.getCurrentPlayer());
		auctionPhase.pass(1000L);
		assertEquals(2, auctionPhase.getRemainingPlayers().size());
		assertEquals(allPlayers.get(2), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(2000L);
		assertEquals(allPlayers.get(0), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(3000L);
		assertEquals(allPlayers.get(2), auctionPhase.getCurrentPlayer());
	}

	public void testLastPlayerCannotBid() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(2);
		AuctionPhase auctionPhase = new AuctionPhase(allPlayers, allPlayers.get(0));

		auctionPhase.pass(0L);
		try {
			auctionPhase.bid(1000L);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testLastPlayerCannotPass() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(2);
		AuctionPhase auctionPhase = new AuctionPhase(allPlayers, allPlayers.get(0));

		auctionPhase.pass(0L);
		try {
			auctionPhase.pass(1000L);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}
}
