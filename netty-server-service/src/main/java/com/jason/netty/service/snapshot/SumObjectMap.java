/**
 * Create at Feb 19, 2013
 */
package com.jason.netty.service.snapshot;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author jason
 * 
 */
public class SumObjectMap {

	DecimalFormat format = new DecimalFormat("#,##0.### ms");
	private String reportTitle;
	private Date reportStartDate = new Date();

	private Map<String, SumObject> map = new HashMap<String, SumObject>();

	public SumObjectMap(String title) {
		this.reportTitle = title;
	}

	private class SumObject implements Comparable {
		private String key;

		AtomicLong accessCount;
		AtomicLong costTime;

		/**
		 * @return the key
		 */
		public String getKey() {
			return key;
		}

		/**
		 * @param key
		 */
		public SumObject(String key) {
			this.key = key;

			this.accessCount = new AtomicLong();
			this.costTime = new AtomicLong();
		}

		public void setKey(String key) {
			this.key = key;
		}

		public AtomicLong getAccessCount() {
			return accessCount;
		}

		public void setAccessCount(AtomicLong accessCount) {
			this.accessCount = accessCount;
		}

		public AtomicLong getCostTime() {
			return costTime;
		}

		public void setCostTime(AtomicLong costTime) {
			this.costTime = costTime;
		}

		public int compareTo(Object o) {
			SumObject sumObject = (SumObject) o;
			if (sumObject.getAccessCount().doubleValue()
					> this.accessCount.doubleValue()) {
				return 1;
			} else if(sumObject.getAccessCount().doubleValue()
					< this.accessCount.doubleValue()){
				return -1;
			} else {
				return 0;
			}
		}
	}

	/**
	 *
	 * @param weight
	 * @param costNanoTime
	 */
	public void add(String key, int weight, long costNanoTime) {

		if (null == map.get(key)) {
			map.put(key, new SumObject(key));
		}
		// access time plus one
		map.get(key).accessCount.addAndGet(weight);
		// add cost nano time
		map.get(key).costTime.addAndGet(costNanoTime);
	}


	private String formatLong(long number) {
		return format.format(number);
	}

	/**
	 * double format
	 * @param number
	 * @return
	 */
	private String formatDouble(double number) {
		return format.format(number);
	}

	/**
	 *
	 * @param format
	 *            0-plantext, 1-html
	 * @return
	 */
	public String toString(int format) {

		// to html format
		StringBuffer buf = new StringBuffer();

		long totalCount = 0;
		double totalCost = 0;

		buf.append(
				"<h3>" + this.reportTitle + "(start from "
						+ this.reportStartDate + ")</h3>")
				.append("<table border=\"1\">")
				.append("<tr><td><b>Key</b>\t</td><td><b>Count</b></td>")
				.append("<td><b>Total Cost(ms)</b></td>")
				.append("<td><b>Average Execute Cost Time(ms)</b></td></tr>\n");
		for(SumObject record: map.values()) {
			double avrCostTime = record.costTime.doubleValue() / record.accessCount.get() / 1000000;
			buf.append("<tr><td>").append(record.getKey());
			buf.append("</td><td>").append(record.accessCount);
			buf.append("</td><td>").append(formatDouble(record.costTime.doubleValue() / 1000000));
			buf.append("</td><td>")
					.append(formatDouble(avrCostTime))
					.append("</td></tr>\n");
			totalCount += record.accessCount.get();
			totalCost += record.costTime.doubleValue();
		}

		buf.append("</table>");

		buf.append("<h3>Report Summary</h3>");
		buf.append("<br>Total Count: \n").append(totalCount);
		buf.append("<br>Total Cost Time: \n").append(
				formatDouble(totalCost / 1000000));
		if (totalCount != 0) {
			double avrTotalCostTime = totalCost / totalCount / 1000000;
			buf.append("<br>Average Execute Cost Time: \n").append(
					formatDouble(avrTotalCostTime));
		}

		buf.append("<hr>\n");

		return buf.toString();
	}

