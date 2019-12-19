package com.ymw.love.system.util;

import com.ymw.love.system.config.excep.MissRequiredParamException;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期工具类
 */
public class DateUtil {

	public static final String DATE_FORMAT_YYYY = "yyyy";

	public static final String DATE_FORMAT_MM = "MM";

//	年月
	public static final String DATE_FORMAT_YYYYMM = "yyyyMM";

	public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";

//	年月日
	public static final String DATE_FORMAT_YYMMDD = "yyMMdd";

	public static final String DATE_FORMAT_YY_MM_DD = "yy-MM-dd";

	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	public static final String DATE_FORMAT_POINTYYYYMMDD = "yyyy.MM.dd";

	public static final String DATE_TIME_FORMAT_YYYY年MM月DD日 = "yyyy年MM月dd日";

	private static final String MM_DD_yyyy = "MM/dd/yyyy";

//	年月日时分
	public static final String DATE_FORMAT_YYYYMMDDHHmm = "yyyyMMddHHmm";

	public static final String DATE_TIME_FORMAT_MM_DD_YYYY_HH_MI = "MM-dd-yyyy HH:mm";

	public static final String DATE_TIME_FORMAT_YYYYMMDD_HH_MI = "yyyyMMdd HH:mm";

	public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI = "yyyy-MM-dd HH:mm";

//	年月日时分秒
	public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISS = "yyyyMMddHHmmss";

	public static final String DATE_TIME_FORMAT_HH_MM_SS_MM_DD_YYYY = "HH:mm:ss MM-dd-yyyy";

	public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String HH_MI = "HH:mm";

//	年月日时分秒毫秒
	public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS = "yyyyMMddHHmmssSSS";

	public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS_S = "yyyy-MM-dd HH:mm:ss.S";

	/* ************工具方法*************** */

