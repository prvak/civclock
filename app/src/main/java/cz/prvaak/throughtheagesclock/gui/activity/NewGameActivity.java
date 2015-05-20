package cz.prvaak.throughtheagesclock.gui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;

import java.util.ArrayList;

import cz.prvaak.throughtheagesclock.Game;
import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.TimeAmount;
import cz.prvaak.throughtheagesclock.gui.Player;
import cz.prvaak.throughtheagesclock.gui.PlayerColor;
import cz.prvaak.throughtheagesclock.gui.PlayerData;
import cz.prvaak.throughtheagesclock.gui.view.NewPlayersListView;

/**
 * Activity for starting a new game. It handles player selection and time settings.
 */
public class NewGameActivity extends ActionBarActivity {

	ArrayList<PlayerData> playerData = new ArrayList<>(PlayerColor.values().length);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_game);

		playerData.clear();
		playerData.add(new PlayerData(PlayerColor.RED, new TimeAmount(600000L), new TimeAmount(10000L), new TimeAmount(30000L)));
		playerData.add(new PlayerData(PlayerColor.GREEN, new TimeAmount(600000L), new TimeAmount(10000L), new TimeAmount(30000L)));
		playerData.add(new PlayerData(PlayerColor.BLUE, new TimeAmount(600000L), new TimeAmount(10000L), new TimeAmount(30000L)));
		playerData.add(new PlayerData(PlayerColor.YELLOW, new TimeAmount(600000L), new TimeAmount(10000L), new TimeAmount(30000L)));

		NewPlayersListView newPlayersListView = (NewPlayersListView) findViewById(R.id.new_players_list_view);
		newPlayersListView.setPlayerData(playerData);
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
		for (PlayerData data: playerData) {
			playerClocks.add(new Player(data));
		}

		Game game = new Game(playerClocks);
		timerActivityIntent.putExtra("game", game);
		startActivity(timerActivityIntent);
	}
}
