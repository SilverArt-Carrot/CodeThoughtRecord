package day04;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    /**
     * 字符串
     * */
    public static void main(String[] args) {
        int[] arr = new int[5];
        for (int item : arr) {
            System.out.println(item);
        }
    }

    /**
     * 反转字符串
     *
     * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
     *
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     *
     * 你可以假设数组中的所有字符都是 ASCII 码表中的可打印字符。
     *
     * 示例 1：
     * 输入：["h","e","l","l","o"]
     * 输出：["o","l","l","e","h"]
     *
     * 示例 2：
     * 输入：["H","a","n","n","a","h"]
     * 输出：["h","a","n","n","a","H"]
     *
     */
    public static void learn01(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            char t = s[left];
            s[left] = s[right];
            s[right] = t;
            left++;
            right--;
        }
    }

    /**
     * 反转字符串II
     *
     * 给定一个字符串 s 和一个整数 k，从字符串开头算起, 每计数至 2k 个字符，就反转这 2k 个字符中的前 k 个字符。
     *
     * 如果剩余字符少于 k 个，则将剩余字符全部反转。
     *
     * 如果剩余字符小于 2k 但大于或等于 k 个，则反转前 k 个字符，其余字符保持原样。
     *
     * 示例:
     *
     * 输入: s = "abcdefg", k = 2
     * 输出: "bacdfeg"
     */
    public static String learn02(String s, int k) {
        char[] chars = s.toCharArray();
        boolean flag = true;
        int left = 0;
        for (int i = k; i < chars.length; i += k) {
            int right = i - 1;
            if (flag) {
                swap(chars, left, right);
            }
            flag = !flag;
            left = i;
        }
        if (flag) {
            int right = chars.length - 1;
            swap(chars, left, right);
        }
        return new String(chars);
    }

    public static void swap(char[] chars, int l, int r) {
        while (l < r) {
            char t = chars[l];
            chars[l] = chars[r];
            chars[r] = t;
            l++;
            r--;
        }
    }

    /**
     * 另一种写法，比我写得好
     */
    public String reverseStr(String s, int k) {
        char[] ch = s.toCharArray();
        for(int i = 0;i < ch.length;i += 2 * k){
            int start = i;
            // 判断尾数够不够k个来取决end指针的位置
            int end = Math.min(ch.length - 1,start + k - 1);
            while(start < end){

                char temp = ch[start];
                ch[start] = ch[end];
                ch[end] = temp;

                start++;
                end--;
            }
        }
        return new String(ch);
    }

    /**
     * 替换空格
     *
     * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
     *
     * 示例 1： 输入：s = "We are happy."
     * 输出："We%20are%20happy."
     *
     */
    public static String learn03(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                sb.append("%20");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 翻转字符串里的单词
     *
     * 给定一个字符串，逐个翻转字符串中的每个单词。
     *
     * 示例 1：
     * 输入: "the sky is blue"
     * 输出: "blue is sky the"
     *
     * 示例 2：
     * 输入: "  hello world!  "
     * 输出: "world! hello"
     * 解释: 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
     *
     * 示例 3：
     * 输入: "a good   example"
     * 输出: "example good a"
     * 解释: 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
     *
     */
    public static String learn04(String s) { // 我自己写的
        List<String> collect = Arrays.stream(s.split(" ")).filter(item -> !item.equals("")).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        for (int i = collect.size() - 1; i >= 0; i--) {
            sb.append(collect.get(i));
            if (i != 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    // 和上面的一样，优化了一下
    public static String learn05(String s) {
        String[] split = s.trim().split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = split.length - 1; i >= 0; i--) {
            if (!"".equals(split[i])) {
                sb.append(split[i]);
                if (i != 0) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 我觉得比较好的写法
     */
    public static String learn06(String s) {
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int right = chars.length - 1;
        // 去除空格
        while (chars[i] == ' ') i++;
        while (chars[right] == ' ') right--;
        int left;
        while (right >= i) {
            left = right;
            while (left >= i && chars[left] != ' ') left--;
            sb.append(chars, left + 1, right - left);
            if (left > i) sb.append(" ");
            while (left >= i && chars[left] == ' ') left--;
            right = left;
        }
        return sb.toString();
    }

    /**
     * II.左旋转字符串
     *
     * 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
     *
     * 示例 1：
     * 输入: s = "abcdefg", k = 2
     * 输出: "cdefgab"
     *
     * 示例 2：
     * 输入: s = "lrloseumgh", k = 6
     * 输出: "umghlrlose"
     *
     * 限制：
     * 1 <= k < s.length <= 10000
     *
     */
    public static String learn07(String s, int n) {  // ？？？可以对char数组反转3次
        return s.substring(n) + s.substring(0, n);
    }


    /**
     * 实现 strStr() 函数。(KMP)
     *
     * 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
     *
     * 示例 1: 输入: haystack = "hello", needle = "ll" 输出: 2
     *
     * 示例 2: 输入: haystack = "aaaaa", needle = "bba" 输出: -1
     *
     * 说明: 当 needle 是空字符串时，我们应当返回什么值呢？这是一个在面试中很好的问题。 对于本题而言，当 needle 是空字符串时我们应当返回 0 。这与C语言的 strstr() 以及 Java的 indexOf() 定义相符。
     */
    public static int learn08(String haystack, String needle) {
        int[] next = getNext(needle);
        int j = 0;
        for (int i = 0; i < haystack.length(); i++) {
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) {
                j = next[j - 1];
            }
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++;
            }
            if (j == needle.length()) {
                return i - needle.length() + 1;
            }
        }
        return -1;
    }

    public static int[] getNext(String s) {
        int[] next = new int[s.length()];
        int j = 0;
        next[0] = j;
        for (int i = 1; i < s.length(); i++) {
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = next[j - 1];  // 牛
            }
            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    /**
     * 459.重复的子字符串
     *
     * 给定一个非空的字符串，判断它是否可以由它的一个子串重复多次构成。给定的字符串只含有小写英文字母，并且长度不超过10000。
     *
     * 示例 1:
     * 输入: "abab"
     * 输出: True
     * 解释: 可由子字符串 "ab" 重复两次构成。
     *
     * 示例 2:
     * 输入: "aba"
     * 输出: False
     *
     * 示例 3:
     * 输入: "abcabcabcabc"
     * 输出: True
     * 解释: 可由子字符串 "abc" 重复四次构成。 (或者子字符串 "abcabc" 重复两次构成。)
     */
    public static boolean learn09(String s) {
        int[] next = getNext(s);
        int last = next[next.length - 1];
        int len = s.length() - last;
        return last > 0 && s.length() % len == 0;
    }
}
