package cz.prvaak.throughtheagesclock.gui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import cz.prvaak.throughtheagesclock.Game;
import cz.prvaak.throughtheagesclock.GameHistory;
import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.TimeInstant;
import cz.prvaak.throughtheagesclock.clock.PlayerId;
import cz.prvaak.throughtheagesclock.gui.Age;
import cz.prvaak.throughtheagesclock.gui.view.InactivePlayersListView;
import cz.prvaak.throughtheagesclock.gui.view.PlayerButtonListener;
import cz.prvaak.throughtheagesclock.gui.view.PlayerView;
import cz.prvaak.throughtheagesclock.gui.view.display.PhaseDisplay;
import cz.prvaak.throughtheagesclock.phase.AuctionPhase;
import cz.prvaak.throughtheagesclock.phase.GamePhase;
import cz.prvaak.throughtheagesclock.phase.NormalPhase;


public class TimerActivity extends ActionBarActivity implements PhaseDisplay {

	/** Object that contains all information about current state of the game. */
	private Game game;
	/** Stored game states used for undo operations. */
	private GameHistory history;

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

			TimeInstant now = new TimeInstant();
			game.startOneOnOnePhase(now, game.getPlayer(playerId));

			updatePlayers(now);
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
		updatePhase(phase);
		activePlayerView.updatePhase(phase);
		inactivePlayersListView.updatePhase(phase);
	}

	private void updatePlayers(TimeInstant now) {
		GamePhase currentPhase = game.getCurrentPhase();

		// update active player
		activePlayerView.setPlayerClock(currentPhase.getCurrentPlayer());
		activePlayerView.setGamePaused(game.isPaused());
		activePlayerView.updateTime(now);
		activePlayerView.updateTurn(game.getTurnCounter());

		// update inactive players
		inactivePlayersListView.setPlayerClocks(currentPhase.getNextPlayers(), dealButtonListener);
		inactivePlayersListView.updateTime(now);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Prevent screen from locking.
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		if (savedInstanceState == null) {
			history = new GameHistory();
			game = (Game) getIntent().getExtras().getSerializable("game");
			TimeInstant now = new TimeInstant();
			game.start(now);
			history.add(now, game); // Backup the initial state of the game.

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
				history.add(now, game);
				game.nextPlayer(now);
				updatePlayers(now);
				updatePhase();
			}
		});
		activePlayerView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				TimeInstant now = new TimeInstant();
				if (game.isPaused()) {
					game.resume(now);
					resumeTimer();
				} else {
					game.pause(now);
					pauseTimer();
				}
				updatePlayers(now);
				updatePhase();
				return true;
			}
		});

		inactivePlayersListView =
				(InactivePlayersListView) findViewById(R.id.inactive_players_list_view);
		updatePlayers(new TimeInstant());
		updatePhase();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_timer, menu);
		return true;
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
		pauseTimer();
	}

	@Override
	protected void onResume() {
		super.onResume();
		resumeTimer();
	}

	private void pauseTimer() {
		updateHandler.removeCallbacks(updateTask);
	}

	private void resumeTimer() {
		updateHandler.postDelayed(updateTask, UPDATE_DELAY_MS);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_undo) {
			onUndo();
            return true;
        } else if (id == R.id.action_redo) {
			onRedo();
			return true;
		}

        return super.onOptionsItemSelected(item);
    }

	public void onUndo() {
		if (!history.canUndo()) {
			return;
		}

		TimeInstant now = new TimeInstant();
		game = history.undo(now, game);
		updatePlayers(now);
		updatePhase();
	}

	public void onRedo() {
		if (!history.canRedo()) {
			return;
		}

		TimeInstant now = new TimeInstant();
		game = history.redo(now, game);
		updatePlayers(now);
		updatePhase();
	}

	public void onAuctionButton(View view) {
		if (game.isPaused()) {
			return;
		}

		TimeInstant now = new TimeInstant();
		history.add(now, game);
		game.startAuctionPhase();
		updatePlayers(now);
		updatePhase();
	}

	public void onNewAgeButton(View view) {
		if (game.isPaused()) {
			return;
		}
		if (!game.getCurrentAge().hasNextAge()) {
			return;
		}

		TimeInstant now = new TimeInstant();
		history.add(now, game);
		game.nextAge(now);
		updatePhase();
	}

	public void onEventButton(View view) {
		if (game.isPaused()) {
			return;
		}

		TimeInstant now = new TimeInstant();
		history.add(now, game);
		game.addUpkeepBonusTime(now);
	}

	public void onBidButton(View view) {
		if (game.isPaused()) {
			return;
		}

		TimeInstant now = new TimeInstant();
		history.add(now, game);
		AuctionPhase phase = (AuctionPhase) game.getCurrentPhase();
		phase.bid(now, game.getCurrentAge());
		updatePlayers(now);
		updatePhase();
	}

	public void onPassButton(View view) {
		if (game.isPaused()) {
			return;
		}

		TimeInstant now = new TimeInstant();
		history.add(now, game);
		AuctionPhase phase = (AuctionPhase) game.getCurrentPhase();
		phase.pass(now, game.getCurrentAge());
		if (phase.getAllPlayers().size() <= 1) {
			game.startRoundAboutPhase();
		}
		updatePlayers(now);
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

	@Override
	public void updatePhase(Phase phase) {
		switch (phase) {
			case NORMAL:
				Age age = game.getCurrentAge();
				setTitle("Age " + age.toString());
				break;
			case AUCTION:
				setTitle(R.string.auction);
				break;
			case ONE_ON_ONE:
				setTitle(R.string.deal);
				break;
		}
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setMessage(getString(R.string.exit_confirmation))
				.setCancelable(false)
				.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						TimerActivity.this.finish();
					}
				})
				.setNegativeButton(getString(R.string.no), null)
				.show();
	}
}
