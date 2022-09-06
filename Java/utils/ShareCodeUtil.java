package com.webcore.util;

import java.util.HashSet;
import java.util.Set;

public class ShareCodeUtil {
    /**
     * 随机码长度
     */
    public static final int RDC_LEN = 2;

    public static final char[] CHARS = new char[]
            {
                    '2', '3', '4', '5', '6', '7', '8', '9',
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                    'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
                    'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
            };

    private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();

    public static final int CHARS_LEN = ShareCodeUtil.CHARS.length;

    /**
     * 邀请码结构:
     * ----------------------------------------------------------------------------
     * | 随机字符串 | uid对应的十六进制字符串 | 顺序号 | uid对应的校验码 | 随机码 |
     * |   2位      |           8位          |  2位   |      1位       |  1位   |
     * ----------------------------------------------------------------------------
     *
     * @param uid   唯一id
     * @param seqNo 顺序号
     */
    public static String generateCode(long uid, int seqNo) {
        StringBuilder builder = new StringBuilder();

        // 生成随机字符串和检验码
        String magic = ShareCodeUtil.generateRandomMagic();
        int l = magic.length();

        // 0 随机字符串
        String rc = magic.substring(0, l - 1);
        builder.append(rc);

        // 1 uid对应的十六进制字符串码
        builder.append(String.format("%08X", uid));

        // 2 seqNo 对应的十六进制字符串码
        builder.append(String.format("%02X", seqNo));

        // 3 uid对应的校验码
        char checkCode = getUIDCheckCode(uid);
        builder.append(checkCode);

        // 4 随机校验码
        String cc = magic.substring(l - 1);
        builder.append(cc);

        return builder.toString();
    }

    private static char getUIDCheckCode(long uid) {
        return ShareCodeUtil.CHARS[(int) uid % CHARS_LEN];
    }

    /**
     * 校验邀请码是否合法
     *
     * @param shareCode 邀请码
     * @return 邀请码合法 true； 邀请码非法 false
     */
    public static boolean verify(String shareCode) {
        if (shareCode.length() != RDC_LEN + 8 + 2 + 2) {
            return false;
        }
        String randStr = shareCode.substring(0, RDC_LEN);
        String hexStr = shareCode.substring(RDC_LEN, RDC_LEN + 8);
        int serialNo = Integer.parseInt(shareCode.substring(RDC_LEN + 8, RDC_LEN + 10), 16);
        char checkCode = shareCode.substring(RDC_LEN + 10, RDC_LEN + 11).charAt(0);
        char randCode = shareCode.substring(RDC_LEN + 11).charAt(0);

        return checkUidHexCode(hexStr, checkCode) && checkRandCode(randStr, randCode);
    }

    private static boolean checkUidHexCode(String hexStr, char checkCode) {
        long uid = Long.parseLong(hexStr, 16);
        return getUIDCheckCode(uid) == checkCode;
    }

    private static boolean checkRandCode(String randStr, char randCode) {
        int checkSum = 0;
        for (int i = 0; i < randStr.length(); i++) {
            char ch = randStr.charAt(i);
            checkSum += ch * Math.pow(100, RDC_LEN - i - 1);
        }
        char checkCode = CHARS[checkSum % CHARS_LEN];
        return Integer.toHexString(checkCode & 0XF).toUpperCase().charAt(0) == randCode;
    }

    /**
     * 从邀请码中提取用户Id
     *
     * @param shareCode 邀请码
     * @return 用户Id
     */
    public static long parseUId(String shareCode) {
        String uidStr = shareCode.substring(RDC_LEN, RDC_LEN + 8);
        return Long.parseLong(uidStr, 16);
    }

    public static int parseSeqNo(String shareCode) {
        String seqNoStr = shareCode.substring(RDC_LEN + 8, RDC_LEN + 10);
        return Integer.parseInt(seqNoStr);
    }

    /**
     * 生成随机数和对应的校验字符
     */
    private static String generateRandomMagic() {
        int checkSum = 0;
        StringBuilder builder = new StringBuilder(RDC_LEN + 1);

        for (int i = 0; i < RDC_LEN; i++) {
            char ch = CHARS[RandUtil.randInt(0, CHARS_LEN - 1)];
            builder.append(ch);
            checkSum += ch * Math.pow(100, RDC_LEN - i - 1);
        }

        char checkLetter = CHARS[checkSum % CHARS_LEN];
        String hexS = Integer.toHexString(checkLetter & 0XF).toUpperCase();
        builder.append(hexS);

        return builder.toString();
    }

    public static String toHexStr(byte[] bytes) {
        int len = bytes.length;
        char[] out = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            out[j++] = HEX_CHARS[(0xF0 & bytes[i]) >>> 4];
            out[j++] = HEX_CHARS[0x0F & bytes[i]];
        }
        return String.valueOf(out);
    }

    public static void main(String[] args) {
        Set<String> codes = new HashSet<>();
        for (int i = 1; i < 8; i++) {
            int seqNo = 1;
            long uid = 1000794;
            String shareCode = ShareCodeUtil.generateCode(uid, seqNo);
            if (codes.contains(shareCode)) {
                System.out.println(String.format("Duplicate share code: %s", shareCode));
                break;
            }
            uid = ShareCodeUtil.parseUId(shareCode);
            boolean isValidCode = ShareCodeUtil.verify(shareCode);
            if (!isValidCode) {
                ShareCodeUtil.verify(shareCode);
            }
            System.out.println(String.format("Generate share code: %s, uid: %s, valid: %s", shareCode, uid, isValidCode));
            codes.add(shareCode);
        }
        ShareCodeUtil.verify("TA00282BAFCCHB");
    }
}
