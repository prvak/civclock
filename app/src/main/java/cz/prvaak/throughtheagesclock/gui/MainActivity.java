package cz.prvaak.throughtheagesclock.gui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import cz.prvaak.throughtheagesclock.Game;
import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;
import cz.prvaak.throughtheagesclock.gui.view.PlayerView;


public class MainActivity extends ActionBarActivity {

	private List<PlayerClock> allPlayers;
	private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		allPlayers = new ArrayList<>();
		allPlayers.add(new PlayerClock(10000L, 1000L));
		allPlayers.add(new PlayerClock(10000L, 1000L));
		game = new Game(allPlayers);
		setContentView(R.layout.activity_main);

		PlayerView activePlayerView = (PlayerView) findViewById(R.id.active_player_view);
		activePlayerView.setPlayer(allPlayers.get(0), PlayerColor.BLUE);
		PlayerView inactivePlayerView = (PlayerView) findViewById(R.id.inactive_player_view);
		inactivePlayerView.setPlayer(allPlayers.get(1), PlayerColor.RED);
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
