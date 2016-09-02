package com.xmm.cactus.adapter;

import java.util.List;

import com.bumptech.glide.Glide;
import com.xmm.cactus.activity.R;
import com.xmm.cactus.config.NetRequestUrl;
import com.xmm.cactus.entity.HomeInfoBean;
import com.xmm.cactus.imageloader.ImageLoader;
import com.xmm.cactus.imageloader.ImageLoaderUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 首页适配				
 * @author  DENG_MING_HAI
 * @date 	2016年4月21日
 */
public class HomeAdapter extends BaseAdapter {
	
	/**
	 * 上下文
	 */
	private Context mContext;
	
	/**
	 * 列表数据
	 */
	private List<HomeInfoBean> list;
	
	public HomeAdapter(Context context,List<HomeInfoBean> ls) {
		this.mContext = context;
		this.list = ls;
	}
	/**
	 * 由于mAdapter.notifyDataSetChanged();无效   所以这里刷新数据
	 * @param ls  		获取的数据
	 * @param loadMore 
	 * 			 true:获取更多数据 
	 *     		 false：最新数据
	 */
	public void setMoreData(boolean loadMore,List<HomeInfoBean> ls){
		if(loadMore){
			this.list.addAll(ls);
		}else{			
			this.list.addAll(0,ls);
		}
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		ImageView iv_logo;
		TextView tv_name;
		TextView tv_address;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_home_item, null);
			vh = new ViewHolder();
			vh.iv_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
			vh.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			vh.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		HomeInfoBean item = list.get(position);
		vh.tv_name.setText(item.getName());
		vh.tv_address.setText(item.getAddr());
		ImageLoaderUtil.getInstance().loadImage(mContext,new ImageLoader.Builder().url(NetRequestUrl.formalUrl + list.get(position).getLogo()).imgView(vh.iv_logo).build());
		//Glide.with(mContext).load(NetRequestUrl.formalUrl + list.get(position).getLogo()).error(R.drawable.default_image).into(vh.iv_logo);
		return convertView;
	}

}
