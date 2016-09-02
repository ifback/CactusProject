package com.xmm.cactus.base;

import com.xmm.cactus.app.AppManager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 基础Activity 				
 * @author  DENG_MING_HAI
 * @date 	2016年8月30日
 */
public abstract class BaseActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCreate(savedInstanceState);
		initVariables();
		initViews();
		loadData();
		initEvent();
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
	}

	protected abstract void initCreate(Bundle savedInstanceState);

	/**
	 * 获取传过来的Intent参数
	 **/
	protected abstract void initVariables();

	/**
	 * 初始化视图控件
	 **/
	protected abstract void initViews();

	/**
	 * 数据加载
	 */
	protected abstract void loadData();

	/**
	 * 设置相关事件
	 **/
	protected abstract void initEvent();

	/**
	 * 输入框输入时点击外面消失输入法
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return onTouchEvent(ev);
	}

	public boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			// 获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// AppManager类是一个自定义的工具类
		// 作用是将应用程序所有启动的Activity都添加到堆栈，最终退出应用程序时全部释放掉Activity。
		AppManager.getAppManager().finishActivity(this);
	}
}
