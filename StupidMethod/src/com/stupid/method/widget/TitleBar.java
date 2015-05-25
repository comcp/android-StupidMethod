package com.stupid.method.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stupid.method.R;

public class TitleBar extends RelativeLayout implements OnClickListener {
	private Context context; // 上下文对象

	private RelativeLayout mRoot;
	private RelativeLayout mLayout_R_1;
	private RelativeLayout mLayout_R_2;
	private RelativeLayout mLayout_L_1;
	private RelativeLayout mLayout_L_2;

	private TextView tv_R_1;
	private TextView tv_R_2;
	private TextView tv_L_2;
	private TextView tv_L_1;
	private TextView tv_Conter_1;
	private ImageView iv_R_1;
	private ImageView iv_R_2;
	private ImageView iv_L_2;
	private ImageView iv_L_1;

	public TitleBar(Context context) {
		super(context);
		this.context = context;
		initView();

	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
		initFromAttributes(attrs);
	}

	public TitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initView();
		initFromAttributes(attrs);

	}

	public TitleBar reset() {
		tv_R_1.setText("");
		tv_R_2.setText("");
		tv_L_2.setText("");
		tv_L_1.setText("");
		iv_R_1.setImageDrawable(null);
		iv_R_2.setImageDrawable(null);
		iv_L_2.setImageDrawable(null);
		iv_L_1.setImageDrawable(null);
		tv_Conter_1.setText("");
		mLayout_L_2.setOnClickListener(null);
		mLayout_R_2.setOnClickListener(null);
		mLayout_L_1.setOnClickListener(null);
		mLayout_R_1.setOnClickListener(null);
		// LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT);
		// lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		// tv_L_1 = new TextView(this.context);
		// tv_L_1.setLayoutParams(lp);
		// mLayout_L_2.addView(iv_L_1, 0);
		return this;

	}

	private void initFromAttributes(AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.TitleBar);

		if (a != null) {

			setConterText(a.getString(R.styleable.TitleBar_conter_tv));
			setLeftOuterTextOrImage(
					a.getString(R.styleable.TitleBar_left_tv_1),
					a.getDrawable(R.styleable.TitleBar_left_iv_1));
			setRightOuterTextOrImage(
					a.getString(R.styleable.TitleBar_right_tv_1),
					a.getDrawable(R.styleable.TitleBar_right_iv_1));

			setLeftInnerTextOrImage(
					a.getString(R.styleable.TitleBar_left_tv_2),
					a.getDrawable(R.styleable.TitleBar_left_iv_2));
			setRightInnerTextOrImage(
					a.getString(R.styleable.TitleBar_right_tv_2),
					a.getDrawable(R.styleable.TitleBar_right_iv_2));

			// mRoot.setBackgroundResource(a.getInt(
			// R.styleable.TitleBar_c_background, 0));
			// this.setBackgroundDrawable(a
			// .getDrawable(R.styleable.TitleBar_c_background));
			setTextColor(a.getColor(R.styleable.TitleBar_c_textColor, context
					.getResources().getColor(android.R.color.white)));
			// mRoot.getLayoutParams().height = (int) a.getDimension(
			// R.styleable.TitleBar_c_hight, this.context.getResources()
			// .getDimension(R.dimen.titlebar_def_hight));
			a.recycle();
		}

	}

	/**
	 * 初始化控件
	 */
	private void initView() {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.title_bar, this, true);
		mRoot = (RelativeLayout) view.findViewById(R.id.titlebar_mroot);
		mLayout_R_1 = (RelativeLayout) view
				.findViewById(R.id.titlebar_right_layout_1);
		mLayout_R_2 = (RelativeLayout) view
				.findViewById(R.id.titlebar_right_layout_2);

		mLayout_L_1 = (RelativeLayout) view
				.findViewById(R.id.titlebar_left_layout_1);
		mLayout_L_2 = (RelativeLayout) view
				.findViewById(R.id.titlebar_left_layout_2);

		mLayout_R_1.setOnClickListener(this);
		mLayout_R_2.setOnClickListener(this);

		mLayout_L_1.setOnClickListener(this);
		mLayout_L_2.setOnClickListener(this);

		tv_Conter_1 = (TextView) view.findViewById(R.id.titlebar_conter_tv);
		tv_R_1 = (TextView) view.findViewById(R.id.titlebar_right_tv_1);
		tv_R_2 = (TextView) view.findViewById(R.id.titlebar_right_tv_2);

		iv_R_2 = (ImageView) view.findViewById(R.id.titlebar_right_iv_2);
		iv_R_1 = (ImageView) view.findViewById(R.id.titlebar_right_iv_1);

		tv_L_2 = (TextView) view.findViewById(R.id.titlebar_left_tv_2);
		tv_L_1 = (TextView) view.findViewById(R.id.titlebar_left_tv_1);

		iv_L_2 = (ImageView) view.findViewById(R.id.titlebar_left_iv_2);
		iv_L_1 = (ImageView) view.findViewById(R.id.titlebar_left_iv_1);

	}

	public TitleBar setConterText(String str) {
		tv_Conter_1.setText(str);
		return this;
	}

	public void hidden() {
		if (this.getVisibility() != View.GONE)

			this.setVisibility(View.GONE);
	}

	public void show() {
		if (this.getVisibility() != View.VISIBLE)
			this.setVisibility(View.VISIBLE);

	}

	public void v() {

	}

	// -----------最左边文本,图片-------------------
	/** 设置最左边文本或图片 */
	public TitleBar setLeftOuterTextOrImage(String str, Drawable drawable) {

		if (str != null) {
			tv_L_1.setText(str);
			tv_L_1.setVisibility(View.VISIBLE);
			iv_L_1.setVisibility(View.GONE);
		} else if (drawable != null) {
			iv_L_1.setImageDrawable(drawable);
			iv_L_1.setVisibility(View.VISIBLE);
			tv_L_1.setVisibility(View.GONE);
		}
		return this;
	}

	/**
	 * 设置最左边文本或图片, <br>
	 * 一次只能设置一种类型, <br>
	 * 设置文本图片将被清除, <br>
	 * 如果同时为空,则隐藏控件<br>
	 * 优先设置文本
	 * */

	public TitleBar setLeftOuterText(String str) {

		return setLeftOuterTextOrImage(str, null);
	}

	public TitleBar setLeftOuterImage(Drawable drawable) {
		return setLeftOuterTextOrImage(null, drawable);
	}

	// -----------最右边文本,图片-------------------
	/** 设置最左边文本或图片 */
	public TitleBar setRightOuterTextOrImage(String str, Drawable drawable) {

		if (str != null) {
			tv_R_1.setText(str);
			tv_R_1.setVisibility(View.VISIBLE);
			iv_R_1.setVisibility(View.GONE);
		} else if (drawable != null) {
			iv_R_1.setImageDrawable(drawable);
			iv_R_1.setVisibility(View.VISIBLE);
			tv_R_1.setVisibility(View.GONE);
		} else {
			tv_R_1.setVisibility(View.GONE);
			iv_R_1.setVisibility(View.GONE);
		}
		return this;
	}

	/**
	 * 设置最左边文本或图片, <br>
	 * 一次只能设置一种类型, <br>
	 * 设置文本图片将被清除, <br>
	 * 如果同时为空,则隐藏控件<br>
	 * 优先设置文本
	 * */

	public TitleBar setRightOuterText(String str) {

		return setRightOuterTextOrImage(str, null);
	}

	public TitleBar setRightOuterImage(Drawable drawable) {
		return setRightOuterTextOrImage(null, drawable);
	}

	// -----------最左边文本,图片-------------------
	/** 设置最左边文本或图片 */
	public TitleBar setLeftInnerTextOrImage(String str, Drawable drawable) {

		if (str != null) {
			tv_L_2.setText(str);
			tv_L_2.setVisibility(View.VISIBLE);
			iv_L_2.setVisibility(View.GONE);
		} else if (drawable != null) {
			iv_L_2.setImageDrawable(drawable);
			iv_L_2.setVisibility(View.VISIBLE);
			tv_L_2.setVisibility(View.GONE);
		}
		return this;
	}

	/**
	 * 设置最左边文本或图片, <br>
	 * 一次只能设置一种类型, <br>
	 * 设置文本图片将被清除, <br>
	 * 如果同时为空,则隐藏控件<br>
	 * 优先设置文本
	 * */

	public TitleBar setLeftInnerText(String str) {

		return setLeftInnerTextOrImage(str, null);
	}

	public TitleBar setLeftInnerImage(Drawable drawable) {
		return setLeftInnerTextOrImage(null, drawable);
	}

	// -----------最右边文本,图片-------------------
	/** 设置最左边文本或图片 */
	public TitleBar setRightInnerTextOrImage(String str, Drawable drawable) {

		if (str != null) {
			tv_R_2.setText(str);
			tv_R_2.setVisibility(View.VISIBLE);
			iv_R_2.setVisibility(View.GONE);
		} else if (drawable != null) {
			iv_R_2.setImageDrawable(drawable);
			iv_R_2.setVisibility(View.VISIBLE);
			tv_R_2.setVisibility(View.GONE);
		} else {
			tv_R_2.setVisibility(View.GONE);
			iv_R_2.setVisibility(View.GONE);
		}
		return this;
	}

	/**
	 * 设置最左边文本或图片, <br>
	 * 一次只能设置一种类型, <br>
	 * 设置文本图片将被清除, <br>
	 * 如果同时为空,则隐藏控件<br>
	 * 优先设置文本
	 * */

	public TitleBar setRightInnerText(String str) {

		return setRightInnerTextOrImage(str, null);
	}

	public TitleBar setRightInnerImage(Drawable drawable) {
		return setRightInnerTextOrImage(null, drawable);
	}

	/** 设置文字颜色 */
	public TitleBar setTextColor(int color) {
		tv_L_2.setTextColor(color);
		tv_L_1.setTextColor(color);
		tv_R_1.setTextColor(color);
		tv_R_2.setTextColor(color);
		return this;

	}

	@Override
	public void onClick(View v) {

	}

	/** 右边外层容器 **/
	public RelativeLayout getRightOuter() {
		return mLayout_R_1;
	}

	/** 右边内层容器 */
	public RelativeLayout getmRightInner() {
		return mLayout_R_2;
	}

	/** 左边外层容器 **/
	public RelativeLayout getLeftOuter() {
		return mLayout_L_1;
	}

	/** 左边内层容器 **/
	public RelativeLayout getmLeftInner() {
		return mLayout_L_2;
	}

}
