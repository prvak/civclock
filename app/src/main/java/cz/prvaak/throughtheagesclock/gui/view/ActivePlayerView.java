package cz.prvaak.throughtheagesclock.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import cz.prvaak.throughtheagesclock.R;

/**
 * View of an active player.
 */
public class ActivePlayerView extends PlayerView {
	public ActivePlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ActivePlayerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ActivePlayerView(Context context) {
		super(context);
	}

	@Override
	public void updatePhase(Phase phase) {
		Button bidButton = (Button) findViewById(R.id.bid_button);
		Button passButton = (Button) findViewById(R.id.pass_button);
		Button auctionButton = (Button) findViewById(R.id.auction_button);
		switch (phase) {
			case NORMAL:
				bidButton.setVisibility(GONE);
				passButton.setVisibility(GONE);
				auctionButton.setVisibility(VISIBLE);
				break;
			case AUCTION:
				bidButton.setVisibility(VISIBLE);
				passButton.setVisibility(VISIBLE);
				auctionButton.setVisibility(GONE);
				break;
			case ONE_ON_ONE:
				bidButton.setVisibility(GONE);
				passButton.setVisibility(GONE);
				auctionButton.setVisibility(GONE);
				break;
		}
	}
}
