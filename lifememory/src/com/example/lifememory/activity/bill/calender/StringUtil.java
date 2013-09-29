package com.example.lifememory.activity.bill.calender;


/**
 * Created by IntelliJ IDEA.
 * User: zhouxin@easier.cn
 * �ַ����Ĵ�����
 * Date: 12-11-22
 * Time: ����4:35
 * To change this template use File | Settings | File Templates.
 */
public class StringUtil {
    /**
     * �ж��Ƿ�Ϊnull���ֵ
     *
     * @param str String
     * @return true or false
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * �ж�str1��str2�Ƿ���ͬ
     *
     * @param str1 str1
     * @param str2 str2
     * @return true or false
     */
    public static boolean equals(String str1, String str2) {
        return str1 == str2 || str1 != null && str1.equals(str2);
    }

    /**
     * �ж�str1��str2�Ƿ���ͬ(�����ִ�Сд)
     *
     * @param str1 str1
     * @param str2 str2
     * @return true or false
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 != null && str1.equalsIgnoreCase(str2);
    }

    /**
     * �ж��ַ���str1�Ƿ�����ַ���str2
     *
     * @param str1 Դ�ַ���
     * @param str2 ָ���ַ���
     * @return trueԴ�ַ�������ָ���ַ�����falseԴ�ַ���������ָ���ַ���
     */
    public static boolean contains(String str1, String str2) {
        return str1 != null && str1.contains(str2);
    }

    /**
     * �ж��ַ����Ƿ�Ϊ�գ�Ϊ���򷵻�һ����ֵ����Ϊ���򷵻�ԭ�ַ���
     *
     * @param str ���ж��ַ���
     * @return �жϺ���ַ���
     */
    public static String getString(String str) {
        return str == null ? "" : str;
    }
}

