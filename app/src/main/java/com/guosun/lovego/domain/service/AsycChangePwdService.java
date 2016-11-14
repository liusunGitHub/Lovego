package com.guosun.lovego.domain.service;

import com.guosun.lovego.config.ConstsData;
import com.guosun.lovego.entity.ResultModle;
import com.guosun.lovego.client.http.HttpPostThread;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * 
 * @author lgs
 * 
 * @2015-7-15 下午12:57:15
 */
public class AsycChangePwdService extends HttpPostThread {
	private AsycChangePwdServiceLinstener serviceLinstener = null;

	/**
	 * @return the serviceLinstener
	 */
	public AsycChangePwdServiceLinstener getServiceLinstener() {
		return serviceLinstener;
	}

	/**
	 * @param serviceLinstener
	 *            the serviceLinstener to set
	 */
	public void setServiceLinstener(AsycChangePwdServiceLinstener serviceLinstener) {
		this.serviceLinstener = serviceLinstener;
	}

	/**
	 * 
	 * @param sn
	 * @param vcode
	 * @param pwd
	 */
	public AsycChangePwdService(String sn, String vcode, String pwd) {
		super(ConstsData.CHG_PWD, initPostParams(sn, vcode, pwd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.caren.life.http.HttpGetThread#Start(java.lang.String)
	 */
	@Override
	protected void Success(String result) {
		ResultModle item;
		try {
			String content = result;
			JSONObject jsn = new JSONObject(content);
			item = new ResultModle(jsn);
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

	public interface AsycChangePwdServiceLinstener {
		public void Success(String result, ResultModle item);

		public void Failure();
		public void Error();
	}

	private static ArrayList<NameValuePair> initPostParams(String sn,
			String vcode, String pwd) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("s", sn));
		nameValuePairs.add(new BasicNameValuePair("v", vcode));
		nameValuePairs.add(new BasicNameValuePair("p", pwd));
		return nameValuePairs;
	}
}
