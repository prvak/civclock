package cz.prvaak.throughtheagesclock.gui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import cz.prvaak.throughtheagesclock.Game;
import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.TimeInstant;
import cz.prvaak.throughtheagesclock.clock.PlayerId;
import cz.prvaak.throughtheagesclock.gui.view.InactivePlayersListView;
import cz.prvaak.throughtheagesclock.gui.view.PlayerButtonListener;
import cz.prvaak.throughtheagesclock.gui.view.PlayerView;
import cz.prvaak.throughtheagesclock.gui.view.display.PhaseDisplay;
import cz.prvaak.throughtheagesclock.phase.AuctionPhase;
import cz.prvaak.throughtheagesclock.phase.GamePhase;
import cz.prvaak.throughtheagesclock.phase.NormalPhase;
import cz.prvaak.throughtheagesclock.phase.OneOnOnePhase;


public class TimerActivity extends ActionBarActivity {

	/** Object that contains all information about current state of the game. */
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

			game.startOneOnOnePhase(new TimeInstant(), game.getPlayer(playerId));

			updatePlayers();
			updatePhase();
		}
	};

	private void updateRemainingTimes() {
		TimeInstant now = new TimeInstant();
		activePlayerView.updateTime(now);
		inactivePlayersListView.updateTime(now);
	}

	private void updatePhase() {
		PhaseDisplay.Phase phase = getCurrentPhase();
		activePlayerView.updatePhase(phase);
		inactivePlayersListView.updatePhase(phase);
	}

	private void updatePlayers() {
		GamePhase currentPhase = game.getCurrentPhase();

		// update active player
		activePlayerView.setPlayerClock(currentPhase.getCurrentPlayer());

		// update inactive players
		inactivePlayersListView.setPlayerClocks(currentPhase.getNextPlayers(), dealButtonListener);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

		super.onCreate(savedInstanceState);

		// disable action bar
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.hide();
		}

		// Prevent screen from locking.
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		if (savedInstanceState == null) {
			game = (Game) getIntent().getExtras().getSerializable("game");
			game.start(new TimeInstant());
		}

		setContentView(R.layout.activity_timer);

		activePlayerView = (PlayerView) findViewById(R.id.active_player_view);
		activePlayerView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (game.isPaused()) {
					return;
				}

				TimeInstant now = new TimeInstant();
				GamePhase currentPhase = game.getCurrentPhase();
				if (currentPhase instanceof NormalPhase) {
					NormalPhase phase = (NormalPhase) currentPhase;
					phase.turnDone(now);
				} else if (currentPhase instanceof OneOnOnePhase) {
					OneOnOnePhase phase = (OneOnOnePhase) currentPhase;
					phase.turnDone(now);
					game.startRoundAboutPhase();
				}
				updatePlayers();
				updatePhase();
			}
		});
		activePlayerView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				TimeInstant now = new TimeInstant();
				if (game.isPaused()) {
					game.resume(now);
				} else {
					game.pause(now);
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

		game.startAuctionPhase();
		updatePlayers();
		updatePhase();
	}

	public void onBidButton(View view) {
		System.out.println("Bid button clicked.");
		if (game.isPaused()) {
			return;
		}

		AuctionPhase phase = (AuctionPhase) game.getCurrentPhase();
		phase.bid(new TimeInstant());
		updatePlayers();
		updatePhase();
	}

	public void onPassButton(View view) {
		System.out.println("Pass button clicked.");
		if (game.isPaused()) {
			return;
		}

		AuctionPhase phase = (AuctionPhase) game.getCurrentPhase();
		phase.pass(new TimeInstant());
		if (phase.getAllPlayers().size() <= 1) {
			game.startRoundAboutPhase();
		}
		updatePlayers();
		updatePhase();
	}

	private PhaseDisplay.Phase getCurrentPhase() {
		GamePhase phase = game.getCurrentPhase();
		if (phase instanceof NormalPhase) {
			return PhaseDisplay.Phase.NORMAL;
		} else if (phase instanceof AuctionPhase) {
			return PhaseDisplay.Phase.AUCTION;
		} else {
			return PhaseDisplay.Phase.ONE_ON_ONE;
		}
	}
}