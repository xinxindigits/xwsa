package cn.com.xinxin.sass.common.utils;

import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/11/24.
 * 日期工具
 */
public class DateUtils {

    /**
     *@WARN : 禁止对常量类进行修改
     */

    /**
     * 日期格式--斜杠
     */
    public static final String DATE_FORMAT_SPRIT = "yyyy/MM/dd";

    /**
     * yyyy/MM
     */
    public static final String DATE_FORMAT_YEAR_MONTH = "yyyy/MM";

    /**
     * 日期格式--无符号
     */
    public static final String DATE_FORMAT_NOSIGN = "yyyyMMdd";

    /**
     * 日期格式--无符号
     */
    public static final String DATE_FORMAT_DOT = "yyyy.MM.dd";

    /**
     * 日期格式--带时间戳
     */
    public static final String DATE_FORMAT_TIME = "yyyyMMdd HH:mm:ss";

    /**
     * 日期格式--带时间戳--不带秒的
     */
    public static final String DATE_FORMAT_SPRIT_NO_SECOND_TIME = "yyyy/MM/dd HH:mm";

    /**
     * 日期格式--纯数字
     */
    public static final String DATE_FORMAT_NUMBER = "yyyyMMddHHmmss";

    /**
     * 日期格式--横杠
     */
    public static final String DATE_FORMAT_WHIPP = "yyyy-MM-dd";

    /**
     * 日期格式-横杠-带时间戳
     */
    public static final String DATE_FORMAT_WHIPP_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间戳
     */
    public static final String DATE_FORMAT_TIMESTAMP = "HH:mm:ss";

    /**
     * 日期
     */
    public static final String DATE_FORMAT_DAY = "dd";


    /**
     * @param sBeginDate
     * @param sEndDate
     * @return
     * @throws ParseException 获取两个日期之间的天数
     */
    public static int getDays(String sBeginDate, String sEndDate) {
        Date startDate = java.sql.Date.valueOf(sBeginDate.replace('/', '-'));
        Date endDate = java.sql.Date.valueOf(sEndDate.replace('/', '-'));

        int iDays = (int) ((endDate.getTime() - startDate.getTime()) / 86400000L);
        return iDays;
    }

