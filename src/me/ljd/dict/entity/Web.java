package me.ljd.dict.entity;

import java.util.List;

public class Web {
	
	public String key;
	public List<String> values;
	
	@Override
	public String toString() {
		return key + ":" + getAllWebValue();
	}
	
	public String getAllWebValue() {
		StringBuffer buf = new StringBuffer();
		if(null != values && values.size() > 0) {
			for (int i = 0; i < values.size(); i++) {
				buf.append(values.get(i)).append(",");
			}
			return buf.deleteCharAt(buf.length()-1).toString();
		}
		return "无";
	}
	
}
