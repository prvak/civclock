package cz.prvaak.throughtheagesclock.gui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;

import java.util.ArrayList;

import cz.prvaak.throughtheagesclock.Game;
import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.gui.Player;
import cz.prvaak.throughtheagesclock.gui.PlayerColor;
import cz.prvaak.throughtheagesclock.gui.PlayerData;
import cz.prvaak.throughtheagesclock.gui.PlayerSettings;
import cz.prvaak.throughtheagesclock.gui.view.NewPlayerView;
import cz.prvaak.throughtheagesclock.gui.view.NewPlayersListView;

/**
 * Activity for starting a new game. It handles player selection and time settings.
 */
public class NewGameActivity extends ActionBarActivity {

	private PlayerSettings playerSettings;
	private NewPlayersListView newPlayersListView;

	private final NewPlayerView.Listener newPlayerListener = new NewPlayerView.Listener() {

		@Override
		public void onRemovePlayer(PlayerColor playerColor) {
			playerSettings.removePlayer(playerColor);
			updatePlayers();
		}

		@Override
		public void onChangeColor(PlayerColor playerColor, PlayerColor newColor) {
			playerSettings.changeColor(playerColor, newColor);
			updatePlayers();
		}

		@Override
		public void onDataChanged(PlayerColor playerColor, PlayerData playerData) {
			playerSettings.changeData(playerColor, playerData);
			updatePlayers();
		}
	};

	private void updatePlayers() {
		newPlayersListView.setPlayerData(playerSettings, newPlayerListener);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_game);

		newPlayersListView = (NewPlayersListView) findViewById(R.id.new_players_list_view);
		playerSettings = new PlayerSettings(3);

		updatePlayers();
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
		for (PlayerColor color: playerSettings) {
			playerClocks.add(new Player(color, playerSettings.getPlayerData(color)));
		}

		Game game = new Game(playerClocks);
		timerActivityIntent.putExtra("game", game);
		startActivity(timerActivityIntent);
	}

	public void onAddPlayerButton(View view) {
		playerSettings.addPlayer();
		updatePlayers();
	}
}
