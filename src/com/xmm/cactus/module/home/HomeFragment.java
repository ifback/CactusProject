package com.xmm.cactus.module.home;

import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Request;
import com.xmm.cactus.activity.R;
import com.xmm.cactus.adapter.HomeAdapter;
import com.xmm.cactus.base.BaseFragment;
import com.xmm.cactus.entity.HomeInfoBean;
import com.xmm.cactus.http.HttpClient;
import com.xmm.cactus.http.HttpResponseHandler;
import com.xmm.cactus.ui.pullableview.PullToRefreshLayout;
import com.xmm.cactus.ui.pullableview.PullToRefreshLayout.OnRefreshListener;
import com.xmm.cactus.ui.pullableview.PullableListView;
import com.xmm.cactus.util.HttpParamDataUtils;
import com.xmm.cactus.util.NetApiRequestUtils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

/**
 * 首页				
 * @author  DENG_MING_HAI
 * @date 	2016年4月18日
 */
@SuppressLint("NewApi")
public class HomeFragment extends BaseFragment {
	
	/**
	 * 首次加载数据
	 */
	private static final int GETDATALISTKEY = 1;

	/**
	 * 加载更多数据
	 */
	private static final int GETLOADMOREDATAKEY = 2;

	/** 列表数据  */
    private List<HomeInfoBean> list;
	/** 适配器  */
    private HomeAdapter mAdapter;
    /** 列表容器   */
    private PullableListView listview;
    /** 列表刷新容器  */
    private PullToRefreshLayout pullLayout;
    /** 请求数据页  */
    private int page = 1;
    
    @SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
    		switch (msg.what) {
			case GETDATALISTKEY:
				list = (List<HomeInfoBean>) msg.obj;
				mAdapter = new HomeAdapter(getActivity(), list);
		        listview.setAdapter(mAdapter);
				break;
			case GETLOADMOREDATAKEY:
				List<HomeInfoBean> datas= (List<HomeInfoBean>) msg.obj;
				if(datas!=null&&datas.size()>0){
					pullLayout.loadmoreFinish(PullToRefreshLayout.RESULT_SUCCEED);
					mAdapter.setMoreData(true,datas);
				}else{
					pullLayout.loadmoreFinish(PullToRefreshLayout.RESULT_LOADEND);
				}
				break;
			default:
				break;
			}
    	};
    };

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_home;
	}

	@Override
	protected void initVariables() {
		
	}

	@Override
	protected void initViews(View view) {
		pullLayout = ((PullToRefreshLayout) view.findViewById(R.id.refresh_view));
        listview = (PullableListView)view.findViewById(R.id.listview);
	}
	
	@Override
	protected void loadData() {
		String param = HttpParamDataUtils.requestHomeLists(page, "beijing", 39.982314, 116.409671, HttpClient.PAGE_SIZE);
		NetApiRequestUtils.getHomeDatas(getActivity(), param, new HttpResponseHandler(){
        	@Override
        	public void onSuccess(String content) {
        		JSONObject object = JSON.parseObject(content);
                list = JSONArray.parseArray(object.getString("body"), HomeInfoBean.class);
                Message msg = mHandler.obtainMessage();
                if(page == 1){                	
                	msg.what = GETDATALISTKEY;
                }else{
                	msg.what = GETLOADMOREDATAKEY;
                }
                msg.obj = list;
                mHandler.sendMessage(msg);
                page++;
        	}
        	
        	@Override
        	public void onFailure(Request request, IOException e) {
        		super.onFailure(request, e);
        		Log.e("xmm", "Exception:"+Log.getStackTraceString(e));
        	}
        });
	}

	@Override
	protected void initEvent() {
		pullLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
				// 下拉刷新操作
				new Handler()
				{
					@Override
					public void handleMessage(Message msg)
					{
						// 千万别忘了告诉控件刷新完毕了哦！
						pullLayout.refreshFinish(PullToRefreshLayout.RESULT_NODATA);
					}
				}.sendEmptyMessageDelayed(0, 3000);
			}
			
			@Override
			public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
				loadData();
			}
		});
	}
}
