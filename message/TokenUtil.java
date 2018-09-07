package cn.sinobest.jzpt.message;

/**
 * token加密解密
 *
 * @author yanjunhao
 * @date 2018年9月4日
 */
public class TokenUtil {

    /**
     * 解码
     * @param inStr
     * @param salt
     * @return
     */
    public static String decode(String inStr, String salt) {
        return encode(inStr, salt);
    }

    /**
     * 编码-位异或运算
     */
    public static String encode(String inStr, String salt) {
        char[] a = inStr.toCharArray();
        char[] s = salt.toCharArray();
        for (int i = 0; i < a.length; i++) {
            char sItem;
            if (i < s.length) {
                sItem = s[i];
            } else {
                int j = i;
                while (j >= s.length) {
                    j -= s.length;
                }
                sItem = s[j];
            }
            a[i] = (char) (a[i] ^ sItem);
        }
        return new String(a);
    }


    // 测试主函数
   /* public static void main(String args[]) {
        *//*String s = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiMCIsIm5hbWUiOiLkuInlhYPph4zmsJHoraYiLCJ1c2VyaWQiOiJzeWxtaiIsImpoIjoic3lsbWoiLCJkZXB0IjoiNDQwMTExNTQwMDAwIiwid29ya2RlcHQiOiI0NDAxMDUwMDAwMDAiLCJkd21jIjoi5bm_5bee5biC5YWs5a6J5bGA55m95LqR5Yy65YiG5bGA5LiJ5YWD6YeM5rS-5Ye65omAIiwiaXNzIjoiIiwiYXVkIjoiIiwiZXhwIjoxNTM1MzUxODYwfQ.4zBUvJ_p3JeW6IE8YRFNWVOa-CE583V20TEOp3JnpzY";
        System.out.println("原始：" + s);
        System.out.println("加密：" + encode(s, "aff"));
        System.out.println("解密后的：" + decode(encode(s, "aff"), "aff"));*//*
    }*/
}
