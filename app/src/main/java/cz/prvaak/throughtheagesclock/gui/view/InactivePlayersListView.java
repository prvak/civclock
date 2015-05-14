package cz.prvaak.throughtheagesclock.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.List;

import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.gui.Player;

/**
 * View of inactive players.
 */
public class InactivePlayersListView extends LinearLayout {

	public InactivePlayersListView(Context context) {
		super(context);
	}

	public InactivePlayersListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public InactivePlayersListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setPlayers(List<Player> players) {
		removeAllViews();

		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		for (Player player: players) {
			PlayerView playerView = (PlayerView) inflater.inflate(R.layout.inactive_player_view, null);
			playerView.setPlayer(player);
			addView(playerView);
		}
	}

	public void updateRemainingTimes(long now) {
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			PlayerView playerView = (PlayerView) getChildAt(i);
			playerView.updateRemainingTimes(now);
		}
	}
}