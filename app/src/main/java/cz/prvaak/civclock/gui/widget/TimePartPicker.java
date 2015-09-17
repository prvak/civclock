package cz.prvaak.civclock.gui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

/**
 * Number picker that can pick hours from given range.
 */
public class TimePartPicker extends NumberPicker {

	private String separator = "";

	public TimePartPicker(Context context) {
		super(context);
	}

	public TimePartPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		processAttributes(attrs);
	}

	public TimePartPicker(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		processAttributes(attrs);
	}

	private void processAttributes(AttributeSet attrs) {
		separator = attrs.getAttributeValue(null, "separator");
		if (separator == null) {
			separator = "";
		}
		setFormatter(new Formatter() {
			@Override
			public String format(int value) {
				return String.format("%d%s", value, separator);
			}
		});
		setMinValue(attrs.getAttributeIntValue(null, "min", 0));
		setMaxValue(attrs.getAttributeIntValue(null, "max", 24));
		setValue(attrs.getAttributeIntValue(null, "default", getMinValue()));
	}


}
