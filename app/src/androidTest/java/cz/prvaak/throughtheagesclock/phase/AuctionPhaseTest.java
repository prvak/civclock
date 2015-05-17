package cz.prvaak.throughtheagesclock.phase;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import java.util.List;

import cz.prvaak.throughtheagesclock.TimeInstant;
import cz.prvaak.throughtheagesclock.clock.FakePlayerClock;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * Tests of {@link cz.prvaak.throughtheagesclock.phase.AuctionPhase} class.
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
		auctionPhase.bid(new TimeInstant(0L));
		assertEquals(allPlayers.get(1), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(new TimeInstant(1000L));
		assertEquals(allPlayers.get(2), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(new TimeInstant(2000L));
		assertEquals(allPlayers.get(0), auctionPhase.getCurrentPlayer());
		assertEquals(3, auctionPhase.getAllPlayers().size());
	}

	public void testPass() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		AuctionPhase auctionPhase = new AuctionPhase(allPlayers, allPlayers.get(0));

		assertEquals(allPlayers.get(0), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(new TimeInstant(1000L));
		assertEquals(allPlayers.get(1), auctionPhase.getCurrentPlayer());
		auctionPhase.pass(new TimeInstant(1000L));
		assertEquals(2, auctionPhase.getAllPlayers().size());
		assertEquals(allPlayers.get(2), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(new TimeInstant(2000L));
		assertEquals(allPlayers.get(0), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(new TimeInstant(3000L));
		assertEquals(allPlayers.get(2), auctionPhase.getCurrentPlayer());
	}

	public void testLastPlayerCannotBid() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(2);
		AuctionPhase auctionPhase = new AuctionPhase(allPlayers, allPlayers.get(0));

		auctionPhase.pass(new TimeInstant(0L));
		try {
			auctionPhase.bid(new TimeInstant(1000L));
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testLastPlayerCannotPass() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(2);
		AuctionPhase auctionPhase = new AuctionPhase(allPlayers, allPlayers.get(0));

		auctionPhase.pass(new TimeInstant(0L));
		try {
			auctionPhase.pass(new TimeInstant(1000L));
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}
}
