package com.news.common.util;

import com.google.common.base.Strings;

public class Hex64 {
    private static char[] rDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '_', '-'};

    public Hex64() {
    }

    public static String parse64Encode(long value) {
        long longPositive = Math.abs(value);
        int radix = 64;
        char[] outDigits = new char[65];

        int digitIndex;
        for(digitIndex = 0; digitIndex <= 64 && longPositive != 0L; ++digitIndex) {
            outDigits[outDigits.length - digitIndex - 1] = rDigits[(int)(longPositive % (long)radix)];
            longPositive /= (long)radix;
        }

        return new String(outDigits, outDigits.length - digitIndex, digitIndex);
    }

    public static long parse64Decode(String value) throws Exception {
        int fromBase = 64;
        value = value.trim();
        if (Strings.isNullOrEmpty(value)) {
            return 0L;
        } else {
            String sDigits = new String(rDigits, 0, fromBase);
            long result = 0L;

            for(int i = 0; i < value.length(); ++i) {
                if (sDigits.indexOf(value.charAt(i)) < 0) {
                    throw new Exception(String.format("The argument \"{0}\" is not in {1} system", value.charAt(i), Integer.valueOf(fromBase)));
                }

                try {
                    int index = 0;

                    for(int xx = 0; xx < rDigits.length; ++xx) {
                        if (rDigits[xx] == value.charAt(value.length() - i - 1)) {
                            index = xx;
                        }
                    }

                    result += (long)Math.pow((double)fromBase, (double)i) * (long)index;
                } catch (Exception var8) {
                    var8.printStackTrace();
                }
            }

            return result;
        }
    }
}
