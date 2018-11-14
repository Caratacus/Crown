package org.crown.common.kit;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import org.crown.common.exception.CrownException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.util.IOUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 类型转换
 *
 * @author Caratacus
 * @see com.alibaba.fastjson.util.TypeUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class TypeUtils {

    private static boolean oracleTimestampMethodInited = false;
    private static Method oracleTimestampMethod;

    private static boolean oracleDateMethodInited = false;
    private static Method oracleDateMethod;


    public static String castToString(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public static String castToString(Object value, String defaults) {
        String castString = castToString(value);
        return Objects.isNull(castString) ? defaults : castString;
    }

    public static Byte castToByte(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).byteValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }

            return Byte.parseByte(strVal);
        }

        throw new CrownException("can not cast to byte, value : " + value);
    }

    public static Byte castToByte(Object value, Byte defaults) {
        Byte castToByte = castToByte(value);
        return Objects.isNull(castToByte) ? defaults : castToByte;
    }

    public static Character castToChar(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Character) {
            return (Character) value;
        }

        if (value instanceof String) {
            String strVal = (String) value;

            if (strVal.length() == 0) {
                return null;
            }

            if (strVal.length() != 1) {
                throw new CrownException("can not cast to char, value : " + value);
            }

            return strVal.charAt(0);
        }

        throw new CrownException("can not cast to char, value : " + value);
    }

    public static Character castToChar(Object value, Character defaults) {
        Character castToChar = castToChar(value);
        return Objects.isNull(castToChar) ? defaults : castToChar;
    }

    public static Short castToShort(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).shortValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;

            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }

            return Short.parseShort(strVal);
        }

        throw new CrownException("can not cast to short, value : " + value);
    }

    public static Short castToShort(Object value, Short defaults) {
        Short castToShort = castToShort(value);
        return Objects.isNull(castToShort) ? defaults : castToShort;
    }

    public static BigDecimal castToBigDecimal(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }

        if (value instanceof BigInteger) {
            return new BigDecimal((BigInteger) value);
        }

        String strVal = value.toString();
        if (strVal.length() == 0) {
            return null;
        }

        return new BigDecimal(strVal);
    }

    public static BigDecimal castToBigDecimal(Object value, BigDecimal defaults) {
        BigDecimal bigDecimal = castToBigDecimal(value);
        return Objects.isNull(bigDecimal) ? defaults : bigDecimal;

    }

    public static BigInteger castToBigInteger(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof BigInteger) {
            return (BigInteger) value;
        }

        if (value instanceof Float || value instanceof Double) {
            return BigInteger.valueOf(((Number) value).longValue());
        }

        String strVal = value.toString();
        if (strVal.length() == 0
                || "null".equals(strVal)
                || "NULL".equals(strVal)) {
            return null;
        }

        return new BigInteger(strVal);
    }

    public static BigInteger castToBigInteger(Object value, BigInteger defaults) {

        BigInteger bigInteger = castToBigInteger(value);
        return Objects.isNull(bigInteger) ? defaults : bigInteger;

    }

    public static Float castToFloat(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }

        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }

            if (strVal.indexOf(',') != 0) {
                strVal = strVal.replaceAll(",", "");
            }

            return Float.parseFloat(strVal);
        }

        throw new CrownException("can not cast to float, value : " + value);
    }

    public static Float castToFloat(Object value, Float defaults) {
        Float castToFloat = castToFloat(value);
        return Objects.isNull(castToFloat) ? defaults : castToFloat;

    }

    public static Double castToDouble(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }

        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }

            if (strVal.indexOf(',') != 0) {
                strVal = strVal.replaceAll(",", "");
            }

            return Double.parseDouble(strVal);
        }

        throw new CrownException("can not cast to double, value : " + value);
    }

    public static Double castToDouble(Object value, Double defaults) {
        Double castToDouble = castToDouble(value);
        return Objects.isNull(castToDouble) ? defaults : castToDouble;

    }

    public static Date castToDate(Object value) {
        if (value == null) {
            return null;
        }
        // 使用频率最高的，应优先处理
        if (value instanceof Date) {
            return (Date) value;
        }

        if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        }

        long longValue = -1;

        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
            return new Date(longValue);
        }

        if (value instanceof String) {
            String strVal = (String) value;

            try (JSONScanner dateLexer = new JSONScanner(strVal)) {
                if (dateLexer.scanISO8601DateIfMatch(false)) {
                    Calendar calendar = dateLexer.getCalendar();
                    return calendar.getTime();
                }
            }

            if (strVal.startsWith("/Date(") && strVal.endsWith(")/")) {
                strVal = strVal.substring(6, strVal.length() - 2);
            }

            if (strVal.indexOf('-') != -1) {
                String format;
                if (strVal.length() == JSON.DEFFAULT_DATE_FORMAT.length()) {
                    format = JSON.DEFFAULT_DATE_FORMAT;
                } else if (strVal.length() == 10) {
                    format = "yyyy-MM-dd";
                } else if (strVal.length() == "yyyy-MM-dd HH:mm:ss".length()) {
                    format = "yyyy-MM-dd HH:mm:ss";
                } else {
                    format = "yyyy-MM-dd HH:mm:ss.SSS";
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat(format, JSON.defaultLocale);
                dateFormat.setTimeZone(JSON.defaultTimeZone);
                try {
                    return dateFormat.parse(strVal);
                } catch (ParseException e) {
                    throw new CrownException("can not cast to Date, value : " + strVal);
                }
            }

            if (strVal.length() == 0) {
                return null;
            }

            longValue = Long.parseLong(strVal);
        }

        if (longValue < 0) {
            Class<?> clazz = value.getClass();
            if ("oracle.sql.TIMESTAMP".equals(clazz.getName())) {
                if (oracleTimestampMethod == null && !oracleTimestampMethodInited) {
                    try {
                        oracleTimestampMethod = clazz.getMethod("toJdbc");
                    } catch (NoSuchMethodException e) {
                        // skip
                    } finally {
                        oracleTimestampMethodInited = true;
                    }
                }

                Object result;
                try {
                    assert oracleTimestampMethod != null;
                    result = oracleTimestampMethod.invoke(value);
                } catch (Exception e) {
                    throw new CrownException("can not cast oracle.sql.TIMESTAMP to Date", e);
                }
                return (Date) result;
            }

            if ("oracle.sql.DATE".equals(clazz.getName())) {
                if (oracleDateMethod == null && !oracleDateMethodInited) {
                    try {
                        oracleDateMethod = clazz.getMethod("toJdbc");
                    } catch (NoSuchMethodException e) {
                        // skip
                    } finally {
                        oracleDateMethodInited = true;
                    }
                }

                Object result;
                try {
                    assert oracleDateMethod != null;
                    result = oracleDateMethod.invoke(value);
                } catch (Exception e) {
                    throw new CrownException("can not cast oracle.sql.DATE to Date", e);
                }
                return (Date) result;
            }

            throw new CrownException("can not cast to Date, value : " + value);
        }

        return new Date(longValue);
    }

    public static Date castToDate(Object value, Date defaults) {
        Date castToDate = castToDate(value);
        return Objects.isNull(castToDate) ? defaults : castToDate;

    }

    @SuppressWarnings("Duplicates")
    public static java.sql.Date castToSqlDate(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof java.sql.Date) {
            return (java.sql.Date) value;
        }

        if (value instanceof Date) {
            return new java.sql.Date(((Date) value).getTime());
        }

        if (value instanceof Calendar) {
            return new java.sql.Date(((Calendar) value).getTimeInMillis());
        }

        long longValue = 0;

        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }

            longValue = Long.parseLong(strVal);
        }

        if (longValue <= 0) {
            // 忽略 1970-01-01 之前的时间处理？
            throw new CrownException("can not cast to Date, value : " + value);
        }

        return new java.sql.Date(longValue);
    }

    public static java.sql.Date castToSqlDate(Object value, java.sql.Date defaults) {
        java.sql.Date castToSqlDate = castToSqlDate(value);
        return Objects.isNull(castToSqlDate) ? defaults : castToSqlDate;

    }

    @SuppressWarnings("Duplicates")
    public static java.sql.Timestamp castToTimestamp(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Calendar) {
            return new java.sql.Timestamp(((Calendar) value).getTimeInMillis());
        }

        if (value instanceof java.sql.Timestamp) {
            return (java.sql.Timestamp) value;
        }

        if (value instanceof Date) {
            return new java.sql.Timestamp(((Date) value).getTime());
        }

        long longValue = 0;

        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }

            longValue = Long.parseLong(strVal);
        }

        if (longValue <= 0) {
            throw new CrownException("can not cast to Date, value : " + value);
        }

        return new java.sql.Timestamp(longValue);
    }

    public static java.sql.Timestamp castToTimestamp(Object value, java.sql.Timestamp defaults) {
        java.sql.Timestamp castToTimestamp = castToTimestamp(value);
        return Objects.isNull(castToTimestamp) ? defaults : castToTimestamp;

    }

    public static Long castToLong(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).longValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }

            if (strVal.indexOf(',') != 0) {
                strVal = strVal.replaceAll(",", "");
            }

            try {
                return Long.parseLong(strVal);
            } catch (NumberFormatException ex) {
                //
            }

            JSONScanner dateParser = new JSONScanner(strVal);
            Calendar calendar = null;
            if (dateParser.scanISO8601DateIfMatch(false)) {
                calendar = dateParser.getCalendar();
            }
            dateParser.close();

            if (calendar != null) {
                return calendar.getTimeInMillis();
            }
        }

        throw new CrownException("can not cast to long, value : " + value);
    }

    public static Long castToLong(Object value, Long defaults) {
        Long castToLong = castToLong(value);
        return Objects.isNull(castToLong) ? defaults : castToLong;

    }

    public static Integer castToInt(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Integer) {
            return (Integer) value;
        }

        if (value instanceof Number) {
            return ((Number) value).intValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;

            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }

            if (strVal.indexOf(',') != 0) {
                strVal = strVal.replaceAll(",", "");
            }

            return Integer.parseInt(strVal);
        }

        if (value instanceof Boolean) {
            return (Boolean) value ? 1 : 0;
        }

        throw new CrownException("can not cast to int, value : " + value);
    }

    public static Integer castToInt(Object value, Integer defaults) {
        Integer castToInt = castToInt(value);
        return Objects.isNull(castToInt) ? defaults : castToInt;

    }

    public static byte[] castToBytes(Object value) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        }

        if (value instanceof String) {
            return IOUtils.decodeBase64((String) value);
        }
        throw new CrownException("can not cast to int, value : " + value);
    }

    public static byte[] castToBytes(Object value, byte[] defaults) {
        byte[] castToBytes = castToBytes(value);
        return Objects.isNull(castToBytes) ? defaults : castToBytes;

    }

    public static Boolean castToBoolean(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Boolean) {
            return (Boolean) value;
        }

        if (value instanceof Number) {
            return ((Number) value).intValue() == 1;
        }

        if (value instanceof String) {
            String strVal = (String) value;

            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }

            if ("true".equalsIgnoreCase(strVal)
                    || "1".equals(strVal)) {
                return Boolean.TRUE;
            }

            if ("false".equalsIgnoreCase(strVal)
                    || "0".equals(strVal)) {
                return Boolean.FALSE;
            }
        }

        throw new CrownException("can not cast to boolean, value : " + value);
    }

    public static Boolean castToBoolean(Object value, Boolean defaults) {
        Boolean castToBoolean = castToBoolean(value);
        return Objects.isNull(castToBoolean) ? defaults : castToBoolean;

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T castToEnum(Object obj, Class<T> clazz) {
        try {
            if (obj instanceof String) {
                String name = (String) obj;
                if (name.length() == 0) {
                    return null;
                }

                return (T) Enum.valueOf((Class<? extends Enum>) clazz, name);
            }

            if (obj instanceof Number) {
                int ordinal = ((Number) obj).intValue();
                Object[] values = clazz.getEnumConstants();
                if (ordinal < values.length) {
                    return (T) values[ordinal];
                }
            }
        } catch (Exception ex) {
            throw new CrownException("can not cast to : " + clazz.getName(), ex);
        }

        throw new CrownException("can not cast to : " + clazz.getName());
    }


    public static <T> T castToEnum(Object value, Class<T> clazz, T defaults) {
        T castToEnum = castToEnum(value, clazz);
        return Objects.isNull(castToEnum) ? defaults : castToEnum;

    }
}