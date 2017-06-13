/**
 * Create at Jan 24, 2013
 */
package com.jason.netty.service.snapshot;

import com.jason.business.BusinessException;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jason
 * 
 */
public class SnapshotService {
	public static final int WATCH_KEY_ACCESS_BY_URI = 1;
	public static final int WATCH_KEY_ACCESS_BY_IP = 2;
	public static final int WATCH_KEY_PROCESS_BY_STATUS = 3;
	public static final int WATCH_KEY_Exception_BY_Type = 4;
	public static final int WATCH_KEY_BUSINESS_BY_SUBPOINT = 5;
	private Map<String, SumObjectMap> watchMap = new HashMap<String, SumObjectMap>();
	private DecimalFormat format = new DecimalFormat("#,##0.### ms");

	public SnapshotService() {
		// init watch map
		SumObjectMap map = new SumObjectMap("Access Count Group By Uri");
		watchMap.put("1", map);

		map = new SumObjectMap("Access Count Group By IP");
		watchMap.put("2", map);

		map = new SumObjectMap("Process Count Group By Status");
		watchMap.put("3", map);

		map = new SumObjectMap("Exception Count Group By Type");
		watchMap.put("4", map);

		map = new SumObjectMap("Business Count Group By Sub business");
		watchMap.put("5", map);
	}

	public String getAccessKey(HttpRequest request) {
		return HttpHeaders.getHost(request, "") + request.getUri();
	}

	/**
	 * 添加检测消息
	 * @param watch
	 * @param exceptions
	 */
	public void addMessageWatch(MessageWatch watch, List<Exception> exceptions) {
		String key = watch.getRequestUri();
		watchMap.get(String.valueOf(WATCH_KEY_ACCESS_BY_URI)).add(key, 1,
				watch.getAliveTime());

		key = String.valueOf(watch.getResponseCode());
		// only add business log
		if (watch.isBusiness()) {
			watchMap.get(String.valueOf(WATCH_KEY_PROCESS_BY_STATUS)).add(key,
					1, watch.getAliveTime(MessageWatch.STATE_BUSINESS));

			// split count BusinessException
			for (Exception ex : exceptions) {
				key = ex.getClass().getName();
				if (ex instanceof BusinessException) {
					watchMap.get(String.valueOf(WATCH_KEY_Exception_BY_Type))
							.add("[logic error]"+key,
									1,watch.getAliveTime(MessageWatch.STATE_BUSINESS));
				} else {
					watchMap.get(String.valueOf(WATCH_KEY_Exception_BY_Type))
							.add(key,1,watch.getAliveTime(MessageWatch.STATE_BUSINESS));
				}
			}

			// sub business point of a complete business
			Map<String,Integer> subBusiness = watch.getSubBusiness();
			if (subBusiness.size() > 0) {
				for (String keyName: subBusiness.keySet()) {
					key = keyName;
					int index = subBusiness.get(keyName);
					watchMap.get(String.valueOf(WATCH_KEY_BUSINESS_BY_SUBPOINT))
							.add(key,1,watch.getAliveTime(index));
				}
			}
		}

		key = watch.getRemoteIP();
		watchMap.get(String.valueOf(WATCH_KEY_ACCESS_BY_IP)).add(key, 1,
				watch.getAliveTime());
	}

	/**
	 * 查看监控信息
	 * @param key
	 * @param format
	 * @return
	 */
	public String getWatchInfo(int key, int format, boolean showAll) {

		// return all known report
		if (key == 0) {
			StringBuffer sb = new StringBuffer(1000);
			for (int i = 1; i < 6; i++) {
				if (WATCH_KEY_BUSINESS_BY_SUBPOINT == i) {     //toBusinessString
					sb.append(watchMap.get(String.valueOf(i)).toBusinessString(format,
							watchMap.get(String.valueOf(WATCH_KEY_ACCESS_BY_URI)).getMap()));
				} else if(WATCH_KEY_ACCESS_BY_IP == i) {
					if (showAll) {
						sb.append(watchMap.get(String.valueOf(i)).toString(format));
					} else {
						sb.append(watchMap.get(String.valueOf(i)).toIpString(format, true));
					}
				} else {
					sb.append(watchMap.get(String.valueOf(i)).toString(format));
				}
				sb.append("\n<br>");
			}

			return sb.toString();
		}

		if (null == watchMap.get(String.valueOf(key))) {
			return "Unknow report key: " + key;
		}

		return watchMap.get(String.valueOf(key)).toString(format);
	}

	/**
	 * 根据key获取map
	 * @return
	 */
	public Map<String, SumObjectMap> getMap(){
		return watchMap;
	}
}
