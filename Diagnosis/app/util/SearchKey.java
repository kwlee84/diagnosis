package util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.h2.util.StringUtils;


public class SearchKey extends HashMap<String, String> {
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, Boolean> sortField = new HashMap<String, Boolean>();
	
	public String getSerializedKeys() throws UnsupportedEncodingException {
		String result = "";
		Set<String> keys  = this.keySet(); 
		Iterator<String> itr = keys.iterator();

		while(itr.hasNext()){
			String key = (String) itr.next();
			String value = URLEncoder.encode(this.get(key), "UTF-8");
			if(value == null) continue;
			result += (key + "=" + value + "&");
		}
		if(result.equals("")) {
			return null;
		}
		return result.substring(0, result.length()-1);
	}

	public void addKey(String key, String value) {
		//
		if(value == null) return;
		this.put(key, value);
	}
	
	public void addSort(String fieldName, Boolean ascending) {
		//
		if(fieldName == null || ascending == null) return;
		sortField.put(fieldName, ascending);
	}
	
	public void addSort(String fieldName, String ascending) {
		//
		if(fieldName == null || StringUtils.isNullOrEmpty(ascending)) return;
		sortField.put(fieldName, Boolean.parseBoolean(ascending));
	}
	
	public Map<String, Boolean> getSortField() {
		return sortField;
	}

}