    /**
     * 获取相差日期
     *
     * @param date
     * @param field the calendar field.
     * @param step
     * @return
     * @throws Exception
     */
    public static String getRelativeDate(String date, String format, int field, int step){
        try {
            if (step == 0) return date;
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            cal.setTime(formatter.parse(date));
            cal.add(field, step);
            return formatter.format(cal.getTime());
        }catch (Exception e){
            throw new BusinessException(SassBizResultCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 时间戳转换为指定格式
     *
     * @param time   java 时间戳
     * @param format 时间格式
     * @return
     */
    public static String formatTime(Long time, String format) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 时间戳转换为指定格式
     *
     * @param date   java 时间
     * @param format 时间格式
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * @param cstStr     Wed Sep 16 11:26:23 CST 2009
     * @param dateFormat
     * @return
     * @throws ParseException
     */
    public static String pasrCstDateFormat(String cstStr, String dateFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date date = sdf.parse(cstStr);
        SimpleDateFormat sdf2 = new SimpleDateFormat(dateFormat);
        return sdf2.format(date);
    }

    /**
     * 将时间字符串转换为日期
     *
     * @param dateStr
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            throw new BusinessException(SassBizResultCodeEnum.SYSTEM_ERROR, "日期转换异常");
        }
    }

    public static String getMonthLastDay(String date, String format) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar cale = Calendar.getInstance();
        cale.setTime(sdf.parse(date));
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        String lastday = sdf.format(cale.getTime());
        return lastday;
    }

    @Deprecated
    public static String convertDateFmt(String dateStr, String origifmt, String destFmt) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        try {
            Date date = parseDate(dateStr, origifmt);
            return formatDate(date, destFmt);
        } catch (Exception e) {
            throw new BusinessException(SassBizResultCodeEnum.SYSTEM_ERROR, "日期格式转换异常！");
        }
    }


    public static String convertDateFmtNew(String dateStr, String origifmt, String destFmt) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(dateStr)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(origifmt);
            sdf.setLenient(false);
            Date date = sdf.parse(dateStr);
            return formatDate(date, destFmt);
        } catch (Exception e) {
            throw new BusinessException(SassBizResultCodeEnum.SYSTEM_ERROR, "日期格式转换异常！");
        }
    }

    public static long diffDays(Date begin, Date end) {
        long diffTime = end.getTime() - begin.getTime();
        return diffTime / (24 * 60 * 60 * 1000);
    }

    public static long diffHour(Date begin, Date end) {
        long diffTime = end.getTime() - begin.getTime();
        return diffTime / (60 * 60 * 1000);
    }

    /**
     * 查询天数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long diffDays(String beginDate, String endDate){
        return DateUtils.diffDays(DateUtils.parseDate(beginDate, DATE_FORMAT_SPRIT),
                DateUtils.parseDate(endDate, DATE_FORMAT_SPRIT));
    }

    /**
     * 日期替换字符
     *
     * @param dateStr
     * @param originSign
     * @param destSign
     * @return
     * @throws Exception
     */
    public static String convertDateString(String dateStr, String originSign, String destSign) throws Exception {
        if (dateStr == null) throw new Exception("原始日期字符不能为空！");
        return dateStr.replaceAll(originSign, destSign);
    }

    /**
     * 计算相关日期
     *
     * @param date
     * @param termUnit
     * @param step
     * @return
     * @throws Exception
     */
    public static String calDate(String date, String termUnit, int step) throws Exception {

        if (termUnit == null) {
            throw new Exception("未传入期限单位不正确！");
        }
        if (step == 0) {
            return date;
        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        cal.setTime(formatter.parse(date));
        if ("D".equals(termUnit)) {
            cal.add(Calendar.DATE, step);
        } else if ("M".equals(termUnit)) {
            cal.add(Calendar.MONTH, step);
        } else if ("Y".equals(termUnit)) {
            cal.add(Calendar.YEAR, step);
        } else {
            throw new Exception("期限单位不正确！");
        }
        return formatter.format(cal.getTime());
    }

    /**
     * 根据原始日期，或取n天后或前的日期
     *
     * @param origiDate 开始天数 格式：yyyy/MM/dd
     * @param days      添加天数
     * @return
     */
    public static String addDays(String origiDate, int days) {
        Date date = parseDate(origiDate, "yyyy/MM/dd");
        Date afterDay = org.apache.commons.lang3.time.DateUtils.addDays(date, days);
        return formatDate(afterDay, "yyyy/MM/dd");
    }

    /**
     * 获取当前时间HH:mm:ss
     *
     * @return
     */
    public static String nowTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return format.format(date);
    }

    /**
     * 获取时间
     *
     * @param date
     * @return
     */
    public static String getConcatDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return getTodayNow();
        }
        return date + " " + nowTime();
    }

    /**
     * 获取当期日期
     *
     * @param dateFmt
     * @return
     */
    public static String getToday(String dateFmt) {
        return formatDate(new Date(), dateFmt);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getToday() {
        return getToday(DATE_FORMAT_SPRIT);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getTodayNow() {
        return getToday(DATE_FORMAT_TIME);
    }

    /**
     * 获取long型时间
     *
     * @return
     */
    public static long getLongTime() {
        return System.currentTimeMillis();
    }

    /**
     * 校验String是否为yyyy/MM/dd
     *
     * @param str
     * @return true 符合 false不符合
     */
    public static boolean isValidSpritDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(DateUtils.DATE_FORMAT_SPRIT);
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);

            // 正则表达判断
            String regEx = "[0-9]{4}\\/[0-9]{2}\\/[0-9]{2}";
            Pattern pattern = Pattern.compile(regEx);
            if (!pattern.matcher(str).matches()) {
                convertSuccess = false;
            }

        } catch (ParseException e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 校验String是否为输入的格式
     *
     * @param str
     * @return true 符合 false不符合
     */
    public static boolean isValidDateFormat(String str,String formatStr) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        format.setLenient(false);
        try {
            format.parse(str);
        } catch (ParseException e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }


    /**
     * 由 日期时间 转为 毫秒数
     *
     * @param time       20180920010101 时间字符串
     * @param timeFormat yyyyMMddHHmmss 时间格式
     * @return
     */
    public static Long timeToMilliSecond(String time, String timeFormat) {
        if (StringUtils.isEmpty(time) || StringUtils.isEmpty(timeFormat)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(timeFormat);
        try {
            Date parseDate = dateFormat.parse(time);
            return parseDate.getTime();
        } catch (Exception e) {
            throw new BusinessException(SassBizResultCodeEnum.SYSTEM_ERROR, "日期时间转为毫秒数异常");
        }
    }


    /**
     * 获取 2个时间相隔的毫秒数
     *
     * @param startTime  20180920010101  开始时间字符串
     * @param endTime    20180920010101  结束时间字符串
     * @param timeFormat yyyyMMddHHmmss 时间格式
     * @return
     */
    public static Long diffMilliSecond(String startTime, String endTime, String timeFormat) throws Exception {
        Long preTime = timeToMilliSecond(startTime, timeFormat);
        Long postTime = timeToMilliSecond(endTime, timeFormat);
        return postTime - preTime;
    }

    /**
     * 转换成业务数据的日期格式
     *
     * @param sourceDate
     * @return
     */
    public static String formatDate(String sourceDate) throws ParseException {

        String targetDate = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setLenient(false);
        Date newDate = formatter.parse(sourceDate);

        formatter = new SimpleDateFormat("yyyy/MM/dd");
        targetDate = formatter.format(newDate);

        return targetDate;
    }

    public static int compare(String time1, String time2, String dateFormat) {

        if (org.apache.commons.lang3.StringUtils.isBlank(time1)) {
            if (org.apache.commons.lang3.StringUtils.isBlank(time2)) {
                return 0;
            }
            return -1;
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(time2)) {
            return 1;
        }
        SimpleDateFormat t1 = new SimpleDateFormat(dateFormat);
        SimpleDateFormat t2 = new SimpleDateFormat(dateFormat);
        try {
            Date d1 = t1.parse(time1);
            Date d2 = t2.parse(time2);
            return d1.compareTo(d2);
        } catch (Exception e) {
            throw new BusinessException(SassBizResultCodeEnum.SYSTEM_ERROR, "日期比较异常");
        }
    }


    /**
     * 比较时间大小 默认格式HH:mm:ss
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static int compareTime(String beginTime, String endTime) {

        DateFormat df = new SimpleDateFormat(DATE_FORMAT_TIMESTAMP);//创建日期转换对象HH:mm:ss为时分秒，年月日为yyyy-MM-dd
        try {
            Date dt1 = df.parse(beginTime);//将字符串转换为date类型
            Date dt2 = df.parse(endTime);
            if (dt1.getTime() == dt2.getTime()) {
                return 0;
            } else if (dt1.getTime() > dt2.getTime()) {
                return -1;
            } else {
                return 1;//运行输出no
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断指定格式的日期是否是同一个月份
     * @param date1
     * @param date2
     * @param sourceFormat
     * @return
     */
    public static boolean sameMonth(String date1, String date2, String sourceFormat){
        try {
            if (StringUtils.isNotEmpty(date1)
                    && StringUtils.isNotEmpty(date2)
                    && StringUtils.isNotEmpty(sourceFormat)
            ) {
                return StringUtils.equals(
                        DateUtils.convertDateFmt(date1, sourceFormat, DATE_FORMAT_YEAR_MONTH),
                        DateUtils.convertDateFmt(date2, sourceFormat, DATE_FORMAT_YEAR_MONTH)
                );
            }
            return false;
        }catch (Exception e){
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "系统异常-判断是否同一月份");
        }
    }
}
