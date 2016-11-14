/**
 * 
 */
package com.guosun.lovego.domain.service;

import com.guosun.lovego.config.ConstsData;
import com.guosun.lovego.entity.UserInfoModle;
import com.guosun.lovego.client.http.HttpGetThread;

import org.json.JSONObject;


/**
 * @author liuguosheng
 * 
 * @2015-7-6 下午1:22:20
 */
public class AsycGetUserInfoService extends HttpGetThread {
	private GetFirstInfoService serviceLinstener = null;

	/**
	 * @return the serviceLinstener
	 */
	public GetFirstInfoService getServiceLinstener() {
		return serviceLinstener;
	}

	/**
	 * @param serviceLinstener
	 *            the serviceLinstener to set
	 */
	public void setFirstInfoServiceLinstener(
			GetFirstInfoService serviceLinstener) {
		this.serviceLinstener = serviceLinstener;
	}

	public AsycGetUserInfoService(String sn) {
		super(ConstsData.FIRST, initParams(sn));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.caren.life.http.HttpGetThread#Start(java.lang.String)
	 */
	@Override
	protected void Success(String result) {
		// TODO Auto-generated method stub
		UserInfoModle item;
		try {
			String content = result;
			JSONObject jsn = new JSONObject(content);
			item = new UserInfoModle(jsn);
		} catch (Exception e) {
			if (serviceLinstener != null)
				serviceLinstener.Error();
			e.printStackTrace();
			return;
		}
		if (serviceLinstener != null)
			serviceLinstener.Success(result, item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.caren.life.http.HttpGetThread#Progress()
	 */
	@Override
	protected void Failure() {
		// TODO Auto-generated method stub
		if (serviceLinstener != null)
			serviceLinstener.Failure();
	}

	public interface GetFirstInfoService {
		public void Success(String result, UserInfoModle item);

		public void Failure();
		public void Error();
	}

	private static String initParams(String sn) {
		StringBuilder sb = new StringBuilder();
//		sb.append("d=").append(Config.SERVER_DERVICE).append("&s=").append(sn);
		return sb.toString();
	}

}
