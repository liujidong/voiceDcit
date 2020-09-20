package me.ljd.dict.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class AsycHttp {
	
	public static final String TAG=AsycHttp.class.getSimpleName();
	
	private static AsycHttp mAsycHttp;
	private AsycHttp() {
		
	}
	public static AsycHttp getInstance() {
		if(null == mAsycHttp) {
			synchronized (AsycHttp.class) {
				if(null == mAsycHttp) {
					mAsycHttp = new AsycHttp();
				}
			}
		}
		return mAsycHttp;
	}
	
	public void requestForHttp(String url,Map<String,String> params,OnResonseListener monResonseListener){
		if(params != null) {
	        /** httpPost */
	        HttpPost httpPost = new HttpPost(url);
	        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
	        Iterator<Map.Entry<String,String>> it = params.entrySet().iterator();
	        while(it.hasNext()){
	            Map.Entry<String,String> en = it.next();
	            String key = en.getKey();
	            String value = en.getValue();
	            paramsList.add(new BasicNameValuePair(key,value));
	        }
	        try {
				httpPost.setEntity(new UrlEncodedFormEntity(paramsList,"UTF-8"));
				
				new DictAsycTak(monResonseListener).execute(httpPost);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    class DictAsycTak extends AsyncTask<HttpUriRequest, Void, String>{
    	OnResonseListener monResonseListener;
    	public DictAsycTak(OnResonseListener monResonseListener) {
    		this.monResonseListener = monResonseListener;
    	}

    	int resultCode;
		@Override
		protected String doInBackground(HttpUriRequest... params) {
			// 执行网络请求
			HttpClient client = new DefaultHttpClient();
			try {
				HttpResponse response = client.execute(params[0]);
	            Header[] contentType = response.getHeaders("Content-Type");
	            Log.i("Content-Type:" , contentType[0].getValue());
	            if(!"audio/mp3".equals(contentType[0].getValue())){				
					resultCode = response.getStatusLine().getStatusCode();
					if(resultCode==HttpStatus.SC_OK) {
						HttpEntity httpEntity = response.getEntity();
						String json = EntityUtils.toString(httpEntity,"UTF-8");
						Log.i("json",json);
		                System.out.println(json);
						return json;
					}else {
						params[0].abort();
					}
	            }
			} catch (ClientProtocolException e) {
				Log.e(TAG,"ClientProtocolException");
			} catch (IOException e) {
				Log.e(TAG,"IOException");
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(null != result) {
				//成功
				//Log.i(TAG, "结果:"+result);
				monResonseListener.onSuccess(result);
			}else {
				//Log.e(TAG, "失败："+resultCode);
				monResonseListener.onFailed("error-->"+resultCode);
			}
		}
    	
    }
    //回调接口
    public interface OnResonseListener{
    	void onSuccess(String result);
    	void onFailed(String error);
    }
}
