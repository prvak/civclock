package cz.prvaak.throughtheagesclock.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import cz.prvaak.throughtheagesclock.R;
import cz.prvaak.throughtheagesclock.TimeInstant;
import cz.prvaak.throughtheagesclock.clock.PlayerClock;

/**
 * View of inactive players.
 */
public class InactivePlayersListView extends LinearLayout implements TimeView, PhaseView {

	public InactivePlayersListView(Context context) {
		super(context);
	}

	public InactivePlayersListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public InactivePlayersListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setPlayerClocks(List<PlayerClock> playerClocks, final PlayerButtonListener playerButtonListener) {
		removeAllViews();

		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		for (final PlayerClock playerClock: playerClocks) {
			PlayerView playerView = (PlayerView) inflater.inflate(R.layout.inactive_player_view, null);
			playerView.setPlayerClock(playerClock);
			addView(playerView);

			Button dealButton = (Button) playerView.findViewById(R.id.deal_button);
			dealButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					playerButtonListener.onPlayerButtonClicked(playerClock.getPlayerId());
				}
			});
		}
	}

	@Override
	public void updateTime(TimeInstant now) {
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child instanceof TimeView) {
				TimeView view = (TimeView) child;
				view.updateTime(now);
			}
		}
	}

	@Override
	public void setPhase(Phase phase) {
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child instanceof PhaseView) {
				PhaseView view = (PhaseView) child;
				view.setPhase(phase);
			}
		}
	}
}