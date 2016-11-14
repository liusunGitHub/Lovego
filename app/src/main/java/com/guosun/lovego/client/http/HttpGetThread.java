package com.guosun.lovego.client.http;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.guosun.lovego.config.Config;
import com.guosun.lovego.util.ULog;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;


public abstract class HttpGetThread implements Runnable {
	private String TAG = "MyHttp";

	private Handler hand = new Handler() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 200) {
				if (msg.obj != null)
					Success((String) msg.obj);
			} else {
				Failure();
			}
		}

	};
	private String urlGet;

	public HttpGetThread(String url, String params) {
		// 拼接访问服务器完整的地址
		if (!TextUtils.isEmpty(url)) {
			if ("?".equals(url.lastIndexOf(url.length() - 1))) {
				this.urlGet = Config.BASE_URL + url + params;
			} else {
				this.urlGet = Config.BASE_URL + url + "?" + params;
			}
		}
		ThreadPoolUtils.execute(this);
		ULog.d(TAG, this.urlGet);
	}

	@Override
	public void run() {
		// 获取我们回调主ui的message
		Message msg = hand.obtainMessage();
		try {
			String result = doGet(urlGet);
			if (result!=null) {
				msg.what = 200;
				msg.obj = result;
			}else{
				msg.what = 404;
			}
		} catch (Exception e) {
			msg.what = 404;
			ULog.e(TAG,e.toString());
		}
		// 给主ui发送消息传递数据
		hand.sendMessage(msg);
	}

	protected abstract void Success(String result);

	protected abstract void Failure();


	private String doGet(String url) throws Exception{
		String result = null;//我们的网络交互返回值
		HttpGet myGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter(
				HttpConnectionParams.CONNECTION_TIMEOUT, 50*1000);
		httpClient.getParams().setIntParameter(
				HttpConnectionParams.SO_TIMEOUT, 30*1000);
		HttpResponse httpResponse = httpClient.execute(myGet);
		ULog.i(TAG, "code " + httpResponse.getStatusLine().getStatusCode());
		if(httpResponse.getStatusLine().getStatusCode() == 200){
			HttpEntity entity = httpResponse.getEntity();
			result = EntityUtils.toString(entity , "GBK");
		}
		ULog.i(TAG, result);
		return result;
	}

}