	/**
	 * 获取UTC 时间
	 * 
	 * @return 13位
	 */
	public static long getUTCMillisecond() {
		Calendar cal = Calendar.getInstance();
		int offset = cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET);
		cal.add(Calendar.MILLISECOND, -offset);
		return cal.getTimeInMillis();
	}

	/**
	 * 获取UTC 时间
	 * 
	 * @return 10位
	 */
	public static long getUTCSecond() {
		return getUTCMillisecond() / 1000;
	}

	/**
	 * 通过秒值获取置顶格式时间
	 * 
	 * @return
	 */
	public String secondToFormat(Long second, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(second * 1000);
		Date date = calendar.getTime();
		String dateString = new SimpleDateFormat(format).format(date);
		return dateString;
	}

	/**
	 * 时间搓转日期 MM/dd/yyyy
	 * 
	 * @author zjc
	 * @return
	 */
	public static String dateMM_DD_yyyy(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
		String sd = sdf.format(date);
		return sd;
	}

	/**
	 * 时间搓转日期 MM/dd/yyyy
	 * 
	 * @author zjc
	 * @return
	 */
	public String dateTimeMM_DD_yyyy(Long dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(MM_DD_yyyy);
		String sd = sdf.format(new Date(dateTime));
		return sd;
	}

	/**
	 * 获取某日期的年份
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 获取某日期的月份
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取某日期的日数
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DATE);// 获取日
		return day;
	}

	/**
	 * 字符串时间格式转字符串时间格式
	 * 
	 * @param priFormat
	 * @param aftFormat
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public String timeYearToYearTime(String priFormat, String aftFormat, String time) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(priFormat);
		long timeLong;
		timeLong = format.parse(time).getTime();
		SimpleDateFormat aDate = new SimpleDateFormat(aftFormat);
		return aDate.format(timeLong);
	}

	/**
	 * @Description:秒值距离今天多少天
	 * @author: Lee
	 * @Data: 2018/12/5/11:42
	 */
	public static String getDayBySecond(Long time) {
		long dateLong = getUTCMillisecond() / 1000;
		long dateTemp1 = (dateLong - time);
		String msg = getTimeBySecond(dateTemp1);
		return msg;
	}

	/**
	 * 两个时间之间相差距离多少天
	 * @param str1 时间参数 1：
	 * @param str2 时间参数 2：
	 * @return 相差天数
	 */
	public static Long getDistance(String str1, String str2){
		DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT_YYYYMMDDHHMISS);
		Date one;
		Date two;
		long days=0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff ;
			if(time1<time2) {
				diff = time2 - time1;
				days = - (diff / (1000 * 60 * 60 * 24));
			} else {
				diff = time1 - time2;
				days = diff / (1000 * 60 * 60 * 24);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return days;
	}

	/**
	 * @Description:毫秒值距离今天多少天
	 * @author: Lee
	 * @Data: 2018/12/5/11:42
	 */
	public static String getDayByMillisecond(Long time) {
		long dateLong = getUTCMillisecond();
		long dateDiff = (dateLong - time);
		long dateTemp1 = dateDiff / 1000; // 秒
		String msg = getTimeBySecond(dateTemp1);
		return msg;
	}

	private static String getTimeBySecond(long dateTemp1) {
		long dateTemp2 = dateTemp1 / 60; // 分钟
		long dateTemp3 = dateTemp2 / 60; // 小时
		long dateTemp4 = dateTemp3 / 24; // 天数
		long dateTemp5 = dateTemp4 / 30; // 月数
		long dateTemp6 = dateTemp5 / 12; // 年数
		String msg = null;
		if (dateTemp6 > 0) {
			msg = dateTemp6 + " Years ago";
		} else if (dateTemp5 > 0) {
			msg = dateTemp5 + " Months ago";
		} else if (dateTemp4 > 0) {
			msg = dateTemp4 + " Days ago";
		} else if (dateTemp3 > 0) {
			msg = dateTemp3 + " Hours ago";
		} else if (dateTemp2 > 0) {
			msg = dateTemp2 + " Minutes ago";
		} else if (dateTemp1 >= 0) {
			msg = "Just,Less than a minute";
		}
		return msg;
	}

	/**
	 * 格式化Date时间
	 *
	 * @param time       Date类型时间
	 * @return 格式化后的字符串
	 */
	public static String parseDateToStr(Date time) {
		return parseDateToStr(time,DATE_TIME_FORMAT_YYYYMMDDHHMISS);
	}

	/**
	 * 格式化Date时间
	 * 
	 * @param time       Date类型时间
	 * @param timeFromat String类型格式
	 * @return 格式化后的字符串
	 */
	public static String parseDateToStr(Date time, String timeFromat) {
		DateFormat dateFormat = new SimpleDateFormat(timeFromat);
		return dateFormat.format(time);
	}

	/**
	 * 格式化Timestamp时间
	 * 
	 * @param timestamp  Timestamp类型时间
	 * @param timeFromat
	 * @return 格式化后的字符串
	 */
	public static String parseTimestampToStr(Timestamp timestamp, String timeFromat) {
		SimpleDateFormat df = new SimpleDateFormat(timeFromat);
		return df.format(timestamp);
	}

	/**
	 * 格式化Date时间
	 * 
	 * @param time         Date类型时间
	 * @param timeFromat   String类型格式
	 * @param defaultValue 默认值为当前时间Date
	 * @return 格式化后的字符串
	 */
	public String parseDateToStr(Date time, String timeFromat, final Date defaultValue) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(timeFromat);
			return dateFormat.format(time);
		} catch (Exception e) {
			if (defaultValue != null)
				return parseDateToStr(defaultValue, timeFromat);
			else
				return parseDateToStr(new Date(), timeFromat);
		}
	}

	/**
	 * 格式化Date时间
	 * 
	 * @param time         Date类型时间
	 * @param timeFromat   String类型格式
	 * @param defaultValue 默认时间值String类型
	 * @return 格式化后的字符串
	 */
	public String parseDateToStr(Date time, String timeFromat, final String defaultValue) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(timeFromat);
			return dateFormat.format(time);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 正则校验年份小于等于当前年份
	 * 
	 * @return
	 */
	public static boolean lessThanCurrentYear(String yearStr) {
		/**
		 * 判断日期格式和范围
		 */
		String rexp = "[0-9]{4}";
		Pattern pat = Pattern.compile(rexp);

		Matcher mat = pat.matcher(yearStr);

		Calendar date = Calendar.getInstance();

		return mat.matches() && Integer.valueOf(yearStr) <= Integer.valueOf(String.valueOf(date.get(Calendar.YEAR)));
	}

	/**
	 * 格式化String时间
	 * 
	 * @param time       String类型时间
	 * @param timeFromat String类型格式
	 * @return 格式化后的Date日期
	 */
	public static Date parseStrToDate(String time, String timeFromat) {
		if (time == null || time.equals("")) {
			return null;
		}

		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(timeFromat);
			date = dateFormat.parse(time);
		} catch (Exception e) {

		}
		return date;
	}

	/**
	 * 格式化String时间
	 * 
	 * @param strTime      String类型时间
	 * @param timeFromat   String类型格式
	 * @param defaultValue 异常时返回的默认值
	 * @return
	 */
	public Date parseStrToDate(String strTime, String timeFromat, Date defaultValue) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(timeFromat);
			return dateFormat.parse(strTime);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 当strTime为2008-9时返回为2008-9-1 00:00格式日期时间，无法转换返回null.
	 * 
	 * @param strTime
	 * @return
	 */
	public Date strToDate(String strTime) {
		if (strTime == null || strTime.trim().length() <= 0)
			return null;

		Date date = null;
		List<String> list = new ArrayList<String>(0);

		list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
		list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS);
		list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI);
		list.add(DATE_TIME_FORMAT_YYYYMMDD_HH_MI);
		list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISS);
		list.add(DATE_FORMAT_YYYY_MM_DD);
		// list.add(DATE_FORMAT_YY_MM_DD);
		list.add(DATE_FORMAT_YYYYMMDD);
		list.add(DATE_FORMAT_YYYY_MM);
		list.add(DATE_FORMAT_YYYYMM);
		list.add(DATE_FORMAT_YYYY);

		for (Iterator iter = list.iterator(); iter.hasNext();) {
			String format = (String) iter.next();
			if (strTime.indexOf("-") > 0 && format.indexOf("-") < 0)
				continue;
			if (strTime.indexOf("-") < 0 && format.indexOf("-") > 0)
				continue;
			if (strTime.length() > format.length())
				continue;
			date = parseStrToDate(strTime, format);
			if (date != null)
				break;
		}

		return date;
	}

	/**
	 * 解析两个日期之间的所有月份
	 * 
	 * @param beginDateStr 开始日期，至少精确到yyyy-MM
	 * @param endDateStr   结束日期，至少精确到yyyy-MM
	 * @return yyyy-MM日期集合
	 */
	public List<String> getMonthListOfDate(String beginDateStr, String endDateStr) {
		// 指定要解析的时间格式
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");
		// 返回的月份列表
		String sRet = "";

		// 定义一些变量
		Date beginDate = null;
		Date endDate = null;

		GregorianCalendar beginGC = null;
		GregorianCalendar endGC = null;
		List<String> list = new ArrayList<String>();

		try {
			// 将字符串parse成日期
			beginDate = f.parse(beginDateStr);
			endDate = f.parse(endDateStr);

			// 设置日历
			beginGC = new GregorianCalendar();
			beginGC.setTime(beginDate);

			endGC = new GregorianCalendar();
			endGC.setTime(endDate);

			// 直到两个时间相同
			while (beginGC.getTime().compareTo(endGC.getTime()) <= 0) {
				sRet = beginGC.get(Calendar.YEAR) + "-" + (beginGC.get(Calendar.MONTH) + 1);
				list.add(sRet);
				// 以月为单位，增加时间
				beginGC.add(Calendar.MONTH, 1);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解析两个日期段之间的所有日期
	 * 
	 * @param beginDateStr 开始日期 ，至少精确到yyyy-MM-dd
	 * @param endDateStr   结束日期 ，至少精确到yyyy-MM-dd
	 * @return yyyy-MM-dd日期集合
	 */
	public List<String> getDayListOfDate(String beginDateStr, String endDateStr) {
		// 指定要解析的时间格式
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

		// 定义一些变量
		Date beginDate = null;
		Date endDate = null;

		Calendar beginGC = null;
		Calendar endGC = null;
		List<String> list = new ArrayList<String>();

		try {
			// 将字符串parse成日期
			beginDate = f.parse(beginDateStr);
			endDate = f.parse(endDateStr);

			// 设置日历
			beginGC = Calendar.getInstance();
			beginGC.setTime(beginDate);

			endGC = Calendar.getInstance();
			endGC.setTime(endDate);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			// 直到两个时间相同
			while (beginGC.getTime().compareTo(endGC.getTime()) <= 0) {

				list.add(sdf.format(beginGC.getTime()));
				// 以日为单位，增加时间
				beginGC.add(Calendar.DAY_OF_MONTH, 1);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取当下年份指定前后数量的年份集合
	 * 
	 * @param before 当下年份前年数
	 * @param behind 当下年份后年数
	 * @return 集合
	 */
	public List<Integer> getYearListOfYears(int before, int behind) {
		if (before < 0 || behind < 0) {
			return null;
		}
		List<Integer> list = new ArrayList<Integer>();
		Calendar c = null;
		c = Calendar.getInstance();
		c.setTime(new Date());
		int currYear = Calendar.getInstance().get(Calendar.YEAR);

		int startYear = currYear - before;
		int endYear = currYear + behind;
		for (int i = startYear; i < endYear; i++) {
			list.add(Integer.valueOf(i));
		}
		return list;
	}

	/**
	 * 获取当前日期是一年中第几周
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getWeekthOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取某一年各星期的始终时间 实例：getWeekList(2016)，第52周(从2016-12-26至2017-01-01)
	 * 
	 * @param
	 * @return
	 */
	public HashMap<Integer, String> getWeekTimeOfYear(int year) {
		HashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		int count = getWeekthOfYear(c.getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dayOfWeekStart = "";
		String dayOfWeekEnd = "";
		for (int i = 1; i <= count; i++) {
			dayOfWeekStart = sdf.format(getFirstDayOfWeek(year, i));
			dayOfWeekEnd = sdf.format(getLastDayOfWeek(year, i));
			map.put(Integer.valueOf(i), "第" + i + "周(从" + dayOfWeekStart + "至" + dayOfWeekEnd + ")");
		}
		return map;

	}

	/**
	 * 获取某一年的总周数
	 * 
	 * @param year
	 * @return
	 */
	public Integer getWeekCountOfYear(int year) {
		Calendar c = Calendar.getInstance();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		int count = getWeekthOfYear(c.getTime());
		return count;
	}

	/**
	 * 获取指定日期所在周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/**
	 * 获取指定日期所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	/**
	 * 获取某年某周的第一天
	 * 
	 * @param year 目标年份
	 * @param week 目标周数
	 * @return
	 */
	public Date getFirstDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	 * 获取某年某周的最后一天
	 * 
	 * @param year 目标年份
	 * @param week 目标周数
	 * @return
	 */
	public Date getLastDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());
	}

	/**
	 * 获取某年某月的第一天
	 * 
	 * @param year  目标年份
	 * @param month 目标月份
	 * @return
	 */
	public static Date getFirstDayOfMonth(int year, int month) {
		month = month - 1;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);

		int day = c.getActualMinimum(c.DAY_OF_MONTH);

		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param year  目标年份
	 * @param month 目标月份
	 * @return
	 */
	public static Date getLastDayOfMonth(int year, int month) {
		month = month - 1;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		int day = c.getActualMaximum(c.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 获取某个日期为星期几
	 * 
	 * @param date
	 * @return String "星期*"
	 */
	public static String getDayWeekOfDate1(Date date) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * 获得指定日期的星期几数
	 * 
	 * @param date
	 * @return int
	 */
	public static Integer getDayWeekOfDate2(Date date) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(date);
		int weekDay = aCalendar.get(Calendar.DAY_OF_WEEK);
		return weekDay;
	}

	/**
	 * 验证字符串是否为日期
	 * 验证格式:YYYYMMDD、YYYY_MM_DD、YYYYMMDDHHMISS、YYYYMMDD_HH_MI、YYYY_MM_DD_HH_MI、YYYYMMDDHHMISSSSS、YYYY_MM_DD_HH_MI_SS
	 * 
	 * @param strTime
	 * @return null时返回false;true为日期，false不为日期
	 */
	public boolean validateIsDate(String strTime) {
		if (strTime == null || strTime.trim().length() <= 0)
			return false;

		Date date = null;
		List<String> list = new ArrayList<String>(0);

		list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
		list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS);
		list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI);
		list.add(DATE_TIME_FORMAT_YYYYMMDD_HH_MI);
		list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISS);
		list.add(DATE_FORMAT_YYYY_MM_DD);
		// list.add(DATE_FORMAT_YY_MM_DD);
		list.add(DATE_FORMAT_YYYYMMDD);
		// list.add(DATE_FORMAT_YYYY_MM);
		// list.add(DATE_FORMAT_YYYYMM);
		// list.add(DATE_FORMAT_YYYY);

		for (Iterator iter = list.iterator(); iter.hasNext();) {
			String format = (String) iter.next();
			if (strTime.indexOf("-") > 0 && format.indexOf("-") < 0)
				continue;
			if (strTime.indexOf("-") < 0 && format.indexOf("-") > 0)
				continue;
			if (strTime.length() > format.length())
				continue;
			date = parseStrToDate(strTime.trim(), format);
			if (date != null)
				break;
		}

		if (date != null) {
			System.out.println("生成的日期:"
					+ new DateUtil().parseDateToStr(date, DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS, "--null--"));
			return true;
		}
		return false;
	}

	/**
	 * 将指定日期的时分秒格式为零
	 * 
	 * @param date
	 * @return
	 */
	public static Date formatHhMmSsOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得指定时间加减参数后的日期(不计算则输入0)
	 * 
	 * @param date        指定日期
	 * @param year        年数，可正可负
	 * @param month       月数，可正可负
	 * @param day         天数，可正可负
	 * @param hour        小时数，可正可负
	 * @param minute      分钟数，可正可负
	 * @param second      秒数，可正可负
	 * @param millisecond 毫秒数，可正可负
	 * @return 计算后的日期
	 */
	public static Date addDate(Date date, int year, int month, int day, int hour, int minute, int second,
			int millisecond) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, year);// 加减年数
		c.add(Calendar.MONTH, month);// 加减月数
		c.add(Calendar.DATE, day);// 加减天数
		c.add(Calendar.HOUR, hour);// 加减小时数
		c.add(Calendar.MINUTE, minute);// 加减分钟数
		c.add(Calendar.SECOND, second);// 加减秒
		c.add(Calendar.MILLISECOND, millisecond);// 加减毫秒数

		return c.getTime();
	}

	/**
	 * 获得两个日期的时间戳之差
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Long getDistanceTimestamp(Date startDate, Date endDate) {
		long daysBetween = (endDate.getTime() - startDate.getTime() + 1000000) / (3600 * 24 * 1000);
		return daysBetween;
	}

	/**
	 * 判断二个时间是否为同年同月
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Boolean compareIsSameMonth(Date date1, Date date2) {
		boolean flag = false;
		int year1 = getYear(date1);
		int year2 = getYear(date2);
		if (year1 == year2) {
			int month1 = getMonth(date1);
			int month2 = getMonth(date2);
			if (month1 == month2)
				flag = true;
		}
		return flag;
	}

	/**
	 * 获得两个时间相差距离多少天多少小时多少分多少秒
	 * 
	 * @param one 时间参数 1 格式：1990-01-01 12:00:00
	 * @param two 时间参数 2 格式：2009-01-01 12:00:00
	 * @return long[] 返回值为：{天, 时, 分, 秒}
	 */
	public static long[] getDistanceTime(Date one, Date two) {
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {

			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long[] times = { day, hour, min, sec };
		return times;
	}

	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * 
	 * @param str1 时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2 时间参数 2 格式：2009-01-01 12:00:00
	 * @return String 返回值为：{天, 时, 分, 秒}
	 */
	public long[] getDistanceTime(String str1, String str2) {
		DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long[] times = { day, hour, min, sec };
		return times;
	}

	/**
	 * 两个时间之间相差距离多少天
	 * 
	 * @param str1 时间参数 1：
	 * @param str2 时间参数 2：
	 * @return 相差天数
	 */
	public static Long getDistanceDays(String str1, String str2) throws Exception {
		DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
		Date one;
		Date two;
		long days = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			days = diff / (1000 * 60 * 60 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}

	/**
	 * 获取指定时间的那天 00:00:00.000 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayBeginTime(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取指定时间的那天 23:59:59.999 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayEndTime(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 当前日期后几天
	 * @return
	 */
	public static String currentDateAfter(int days){
		//同理，3天后，取正值即可：
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
		calendar.add(Calendar.DATE, days);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 当前日期后几个月
	 * @return
	 */
	public static String currentDateAfterMonths(int months){
		//同理，3天后，取正值即可：
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
		calendar.add(Calendar.MONTH, months);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 当前日期后几天, 精确到时分秒
	 * @return
	 */
	public static Date currentDateAfterDays(int days){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	/**
	 * 当前日期日期前几天
	 * @return
	 */
	public static String currentDateBefore(int days){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
		calendar.add(Calendar.DATE, -days);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 当前日期前几个月  yyyy-MM-dd
	 * @return
	 */
	public static String currentDateBeforeMonths(int months){
		//同理，3天后，取正值即可：
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
		calendar.add(Calendar.MONTH, -months);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 当前日期前一个月 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date currentDateBeforeMonth(int months){
		//同理，3天后，取正值即可：
		Calendar calendar = Calendar.getInstance();
//		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
		calendar.add(Calendar.MONTH, -months);
		return calendar.getTime();
	}

	/**
	 * 某个日期后几个月
	 * @return
	 */
	public static String dateAfterMonths(String date, int months){
		//同理
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
		calendar.setTime(parseStrToDate(date, DATE_FORMAT_YYYY_MM_DD));
		calendar.add(Calendar.MONTH, months);
		return sdf.format(calendar.getTime());
	}

    /**
     * 某个日期后1个月
     * @return
     */
    public static Long dateAfterMonth(Long targetTime, int months){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(targetTime*1000));
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime().getTime()/1000;
    }

	/**
	 * 某个日期前后日期
	 * @return
	 */
	public static String dateBeforeAfter(String date, int days){
		//同理
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
		calendar.setTime(parseStrToDate(date, DATE_FORMAT_YYYY_MM_DD));
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 获取时间范围 day与month 二选一
	 * 
	 * @param date  时间
	 * @param day   正数：加几天。 负数：减几天 如果当天就设为0
	 * @param month 正数：加几个月， 负数：减几个月
	 * @return startTime 开始时间 ，closeTime 结束时间
	 */
	public static Map<String, String> getTimeScope(Date date, Integer day, Integer month) {
		
		if(StringUtils.isEmpty(date)) {
			return null;
		}
		
		Map<String, String> map = new HashMap<String, String>();
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		Date startTime=date, closeTime=date;

		if (day != null) {

			if (day != 0) {
				c.add(Calendar.DATE, day);// 加减天数
			}
			startTime = day < 0 ? c.getTime() : date;
			closeTime = day <= 0 ? date : c.getTime();

		} else if (month != null) {
			c.add(Calendar.MONTH, month);// 加减月数
			startTime = month < 0 ? c.getTime() : date;
			closeTime = month <= 0 ? date : c.getTime();

		}
		//map.put("startTime", dateFormat.format(startTime) + " 00:00:01");
		//map.put("closeTime", dateFormat.format(closeTime) + " 23:59:59");
		map.put("startTime", dateFormat.format(startTime));
		map.put("closeTime", dateFormat.format(closeTime));

		return map;
	}

	/**
	 * 获取两个日期之间的所有日期
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static List<String> getDays(String startTime, String endTime) {
		// 返回的日期集合
		List<String> days = new CopyOnWriteArrayList<String>();
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
		try {
			Date start = dateFormat.parse(startTime);
			Date end = dateFormat.parse(endTime);

			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(start);

			Calendar tempEnd = Calendar.getInstance();
			tempEnd.setTime(end);
			tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
			while (tempStart.before(tempEnd)) {
				days.add(dateFormat.format(tempStart.getTime()));
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}
	public static int getDayOfMonth(){
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		int day=aCalendar.getActualMaximum(Calendar.DATE);
		return day;
	}
	public static  Date getDateAdd(int days){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -days);
		return c.getTime();
	}
	public static  List<String> getDaysBetwwen(int days){
		List<String> dayss = new ArrayList<>();
		Calendar start = Calendar.getInstance();
		start.setTime(getDateAdd(days));
		Long startTIme = start.getTimeInMillis();
		Calendar end = Calendar.getInstance();
		end.setTime(new Date());
		Long endTime = end.getTimeInMillis();
		Long oneDay = 1000 * 60 * 60 * 24L;
		Long time = startTIme;
		while (time <= endTime) {
			Date d = new Date(time);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			dayss.add(df.format(d));
			time += oneDay;
		}
		return dayss;
	}
	public static String getYesterdayByCalendar(int dd){
		Calendar calendar = Calendar.getInstance();
		System.out.println(Calendar.DATE);
		calendar.add(Calendar.DATE,dd);
		Date time = calendar.getTime();
		String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(time);
		return yesterday;
	}

    /**
     * 获取日期 时:分 HH:mm
     * @param hour
     * @param minutes
     * @return
     */
	public static Date getHourWithMinutes(Integer hour, Integer minutes){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

	/**
	 * 获取指定日期的后一天
	 * @param date
	 * @return
	 */
	public static Long getOneDayAfter(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DATE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		cal.set(Calendar.DATE, day+1);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return cal.getTime().getTime();
	}

	/**
	 * 获取指定日期的起始时间
	 * @param dayLimit
	 * @return
	 */
	public static Map<String, String> somedayStartEnd(Integer dayLimit){
		Map<String, String> yesterdayMap = new HashMap<String, String>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, dayLimit);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		Date start = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.SECOND, -1);

		Date end = calendar.getTime();
		yesterdayMap.put("start",parseDateToStr(start, DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
		yesterdayMap.put("end", parseDateToStr(end, DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
		return yesterdayMap;
    }


		/**
      * 判断时间是否在时间段内
      * 
      * @param nowTime
      * @param beginTime
      * @param endTime
      * @return
      */
	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);
		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		if (date.after(begin) && date.before(end)) {
			return true;
		} else if (nowTime.compareTo(beginTime) == 0 || nowTime.compareTo(endTime) == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static Integer belongAMPM(){
		//当前时间 1 上午（8：00-12：00） 2下午（12：30-17：30） 其他时间为0
		Integer timeStatus = 0;
		Date date = new Date();
		//小时
		SimpleDateFormat hdf = new SimpleDateFormat("HH");
		//分
		SimpleDateFormat mdf = new SimpleDateFormat("mm");
		String hstr = hdf.format(date);
		String mstr = mdf.format(date);
		int a = Integer.parseInt(hstr);
		int b = Integer.parseInt(mstr);
		//上午：8：00-12：00  下午 12：30-17：30
		if (a >= 8 && a <= 12) {
			timeStatus = 1;
		}
		//下午
		if (a > 12 && a <= 17){
			if(a == 17){
				if(b <= 30){
					timeStatus = 2;
				}
			}else if(a < 17){
				timeStatus = 2;
			}
		}
		return timeStatus;
	}

	/**
	 * 获取当前月的第一天和最后一天
	 * @return
	 */
	public static Map<String, String> getCurrentMonthStartEndTime(){
		Map<String, String> monthMap = new HashMap<String, String>();
		Date date = new Date();
		String yearTmp = parseDateToStr(date, DATE_FORMAT_YYYY);
		String monthTmp = parseDateToStr(date, DATE_FORMAT_MM);
		monthTmp = monthTmp.startsWith("0")?monthTmp.split("")[1]:monthTmp;
		Integer year = Integer.parseInt(yearTmp);
		Integer month = Integer.parseInt(monthTmp);
		String firstDay = parseDateToStr(getFirstDayOfMonth(year, month), DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
		String lastDay = parseDateToStr(getLastDayOfMonth(year, month), DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
		monthMap.put("firstDay", firstDay);
		monthMap.put("lastDay", lastDay);
		return monthMap;
	}

	/**
	 * 获取指定日期的下个月第一天
	 * @return
	 */
	public static Long getNextMonthFirstDay(Date date){
		String yearTmp = parseDateToStr(date, DATE_FORMAT_YYYY);
		String monthTmp = parseDateToStr(date, DATE_FORMAT_MM);
		monthTmp = monthTmp.startsWith("0")?monthTmp.split("")[1]:monthTmp;
		Integer year = Integer.parseInt(yearTmp);
		Integer month = Integer.parseInt(monthTmp)+1;
		return getFirstDayOfMonth(year, month).getTime();
	}

	public static Long getNow2EndTime(Date now, Date endDate){
		DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        Date nowDate = null;
        Date finishDate = null;
        try {
            nowDate = df.parse(df.format(now));
            finishDate = df.parse(df.format(endDate));
        } catch (ParseException e) {
            throw new MissRequiredParamException(e.getMessage());
        }
        return finishDate.getTime()/1000 - nowDate.getTime()/1000;
	}

    /**
     * 获取往后两天8点到现在的秒值
     * @return
     */
    public static Long getDistanceBetweenDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        return calendar.getTime().getTime()/1000 - new Date().getTime()/1000;
    }

    /**
     * 2天后的8点
     * @return
     */
    public static Date daysAfter8Clock(int days){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

	
	public static void main(String[] args) {
		System.out.println(parseDateToStr(daysAfter8Clock(2), DATE_TIME_FORMAT_HH_MM_SS_MM_DD_YYYY));
//		System.out.println(somedayStartEnd(0));
//		System.out.println(currentDateBefore(1));
//		Calendar instance = Calendar.getInstance();
//		instance.set(Calendar.YEAR, 2019);
//		instance.set(Calendar.MONTH, 11);
//		instance.set(Calendar.DAY_OF_MONTH, 8);
//		System.out.println(instance.getTime().getTime());
//		System.out.println(getNextMonthFirstDay(new Date()));
//		System.out.println(getCurrentMonthStartEndTime());
//        System.out.println(dateAfterMonth(1574404629l, 1));
//		System.out.println(getNow2EndTime(new Date(), new Date()));
//		System.out.println(currentDateBeforeMonth(1));
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.HOUR_OF_DAY, 8);
//		cal.set(Calendar.MINUTE, 0);
//        Date time = cal.getTime();
//
//
//        String s = parseDateToStr(time, HH_MI);
//        System.out.println(s);
//		System.out.println(getDistance(DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD), "2019-08-22"));
//		System.out.println(getDistance("2019-08-22", "2019-08-24"));
//		System.out.println(getDays("2019-08-23","2019-09-02 15:45:57"));
//		System.out.println(belongAMPM());
//		Date date = new Date();
//		String yearTmp = parseDateToStr(date, DATE_FORMAT_YYYY);
//		String monthTmp = parseDateToStr(date, DATE_FORMAT_MM);
//		monthTmp = monthTmp.startsWith("0")?monthTmp.split("")[1]:monthTmp;
//		Integer year = Integer.parseInt(yearTmp);
//		Integer month = Integer.parseInt(monthTmp);
//		System.out.println(parseDateToStr(getFirstDayOfMonth(year, month), DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
//		System.out.println(parseDateToStr(getLastDayOfMonth(year, month), DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
//		Date effectiveDate = parseStrToDate("2019-08-09 22:45:30", DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
//		Date invalidDate = parseStrToDate("2019-08-20 20:35:12", DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
//		System.out.println(belongCalendar(new Date(), effectiveDate, invalidDate));
//		System.out.println(getTimeScope(new Date(), null, -1));
	}

}