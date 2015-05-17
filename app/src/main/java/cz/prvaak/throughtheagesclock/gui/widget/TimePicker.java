package cz.prvaak.throughtheagesclock.gui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Widget for selecting time.
 */
public class TimePicker extends RelativeLayout {

	public TimePicker(Context context) {
		super(context);
	}

	public TimePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public long getTime() {
		return 90L;
	}

	public void setTime(long time) {
	}
}
