package cz.prvaak.throughtheagesclock.gui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import cz.prvaak.throughtheagesclock.R;

/**
 * Activity for starting a new game. It handles player selection and time settings.
 */
public class NewGameActivity extends ActionBarActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_game);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_new_game, menu);
		return true;
	}
}
