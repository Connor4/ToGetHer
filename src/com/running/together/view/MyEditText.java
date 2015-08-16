package com.running.together.view;



import com.running.together.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

public class MyEditText extends EditText implements OnFocusChangeListener,
TextWatcher {
private boolean hasFocus;
private Drawable clearDrawable;

public MyEditText(Context context) {
super(context, null);
}

public MyEditText(Context context, AttributeSet attrs) {
super(context, attrs, android.R.attr.editTextStyle);
}

public MyEditText(Context context, AttributeSet attrs, int defStyle) {
super(context, attrs, defStyle);
init();
}

private void init() {
// 2表示drawableRight参数
clearDrawable = getCompoundDrawables()[2];
if (clearDrawable == null) {
	clearDrawable = getResources().getDrawable(
			R.drawable.drawable_right_icon_selector);
	clearDrawable.setBounds(0, 0, clearDrawable.getIntrinsicWidth(),
			clearDrawable.getIntrinsicHeight());
}
// 默认设置隐藏图标
setClearIconVisible(false);
// 设置焦点改变的监听
setOnFocusChangeListener(this);
// 设置输入框里面内容发生改变的监听
addTextChangedListener(this);
}

private void setClearIconVisible(boolean b) {
Drawable right = b ? clearDrawable : null;
setCompoundDrawables(getCompoundDrawables()[0],
		getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
}

@Override
public void onFocusChange(View v, boolean hasFocus) {
this.hasFocus = hasFocus;
if (hasFocus) {
	setClearIconVisible(getText().length() > 0);
} else {
	setClearIconVisible(false);
}
}

@Override
public void beforeTextChanged(CharSequence s, int start, int count,
	int after) {
// TODO Auto-generated method stub

}

@Override
public void afterTextChanged(Editable s) {
// TODO Auto-generated method stub

}

public void onTextChanged(CharSequence text, int start, int lengthBefore,
	int lengthAfter) {
if (hasFocus) {
	setClearIconVisible(text.length() > 0);
}
}

@Override
public boolean onTouchEvent(MotionEvent event) {
if (event.getAction() == MotionEvent.ACTION_UP) {
	if (getCompoundDrawables()[2] != null) {

		boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
				&& (event.getX() < ((getWidth() - getPaddingRight())));

		if (touchable) {
			this.setText("");
		}
	}
}
return super.onTouchEvent(event);  
}
}

