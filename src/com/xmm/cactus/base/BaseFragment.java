package com.xmm.cactus.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 基础Fragment				
 * @author  DENG_MING_HAI
 * @date 	2016年8月30日
 */
public abstract class BaseFragment  extends Fragment {

    /**
     * 当前 view
     */
    protected View mRootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mRootView == null){
            if ( getLayoutId() != 0 && getLayoutId() > 0) {
                mRootView =  inflater.inflate(getLayoutId(), container, false);
                initViews(mRootView);
                initVariables();
            }else{
                throw new IllegalArgumentException("layoutId不能为空");
            }
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
        initEvent();
    }

    /**
     * 设置布局文件 Id
     **/
    protected abstract int getLayoutId();


	/**
	 * 获取传过来的Intent参数
	 **/
	protected abstract void initVariables();
	
    /**
     * 初始化视图
     **/
    protected abstract void initViews(View view);

    /**
     * 数据加载
     */
    protected abstract void loadData();
    
    /**
     * 初始化相关事件
     **/
    protected abstract void initEvent();
}
