package cz.prvaak.throughtheagesclock.gui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import cz.prvaak.throughtheagesclock.Game;
import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.clock.PlayerId;
import cz.prvaak.throughtheagesclock.gui.Player;
import cz.prvaak.throughtheagesclock.gui.PlayerColor;
import cz.prvaak.throughtheagesclock.gui.PlayerData;
import cz.prvaak.throughtheagesclock.gui.view.NewPlayersListView;
import cz.prvaak.throughtheagesclock.gui.view.PlayerButtonListener;

/**
 * Activity for starting a new game. It handles player selection and time settings.
 */
public class NewGameActivity extends ActionBarActivity {

	private PlayerData defaultPlayer;
	LinkedHashMap<PlayerColor, PlayerData> activePlayers = new LinkedHashMap<>(
			PlayerColor.values().length);
	Set<PlayerColor> inactivePlayers = new HashSet<>(PlayerColor.values().length);
	private NewPlayersListView newPlayersListView;

	private final PlayerButtonListener removeButtonListener = new PlayerButtonListener() {
		@Override
		public void onPlayerButtonClicked(PlayerId playerId) {
			onRemovePlayerButton((PlayerColor) playerId);
		}
	};

	private void updatePlayers() {
		newPlayersListView.setPlayerData(activePlayers, removeButtonListener);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_game);

		newPlayersListView = (NewPlayersListView) findViewById(R.id.new_players_list_view);

		activePlayers.clear();
		inactivePlayers.clear();
		inactivePlayers.addAll(Arrays.asList(PlayerColor.values()));

		activatePlayer(PlayerColor.RED);
		activatePlayer(PlayerColor.GREEN);
		activatePlayer(PlayerColor.BLUE);

		updatePlayers();
	}

	private PlayerData newDefaultPlayer() {
		if (defaultPlayer == null) {
			defaultPlayer = new PlayerData(new TimeAmount(600000L),
					new TimeAmount(10000L), new TimeAmount(30000L));
		}
		return new PlayerData(defaultPlayer);
	}

	private void activatePlayer(PlayerColor playerColor) {
		PlayerData playerData = activePlayers.get(playerColor);
		if (playerData != null) {
			return;
		}

		if (!inactivePlayers.remove(playerColor)) {
			throw new IllegalStateException(
					String.format("Player %s is neither active nor inactive", playerColor));
		}

		activePlayers.put(playerColor, newDefaultPlayer());
	}

	private void deactivatePlayer(PlayerColor playerColor) {
		activePlayers.remove(playerColor);
		inactivePlayers.add(playerColor);
	}

	private void activateNextPlayer() {
		PlayerColor playerColor = getNextColor();
		activatePlayer(playerColor);
	}

	private PlayerColor getNextColor() {
		if (inactivePlayers.isEmpty()) {
			throw new IllegalStateException("There are no more players to activate!");
		}

		return inactivePlayers.iterator().next();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_new_game, menu);
		return true;
	}

	public void onStartGameButton(View view) {
		Intent timerActivityIntent = new Intent(this, TimerActivity.class);
		ArrayList<Player> playerClocks = new ArrayList<>(PlayerColor.values().length);
		for (PlayerColor color: activePlayers.keySet()) {
			playerClocks.add(new Player(color, activePlayers.get(color)));
		}

		Game game = new Game(playerClocks);
		timerActivityIntent.putExtra("game", game);
		startActivity(timerActivityIntent);
	}

	public void onAddPlayerButton(View view) {
		if (activePlayers.size() >= PlayerColor.values().length) {
			// All players are already active.
			return;
		}

		activateNextPlayer();
		updatePlayers();
	}

	public void onRemovePlayerButton(PlayerColor playerColor) {
		deactivatePlayer(playerColor);
		updatePlayers();
	}
}
