package com.example;

public class KMP {

    public static void main(String[] args) {
//        System.err.println(index_KMP("aaaabcde".toCharArray(), "aaaaax".toCharArray(), 0));
//        System.err.println(index_KMP("abababcabcaabca".toCharArray(), "bcabc".toCharArray(), 0));
//        System.err.println(Arrays.toString(get_next("abcdabd".toCharArray())));
//        System.err.println(Arrays.toString(get_next("ababaaaba".toCharArray())));
//        System.err.println(Arrays.toString(get_next("ababaaaba".toCharArray())));
//        System.err.println(Arrays.toString(get_nextval("ababaaaba".toCharArray())));
//        System.err.println(index_KMP("baaaaaaaaaaaaaaaaaaaaaaaaaaaabcdeaaaaaaaaaaaaaaaaaaaaaaaaaaaac".toCharArray(), "aaaaaaaaaaaaaaaaaaaaaaaaaaaac".toCharArray(), 0));
        System.err.println(index_KMP("abacababc".toCharArray(), "abab".toCharArray(), 0));
    }

    // 非常规模式,有小修改,便于理解算法所改.
    public static int[] get_next_new(char[] str) {
        int i = 1;
        int j = 0;
        int[] next = new int[str.length];
        next[0] = 0;
        while (i < str.length) {
            if (str[i] == (str[j])) {   // 匹配记录下当前匹配的位置
                ++j;
                next[i] = j;
                ++i;
            } else if (j == 0) {  // 完全不匹配,跳至下一个字符
                next[i] = j;
                ++j;
                ++i;
            } else {
                // 移动位数 = 已匹配的字符数 - 对应的部分匹配值
                // 已匹配的字符数为: j;
                // 对应部分匹配值为: kmp[j - 1];
                // 移动位数为: j - (kmp[j - 1]);
                // 新的索引 = 旧索引 - 移动位数;
                // j = j - (j - (kmp[j - 1]));
                // 最后简化结果如下
                j = next[j - 1];  // 回溯至合适的位置
            }
        }
        return next;
    }

    public static int[] get_nextval_new(char[] s) {
        int[] nextval = get_next_new(s);
        return null;
    }

    public static int index_KMP_new(char[] s, char[] t, int pos) {
        int c = 0;
        int i = pos;
        int j = 0;
        int[] next = get_next(t);
        while (i < s.length && j < t.length) {
            c++;
            if (s[i] == t[j]) {
                i++;
                j++;
            } else if (j == 0) {
                i++;
            } else {
                // 移动位数 = 已匹配的字符数 - 对应的部分匹配值
                // 已匹配的字符数为: j;
                // 对应部分匹配值为: next[j - 1];
                // 移动位数为: j - (next[j - 1]);
                // 新的索引 = 旧索引 - 移动位数;
                // j = j - (j - (next[j - 1]));
                j = next[j - 1];
            }
        }
        if (j == t.length) {
            System.err.println(c);
            return i - t.length;
        }
        System.err.println(c);
        return 0;
    }


    public static int[] get_next(char[] str) {
        int i = 0;
        int j = -1;
        int[] nextval = new int[str.length];
        nextval[0] = -1;
        while (i < str.length - 1) {
            if (j == -1 || str[i] == (str[j])) {   // 匹配记录下当前匹配的位置
                ++j;
                ++i;
                nextval[i] = j;
            } else {
                j = nextval[j];  // 回溯至合适的位置
            }
        }
        return nextval;
    }

    public static int[] get_nextval(char[] str) {
        int i = 0;
        int j = -1;
        int[] nextval = new int[str.length];
        nextval[0] = -1;
        while (i < str.length - 1) {
            if (j == -1 || str[i] == (str[j])) {   // 匹配记录下当前匹配的位置
                ++j;
                ++i;
                nextval[i] = j;
                if (str[i] != str[j]) {
                    nextval[i] = j;
                } else {
                    nextval[i] = nextval[j];
                }
            } else {
                j = nextval[j];  // 回溯至合适的位置
            }
        }
        return nextval;
    }

    public static int index_KMP(char[] s, char[] t, int pos) {
        int c = 0;
        int i = pos;
        int j = 0;
        int[] next = get_nextval(t);
        while (i < s.length && j < t.length) {
            c++;
            if (j == -1 || s[i] == t[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == t.length) {
            System.err.println(c);
            return i - t.length;
        }
        System.err.println(c);
        return 0;
    }

}
