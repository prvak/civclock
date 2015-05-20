package cz.prvaak.throughtheagesclock.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.gui.PlayerColor;
import cz.prvaak.throughtheagesclock.gui.PlayerData;

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

	public void setPlayerData(List<PlayerData> playerData) {
		if (newPlayerViews.isEmpty()) {
			newPlayerViews.add((NewPlayerView) findViewById(R.id.new_player_0));
			newPlayerViews.add((NewPlayerView) findViewById(R.id.new_player_1));
			newPlayerViews.add((NewPlayerView) findViewById(R.id.new_player_2));
			newPlayerViews.add((NewPlayerView) findViewById(R.id.new_player_3));
		}

		for (int i = 0; i < newPlayerViews.size(); i++) {
			NewPlayerView view = newPlayerViews.get(i);
			if (i < playerData.size()) {
				PlayerData data = playerData.get(i);
				view.setPlayerData(data);
			}
		}
	}
}
