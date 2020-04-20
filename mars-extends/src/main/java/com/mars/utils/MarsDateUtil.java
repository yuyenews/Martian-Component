package com.mars.utils;

import com.mars.utils.enums.DateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期格式处理
 *
 * @author yuye
 */
public class MarsDateUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(MarsDateUtil.class);// 日志

	/**
	 * 完整时间 yyyy-MM-dd HH:mm:ss
	 */
	public static final String SIMPLE = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 年月日 yyyy-MM-dd
	 */
	public static final String DT_SIMPLE = "yyyy-MM-dd";

	/**
	 * 年月日 时yyyy-MM-dd HH
	 */
	public static final String DT_SIMPLE_HH = "yyyy-MM-dd HH";

	/**
	 * 年月日(中文) yyyy年MM月dd日
	 */
	public static final String DT_SIMPLE_CHINESE = "yyyy年MM月dd日";

	/**
	 * The constant WEEK.
	 */
	public static final String WEEK = "EEEE";

	/**
	 * 年月日(无下划线) yyyyMMdd
	 */
	public static final String DT_SHORT = "yyyyMMdd";

	/**
	 * 年月日时分(无下划线) yyyyMMddHHmm
	 */
	public static final String DT_MIDDLE = "yyyyMMddHHmm";

	/**
	 * 年月日时分秒(无下划线) yyyyMMddHHmmss
	 */
	public static final String DT_LONG = "yyyyMMddHHmmss";

	/**
	 * 时分秒 HH:mm:ss
	 */
	public static final String HMS_FORMAT = "HH:mm:ss";

	/**
	 * 时分 HH:mm
	 */
	public static final String HM_FORMAT = "HHmm";

	/**
	 * 年-月-日 小时:分钟 yyyy-MM-dd HH:mm
	 */
	public static final String SIMPLE_FORMAT = "yyyy-MM-dd HH:mm";

	/**
	 * 年月日(中文) yyyy年MM月dd日 HH:mm
	 */
	public static final String DT_SIMPLE_CHINESE_WITH_TIME = "yyyy年MM月dd日 HH:mm";
	/**
	 * 2017.02.30 
	 */
	public static final String COMMENT_WITH_TIME = "yyyy.MM.dd HH:mm:ss";

	/**
	 * 获取格式
	 *
	 * @param format
	 *            the format
	 * @return format format
	 */
	public static DateFormat getFormat(String format) {
		return new SimpleDateFormat(format);
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 *
	 * @param date
	 *            the date
	 * @return string string
	 */
	public static String simpleFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat(SIMPLE).format(date);
	}
	
	/**
	 * yyyy.MM.dd HH:mm:ss
	 *
	 * @param date
	 *            the date
	 * @return string string
	 */
	public static String simpleCommentFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat(COMMENT_WITH_TIME).format(date);
	}

	/**
	 * yyyy-MM-dd
	 *
	 * @param date
	 *            the date
	 * @return string string
	 */
	public static String dtSimpleFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat(DT_SIMPLE).format(date);
	}

	/**
	 * yyyy-MM-dd
	 *
	 * @param date
	 *            the date
	 * @param style
	 *            the style
	 * @return string string
	 */
	public static String dtFormat(Date date, String style) {
		if (date == null) {
			return "";
		}
		return getFormat(style).format(date);
	}

	/**
	 * <pre>
	 * yyyy-MM-dd HH
	 * </pre>
	 *
	 * @param date
	 *            the date
	 * @return hm string
	 * @throws ParseException
	 *             the parse exception
	 */
	public static String dtSimpleFormatHH(Date date) throws ParseException {
		if (date == null) {
			return null;
		}
		return getFormat(DT_SIMPLE_HH).format(date);
	}

	/**
	 * yyyyMMdd
	 *
	 * @param date
	 *            the date
	 * @return string string
	 */
	public static String dtShortSimpleFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat(DT_SHORT).format(date);
	}

	/**
	 * <pre>
	 * yyyyMMddHHmm
	 * </pre>
	 *
	 * @param date
	 *            the date
	 * @return string string
	 */
	public static String dtMiddleSimpleFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat(DT_MIDDLE).format(date);
	}

	/**
	 * <pre>
	 * yyyyMMddHHmmss
	 * </pre>
	 *
	 * @param date
	 *            the date
	 * @return string string
	 */
	public static String dtLongSimpleFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat(DT_LONG).format(date);
	}

	/**
	 * Dt from short to simple str string.
	 *
	 * @param strDate
	 *            yyyyMMdd
	 * @return yyyy -MM-dd
	 */
	public static String dtFromShortToSimpleStr(String strDate) {
		if (null != strDate) {
			Date date;
			try {
				date = shortstring2Date(strDate);
			} catch (ParseException e) {
				date = null;
			}
			if (null != date) {
				return dtSimpleFormat(date);
			}
		}
		return "";
	}

	/**
	 * yyyy-mm-dd 日期格式转换为日期
	 *
	 * @param strDate
	 *            the str date
	 * @param format
	 *            the format
	 * @return date date
	 */
	public static Date strToDateFormat(String strDate, String format) {
		if (strDate == null || strDate.trim().equals("")) {
			return null;
		}

		try {
			return new SimpleDateFormat(format).parse(strDate);
		} catch (Exception e) {
			LOGGER.error("Date conversion exception ", e);
		}

		return null;
	}

	/**
	 * 获取输入日期的相差日期
	 *
	 * @param dt
	 *            the dt
	 * @param idiff
	 *            the idiff
	 * @return diff date
	 */
	public static String getDiffDate(Date dt, int idiff) {
		Calendar c = Calendar.getInstance();

		c.setTime(dt);
		c.add(Calendar.DATE, idiff);
		return dtSimpleFormat(c.getTime());
	}

	/**
	 * 获取输入日期的相差日期
	 *
	 * @param dt
	 *            the dt
	 * @param idiff
	 *            the idiff
	 * @return diff date dt short
	 */
	public static String getDiffDateDtShort(Date dt, int idiff) {
		Calendar c = Calendar.getInstance();

		c.setTime(dt);
		c.add(Calendar.DATE, idiff);
		return dtShortSimpleFormat(c.getTime());
	}

	/**
	 * 获取输入日期月份的相差日期
	 *
	 * @param dt
	 *            the dt
	 * @param idiff
	 *            the idiff
	 * @return diff mon
	 */
	public static String getDiffMon(Date dt, int idiff) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.MONTH, idiff);
		return dtSimpleFormat(c.getTime());
	}

	/**
	 * yyyy年MM月dd日
	 *
	 * @param date
	 *            the date
	 * @return string string
	 */
	public static String dtSimpleChineseFormat(Date date) {
		if (date == null) {
			return "";
		}

		return getFormat(DT_SIMPLE_CHINESE).format(date);
	}

	/**
	 * yyyy-MM-dd到 yyyy年MM月dd日 转换
	 *
	 * @param date
	 *            the date
	 * @return string string
	 * @throws ParseException
	 *             the parse exception
	 */
	public static String dtSimpleChineseFormatStr(String date) throws ParseException {
		if (date == null) {
			return "";
		}

		return getFormat(DT_SIMPLE_CHINESE).format(string2Date(date));
	}

	/**
	 * yyyy-MM-dd 日期字符转换为时间
	 *
	 * @param stringDate
	 *            the string date
	 * @return date date
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date string2Date(String stringDate) throws ParseException {
		if (stringDate == null) {
			return null;
		}

		return getFormat(DT_SIMPLE).parse(stringDate);
	}

	/**
	 * String 2 date hh date.
	 *
	 * @param stringDate
	 *            the string date
	 * @return the date
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date string2DateHH(String stringDate) throws ParseException {
		if (stringDate == null) {
			return null;
		}
		return getFormat(DT_SIMPLE_HH).parse(stringDate);

	}

	/**
	 * 返回日期时间（Add by Sunzy）
	 *
	 * @param stringDate
	 *            "yyyy-MM-dd HH:mm:ss"
	 * @return date date
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date string2DateTime(String stringDate) throws ParseException {
		if (stringDate == null) {
			return null;
		}

		return getFormat(SIMPLE).parse(stringDate);
	}

	/**
	 * <pre>
	 * 时间转化为日期
	 * </pre>
	 *
	 * @param date
	 *            the date
	 * @return hm string
	 * @throws ParseException
	 *             the parse exception
	 */
	public static String getHmString(Date date) throws ParseException {
		if (date == null) {
			return null;
		}
		return getFormat(HM_FORMAT).format(date);
	}

	/**
	 * 年月日(中文) yyyy年MM月dd日 HH:mm
	 *
	 * @param date
	 *            the date
	 * @return string string
	 */
	public static String dtSimpleChineseFormatWithTime(Date date) {
		if (date == null) {
			return "";
		}

		return getFormat(DT_SIMPLE_CHINESE_WITH_TIME).format(date);
	}

	/**
	 * 返回日期时间（Add by Sunzy）
	 *
	 * @param sDate
	 *            the s date
	 * @return date date
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date string2DateTimeByAutoZero(String sDate) throws ParseException {
		String stringDate = sDate;
		if (stringDate == null) {
			return null;
		}
		if (stringDate.length() == 11) {
			stringDate = stringDate + "00:00:00";
		} else if (stringDate.length() == 13) {
			stringDate = stringDate + ":00:00";
		} else if (stringDate.length() == 16) {
			stringDate = stringDate + ":00";
		} else if (stringDate.length() == 10) {
			stringDate = stringDate + " 00:00:00";
		}

		return getFormat(SIMPLE).parse(stringDate);
	}

	/**
	 * 返回日期时间（Add by wangjl）(时分秒：23:59:59)
	 *
	 * @param sDate
	 *            the s date
	 * @return date date
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date string2DateTimeBy23(String sDate) throws ParseException {
		String stringDate = sDate;
		if (stringDate == null) {
			return null;
		}
		if (stringDate.length() == 11) {
			stringDate = stringDate + "23:59:59";
		} else if (stringDate.length() == 13) {
			stringDate = stringDate + ":59:59";
		} else if (stringDate.length() == 16) {
			stringDate = stringDate + ":59";
		} else if (stringDate.length() == 10) {
			stringDate = stringDate + " 23:59:59";
		}

		return getFormat(SIMPLE).parse(stringDate);
	}

	/**
	 * 计算日期差值
	 *
	 * @param beforDate
	 *            the befor date
	 * @param afterDate
	 *            the after date
	 * @return int （天数）
	 * @throws ParseException
	 *             the parse exception
	 */
	public static int calculateDecreaseDate(String beforDate, String afterDate) throws ParseException {
		Date date1 = getFormat(DT_SIMPLE).parse(beforDate);
		Date date2 = getFormat(DT_SIMPLE).parse(afterDate);
		long decrease = getDateBetween(date1, date2) / 1000 / 3600 / 24;
		return (int) decrease;
	}

	/**
	 * 计算时间差
	 *
	 * @param dBefor
	 *            首日
	 * @param dAfter
	 *            尾日
	 * @return 时间差(毫秒) date between
	 */
	public static long getDateBetween(Date dBefor, Date dAfter) {
		long lBefor;
		long lAfter;
		long lRtn;

		/** 取得距离 1970年1月1日 00:00:00 GMT 的毫秒数 */
		lBefor = dBefor.getTime();
		lAfter = dAfter.getTime();

		lRtn = lAfter - lBefor;

		return lRtn;
	}

	/**
	 * 当前时间与参数时间差
	 *
	 * @param dateBefore
	 *            the date before
	 * @return 时间差(分) date between now
	 */
	public static int getDateBetweenNow(Date dateBefore) {
		if (dateBefore == null) {
			return 0;
		}
		return (int) (getDateBetween(dateBefore, new Date()) / 1000 / 60);
	}

	/**
	 * 返回日期时间（Add by Gonglei）
	 *
	 * @param stringDate
	 *            (yyyyMMdd)
	 * @return date date
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date shortstring2Date(String stringDate) throws ParseException {
		if (stringDate == null) {
			return null;
		}

		return getFormat(DT_SHORT).parse(stringDate);
	}

	/**
	 * 返回短日期格式（yyyyMMdd格式）
	 *
	 * @param date
	 *            the date
	 * @return string string
	 */
	public static String shortDate(Date date) {
		if (date == null) {
			return null;
		}

		return getFormat(DT_SHORT).format(date);
	}

	/**
	 * 返回长日期格式（yyyyMMddHHmmss格式）
	 *
	 * @param date
	 *            the date
	 * @return string string
	 */
	public static String longDate(Date date) {
		if (date == null) {
			return null;
		}

		return getFormat(DT_LONG).format(date);
	}

	/**
	 * yyyy-MM-dd 日期字符转换为长整形
	 *
	 * @param stringDate
	 *            the string date
	 * @return long long
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Long string2DateLong(String stringDate) throws ParseException {
		Date d = string2Date(stringDate);

		if (d == null) {
			return null;
		}

		return new Long(d.getTime());
	}

	/**
	 * 日期转换为字符串 HH:mm:ss
	 *
	 * @param date
	 *            the date
	 * @return string string
	 */
	public static String hmsFormat(Date date) {
		if (date == null) {
			return "";
		}

		return getFormat(HMS_FORMAT).format(date);
	}

	/**
	 * 时间转换字符串 2005-06-30 15:50
	 *
	 * @param date
	 *            the date
	 * @return string string
	 */
	public static String simpleDate(Date date) {
		if (date == null) {
			return "";
		}

		return getFormat(SIMPLE_FORMAT).format(date);
	}

	/**
	 * 字符串 2005-06-30 15:50 转换成时间
	 *
	 * @param dateString
	 *            the date string
	 * @return date date
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date simpleFormatDate(String dateString) throws ParseException {
		if (dateString == null) {
			return null;
		}
		return getFormat(SIMPLE_FORMAT).parse(dateString);
	}

	/**
	 * 获取当前日期的日期差
	 *
	 * @param diff thediff
	 * @return diff date
	 */
	public static String getDiffDate(int diff) {
		Calendar c = Calendar.getInstance();

		c.setTime(new Date());
		c.add(Calendar.DATE, diff);
		return dtSimpleFormat(c.getTime());
	}

	/**
	 * Gets diff date time.
	 *
	 * @param diff
	 *            the diff
	 * @return the diff date time
	 */
	public static Date getDiffDateTime(int diff) {
		Calendar c = Calendar.getInstance();

		c.setTime(new Date());
		c.add(Calendar.DATE, diff);
		return c.getTime();
	}

	/**
	 * 获取当前日期的日期时间差
	 *
	 * @param diff
	 *            the diff
	 * @param hours
	 *            the hours
	 * @return diff date time
	 */
	public static String getDiffDateTime(int diff, int hours) {
		Calendar c = Calendar.getInstance();

		c.setTime(new Date());
		c.add(Calendar.DATE, diff);
		c.add(Calendar.HOUR, hours);
		return dtSimpleFormat(c.getTime());
	}

	/**
	 * 计算当前时间几小时之后的时间
	 *
	 * @param date
	 *            the date
	 * @param hours
	 *            the hours
	 * @return date date
	 */
	public static Date addHours(Date date, long hours) {
		return addMinutes(date, hours * 60);
	}

	/**
	 * 计算当前时间几分钟之后的时间
	 *
	 * @param date
	 *            the date
	 * @param minutes
	 *            the minutes
	 * @return date date
	 */
	public static Date addMinutes(Date date, long minutes) {
		return addSeconds(date, minutes * 60);
	}

	/**
	 * Add seconds date.
	 *
	 * @param date1
	 *            the date 1
	 * @param secs
	 *            the secs
	 * @return date date
	 */
	public static Date addSeconds(Date date1, long secs) {
		return new Date(date1.getTime() + (secs * 1000));
	}

	/**
	 * 把日期类型的日期换成数字类型 YYYYMMDD类型
	 *
	 * @param date
	 *            the date
	 * @return long long
	 */
	public static Long dateToNumber(Date date) {
		if (date == null) {
			return null;
		}

		Calendar c = Calendar.getInstance();

		c.setTime(date);

		String month;
		String day;

		if ((c.get(Calendar.MONTH) + 1) >= 10) {
			month = String.valueOf(c.get(Calendar.MONTH) + 1);
		} else {
			month = "0" + (c.get(Calendar.MONTH) + 1);
		}

		if (c.get(Calendar.DATE) >= 10) {
			day = String.valueOf(c.get(Calendar.DATE));
		} else {
			day = "0" + c.get(Calendar.DATE);
		}

		String number = String.valueOf(c.get(Calendar.YEAR)) + month + day;

		return new Long(number);
	}

	/**
	 * 获取下月
	 *
	 * @param stringDate
	 *            the string date
	 * @return next mon
	 * @throws ParseException
	 *             the parse exception
	 */
	public static String getNextMon(String stringDate) throws ParseException {
		Date tempDate = MarsDateUtil.shortstring2Date(stringDate);
		Calendar cad = Calendar.getInstance();

		cad.setTime(tempDate);
		cad.add(Calendar.MONTH, 1);
		return MarsDateUtil.shortDate(cad.getTime());
	}

	/**
	 * add by daizhixia 20050808 获取下一天
	 *
	 * @param stringDate
	 *            the string date
	 * @return next day
	 * @throws ParseException
	 *             the parse exception
	 */
	public static String getNextDay(String stringDate) throws ParseException {
		Date tempDate = MarsDateUtil.string2Date(stringDate);
		Calendar cad = Calendar.getInstance();

		cad.setTime(tempDate);
		cad.add(Calendar.DATE, 1);
		return MarsDateUtil.dtSimpleFormat(cad.getTime());
	}

	/**
	 * Gets web next day string.
	 *
	 * @return the web next day string
	 */
	public static String getWebNextDayString() {
		Calendar cad = Calendar.getInstance();
		cad.setTime(new Date());
		cad.add(Calendar.DATE, 1);
		return MarsDateUtil.dtSimpleFormat(cad.getTime());
	}

	/**
	 * add by chencg 获取下一天 返回 dtSimple 格式字符
	 *
	 * @param date
	 *            the date
	 * @return next day
	 * @throws ParseException
	 *             the parse exception
	 */
	public static String getNextDay(Date date) throws ParseException {
		Calendar cad = Calendar.getInstance();
		cad.setTime(date);
		cad.add(Calendar.DATE, 1);
		return MarsDateUtil.dtSimpleFormat(cad.getTime());
	}

	/**
	 * add by chencg 获取下一天 返回 dtshort 格式字符
	 *
	 * @param stringDate
	 *            "20061106"
	 * @return String "2006-11-07"
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date getNextDayDtShort(String stringDate) throws ParseException {
		Date tempDate = MarsDateUtil.shortstring2Date(stringDate);
		Calendar cad = Calendar.getInstance();

		cad.setTime(tempDate);
		cad.add(Calendar.DATE, 1);
		return cad.getTime();
	}

	/**
	 * add by daizhixia 20050808 取得相差的天数
	 *
	 * @param startDate
	 *            格式为 2008-08-01
	 * @param endDate
	 *            格式为 2008-08-01
	 * @return long long
	 */
	public static long countDays(String startDate, String endDate) {
		Date tempDate1 = null;
		Date tempDate2 = null;
		long days = 0;

		try {
			tempDate1 = MarsDateUtil.string2Date(startDate);

			tempDate2 = MarsDateUtil.string2Date(endDate);
			days = (tempDate2.getTime() - tempDate1.getTime()) / (1000 * 60 * 60 * 24);
		} catch (ParseException e) {
			LOGGER.error("日期转换错误", e);
		}

		return days;
	}

	/**
	 * 返回日期相差天数，向下取整数
	 *
	 * @param dateStart
	 *            一般前者小于后者dateEnd
	 * @param dateEnd
	 *            the date end
	 * @return int int
	 */
	public static int countDays(Date dateStart, Date dateEnd) {
		if ((dateStart == null) || (dateEnd == null)) {
			return -1;
		}

		return (int) ((dateEnd.getTime() - dateStart.getTime()) / (1000 * 60 * 60 * 24));
	}

	/**
	 * 校验start与end相差的天数，是否满足end-start lessEqual than days
	 *
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @param days
	 *            the days
	 * @return boolean boolean
	 */
	public static boolean checkDays(Date start, Date end, int days) {
		int g = countDays(start, end);

		return g <= days;
	}

	/**
	 * Now date.
	 *
	 * @return the date
	 */
	public static Date now() {
		return new Date();
	}

	/**
	 * alahan add 20050825 获取传入时间相差的日期
	 *
	 * @param dt
	 *            传入日期，可以为空
	 * @param diff
	 *            需要获取相隔diff天的日期 如果为正则取以后的日期，否则时间往前推
	 * @return diff string date
	 */
	public static String getDiffStringDate(Date dt, int diff) {
		Calendar ca = Calendar.getInstance();

		if (dt == null) {
			ca.setTime(new Date());
		} else {
			ca.setTime(dt);
		}

		ca.add(Calendar.DATE, diff);
		return dtSimpleFormat(ca.getTime());
	}

	/**
	 * 校验输入的时间格式是否合法，但不需要校验时间一定要是8位的
	 *
	 * @param statTime
	 *            the stat time
	 * @return alahan add 20050901
	 */
	public static boolean checkTime(String statTime) {
		if (statTime.length() > 8) {
			return false;
		}

		String[] timeArray = statTime.split(":");

		if (timeArray.length != 3) {
			return false;
		}

		for (int i = 0; i < timeArray.length; i++) {
			String tmpStr = timeArray[i];

			try {
				Integer tmpInt = new Integer(tmpStr);

				if (i == 0) {
					if ((tmpInt.intValue() > 23) || (tmpInt.intValue() < 0)) {
						return false;
					} else {
						continue;
					}
				}

				if ((tmpInt.intValue() > 59) || (tmpInt.intValue() < 0)) {
					return false;
				}
			} catch (Exception e) {
				LOGGER.error("时间检查错误", e);
				return false;
			}
		}

		return true;
	}

	/**
	 * 返回日期时间（Add by Gonglei）
	 *
	 * @param stringDate
	 *            (yyyyMMdd)
	 * @return string string
	 */
	public static String stringToStringDate(String stringDate) {

		if (stringDate == null) {
			return null;
		}

		if (stringDate.length() != 8) {
			return null;
		}

		return stringDate.substring(0, 4) + stringDate.substring(4, 6) + stringDate.substring(6, 8);
	}

	/**
	 * 将字符串按format格式转换为date类型
	 *
	 * @param str
	 *            the str
	 * @param format
	 *            the format
	 * @return date date
	 */
	public static Date string2Date(String str, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 加减天数
	 *
	 * @param aDate
	 *            the a date
	 * @param days
	 *            the days
	 * @return Date date
	 * @author shencb 2006-12 add
	 */
	public static Date increaseDate(Date aDate, int days) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(aDate);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	/**
	 * 加减秒
	 *
	 * @param aDate
	 *            the a date
	 * @param seconds
	 *            the seconds
	 * @return Date date
	 * @author shencb 2006-12 add
	 */
	public static Date increaseSeconds(Date aDate, int seconds) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(aDate);
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}

	/**
	 * 是否闰年
	 *
	 * @param year
	 *            the year
	 * @return boolean boolean
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

	}

	/**
	 * 判断是否是默认工作日，一般默认工作日是星期一都星期五， 所以，这个函数本质是判断是否是星期一到星期五
	 *
	 * @param date
	 *            the date
	 * @return boolean boolean
	 */
	public static boolean isDefaultWorkingDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		return !(week == 7 || week == 1);
	}

	/**
	 * 获取星期名，如“星期一”、“星期二”
	 *
	 * @param date
	 *            the date
	 * @return week day
	 */
	public static String getWeekDay(Date date) {
		return getFormat(WEEK).format(date);
	}

	/**
	 * Parse date no time date.
	 *
	 * @param sDate
	 *            the s date
	 * @param format
	 *            the format
	 * @return the date
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date parseDateNoTime(String sDate, String format) throws ParseException {
		if (format == null || format.trim().equals("")) {
			throw new ParseException("Null format. ", 0);
		}

		DateFormat dateFormat = new SimpleDateFormat(format);

		if ((sDate == null) || (sDate.length() < format.length())) {
			throw new ParseException("length too little", 0);
		}

		return dateFormat.parse(sDate);
	}

	/**
	 * 获取当前时间的字符串格式，以半个小时为单位<br>
	 * 当前时间2007-02-02 22:23 则返回 2007-02-02 22:00 当前时间2007-02-02 22:33 则返回
	 * 2007-02-02 22:30
	 *
	 * @return now date for page select ahead
	 */
	public static String getNowDateForPageSelectAhead() {

		Calendar cal = Calendar.getInstance();
		if (cal.get(Calendar.MINUTE) < 30) {
			cal.set(Calendar.MINUTE, 0);
		} else {
			cal.set(Calendar.MINUTE, 30);
		}
		return simpleDate(cal.getTime());
	}

	/**
	 * 获取当前时间的字符串格式，以半个小时为单位<br>
	 * 当前时间2007-02-02 22:23 则返回 2007-02-02 22:30 当前时间2007-02-02 22:33 则返回
	 * 2007-02-02 23:00
	 *
	 * @return now date for page select behind
	 */
	public static String getNowDateForPageSelectBehind() {
		Calendar cal = Calendar.getInstance();
		if (cal.get(Calendar.MINUTE) < 30) {
			cal.set(Calendar.MINUTE, 30);
		} else {
			cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);
			cal.set(Calendar.MINUTE, 0);
		}
		return simpleDate(cal.getTime());
	}

	/**
	 * 把日期2007/06/14转换为20070614
	 *
	 * @param date
	 *            the date
	 * @return string string
	 */
	public static String formatDateString(String date) {
		String result = "";
		if (date == null || "".equals(date)) {
			return "";
		}
		if (date.length() == 10) {
			result = date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10);
		}
		return result;
	}

	/**
	 * Gets new format date string.
	 *
	 * @param date
	 *            the date
	 * @return the new format date string
	 */
	public static String getNewFormatDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(SIMPLE);
		return getDateString(date, dateFormat);
	}

	/**
	 * Gets date string.
	 *
	 * @param date
	 *            the date
	 * @param dateFormat
	 *            the date format
	 * @return the date string
	 */
	public static String getDateString(Date date, DateFormat dateFormat) {
		if (date == null || dateFormat == null) {
			return null;
		}

		return dateFormat.format(date);
	}

	/**
	 * 完整时间 yyyy-MM-dd HH:mm:ss
	 *
	 * @param ts
	 *            时间戳 毫秒
	 * @return string string
	 */
	public static String formatTimestamp(long ts) {
		return getFormat(SIMPLE).format(ts);
	}

	/**
	 * 判断是否过期
	 *
	 * @param date
	 *            过期时间
	 * @return boolean boolean
	 */
	public static boolean isExpire(Date date) {
		return date.getTime() <= now().getTime();
	}

	/**
	 * 获取当前天 年月日
	 *
	 * @return current day
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date getCurrentDay() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(MarsDateUtil.now());
		return sdf.parse(str);
	}

	/**
	 * 获取时间
	 *
	 * @param str
	 *            the str //3Y 3年 3M 3月 3D 3天
	 * @return the next time
	 */
	public static String getNextTime(String str) {

		Calendar Cal = Calendar.getInstance();
		String date;
		int time;

		if (null != str && !"".equals(str)) {
			String s = str.substring(0, str.length() - 1);
			time = s.equals("") ? 0 : Integer.valueOf(s);
			DateEnum de = DateEnum.getEnumByCode(str.substring(str.length() - 1, str.length()));
			switch (de) {
			case Y:
				Cal.setTime(MarsDateUtil.now());
				Cal.add(Calendar.YEAR, time);
				date = MarsDateUtil.dtSimpleFormat(Cal.getTime());
				break;
			case M:
				Cal.setTime(MarsDateUtil.now());
				Cal.add(Calendar.MONTH, time);
				date = MarsDateUtil.dtSimpleFormat(Cal.getTime());
				break;
			case D:
				Cal.setTime(MarsDateUtil.now());
				Cal.add(Calendar.DAY_OF_YEAR, time);
				date = MarsDateUtil.dtSimpleFormat(Cal.getTime());
				break;
			case H:
				Cal.setTime(MarsDateUtil.now());
				Cal.add(Calendar.HOUR_OF_DAY, time);
				date = MarsDateUtil.dtSimpleFormat(Cal.getTime());
				break;
			default:
				Cal.setTime(new Date());
				Cal.set(Calendar.HOUR_OF_DAY, 0);
				Cal.set(Calendar.MINUTE, 0);
				Cal.set(Calendar.SECOND, 0);
				Cal.set(Calendar.MILLISECOND, 0);
				Cal.add(Calendar.DAY_OF_MONTH, 1);
				date = MarsDateUtil.dtSimpleFormat(Cal.getTime());
				break;
			}
		} else {
			Cal.setTime(new Date());
			Cal.set(Calendar.HOUR_OF_DAY, 0);
			Cal.set(Calendar.MINUTE, 0);
			Cal.set(Calendar.SECOND, 0);
			Cal.set(Calendar.MILLISECOND, 0);
			Cal.add(Calendar.DAY_OF_MONTH, 1);
			date = MarsDateUtil.dtSimpleFormat(Cal.getTime());
		}
		return date;
	}

	/**
	 * 两个日期的相差天数
	 *
	 * @param date1
	 *            the date 1
	 * @param date2
	 *            the date 2
	 * @return the long
	 */
	public static long daysOfTwo(String date1, String date2) {

		Date fDate = MarsDateUtil.strToDateFormat(date1, DT_SIMPLE);
		Date oDate = MarsDateUtil.strToDateFormat(date2, DT_SIMPLE);
		return (oDate.getTime() - fDate.getTime()) / (1000 * 3600 * 24);
	}

	/**
	 * 根据出生日期获取年龄
	 *
	 * @param birthDate 参数
	 * @return 数据
	 * @throws ParseException 异常
	 */
	public static int getAge(Date birthDate) throws ParseException {
		int age = 0;
		Calendar birthCalendar = Calendar.getInstance();// 生日日期
		Calendar nowCalendar = Calendar.getInstance();// 当前日期
		birthCalendar.setTime(birthDate);
		nowCalendar.setTime(now());
		if (birthCalendar.after(nowCalendar)) {
			return -1;
		}
		age = nowCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

		if ((nowCalendar.get(Calendar.MONTH) + 1) < (birthCalendar.get(Calendar.MONTH) + 1)) {
			age -= 1;
		} else if ((nowCalendar.get(Calendar.MONTH) + 1) == (birthCalendar.get(Calendar.MONTH) + 1)) {
			if (nowCalendar.get(Calendar.DAY_OF_MONTH) < birthCalendar.get(Calendar.DAY_OF_MONTH)) {
				age -= 1;
			}
		}
		return age;
	}

	/**
	 * 根据出生日期获取年龄-总共多少月
	 *
	 * @param birthDate 参数
	 * @return 数据
	 */
	public static int getMonth(Date birthDate) {
		int ageMonth = 0;
		Calendar birthCalendar = Calendar.getInstance();// 生日日期
		Calendar nowCalendar = Calendar.getInstance();// 当前日期
		birthCalendar.setTime(birthDate);
		nowCalendar.setTime(now());
		if (birthCalendar.after(nowCalendar)) {
			return -1;
		}
		if (nowCalendar.get(Calendar.YEAR) > birthCalendar.get(Calendar.YEAR)) {
			ageMonth = (nowCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)) * 12;
		}
		if (nowCalendar.get(Calendar.DAY_OF_MONTH) > birthCalendar.get(Calendar.DAY_OF_MONTH)) {
			ageMonth += (nowCalendar.get(Calendar.MONTH) + 1) - (birthCalendar.get(Calendar.MONTH) + 1);
		}
		return ageMonth;
	}

	/**
	 * 根据出生日期获取年龄-总共多少天
	 */
	public static int getDay(Date birthDate) {
		int ageDay = 0;
		Calendar birthCalendar = Calendar.getInstance();// 生日日期
		Calendar nowCalendar = Calendar.getInstance();// 当前日期
		birthCalendar.setTime(birthDate);
		nowCalendar.setTime(now());
		if (birthCalendar.after(nowCalendar)) {
			return -1;
		}
		ageDay += (nowCalendar.getTimeInMillis() - birthCalendar.getTimeInMillis()) / (24 * 60 * 60 * 1000);
		return ageDay;
	}

	/**
	 * 获取某年某月的第一天
	 * 
	 * @param year 年
	 * @param month 月
	 * @return 数据
	 */
	public static String getFisrtDayOfMonth(String year, String month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, Integer.valueOf(year));
		// 设置月份
		cal.set(Calendar.MONTH, (Integer.valueOf(month) - 1));
		// 获取某月最小天数
		int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat(DT_SIMPLE);
		String firstDayOfMonth = sdf.format(cal.getTime());
		return firstDayOfMonth;
	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param year 年
	 * @param month 月
	 * @return 数据
	 */
	public static String getLastDayOfMonth(String year, String month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, Integer.valueOf(year));
		// 设置月份
		cal.set(Calendar.MONTH, (Integer.valueOf(month) - 1));
		// 获取某月最小天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat(DT_SIMPLE);
		String lastDayOfMonth = sdf.format(cal.getTime());
		return lastDayOfMonth;
	}

	/**
	 * 功能： 根据日期、方法、期限获取时间
	 * 如果设置的那一天 小于等于 当月28号，以后都是指定月的那一天 如果设置的那一天 取值 (当月28,当月最后一天)，以后都是 Math.min(设置的那一天，指定月的最后一天)
	 * 
	 * @param date
	 *            yyyy-MM-dd 格式
	 * @param methodType 类型
	 * @param term
	 *            期限（年）
	 * @return 数据
	 */
	public static List<String> getDate(String date, int methodType, int term) {
		List<String> list = new ArrayList<String>();
		// 月数
		int[] months = null;
		int index = 0;
		// 一次性
		if(0 == methodType){
			list.add(date);
			return list;
		}
		// 按月
		if (1 == methodType) {
			months = new int[term * 12];
			index = 1;
		}
		// 按季
		if (2 == methodType) {
			months = new int[term * 4];
			index = 3;
		}
		// 半年
		if (3 == methodType) {
			months = new int[term * 2];
			index = 6;
		}
		// 年
		if (4 == methodType) {
			months = new int[term * 1];
			index = 12;
		}
		// 计算日期
		for (int i = 0; i < months.length; i++) {
			String assignDate = getAssignDate(date, (index * i));
			list.add(assignDate);
		}
		return list;
	}

	/**
	 * 如果设置的那一天 小于等于 当月28号，以后都是指定月的那一天 如果设置的那一天 取值
	 * (当月28,当月最后一天)，以后都是Math.min(设置的那一天，指定月的最后一天)
	 * 
	 * @param date
	 *            开始时间(yyyy-MM-dd格式)
	 * @param mos
	 *            月数(当前月为0)
	 * @return 数据
	 */
	public static String getAssignDate(String date, int mos) {
		int year = Integer.valueOf(date.substring(0, 4));
		int month = Integer.valueOf(date.substring(5, 7));
		int day = Integer.valueOf(date.substring(8, 10));
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1 + mos);
		cal.set(Calendar.DAY_OF_MONTH, day);
		int predMonth = (month - 1 + mos) % 12;
		int behindMonth = cal.get(Calendar.MONTH);
		if (predMonth != behindMonth) {
			// 月份差额为2
			cal.set(Calendar.DAY_OF_MONTH, 1);
			// 获取下个月最大天数
			cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) - 1));
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			day = Math.min(day, lastDay);
		}
		cal.set(Calendar.DAY_OF_MONTH, day);
		return new SimpleDateFormat(DT_SIMPLE).format(cal.getTime());
	}

	/**
	 * 获得当天0点时间 如果设置的那一天 小于等于 当月28号，以后都是指定月的那一天 如果设置的那一天 取值 (当月28,
	 * 当月最后一天)，以后都是Math.min(设置的那一天，指定月的最后一天)
	 * 
	 * @return 数据
	 */
	public static String getMorningTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new SimpleDateFormat(SIMPLE).format(cal.getTime());
	}

	/**
	 * 获得当天24点时间
	 * 
	 * @return 数据
	 */
	public static String getNightTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new SimpleDateFormat(SIMPLE).format(cal.getTime());
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static String getYearFirst(int year) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, year);
		return new SimpleDateFormat(DT_SIMPLE).format(cal.getTime());
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static String getYearLast(int year) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, year);
		cal.roll(Calendar.DAY_OF_YEAR, -1);
		return new SimpleDateFormat(DT_SIMPLE).format(cal.getTime());
	}
}