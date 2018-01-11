package com.nhsoft.report.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	
	public static String getDefaultDateTimeStr(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}	
	
	public static Date addMonth(final Date time, int AddMonthCount){
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.MONTH, AddMonthCount);
		return c.getTime();
	}
	
	public static Date addYear(final Date time, int year){
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.YEAR, year);
		return c.getTime();
	}
	
	public static Date addHour(final Date time, int AddHourCount){
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.HOUR_OF_DAY, AddHourCount);
		return c.getTime();
	}

	// 2006-01-01 12:25:10 to 2006-01-01 00:00:00
	public static Date getMinOfDate(final Date date) {
		return DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
	}
	
	public static Date getMinOfYear(final Date date) {
		return DateUtils.truncate(date, Calendar.YEAR);
	}
	
	public static Date getMinOfMonth(final Date date) {
		return DateUtils.truncate(date, Calendar.MONTH);
	}

	// 2006-01-01 12:25:10 to 2006-01-02 23:59:59
	public static Date getMaxOfDate(final Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date _date = calendar.getTime();
		_date = DateUtils.truncate(_date, Calendar.DAY_OF_MONTH);
		calendar.setTime(_date);
		calendar.add(Calendar.SECOND, -1);
		return calendar.getTime();
	}
	
	public static Date getPreviousYearEnd(final Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Date _date = calendar.getTime();
		_date = DateUtils.truncate(_date, Calendar.YEAR);
		calendar.setTime(_date);
		calendar.add(Calendar.SECOND, -1);
		return calendar.getTime();
	}
	
	public static Date getThisYearEnd(final Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Date _date = calendar.getTime();
		_date = DateUtils.truncate(_date, Calendar.YEAR);
		calendar.setTime(_date);
		calendar.add(Calendar.YEAR, 1);
		calendar.add(Calendar.SECOND, -1);
		return calendar.getTime();
	}
	
	//判断是否为同一月
	public static boolean isSameMonth(final Date date, final Date anotherDate){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		int year = c1.get(Calendar.YEAR);
		int month = c1.get(Calendar.MONTH);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(anotherDate);
		int _year = c2.get(Calendar.YEAR);
		int _month = c2.get(Calendar.MONTH);

		if ((year == _year) && (month == _month)) {
			return true;
		} else {
			return false;
		}
	}
	
	// 判断是否为同一天
	public static boolean isSameDate(final Date date, final Date anotherDate) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		int year = c1.get(Calendar.YEAR);
		int month = c1.get(Calendar.MONTH);
		int day = c1.get(Calendar.DAY_OF_MONTH);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(anotherDate);
		int _year = c2.get(Calendar.YEAR);
		int _month = c2.get(Calendar.MONTH);
		int _day = c2.get(Calendar.DAY_OF_MONTH);

		if ((year == _year) && (month == _month) && (day == _day)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isSameTime(final Date Time, final Date anotherTime) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(Time);
        int hour = c1.get(Calendar.HOUR_OF_DAY);
        int minute = c1.get(Calendar.MINUTE);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(anotherTime);
        int _hour = c2.get(Calendar.HOUR_OF_DAY);
        int _minute = c2.get(Calendar.MINUTE);

        if ((hour == _hour) && (minute == _minute)) {
            return true;
        } else {
            return false;
        }
    }
	
	// 判断生日是否为同一天
    public static boolean isSameBirthDate(final Date date, final Date anotherDate) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        int month = c1.get(Calendar.MONTH);
        int day = c1.get(Calendar.DAY_OF_MONTH);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(anotherDate);
        int _month = c2.get(Calendar.MONTH);
        int _day = c2.get(Calendar.DAY_OF_MONTH);

        if ((month == _month) && (day == _day)) {
            return true;
        } else {
            return false;
        }
    }
	
	//返回当前月份的第一天
	public static Date getFirstDayOfMonth(Date date){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		c1.set(Calendar.DAY_OF_MONTH,1);
		return c1.getTime();
	}

	//返回当前月份最后一天
	public static Date getLastDayOfMonth(Date date){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		int month = c1.get(Calendar.MONTH);
		c1.set(Calendar.MONTH, month + 1);
		c1.set(Calendar.DAY_OF_MONTH, 1);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.add(Calendar.SECOND, -1);
		return c1.getTime();
	}
	
	public static Date getLastMonthDate(Date date){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(getFirstDayOfMonth(date));
		int month = c1.get(Calendar.MONTH);
		c1.set(Calendar.MONTH, month - 1);
		return c1.getTime();		
	}
			
	
	// 判断是否为同一时间，精确到分钟
	public static boolean isSameDateTime(final Date date, final Date anotherDate) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		int year = c1.get(Calendar.YEAR);
		int month = c1.get(Calendar.MONTH);
		int day = c1.get(Calendar.DAY_OF_MONTH);
		int hour = c1.get(Calendar.HOUR_OF_DAY);
		int minute = c1.get(Calendar.MINUTE);
		// int second = c1.get(Calendar.SECOND);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(anotherDate);
		int _year = c2.get(Calendar.YEAR);
		int _month = c2.get(Calendar.MONTH);
		int _day = c2.get(Calendar.DAY_OF_MONTH);
		int _hour = c2.get(Calendar.HOUR_OF_DAY);
		int _minute = c2.get(Calendar.MINUTE);
		// int _second = c2.get(Calendar.SECOND);

		if ((year == _year) && (month == _month) && (day == _day)
				&& (hour == _hour) && (minute == _minute)) {
			return true;
		} else {
			return false;
		}
	}


	public static Integer getMonth(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);// 0 表示一月
		month = month + 1;
		return new Integer(month);
	}
	
	public static Integer getDayOfMonth(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_MONTH);// 0 表示第一天
		return new Integer(day);
	}
	
	public static Integer getDayOfWeek(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_WEEK);// 1 表示星期天
        return day;
    }
	
	//查询一个月有多少天
	public static Integer getDayCountOfMonth(Date date) {
		return getDayOfMonth(getLastDayOfMonth(date));
	}
	
	/**
	 * 判断month是否在开始月份和结束月份之间，开始月份和结束月份可能是跨年度的，如开始月份为11月，结束月份为4月
	 * 
	 * @param month
	 * @param beginMonth
	 * @param endMonth
	 * @return
	 */
	public static boolean betweenMonth(int month, int beginMonth, int endMonth) {
		if (beginMonth > endMonth) {
			if ((month >= 1) && (month <= endMonth)) {
				return true;
			}
			if ((month >= beginMonth) && (month <= 12)) {
				return true;
			}
		} else {
			if ((month >= beginMonth) && (month <= endMonth)) {
				return true;
			}
		}
		return false;
	}

	public static boolean betweenDate(Date date, Date startDate, Date endDate) {
		int val1 = date.compareTo(startDate);// >=0
		int val2 = date.compareTo(endDate);// <=0
		if ((val1 >= 0) && (val2 <= 0)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断时间点是否在startTime/endTime之间
	 * 
	 * @param time
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static boolean betweenTime(Date time, Date startTime, Date endTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(0, 0, 0);
		long timeVal = c.getTime().getTime();

		Calendar c1 = Calendar.getInstance();
		c1.setTime(startTime);
		c1.set(0, 0, 0);
		long startTimeVal = c1.getTime().getTime();

		Calendar c2 = Calendar.getInstance();
		c2.setTime(endTime);
		c2.set(0, 0, 0);
		long endTimeVal = c2.getTime().getTime();

		if (startTimeVal >= endTimeVal) {
			if (timeVal >= startTimeVal) {
				return true;
			}

			if (timeVal <= endTimeVal) {
				return true;
			}
		} else {
			if ((timeVal >= startTimeVal) && (timeVal <= endTimeVal)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否为后半夜班
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isNotLobsterShift(final Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		if (hour >= 17) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isLobsterShift(final Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		if (hour <= 6) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 日期增加天数或者减少天数
	 */
	public static Date addDay(final Date time, int AddDayCount) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.DAY_OF_MONTH, AddDayCount);
		return c.getTime();
	}

	// 增加指定的分钟时间后的日期时间值
	public static Date addMinute(final Date time, int addedMinutes) {
		if(time == null){
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.MINUTE, addedMinutes);
		return c.getTime();
	}
	
	// 增加指定的秒
	public static Date addSecond(final Date time, int addedSeconds) {
		if(time == null){
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.SECOND, addedSeconds);
		return c.getTime();
	}

	// 结束时间 与 开始时间 之间的时间差(按分钟计量)
	public static int diffMinute(Date startTime, Date endTime) {
		if(startTime.after(endTime)){
			Date time = startTime;
			startTime = endTime;
			endTime = time;
		}
		float startTimeVal = startTime.getTime();
		float endTimeVal = endTime.getTime();

		float minutes = (endTimeVal - startTimeVal) / (1000 * 60);

		return new Float(minutes).intValue();
	}
	
	//结束时间 与 开始时间 之间的时间差(按天数计量)
	public static int diffDay(final Date startTime, final Date endTime) {
		float startTimeVal = startTime.getTime();
		float endTimeVal = endTime.getTime();

		float days = (endTimeVal - startTimeVal) / (1000 * 60 * 60 * 24);
		int intValue = new Float(days).intValue();
		//超过一天按一天计算
		if(days > intValue){
			return intValue + 1;
		}
		return intValue;
	}
	
	public static int diffDayV2(final Date startTime, final Date endTime) {
		long startTimeVal = startTime.getTime();
		long endTimeVal = endTime.getTime();

		long days = (endTimeVal - startTimeVal) / (1000 * 60 * 60 * 24);
		long miniSeconds = (endTimeVal - startTimeVal) % (1000 * 60 * 60 * 24);
		int intValue = new Long(days).intValue();
		//超过一天按一天计算
		if(miniSeconds > 0){
			return intValue + 1;
		}
		return intValue;
	}

	/**
	 * Calculates the number of days between two calendar days in a manner which
	 * is independent of the Calendar type used.
	 * 
	 * @param d1
	 *            The first date.
	 * @param d2
	 *            The second date.
	 * 
	 * @return The number of days between the two dates. Zero is returned if the
	 *         dates are the same, one if the dates are adjacent, etc. The order
	 *         of the dates does not matter, the value returned is always >= 0.
	 *         If Calendar types of d1 and d2 are different, the result may not
	 *         be accurate.
	 */
	public static int getDaysBetween(Calendar d1,
			Calendar d2) {
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(Calendar.DAY_OF_YEAR)
				- d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}
	//两个时间点的月份差
	public static BigDecimal getQuarterOfYearBetween(Date d1, Date d2){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		
		if(c1.after(c2)){
			Calendar swap = c1;
			c1 = c2;
			c2 = swap;
		}
		if(c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)){
			if(c2.get(Calendar.MONTH) == c1.get(Calendar.MONTH)){
				return new BigDecimal(1);
			} else {
				return new BigDecimal(c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH));
			}
		} else {
			int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
			return new BigDecimal((c2.get(Calendar.MONTH) + year*12 - c1.get(Calendar.MONTH)));
		}
	}
	
	//判断时间大小
	public static boolean CompareTime(Date date1, Date date2){
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int c1Hour = c1.get(Calendar.HOUR_OF_DAY);
		int c1Minute = c1.get(Calendar.MINUTE);
		int c2Hour = c2.get(Calendar.HOUR_OF_DAY);
		int c2Minute = c2.get(Calendar.MINUTE);
		if(c2Hour == 0 && c2Minute == 0){
			return false;
		}
		if ((c1Hour > c2Hour)) {
			return true;
		}else if((c1Hour == c2Hour) && (c1Minute >= c2Minute) ){
			return true;
		} else {
			return false;
		}
	}

	public static int getDaysBetween(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		return getDaysBetween(c1, c2);
	}

	public static String getDateTimeString(final Date date) {
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA);
		String formatted = dateFmt.format(date);
		return formatted;
	}

	public static String getJobTimeString(final Date date) {
		SimpleDateFormat dateFmt = new SimpleDateFormat("dd/HHmm", Locale.CHINA);
		String formatted = dateFmt.format(date); 
		return formatted;
	}

	public static String getCurrentDateTimeStr() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(dt);
	}
	
	public static String getSmsSendDateTimeStr() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(dt);
	}
	
	public static String getSmsSendDateTimeStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(date);
	}

	public static String getDateTimeStr(Date argument) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(argument);
	}
	
	public static String getTimeMiStr(Date argument) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
		return sdf.format(argument);
	}
	
	public static String getDateStr(Date argument) {
		if(argument == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(argument);
	}
	
	public static String getDateShortStr(Date argument) {
		if(argument == null){
			return null;
		}
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(argument);
    }
	
	public static String getMonthDayStr(Date argument) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
        return sdf.format(argument);
    }
	
	public static String getDay(Date argument) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		return sdf.format(argument);
	}

	public static String getBirthDay(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		return sdf.format(date);
	}

	public static Date getDateTimeMd(String arg){
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		try {
			return sdf.parse(arg);
		} catch (ParseException localParseException) {
		}
		return null;
	}
	
	public static Date getDateTimeHMS(String arg) {
		SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdft.parse(arg);
		} catch (ParseException localParseException) {
		}
		return null;
	}
	
	public static Date getDateTimeHM(String arg) {
		SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			return sdft.parse(arg);
		} catch (ParseException localParseException) {
		}
		return null;
	}

	
	public static Date getXmlDate(String arg) {
		SimpleDateFormat sdft = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		try {
			return sdft.parse(arg);
		} catch (ParseException localParseException) {
		}
		return null;
	}
	
	public static Date getXmlTDate(String arg) {
		try {
			return new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss").parse(arg);
		} catch (Exception localParseException) {
		}
		return null;
	}
	
	public static String getXmlTString(Date date) {		
		return new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss").format(date);	
	}
	
	public static Date getDateTimeShortHMS(String arg) {
		SimpleDateFormat sdft = new SimpleDateFormat("yyyyMMddHHmm");
		try {
			return sdft.parse(arg);
		} catch (ParseException localParseException) {
		}
		return null;
	}
	
	public static String format(final String pattern, final Date date){
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}	
	
	public static Date getDateStr(String date) {
		if (date == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
		
	public static String getLongDateTimeStr(Date argument) {
		if (argument == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(argument);
	}
	
	public static String getTimeStr(Date argument) {
		if (argument == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(argument);
	}
	
	public static String getHHmmStr(Date argument) {
		if (argument == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(argument);
	}
	
	public static String getHHmmStr2(Date argument) {
		if (argument == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		return sdf.format(argument);
	}
	
	public static String getShortDateTimeStr(Date argument) {
		if (argument == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(argument);
	}	
	
	public static String getDateStr(Date date, String format){
		if (format == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static String getTimeString(final Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat dateFmt = new SimpleDateFormat("HHmm");
		String formatted = dateFmt.format(date);
		return formatted;
	}

	public static String getYearString(final Date date) {
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy");
		String formatted = dateFmt.format(date);
		return formatted;
	}
	
	public static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
	
	public static String getYearAndMonthString(final Date date) {
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyyMM");
		String formatted = dateFmt.format(date);
		return formatted;
	}
	
	public static String getDayTimeString(final Date date) {
		SimpleDateFormat dateFmt = new SimpleDateFormat("dd'日'HH:mm");
		String formatted = dateFmt.format(date);
		return formatted;
	}
	
	public static String getTableYear(String propertyYear){		
		if(propertyYear == null){
			return "";
		}
		//return "_" + propertyYear;
		return "";
	}
	
	public static String getDateMonthType(String bizType) {
		String dateType = bizType.substring(0, 4) + "-" + bizType.substring(4, 6);
		return dateType;
	}
	
	public static String getDateType(String bizType) {
		String dateType = bizType.substring(0, 4) + "-" + bizType.substring(4, 6) + "-" + bizType.substring(6);
		return dateType;
	}
	
	public static Date getZeroSecondDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
	
	//返回当前年份
	public static Integer getYear(Date date){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		return c1.get(Calendar.YEAR);
	}
	
	public static Integer getDayOfYear(Date date){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		return c1.get(Calendar.DAY_OF_YEAR);
	}
	
	public static Integer getCurrentHour(){
		Calendar c1 = Calendar.getInstance();
		return c1.get(Calendar.HOUR_OF_DAY);
	}
	
	// 结束时间 与 开始时间 之间的时间差(按秒计量)
	public static int diffSecond(final Date startTime, final Date endTime) {
		long startTimeVal = startTime.getTime();
		long endTimeVal = endTime.getTime();
	
		Long second = (endTimeVal - startTimeVal) / 1000;	
		return second.intValue();
	}
	
	public static void main(String[] args) {
		System.out.println(getRemindStr(new Date()));
	}
	
	//返回当前年最后一天
	public static Date getLastDayOfYear(Date date){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		int year = c1.get(Calendar.YEAR);
		c1.set(Calendar.YEAR, year + 1);
		c1.set(Calendar.MONTH, 0);
		c1.set(Calendar.DAY_OF_MONTH, 1);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.add(Calendar.SECOND, -1);
		return c1.getTime();
	}
	
  	public static Date getFirstDayOfYear(Date date){
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.YEAR, date.getYear()+1900);
        c1.set(Calendar.DAY_OF_YEAR, 1);
        return c1.getTime();
    }
  	
	public static String getLongDateTimeTStr(Date date){
		try {
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date);
			
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取通知时间格式
	 * @param date
	 * @return
	 */
	public static String getRemindStr(Date date) {
		if (date == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH时mm分");
		return sdf.format(date);
	}
	
	public static Date getShortDate(String format){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(format);
		} catch (ParseException localParseException) {
		}
		return null;

	}



}
