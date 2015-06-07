package cz.prvaak.throughtheagesclock.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.gui.PlayerColor;
import cz.prvaak.throughtheagesclock.gui.PlayerData;
import cz.prvaak.throughtheagesclock.gui.PlayerSettings;

/**
 * View containing new player's settings.
 */
public class NewPlayersListView extends LinearLayout {

	private final List<NewPlayerView> newPlayerViews = new ArrayList<>(PlayerColor.values().length);

	public NewPlayersListView(Context context) {
		super(context);
	}

	public NewPlayersListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NewPlayersListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setPlayerData(PlayerSettings playerSettings,
			PlayerButtonListener removeButtonListener) {
		if (newPlayerViews.isEmpty()) {
			newPlayerViews.add((NewPlayerView) findViewById(R.id.new_player_0));
			newPlayerViews.add((NewPlayerView) findViewById(R.id.new_player_1));
			newPlayerViews.add((NewPlayerView) findViewById(R.id.new_player_2));
			newPlayerViews.add((NewPlayerView) findViewById(R.id.new_player_3));
		}

		Iterator<NewPlayerView> viewIterator = newPlayerViews.iterator();
		Iterator<PlayerColor> playerIterator = playerSettings.iterator();
		while (viewIterator.hasNext()) {
			NewPlayerView view = viewIterator.next();
			if (playerIterator.hasNext()) {
				PlayerColor playerColor = playerIterator.next();
				PlayerData playerData = playerSettings.getPlayerData(playerColor);
				view.setPlayerData(playerColor, playerData, removeButtonListener);
				view.setVisibility(VISIBLE);
			} else {
				view.setVisibility(GONE);
			}
		}
	}
}