	/**
	 *
	 * @param format
	 *            0-plantext, 1-html
	 * @return
	 */
	public String toIpString(int format, boolean flag) {

		// to html format
		StringBuffer buf = new StringBuffer();

		long totalCount = 0;
		double totalCost = 0;

		buf.append(
				"<h3>" + this.reportTitle + "(start from "
						+ this.reportStartDate + ")</h3>")
				.append("<table border=\"1\">")
				.append("<tr><td><b>Key</b>\t</td><td><b>Count</b></td>")
				.append("<td><b>Total Cost(ms)</b></td>")
				.append("<td><b>Average Execute Cost Time(ms)</b></td></tr>\n");

		ArrayList<SumObject> sumObjects = new ArrayList<SumObject>(map.values());
		Collections.sort(sumObjects);
		int count = 0;
		for(SumObject record : sumObjects) {
			double avrCostTime = record.costTime.doubleValue() / record.accessCount.get() / 1000000;
			buf.append("<tr><td>").append(record.getKey());
			buf.append("</td><td>").append(record.accessCount);
			buf.append("</td><td>").append(formatDouble(record.costTime.doubleValue() / 1000000));
			buf.append("</td><td>")
					.append(formatDouble(avrCostTime))
					.append("</td></tr>\n");
			totalCount += record.accessCount.get();
			totalCost += record.costTime.doubleValue();
			count++;
			if (flag && count == 10) {
				break;
			}

		}

		buf.append("</table>");

		buf.append("<h3>Report Summary</h3>");
		buf.append("<br>Total Count: \n").append(totalCount);
		buf.append("<br>Total Cost Time: \n").append(
				formatDouble(totalCost / 1000000));
		if (totalCount != 0) {
			double avrTotalCostTime = totalCost / totalCount / 1000000;
			buf.append("<br>Average Execute Cost Time: \n").append(
					formatDouble(avrTotalCostTime));
		}

		buf.append("<hr>\n");

		return buf.toString();
	}

	/**
	 *
	 * @param format
	 *            0-plantext, 1-html
	 * @return
	 */
	public String toBusinessString(int format, Map<String, SumObject> uriMap) {

		// to html format
		StringBuffer buf = new StringBuffer();

		long totalCount = 0;
		double totalCost = 0;

		buf.append(
				"<h3>" + this.reportTitle + "(start from "
						+ this.reportStartDate + ")</h3>")
				.append("<table border=\"1\">")
				.append("<tr><td><b>Key</b>\t</td><td><b>Count</b></td>")
				.append("<td><b>Total Cost(ms)</b></td>")
				.append("<td><b>Average Execute Cost Time(ms)</b></td></tr>\n");
		for(SumObject record: map.values()) {
			double avrCostTime = record.costTime.doubleValue() / record.accessCount.get() / 1000000;
			buf.append("<tr><td>").append(record.getKey());
			buf.append("</td><td>").append(record.accessCount);
			buf.append("</td><td>").append(formatDouble(record.costTime.doubleValue() / 1000000));
			buf.append("</td><td>")
					.append(formatDouble(avrCostTime))
					.append("</td></tr>\n");
			totalCount += record.accessCount.get();
			totalCost += record.costTime.doubleValue();
		}

		buf.append("</table><br>");

		long uriTotalCount = 0;
		double uriTotalCost = 0;
		buf.append(
				"<h3>Compared With Uri</h3>")
				.append("<table border=\"1\">")
				.append("<tr><td><b>Key</b>\t</td><td><b>Count</b></td>")
				.append("<td><b>Total Cost(ms)</b></td>")
				.append("<td><b>Average Execute Cost Time(ms)</b></td></tr>\n");
		for(SumObject record: uriMap.values()) {
			double avrCostTime = record.costTime.doubleValue() / record.accessCount.get() / 1000000;
			buf.append("<tr><td>").append(record.getKey());
			buf.append("</td><td>").append(record.accessCount);
			buf.append("</td><td>").append(formatDouble(record.costTime.doubleValue() / 1000000));
			buf.append("</td><td>")
					.append(formatDouble(avrCostTime))
					.append("</td></tr>\n");
			uriTotalCount += record.accessCount.get();
			uriTotalCost += record.costTime.doubleValue();
		}

		buf.append("</table>");
		buf.append(
				"<h3>Average Execute Cost Time Compared With Uri</h3>")
				.append(formatDouble(totalCost / totalCount * 2 / 1000000)).append("(Business)")
				.append(" <= ")
				.append(formatDouble(uriTotalCost / uriTotalCount / 1000000)).append("(Uri)");

		buf.append("<hr>\n");

		return buf.toString();
	}

	public String getReportTitle() {
		return reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	public Date getReportStartDate() {
		return reportStartDate;
	}

	public void setReportStartDate(Date reportStartDate) {
		this.reportStartDate = reportStartDate;
	}

	public Map<String, SumObject> getMap() {
		return map;
	}

	public void setMap(Map<String, SumObject> map) {
		this.map = map;
	}

}
