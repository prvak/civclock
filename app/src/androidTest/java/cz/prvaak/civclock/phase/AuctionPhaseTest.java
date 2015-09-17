package cz.prvaak.civclock.phase;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import java.util.List;

import cz.prvaak.civclock.TimeInstant;
import cz.prvaak.civclock.clock.FakeEpoch;
import cz.prvaak.civclock.clock.FakePlayerClock;
import cz.prvaak.civclock.clock.PlayerClock;

/**
 * Tests of {@link cz.prvaak.civclock.phase.AuctionPhase} class.
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
		auctionPhase.bid(new TimeInstant(0L), FakeEpoch.ONE);
		assertEquals(allPlayers.get(1), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(new TimeInstant(1000L), FakeEpoch.ONE);
		assertEquals(allPlayers.get(2), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(new TimeInstant(2000L), FakeEpoch.ONE);
		assertEquals(allPlayers.get(0), auctionPhase.getCurrentPlayer());
		assertEquals(3, auctionPhase.getAllPlayers().size());
	}

	public void testPass() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		AuctionPhase auctionPhase = new AuctionPhase(allPlayers, allPlayers.get(0));

		assertEquals(allPlayers.get(0), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(new TimeInstant(1000L), FakeEpoch.ONE);
		assertEquals(allPlayers.get(1), auctionPhase.getCurrentPlayer());
		auctionPhase.pass(new TimeInstant(1000L), FakeEpoch.ONE);
		assertEquals(2, auctionPhase.getAllPlayers().size());
		assertEquals(allPlayers.get(2), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(new TimeInstant(2000L), FakeEpoch.ONE);
		assertEquals(allPlayers.get(0), auctionPhase.getCurrentPlayer());
		auctionPhase.bid(new TimeInstant(3000L), FakeEpoch.ONE);
		assertEquals(allPlayers.get(2), auctionPhase.getCurrentPlayer());
	}

	public void testLastPassStartsInitiatingPlayer() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(3);
		AuctionPhase auctionPhase = new AuctionPhase(allPlayers, allPlayers.get(0));

		auctionPhase.pass(new TimeInstant(1000L), FakeEpoch.ONE);
		auctionPhase.pass(new TimeInstant(1000L), FakeEpoch.ONE);
		FakePlayerClock currentPlayer = (FakePlayerClock) auctionPhase.getCurrentPlayer();
		assertEquals(allPlayers.get(0), currentPlayer);
		assertTrue(currentPlayer.isStarted);
	}

	public void testLastPlayerCannotBid() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(2);
		AuctionPhase auctionPhase = new AuctionPhase(allPlayers, allPlayers.get(0));

		auctionPhase.pass(new TimeInstant(0L), FakeEpoch.ONE);
		try {
			auctionPhase.bid(new TimeInstant(1000L), FakeEpoch.ONE);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}

	public void testLastPlayerCannotPass() throws Exception {
		List<PlayerClock> allPlayers = FakePlayerClock.createPlayerClocks(2);
		AuctionPhase auctionPhase = new AuctionPhase(allPlayers, allPlayers.get(0));

		auctionPhase.pass(new TimeInstant(0L), FakeEpoch.ONE);
		try {
			auctionPhase.pass(new TimeInstant(1000L), FakeEpoch.ONE);
			Assert.fail("Should have thrown IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// success
		}
	}
}
