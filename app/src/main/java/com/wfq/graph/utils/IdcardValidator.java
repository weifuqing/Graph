package com.wfq.graph.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IdcardValidator {

    private static String cityCode[] = { "11", "12", "13", "14", "15", "21",
            "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42",  
            "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62",  
            "63", "64", "65", "71", "81", "82", "91" };  
  
    /** 
     * ÿλ��Ȩ���� 
     */  
    private static int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,  
            8, 4, 2 };  
  
    /** 
     * ��֤���е����֤�ĺϷ��� 
     *  
     * @param idcard 
     *            ���֤ 
     * @return �Ϸ�����true�����򷵻�false 
     */  
    public static boolean isValidatedAllIdcard(String idcard) {
        if (idcard == null || "".equals(idcard)) {  
            return false;  
        }  
        if (idcard.length() == 15) {  
            return validate15IDCard(idcard);  
        }  
        return validate18Idcard(idcard);  
    }  
  
    /** 
     * <p> 
     * �ж�18λ���֤�ĺϷ��� 
     * </p> 
     * ���ݡ��л����񹲺͹����ұ�׼GB11643-1999�����йع�����ݺ���Ĺ涨��������ݺ�������������룬��ʮ��λ���ֱ������һλ����У������ɡ� 
     * ����˳�������������Ϊ����λ���ֵ�ַ�룬��λ���ֳ��������룬��λ����˳�����һλ����У���롣 
     * <p> 
     * ˳����: ��ʾ��ͬһ��ַ������ʶ������Χ�ڣ���ͬ�ꡢͬ�¡�ͬ �ճ������˱ඨ��˳��ţ�˳�����������������ԣ�ż������ ��Ů�ԡ� 
     * </p> 
     * <p> 
     * 1.ǰ1��2λ���ֱ�ʾ������ʡ�ݵĴ��룻 2.��3��4λ���ֱ�ʾ�����ڳ��еĴ��룻 3.��5��6λ���ֱ�ʾ���������صĴ��룻 
     * 4.��7~14λ���ֱ�ʾ�������ꡢ�¡��գ� 5.��15��16λ���ֱ�ʾ�����ڵص��ɳ����Ĵ��룻 
     * 6.��17λ���ֱ�ʾ�Ա�������ʾ���ԣ�ż����ʾŮ�ԣ� 
     * 7.��18λ������У���룺Ҳ�е�˵�Ǹ�����Ϣ�룬һ��������������������������������֤����ȷ�ԡ�У���������0~9�����֣���ʱҲ��x��ʾ�� 
     * </p> 
     * <p> 
     * ��ʮ��λ����(У����)�ļ��㷽��Ϊ�� 1.��ǰ������֤����17λ���ֱ���Բ�ͬ��ϵ�����ӵ�һλ����ʮ��λ��ϵ���ֱ�Ϊ��7 9 10 5 8 4 
     * 2 1 6 3 7 9 10 5 8 4 2 
     * </p> 
     * <p> 
     * 2.����17λ���ֺ�ϵ����˵Ľ����ӡ� 
     * </p> 
     * <p> 
     * 3.�üӳ����ͳ���11���������Ƕ��� 
     * </p> 
     * 4.����ֻ������0 1 2 3 4 5 6 7 8 9 10��11�����֡���ֱ��Ӧ�����һλ���֤�ĺ���Ϊ1 0 X 9 8 7 6 5 4 3 
     * 2�� 
     * <p> 
     * 5.ͨ�������֪���������2���ͻ������֤�ĵ�18λ�����ϳ����������ֵĢ������������10�����֤�����һλ�������2�� 
     * </p> 
     *  
     * @param idcard 
     * @return 
     */  
    public static boolean validate18Idcard(String idcard) {
        if (idcard == null) {  
            return false;  
        }  
  
        // ��18λΪ��  
        if (idcard.length() != 18) {  
            return false;  
        }  
        // ��ȡǰ17λ  
        String idcard17 = idcard.substring(0, 17);
  
        // ǰ17λȫ��Ϊ����  
        if (!isDigital(idcard17)) {  
            return false;  
        }  
  
        String provinceid = idcard.substring(0, 2);
        // У��ʡ��  
        if (!checkProvinceid(provinceid)) {  
            return false;  
        }  
  
        // У���������  
        String birthday = idcard.substring(6, 14);
  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
  
        try {  
            Date birthDate = sdf.parse(birthday);
            String tmpDate = sdf.format(birthDate);
            if (!tmpDate.equals(birthday)) {// ���������ղ���ȷ  
                return false;  
            }  
  
        } catch (ParseException e1) {
  
            return false;  
        }  
  
        // ��ȡ��18λ  
        String idcard18Code = idcard.substring(17, 18);
  
        char c[] = idcard17.toCharArray();  
  
        int bit[] = converCharToInt(c);  
  
        int sum17 = 0;  
  
        sum17 = getPowerSum(bit);  
  
        // ����ֵ��11ȡģ�õ���������У�����ж�  
        String checkCode = getCheckCodeBySum(sum17);
        if (null == checkCode) {  
            return false;  
        }  
        // �����֤�ĵ�18λ���������У�����ƥ�䣬����Ⱦ�Ϊ��  
        if (!idcard18Code.equalsIgnoreCase(checkCode)) {  
            return false;  
        }  
  
        return true;  
    }  
  
    /** 
     * У��15λ���֤ 
     *  
     * <pre> 
     * ֻУ��ʡ�ݺͳ��������� 
     * </pre> 
     *  
     * @param idcard 
     * @return 
     */  
    public static boolean validate15IDCard(String idcard) {
        if (idcard == null) {  
            return false;  
        }  
        // ��15λΪ��  
        if (idcard.length() != 15) {  
            return false;  
        }  
  
        // 15ȫ��Ϊ����  
        if (!isDigital(idcard)) {  
            return false;  
        }  
  
        String provinceid = idcard.substring(0, 2);
        // У��ʡ��  
        if (!checkProvinceid(provinceid)) {  
            return false;  
        }  
  
        String birthday = idcard.substring(6, 12);
  
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
  
        try {  
            Date birthDate = sdf.parse(birthday);
            String tmpDate = sdf.format(birthDate);
            if (!tmpDate.equals(birthday)) {// ���֤���ڴ���  
                return false;  
            }  
  
        } catch (ParseException e1) {
  
            return false;  
        }  
  
        return true;  
    }  
  
    /** 
     * ��15λ�����֤ת��18λ���֤ 
     *  
     * @param idcard 
     * @return 
     */  
    public static String convertIdcarBy15bit(String idcard) {
        if (idcard == null) {  
            return null;  
        }  
  
        // ��15λ���֤  
        if (idcard.length() != 15) {  
            return null;  
        }  
  
        // 15ȫ��Ϊ����  
        if (!isDigital(idcard)) {  
            return null;  
        }  
  
        String provinceid = idcard.substring(0, 2);
        // У��ʡ��  
        if (!checkProvinceid(provinceid)) {  
            return null;  
        }  
  
        String birthday = idcard.substring(6, 12);
  
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
  
        Date birthdate = null;
        try {  
            birthdate = sdf.parse(birthday);  
            String tmpDate = sdf.format(birthdate);
            if (!tmpDate.equals(birthday)) {// ���֤���ڴ���  
                return null;  
            }  
  
        } catch (ParseException e1) {
            return null;  
        }  
  
        Calendar cday = Calendar.getInstance();
        cday.setTime(birthdate);  
        String year = String.valueOf(cday.get(Calendar.YEAR));
  
        String idcard17 = idcard.substring(0, 6) + year + idcard.substring(8);
  
        char c[] = idcard17.toCharArray();  
        String checkCode = "";
  
        // ���ַ�����תΪ��������  
        int bit[] = converCharToInt(c);  
  
        int sum17 = 0;  
        sum17 = getPowerSum(bit);  
  
        // ��ȡ��ֵ��11ȡģ�õ���������У����  
        checkCode = getCheckCodeBySum(sum17);  
  
        // ��ȡ����У��λ  
        if (null == checkCode) {  
            return null;  
        }  
        // ��ǰ17λ���18λУ����ƴ��  
        idcard17 += checkCode;  
        return idcard17;  
    }  
  
    /** 
     * У��ʡ�� 
     *  
     * @param provinceid 
     * @return �Ϸ�����TRUE�����򷵻�FALSE 
     */  
    private static boolean checkProvinceid(String provinceid) {
        for (String id : cityCode) {
            if (id.equals(provinceid)) {  
                return true;  
            }  
        }  
        return false;  
    }  
  
    /** 
     * ������֤ 
     *  
     * @param str 
     * @return 
     */  
    private static boolean isDigital(String str) {
        return str.matches("^[0-9]*$");  
    }  
  
    /** 
     * �����֤��ÿλ�Ͷ�Ӧλ�ļ�Ȩ�������֮���ٵõ���ֵ 
     *  
     * @param bit 
     * @return 
     */  
    private static int getPowerSum(int[] bit) {  
  
        int sum = 0;  
  
        if (power.length != bit.length) {  
            return sum;  
        }  
  
        for (int i = 0; i < bit.length; i++) {  
            for (int j = 0; j < power.length; j++) {  
                if (i == j) {  
                    sum = sum + bit[i] * power[j];  
                }  
            }  
        }  
        return sum;  
    }  
  
    /** 
     * ����ֵ��11ȡģ�õ���������У�����ж� 
     *  
     * @param checkCode 
     * @param sum17 
     * @return У��λ 
     */  
    private static String getCheckCodeBySum(int sum17) {
        String checkCode = null;
        switch (sum17 % 11) {  
        case 10:  
            checkCode = "2";  
            break;  
        case 9:  
            checkCode = "3";  
            break;  
        case 8:  
            checkCode = "4";  
            break;  
        case 7:  
            checkCode = "5";  
            break;  
        case 6:  
            checkCode = "6";  
            break;  
        case 5:  
            checkCode = "7";  
            break;  
        case 4:  
            checkCode = "8";  
            break;  
        case 3:  
            checkCode = "9";  
            break;  
        case 2:  
            checkCode = "x";  
            break;  
        case 1:  
            checkCode = "0";  
            break;  
        case 0:  
            checkCode = "1";  
            break;  
        }  
        return checkCode;  
    }  
  
    /** 
     * ���ַ�����תΪ�������� 
     *  
     * @param c 
     * @return 
     * @throws NumberFormatException
     */  
    private static int[] converCharToInt(char[] c) throws NumberFormatException {
        int[] a = new int[c.length];  
        int k = 0;  
        for (char temp : c) {  
            a[k++] = Integer.parseInt(String.valueOf(temp));
        }  
        return a;  
    }  
  
    public static void main(String[] args) throws Exception {
        String idcard15 = "130321860311519";
        String idcard18 = "210102198617083732";//
        // 15λ���֤  
        System.out.println(isValidatedAllIdcard(idcard15));
        // 18λ���֤  
        System.out.println(isValidatedAllIdcard(idcard18));
        // 15λ���֤ת18λ���֤  
        System.out.println(convertIdcarBy15bit(idcard15));
    }  
	

}
