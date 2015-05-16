package cz.prvaak.throughtheagesclock.gui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cz.prvaak.throughtheagesclock.Game;
import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.clock.PlayerId;
import cz.prvaak.throughtheagesclock.gui.Player;
import cz.prvaak.throughtheagesclock.gui.PlayerColor;
import cz.prvaak.throughtheagesclock.gui.view.InactivePlayersListView;
import cz.prvaak.throughtheagesclock.gui.view.PhaseView;
import cz.prvaak.throughtheagesclock.gui.view.PlayerButtonListener;
import cz.prvaak.throughtheagesclock.gui.view.PlayerView;
import cz.prvaak.throughtheagesclock.phase.AuctionPhase;
import cz.prvaak.throughtheagesclock.phase.GamePhase;
import cz.prvaak.throughtheagesclock.phase.NormalPhase;
import cz.prvaak.throughtheagesclock.phase.OneOnOnePhase;


public class TimerActivity extends ActionBarActivity {

	private LinkedHashMap<PlayerId, Player> playersMap =
			new LinkedHashMap<>(PlayerColor.values().length);
	private Game game;

	private PlayerView activePlayerView;
	private InactivePlayersListView inactivePlayersListView;
	private static final long UPDATE_DELAY_MS = 40L;

	private final Handler updateHandler = new Handler();

	/** Task that updates the screen periodically. */
	private final Runnable updateTask = new Runnable() {
		@Override
		public void run() {
			updateRemainingTimes();
			// post this event again
			updateHandler.postDelayed(this, UPDATE_DELAY_MS);
		}
	};

	private final PlayerButtonListener dealButtonListener = new PlayerButtonListener() {
		@Override
		public void onPlayerButtonClicked(PlayerId playerId) {
			System.out.println("Deal button clicked.");
			if (game.isPaused()) {
				return;
			}

			long now = System.currentTimeMillis();
			game.startOneOnOnePhase(now, playersMap.get(playerId));

			updatePlayers();
			updatePhase();
		}
	};

	private void updateRemainingTimes() {
		long now = System.currentTimeMillis();
		activePlayerView.updateTime(now);
		inactivePlayersListView.updateTime(now);
	}

	private void updatePhase() {
		PhaseView.Phase phase = getCurrentPhase();
		activePlayerView.setPhase(phase);
		inactivePlayersListView.setPhase(phase);
	}

	private void updatePlayers() {
		GamePhase currentPhase = game.getCurrentPhase();
		PlayerClock activePlayerClock = currentPhase.getCurrentPlayer();
		List<PlayerClock> inactivePlayersClocks = currentPhase.getNextPlayers();

		// update active player
		Player activePlayer = playersMap.get(activePlayerClock.getPlayerId());
		activePlayerView.setPlayer(activePlayer);

		// update inactive players
		List<Player> inactivePlayers = new ArrayList<>(inactivePlayersClocks.size());
		for (PlayerClock inactivePlayerClock: inactivePlayersClocks) {
			Player player = playersMap.get(inactivePlayerClock.getPlayerId());
			inactivePlayers.add(player);
		}
		inactivePlayersListView.setPlayers(inactivePlayers, dealButtonListener);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		// Prevent screen from locking.
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


		long now = System.currentTimeMillis();
		if (savedInstanceState == null) {
			for (int i = 0; i < PlayerColor.values().length; i++) {
				PlayerColor playerColor = PlayerColor.values()[i];
				playersMap.put(playerColor, new Player(playerColor, 60000L, 10000L, 30000L));
			}

			ArrayList<Player> playerClocks = new ArrayList<>(PlayerColor.values().length);
			for (Player player: playersMap.values()) {
				playerClocks.add(player);
			}
			game = new Game(playerClocks);
			game.start(now);
		}

		setContentView(R.layout.activity_timer);

		activePlayerView = (PlayerView) findViewById(R.id.active_player_view);
		activePlayerView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (game.isPaused()) {
					return;
				}

				long now = System.currentTimeMillis();
				GamePhase currentPhase = game.getCurrentPhase();
				if (currentPhase instanceof NormalPhase) {
					NormalPhase phase = (NormalPhase) currentPhase;
					phase.turnDone(now);
				} else if (currentPhase instanceof OneOnOnePhase) {
					OneOnOnePhase phase = (OneOnOnePhase) currentPhase;
					phase.turnDone(now);
					game.startRoundAboutPhase(now);
				}
				updatePlayers();
				updatePhase();
			}
		});
		activePlayerView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				if (game.isPaused()) {
					game.resume(System.currentTimeMillis());
				} else {
					game.pause(System.currentTimeMillis());
				}
				return true;
			}
		});

		inactivePlayersListView = (InactivePlayersListView) findViewById(R.id.inactive_players_list_view);
		updatePlayers();
		updatePhase();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}


	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		// TODO: serialize player clocks
	}

	@Override
	public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// called after onStart, but only if some previous instance was saved
		// TODO: restore player clocks
	}

	@Override
	protected void onPause() {
		super.onPause();
		updateHandler.removeCallbacks(updateTask);
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateHandler.postDelayed(updateTask, UPDATE_DELAY_MS);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

	public void onAuctionButton(View view) {
		System.out.println("Auction button clicked.");
		if (game.isPaused()) {
			return;
		}

		game.startAuctionPhase(System.currentTimeMillis());
		updatePlayers();
		updatePhase();
	}

	public void onBidButton(View view) {
		System.out.println("Bid button clicked.");
		if (game.isPaused()) {
			return;
		}

		AuctionPhase phase = (AuctionPhase) game.getCurrentPhase();
		phase.bid(System.currentTimeMillis());
		updatePlayers();
		updatePhase();
	}

	public void onPassButton(View view) {
		System.out.println("Pass button clicked.");
		if (game.isPaused()) {
			return;
		}

		AuctionPhase phase = (AuctionPhase) game.getCurrentPhase();
		long now = System.currentTimeMillis();
		phase.pass(now);
		if (phase.getAllPlayers().size() <= 1) {
			game.startRoundAboutPhase(now);
		}
		updatePlayers();
		updatePhase();
	}

	private PhaseView.Phase getCurrentPhase() {
		GamePhase phase = game.getCurrentPhase();
		if (phase instanceof NormalPhase) {
			return PhaseView.Phase.NORMAL;
		} else if (phase instanceof AuctionPhase) {
			return PhaseView.Phase.AUCTION;
		} else {
			return PhaseView.Phase.ONE_ON_ONE;
		}
	}
}
