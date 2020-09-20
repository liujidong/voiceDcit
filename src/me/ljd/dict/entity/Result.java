package me.ljd.dict.entity;

import java.util.List;

public class Result {
	
	public String query;//关键字
	public String translation;//有道翻译
	public String phonetic;//中文音标
	public String usPhonetic;//美式发音
	public String ukPhonetic;//英式发音
	
	public List<String> explains;//基本释义
	
	public List<Web> webs;
	
	@Override
	public String toString() {
		return "关键字："+checkIsEmpty(query)+"\n"
				+"有道翻译："+checkIsEmpty(translation)+"\n"
				+"中文发音："+checkIsEmpty(phonetic)+"\n"
				+"美式发音："+checkIsEmpty(usPhonetic)+"\n"
				+"英式发音："+checkIsEmpty(ukPhonetic)+"\n"
				+"基本释义："+getExplain()+"\n"
				+"网络释义："+getWeb();
	}
	public String checkIsEmpty(String word) {
		return word==null?"无":word;
	}
	public String getExplain() {
		StringBuffer buf = new StringBuffer();
		if(null != explains && explains.size() > 0) {
			for (int i = 0; i < explains.size(); i++) {
				buf.append(explains.get(i)).append(",");
			}
			return buf.deleteCharAt(buf.length()-1).toString();
		}
		return "无";
	}
	
	public String getWeb() {
		StringBuffer buf = new StringBuffer();
		if(null != webs && webs.size() > 0) {
			for (int i = 0; i < webs.size(); i++) {
				buf.append(webs.get(i)).append(";");
			}
			return buf.deleteCharAt(buf.length()-1).toString();
		}
		return "无";	
	}
}
