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
	},
	GREEN {
		@Override
		public int getNameResourceId() {
			return R.string.color_green;
		}
	},
	BLUE {
		@Override
		public int getNameResourceId() {
			return R.string.color_blue;
		}
	},
	YELLOW {
		@Override
		public int getNameResourceId() {
			return R.string.color_yellow;
		}
	};

	public abstract int getNameResourceId();
}
