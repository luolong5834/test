package com.luolong.opensource.java.test.DB;

import java.security.MessageDigest;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/4/25
 */
public class T {
    public static final String MD5(String paramter) {
        String sign = "";
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            byte[] strTemp = paramter.getBytes("UTF-8");
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;

            for (int i = 0; i < j; ++i) {
                byte b = md[i];
                str[k++] = hexDigits[b >> 4 & 15];
                str[k++] = hexDigits[b & 15];
            }

            sign = new String(str);
        } catch (Exception var11) {
        }

        return sign;
    }

    public static void main(String[] args) {
        System.out.println(MD5("8150733289" + "YK4fF37A2139iy5m"));
    }

    static int MoreThanHalf(int a[], int N) {
        int sum1 = 0;//最后一个元素的个数
        int sum2 = 0;
        int candidate = 0;
        int i;
        for (i = 0; i < N - 1; i++)//扫描前N-1个元素
        {
            if (a[i] == a[N - 1])//判断当前元素与最后一个是否相等
                sum1++;
            if (sum2 == 0) {
                candidate = a[i];
                sum2++;
            } else {
                if (a[i] == candidate)
                    sum2++;
                else
                    sum2--;
            }
        }

        if ((sum1 + 1) == N / 2)
            return a[N - 1];
        else
            return candidate;
    }
}
