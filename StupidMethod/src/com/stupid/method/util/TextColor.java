package com.stupid.method.util;

import java.util.LinkedList;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * 复制过来,复制过去....
 * 
 * */
public final class TextColor implements Cloneable {
	private static final class TextColorData implements Cloneable {

		public static final TextColorData mColorData = new TextColorData();

		public static TextColorData newInstance() {
			try {
				return (TextColorData) mColorData.clone();
			} catch (CloneNotSupportedException e) {
				return new TextColorData();
			}
		}

		int end;
		int flags;
		int start;
		String tex;
		Object what;

		public TextColorData() {

		}

		public TextColorData rest(String tex, Object what, int start, int end,
				int flags) {
			this.tex = tex;
			this.what = what;
			this.start = start;
			this.end = end;
			this.flags = flags;

			return this;

		}

		@Override
		public String toString() {

			return String.format("tex=%s;what=%s;start=%s;end=%s;flags=%s;",
					tex, what, start, end, flags);
		}
	}

	StringBuilder builder;

	LinkedList<TextColorData> textColorDatas;

	public TextColor() {
		builder = new StringBuilder(10);
		textColorDatas = new LinkedList<TextColorData>();
	}

	public TextColor append(String str, Object what, int flags) {
		int length = builder.length();
		builder.append(str);
		textColorDatas.add(TextColorData.newInstance().rest(str, what, length,
				length + str.length(), flags));

		return this;
	}

	public TextColor appendTextColor(String txt, int color) {
		if (StringUtils.isEmpty(txt))
			return this;

		int length = builder.length();
		builder.append(txt);
		textColorDatas.add(TextColorData.newInstance().rest(txt,
				new ForegroundColorSpan(color), length, length + txt.length(),
				Spanned.SPAN_EXCLUSIVE_INCLUSIVE));
		return this;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {

		return super.clone();
	}

	public SpannableStringBuilder toStyle() {

		SpannableStringBuilder style = new SpannableStringBuilder(builder);

		for (TextColorData item : textColorDatas) {

			style.setSpan(item.what, item.start, item.end, item.flags);
		}

		return style;
	}
}
