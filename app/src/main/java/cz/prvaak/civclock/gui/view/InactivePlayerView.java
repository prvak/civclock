package cz.prvaak.civclock.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import cz.prvaak.civclock.R;

/**
 * View of an inactive player.
 */
public class InactivePlayerView extends PlayerView {

	public InactivePlayerView(Context context) {
		super(context);
	}

	public InactivePlayerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public InactivePlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void updatePhase(Phase phase) {
		Button dealButton = (Button) findViewById(R.id.deal_button);
		switch (phase) {
			case NORMAL:
				dealButton.setVisibility(VISIBLE);
				break;
			default:
				dealButton.setVisibility(GONE);
		}
	}
}
