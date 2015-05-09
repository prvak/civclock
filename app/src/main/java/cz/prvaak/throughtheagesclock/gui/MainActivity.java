package cz.prvaak.throughtheagesclock.gui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cz.prvaak.throughtheagesclock.Game;
import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.gui.view.InactivePlayersListView;
import cz.prvaak.throughtheagesclock.gui.view.PlayerView;
import cz.prvaak.throughtheagesclock.phase.Phase;
import cz.prvaak.throughtheagesclock.phase.RoundAboutPhase;
import cz.prvaak.throughtheagesclock.utils.RepeatingIterator;


public class MainActivity extends ActionBarActivity {

	private LinkedHashMap<PlayerClock, PlayerData> players = new LinkedHashMap<>(4);
	private Game game;
	private Phase currentPhase;

	private PlayerView activePlayerView;
	private InactivePlayersListView inactivePlayersListView;
	private static final long UPDATE_DELAY_MS = 40L;

	private final Handler updateHandler = new Handler();
	private final Runnable updateTask = new Runnable() {
		@Override
		public void run() {
			updateTimes();
			// post this event again
			updateHandler.postDelayed(this, UPDATE_DELAY_MS);
		}
	};

	private void updateTimes() {
		long now = System.currentTimeMillis();
		activePlayerView.updateTimes(now);
		inactivePlayersListView.updateTimes(now);
	}

	private void updatePlayers() {
		long now = System.currentTimeMillis();
		PlayerClock activePlayer = currentPhase.getCurrentPlayer();
		List<PlayerClock> inactivePlayers = currentPhase.getNextPlayers();

		// update active player
		PlayerData activePlayerData = players.get(activePlayer);
		activePlayerView.setPlayer(activePlayerData);

		// update inactive players
		List<PlayerData> inactivePlayersData = new ArrayList<>(inactivePlayers.size());
		for (PlayerClock inactivePlayer: inactivePlayers) {
			PlayerData playerData = players.get(inactivePlayer);
			inactivePlayersData.add(playerData);
		}
		inactivePlayersListView.setPlayers(inactivePlayersData);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		long now = System.currentTimeMillis();
		if (savedInstanceState == null) {
			ArrayList<PlayerClock> playerClocks = new ArrayList<>(4);
			playerClocks.add(new PlayerClock(60000L, 10000L));
			playerClocks.add(new PlayerClock(60000L, 10000L));
			playerClocks.add(new PlayerClock(60000L, 10000L));
			playerClocks.add(new PlayerClock(60000L, 10000L));
			game = new Game(playerClocks);
			currentPhase = game.startGame(now);

			for (int i = 0; i < playerClocks.size(); i++) {
				PlayerClock playerClock = playerClocks.get(i);
				PlayerColor playerColor = PlayerColor.values()[i];
				players.put(playerClock, new PlayerData(playerClock, playerColor));
			}
		}

		setContentView(R.layout.activity_main);

		activePlayerView = (PlayerView) findViewById(R.id.active_player_view);
		activePlayerView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (game.isPaused()) {
					return;
				}

				if (currentPhase instanceof RoundAboutPhase) {
					RoundAboutPhase phase = (RoundAboutPhase) currentPhase;
					phase.turnDone(System.currentTimeMillis());
				}
				updatePlayers();
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
	public void onRestoreInstanceState(Bundle savedInstanceState) {
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
