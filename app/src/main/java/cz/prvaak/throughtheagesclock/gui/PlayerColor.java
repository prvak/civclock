package cz.prvaak.throughtheagesclock.gui;

import cz.prvaak.throughtheagesclock.R;

/**
 *
 */
public enum PlayerColor {
	RED {
		@Override
		public int getNameResourceId() {
			return R.string.color_red;
		}

		@Override
		public int getColorResourceId() {
			return R.color.player_red;
		}
	},
	GREEN {
		@Override
		public int getNameResourceId() {
			return R.string.color_green;
		}

		@Override
		public int getColorResourceId() {
			return R.color.player_green;
		}
	},
	BLUE {
		@Override
		public int getNameResourceId() {
			return R.string.color_blue;
		}

		@Override
		public int getColorResourceId() {
			return R.color.player_blue;
		}
	},
	YELLOW {
		@Override
		public int getNameResourceId() {
			return R.string.color_yellow;
		}

		@Override
		public int getColorResourceId() {
			return R.color.player_yellow;
		}
	};

	public abstract int getNameResourceId();
	public abstract int getColorResourceId();
}
