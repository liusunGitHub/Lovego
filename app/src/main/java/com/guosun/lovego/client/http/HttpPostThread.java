package com.guosun.lovego.client.http;

import android.os.Handler;
import android.os.Message;

import com.guosun.lovego.config.Config;
import com.guosun.lovego.util.ULog;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;


public abstract class HttpPostThread implements Runnable {
	private String TAG = "MyHttp";

	private Handler hand = new Handler() {

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
	private String url;
	private ArrayList<NameValuePair> params;

	public HttpPostThread(String url,ArrayList<NameValuePair> params) {
		// 拼接访问服务器完整的地址
		this.url = Config.BASE_URL + url;
		this.params = params;
		ThreadPoolUtils.execute(this);
		ULog.d(TAG, this.url);
	}

	@Override
	public void run() {
		// 获取我们回调主ui的message
		Message msg = hand.obtainMessage();
		try {
			String result = doPost(url, params);
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


	private String doPost(String url, ArrayList<NameValuePair> nameValuePairs) throws Exception{
		String result = null;
		HttpResponse httpResponse = null;
		HttpPost post = new HttpPost(url);
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT,
				30000); // 超时设置
		client.getParams().setIntParameter(
				HttpConnectionParams.CONNECTION_TIMEOUT, 10000);// 连接超时
		ULog.i(url, "post - params : " + nameValuePairs.toString());
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
			httpResponse = client.execute(post);
			ULog.i(url, "code " + httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils
						.toString(httpResponse.getEntity(), "utf-8");
			}
		ULog.i(url,result);
		return result;
	}
}
