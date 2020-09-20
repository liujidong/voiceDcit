package me.ljd.dict.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import me.ljd.dict.entity.Result;
import me.ljd.dict.entity.Web;

/**
 * 解析结果
 * @author ljd
 *
 */
public class Parser {
	
	public static final String TAG=Parser.class.getSimpleName();
	
	public static Result ParserData(String re) {
		try {
			JSONObject rootObj = new JSONObject(re);
			if(rootObj.getInt("errorCode")==0) {
				Result result = new Result();
				//关键字
				if(rootObj.has("query")) {
					String query = rootObj.getString("query");
					result.query=query;
				}
				if(rootObj.has("translation")) {
					result.translation=rootObj.getJSONArray("translation").toString();
				}
				if(rootObj.has("basic")) {
					JSONObject basicObj = rootObj.getJSONObject("basic");
					//发音
					if(basicObj.has("phonetic")) {
						result.phonetic=basicObj.getString("phonetic");
					}
					//美式发音
					if(basicObj.has("us-phonetic")) {
						result.usPhonetic=basicObj.getString("us-phonetic");
					}
					//英式发音
					if(basicObj.has("uk-phonetic")) {
						result.ukPhonetic=basicObj.getString("uk-phonetic");
					}	
					//基本释义
					if(basicObj.has("explains")) {
						JSONArray explainsArray = basicObj.getJSONArray("explains");
						List<String> explains = new ArrayList<String>();
						for (int i = 0; i < explainsArray.length(); i++) {
							explains.add(explainsArray.getString(i));
						}
						result.explains = explains;
					}
				}
				//网络释义
				if(rootObj.has("web")) {
					List<Web> webs = new ArrayList<Web>();
					JSONArray webArray = rootObj.getJSONArray("web");
					for (int i = 0; i < webArray.length(); i++) {
						JSONObject webObj = webArray.getJSONObject(i);
						Web web = new Web();
						web.key = webObj.getString("key");
						
						List<String> values = new ArrayList<String>();
						JSONArray valueArray = webObj.getJSONArray("value");
						for (int j = 0; j < valueArray.length(); j++) {
							values.add(valueArray.getString(i));
						}
						web.values = values;
						
						webs.add(web);
					}
					result.webs = webs;
				}
				return result;
			}
		} catch (JSONException e) {
			Log.e(TAG,"JSONException");
		}
		return null;
	}
}
