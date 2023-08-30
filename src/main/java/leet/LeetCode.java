package leet;

import day07.TreeNode;

import java.util.*;

public class LeetCode {

    /**
     * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
     * 输入：n = 3
     * 输出：["((()))","(()())","(())()","()(())","()()()"]
     */
    public List<String> generateParenthesis(int n) {
        if (n < 1) {
            return new ArrayList<>();
        }
        backTracking(n, 0, 0);
        return result;
    }

    private void backTracking(int n, int left, int right) {
        if (arr.size() == n * 2) {
            result.add(String.join("", arr));
            return;
        }
        if (left < n) {
            arr.add("(");
            backTracking(n, left + 1, right);
            arr.removeLast();
        }
        if (left > right) {
            arr.add(")");
            backTracking(n, left, right + 1);
            arr.removeLast();
        }
    }

    private LinkedList<String> arr = new LinkedList<>();
    private List<String> result = new ArrayList<>();

    /**
     * 11. 盛最多水的容器
     * 给定一个长度为 n 的整数数组height。有n条垂线，第 i 条线的两个端点是(i, 0)和(i, height[i])。
     *
     * 找出其中的两条线，使得它们与x轴共同构成的容器可以容纳最多的水。
     *
     * 返回容器可以储存的最大水量。
     * n == height.length
     * 2 <= n <= 105
     * 0 <= height[i] <= 104
     */
    public int maxArea(int[] height) {
        int max = 0;
        for (int len = 1; len < height.length; len++) {
            for (int i = 0; i < height.length; i++) {
                int end = i + len;
                if (end >= height.length) {
                    break;
                }
                max = Math.max(max, len * (Math.min(height[i], height[end])));
            }
        }
        return max;
    }

    /**
     * 双指针
     */
    public int maxArea2(int[] height) {
        int left = 0, right = height.length - 1, max = 0;
        while (left < right) {
            max = height[left] < height[right] ?
                    Math.max(max, (right - left) * height[left++]) :
                    Math.max(max, (right - left) * height[right--]);
        }
        return max;
    }

    /**
     * 31. 下一个排列
     * 整数数组的一个 排列 就是将其所有成员以序列或线性顺序排列。
     *
     * 例如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1] 。
     * 整数数组的 下一个排列 是指其整数的下一个字典序更大的排列。更正式地，如果数组的所有排列根据其字典顺序从小到大排列在一个容器中，那么数组的 下一个排列 就是在这个有序容器中排在它后面的那个排列。如果不存在下一个更大的排列，那么这个数组必须重排为字典序最小的排列（即，其元素按升序排列）。
     *
     * 例如，arr = [1,2,3] 的下一个排列是 [1,3,2] 。
     * 类似地，arr = [2,3,1] 的下一个排列是 [3,1,2] 。
     * 而 arr = [3,2,1] 的下一个排列是 [1,2,3] ，因为 [3,2,1] 不存在一个字典序更大的排列。
     * 给你一个整数数组 nums ，找出 nums 的下一个排列。
     *
     * 必须 原地 修改，只允许使用额外常数空间。
     */
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        int j, i = 0;
        boolean flag = false;
        for (j = nums.length - 1; j > 0; j--) {
            i = j - 1;
            if (nums[i] < nums[j]) {
                flag = true;
                break;
            }
        }
        if (flag) {
            for (int k = nums.length - 1; k >= j; k--) {
                if (nums[k] > nums[i]) {
                    int temp = nums[k];
                    nums[k] = nums[i];
                    nums[i] = temp;
                    reverse(nums, j, nums.length - 1);
                    return;
                }
            }
        } else {
            reverse(nums, 0, nums.length - 1);
        }
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int t = nums[left];
            nums[left] = nums[right];
            nums[right] = t;
            left++;
            right--;
        }
    }

    /**
     * 33. 搜索旋转排序数组
     */
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[left] <= nums[mid]) {
                if (nums[left] <= target && nums[mid] > target) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (nums[right] >= target && nums[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
     *
     * 如果数组中不存在目标值 target，返回[-1, -1]。
     *
     */
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }
        int left = binarySearch(nums, target, true);
        int right = binarySearch(nums, target, false) - 1;
        if (left >= 0 && left <= right && right < nums.length && nums[left] == target && nums[right] == target) {
            return new int[]{left, right};
        } else {
            return new int[]{-1, -1};
        }
    }

    public int binarySearch(int[] nums, int target, boolean lower) {
        int left = 0, right = nums.length - 1, res = nums.length;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] > target || (lower && nums[mid] == target)) {
                right = mid - 1;
                res = mid;
            } else {
                left = mid + 1;
            }
        }
        return res;
    }

    /**
     * 48. 旋转图像
     * 给定一个 n×n 的二维矩阵matrix 表示一个图像。请你将图像顺时针旋转 90 度。
     *
     * 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
     */
    // 90度：转置 左右翻转
    // 180度：上下翻转 左右翻转
    // 270度：转置 上下翻转
    public static void rotate(int[][] matrix) {
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int t = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = t;
            }
        }

        for (int i = 0; i < n; i++) {
            int left = 0;
            int right = n - 1;
            while (left < right) {
                int t = matrix[i][left];
                matrix[i][left] = matrix[i][right];
                matrix[i][right] = t;

                left++;
                right--;
            }
        }
    }

    /**
     * 字母异位词分组
     * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
     *
     * 字母异位词 是由重新排列源单词的所有字母得到的一个新单词
     *
     * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        Map<Integer, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String s = new String(chars);

            int hashCode = s.hashCode();  // 多余的，put进去的string key本身就是计算了hashcode
            List<String> list = map.computeIfAbsent(hashCode, k -> new ArrayList<>());

            list.add(str);
        }

        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }

    /**
     * 128. 最长连续序列
     * 中等
     * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
     *
     * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
     *
     * 示例 1：
     *
     * 输入：nums = [100,4,200,1,3,2]
     * 输出：4
     * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
     * 示例 2：
     *
     * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
     * 输出：9
     */
    public static int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int max = 0;
        for (int num : nums) {
            if (set.contains(num - 1)) {
                continue;
            }
            int length = 1;
            int t = num + 1;
            while (set.contains(t)) {
                length++;
                t++;
            }
            max = Math.max(max, length);
        }
        return max;
    }
    public static int longestConsecutive2(int[] nums) {  // hash版，说是动态规划，我太笨看不出来，很nb就完事了
        int max = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (!map.containsKey(num)) {
                // 左边的
                int left = map.getOrDefault(num - 1, 0);
                // 右边的
                int right = map.getOrDefault(num + 1, 0);

                int length = 1 + left + right;
                if (length > max) {
                    max = length;
                }

                // 左右都没有也不影响，加减0了
                map.put(num - left, length);  // 将端点设置值，因为之后出现的数字也只会寻找端点
                map.put(num + right, length);
            }
        }
        return max;
    }

    /**
     * 121. 买卖股票的最佳时机
     * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
     *
     * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
     *
     * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
     * 示例 1：
     *
     * 输入：[7,1,5,3,6,4]
     * 输出：5
     * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
     * 示例 2：
     * 输入：prices = [7,6,4,3,1]
     * 输出：0
     * 解释：在这种情况下, 没有交易完成, 所以最大利润为 0。
     */
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            minPrice = Math.min(price, minPrice);
            maxProfit = Math.max(price - minPrice, maxProfit);
        }
        return maxProfit;
        // int minprice = Integer.MAX_VALUE;
        // int maxprofit = 0;
        // for (int i = 0; i < prices.length; i++) {
        //     if (prices[i] < minprice) {
        //         minprice = prices[i];
        //     } else if (prices[i] - minprice > maxprofit) {
        //         maxprofit = prices[i] - minprice;
        //     }
        // }
        // return maxprofit;
    }

    /**
     * 283. 移动零
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     *
     * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
     * 示例 1:
     *
     * 输入: nums = [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     * 示例 2:
     *
     * 输入: nums = [0]
     * 输出: [0]
     */
    public static void moveZeroes(int[] nums) {  // 双指针
        int length = nums.length;
        int left = 0;
        int right = 0;

        while (right < length) {
            if (nums[right] != 0) {
                int t = nums[left];
                nums[left] = nums[right];
                nums[right] = t;
                left++;
            }
            right++;
        }
    }

    /**
     * 136. 只出现一次的数字
     * 给你一个 非空 整数数组 nums ，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     *
     * 你必须设计并实现线性时间复杂度的算法来解决此问题，且该算法只使用常量额外空间。
     *
     * 示例 1 ：
     *
     * 输入：nums = [2,2,1]
     * 输出：1
     * 示例 2 ：
     *
     * 输入：nums = [4,1,2,1,2]
     * 输出：4
     * 示例 3 ：
     *
     * 输入：nums = [1]
     * 输出：1
     */
    public int singleNumber(int[] nums) {  // set法，不满足额外空间
        // 因为最多只出现两次
        HashSet<Integer> set = new HashSet<>();

        for (int num : nums) {
            if (set.contains(num)) {
                set.remove(num);
            } else {
                set.add(num);
            }
        }

        return set.isEmpty() ? -1 : set.iterator().next();
    }
    /**
     * 解题思路
     * 标签：位运算
     * 本题根据题意，线性时间复杂度 O(n)，很容易想到使用 Hash 映射来进行计算，遍历一次后结束得到结果，但是在空间复杂度上会达到 O(n)，需要使用较多的额外空间
     * 既满足时间复杂度又满足空间复杂度，就要提到位运算中的异或运算 XOR，主要因为异或运算有以下几个特点：
     * 一个数和 0 做 XOR 运算等于本身：a⊕0 = a
     * 一个数和其本身做 XOR 运算等于 0：a⊕a = 0
     * XOR 运算满足交换律和结合律：a⊕b⊕a = (a⊕a)⊕b = 0⊕b = b
     * 故而在以上的基础条件上，将所有数字按照顺序做异或运算，最后剩下的结果即为唯一的数字
     * 时间复杂度：O(n)，空间复杂度：O(1)
     */
    public static int singleNumber2(int[] nums) {
        int ans = 0;
        for(int num: nums) {
            ans ^= num;
        }
        return ans;
    }

    /**
     * 3. 无重复字符的最长子串
     * 中等
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * 示例 1:
     *
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     *
     * 输入: s = "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     *
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     */
    public static int lengthOfLongestSubstring(String s) {
        int max = 0;
        int left = 0; // 记录不重复子串的左边起始位置
        Map<Character, Integer> map = new HashMap<>();  // key 是不重复字符， value 是字符在字符串中的索引位置
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {  // 如果出现重复字符，则比较left和重复字符索引位+1，取最大值，为了防止abba类似字符串遇到最后的a时left回退回去
                left = Math.max(left, map.get(c) + 1);
            }
            map.put(c, i);
            max = Math.max(max, i - left + 1);
        }
        return max;
    }

    /**
     * 438. 找到字符串中所有字母异位词
     * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
     *
     * 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
     * 示例 1:
     *
     * 输入: s = "cbaebabacd", p = "abc"
     * 输出: [0,6]
     * 解释:
     * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
     * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
     *  示例 2:
     *
     * 输入: s = "abab", p = "ab"
     * 输出: [0,1,2]
     * 解释:
     * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
     * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
     * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
     */
    public static List<Integer> findAnagrams(String s, String p) { // 超时了哥
        List<Integer> result = new ArrayList<>();
        int length = p.length();
        if (s.length() < length) {
            return result;
        }

        char[] chars = p.toCharArray();
        Arrays.sort(chars);
        String p0 = new String(chars);

        LinkedList<Character> list = new LinkedList<>();
        for (int i = 0; i < length - 1; i++) {
            list.add(s.charAt(i));
        }

        for (int i = length - 1; i < s.length(); i++) {
            list.add(s.charAt(i));

            char[] tmp = new char[list.size()];
            for (int j = 0; j < list.size(); j++) {
                tmp[j] = list.get(j);
            }

            Arrays.sort(tmp);
            String tepS = new String(tmp);
            if (tepS.equals(p0)) {
                result.add(i - length + 1);
            }

            list.removeFirst();
        }
        return result;
    }
    public static List<Integer> findAnagrams2(String s, String p) {  // 没超时，但是写的罗里吧嗦
        List<Integer> result = new ArrayList<>();
        int pl = p.length();
        if (s.length() < pl) {
            return result;
        }

        int[] pMap = new int[26];
        for (int i = 0; i < p.length(); i++) {
            pMap[p.charAt(i) - 'a']++;
        }

        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < pl - 1; i++) {
            putIntoMap(map, s.charAt(i));
        }

        boolean isMatch = false;
        Character intoC = null;
        Character removeC = null;

        for (int i = pl - 1; i < s.length(); i++) {
            char ic = s.charAt(i);
            putIntoMap(map, ic);
            intoC = ic;

            if (isMatch) {
                Integer c1 = map.get(intoC);
                Integer c2 = map.get(removeC);
                isMatch = (c1 == pMap[intoC - 'a'] && c2 == pMap[removeC - 'a']);
            } else {
                isMatch = match(map, pMap);
            }

            if (isMatch) {
                result.add(i - pl + 1);
            }

            char rc = s.charAt(i - pl + 1);
            removeFromMap(map, rc);
            removeC = rc;
        }
        return result;
    }
    public List<Integer> findAnagrams3(String s, String p) {  // LeetCode 官方滑动窗口
        int sLen = s.length(), pLen = p.length();

        if (sLen < pLen) {
            return new ArrayList<>();
        }

        List<Integer> ans = new ArrayList<>();
        int[] sCount = new int[26];
        int[] pCount = new int[26];
        for (int i = 0; i < pLen; ++i) {
            ++sCount[s.charAt(i) - 'a'];
            ++pCount[p.charAt(i) - 'a'];
        }

        if (Arrays.equals(sCount, pCount)) {
            ans.add(0);
        }

        for (int i = 0; i < sLen - pLen; ++i) {
            --sCount[s.charAt(i) - 'a'];
            ++sCount[s.charAt(i + pLen) - 'a'];

            if (Arrays.equals(sCount, pCount)) {
                ans.add(i + 1);
            }
        }

        return ans;
    }

    public static void putIntoMap(Map<Character, Integer> map, char c) {
        Integer count = map.getOrDefault(c, 0);
        count++;
        map.put(c, count);
    }

    public static void removeFromMap(Map<Character, Integer> map, char c) {
        Integer count = map.get(c);
        if (count != null) {
            count--;
            map.put(c, count);
        }
    }

    public static boolean match(Map<Character, Integer> map, int[] pMap) {
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();
            if (pMap[key - 'a'] != value) {
                return false;
            }
        }
        return true;
    }

    /**
     * 1749. 任意子数组和的绝对值的最大值
     * 给你一个整数数组 nums 。一个子数组 [numsl, numsl+1, ..., numsr-1, numsr] 的 和的绝对值 为 abs(numsl + numsl+1 + ... + numsr-1 + numsr) 。
     *
     * 请你找出 nums 中 和的绝对值 最大的任意子数组（可能为空），并返回该 最大值 。
     *
     * abs(x) 定义如下：
     *
     * 如果 x 是负整数，那么 abs(x) = -x 。
     * 如果 x 是非负整数，那么 abs(x) = x 。
     * 示例 1：
     *
     * 输入：nums = [1,-3,2,3,-4]
     * 输出：5
     * 解释：子数组 [2,3] 和的绝对值最大，为 abs(2+3) = abs(5) = 5 。
     * 示例 2：
     *
     * 输入：nums = [2,-5,1,-4,3,-2]
     * 输出：8
     * 解释：子数组 [-5,1,-4] 和的绝对值最大，为 abs(-5+1-4) = abs(-8) = 8 。
     */
    public static int maxAbsoluteSum(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = nums[i];
            max = Math.max(max, Math.abs(sum));
            for (int j = i + 1; j < nums.length; j++) {
                sum += nums[j];
                max = Math.max(max, Math.abs(sum));
            }
        }
        return max;
    }
    /**
     * 问题可以转换成求 最大子数组和 以及「最小子数组和的绝对值（相反数）」，这二者中的最大值就是答案。
     */
    public static int maxAbsoluteSum2(int[] nums) {
        int result = 0;
        int minSum = 0;
        int maxSum = 0;
        for (int num : nums) {
            maxSum += num;
            if (maxSum <= 0) {
                maxSum = 0;
            }

            minSum += num;
            if (minSum >= 0) {
                minSum = 0;
            }

            result = Math.max(result, Math.max(maxSum, -minSum));
        }
        return result;
    }

    /**
     * 560. 和为 K 的子数组
     * 相关企业
     * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的连续子数组的个数 。
     *
     * 示例 1：
     *
     * 输入：nums = [1,1,1], k = 2
     * 输出：2
     * 示例 2：
     *
     * 输入：nums = [1,2,3], k = 3
     * 输出：2
     *
     * 1 <= nums.length <= 2 * 104
     * -1000 <= nums[i] <= 1000
     * -107 <= k <= 107
     *
     * 这个问题可以通过计算前缀和的方式来解决。我们维护一个sum变量来记录当前的累积和，
     * 并使用一个Map来存储每个前缀和出现的频率。在遍历数组的过程中，我们不断更新sum和sumFrequency，
     * 并检查是否存在之前的前缀和等于sum - k，如果存在，说明在那个位置到当前位置的子数组和为k，
     * 因此将计数增加。最后，返回计数值即可。
     */
    public static int subarraySum(int[] nums, int k) { // i can't understand
        int count = 0, pre = 0;
        HashMap <Integer, Integer> mp = new HashMap<>();
        mp.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            pre += nums[i];
            if (mp.containsKey(pre - k)) {
                count += mp.get(pre - k);
            }
            mp.put(pre, mp.getOrDefault(pre, 0) + 1);
        }
        return count;
    }

    /**
     * 617. 合并二叉树
     * 给你两棵二叉树： root1 和 root2 。
     *
     * 想象一下，当你将其中一棵覆盖到另一棵之上时，两棵树上的一些节点将会重叠（而另一些不会）。你需要将这两棵树合并成一棵新二叉树。合并的规则是：如果两个节点重叠，那么将这两个节点的值相加作为合并后节点的新值；否则，不为 null 的节点将直接作为新二叉树的节点。
     *
     * 返回合并后的二叉树。
     *
     * 注意: 合并过程必须从两个树的根节点开始。
     *
     *
     * 输入：root1 = [1,3,2,5], root2 = [2,1,3,null,4,null,7]
     * 输出：[3,4,5,5,4,null,7]
     * 示例 2：
     *
     * 输入：root1 = [1], root2 = [1,2]
     * 输出：[2,2]
     */
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }

        TreeNode mergedNode = new TreeNode(root1.val + root2.val);
        mergedNode.left = mergeTrees(root1.left, root2.left);
        mergedNode.right = mergeTrees(root1.right, root2.right);
        return mergedNode;
    }

    /**
     * 2682. 找出转圈游戏输家
     * n 个朋友在玩游戏。这些朋友坐成一个圈，按 顺时针方向 从 1 到 n 编号。从第 i 个朋友的位置开始顺时针移动 1 步会到达第 (i + 1) 个朋友的位置（1 <= i < n），而从第 n 个朋友的位置开始顺时针移动 1 步会回到第 1 个朋友的位置。
     *
     * 游戏规则如下：
     *
     * 第 1 个朋友接球。
     *
     * 接着，第 1 个朋友将球传给距离他顺时针方向 k 步的朋友。
     * 然后，接球的朋友应该把球传给距离他顺时针方向 2 * k 步的朋友。
     * 接着，接球的朋友应该把球传给距离他顺时针方向 3 * k 步的朋友，以此类推。
     * 换句话说，在第 i 轮中持有球的那位朋友需要将球传递给距离他顺时针方向 i * k 步的朋友。
     *
     * 当某个朋友第 2 次接到球时，游戏结束。
     *
     * 在整场游戏中没有接到过球的朋友是 输家 。
     *
     * 给你参与游戏的朋友数量 n 和一个整数 k ，请按升序排列返回包含所有输家编号的数组 answer 作为答案。
     *
     * 示例 1：
     *
     * 输入：n = 5, k = 2
     * 输出：[4,5]
     * 解释：以下为游戏进行情况：
     * 1）第 1 个朋友接球，第 1 个朋友将球传给距离他顺时针方向 2 步的玩家 —— 第 3 个朋友。
     * 2）第 3 个朋友将球传给距离他顺时针方向 4 步的玩家 —— 第 2 个朋友。
     * 3）第 2 个朋友将球传给距离他顺时针方向 6 步的玩家 —— 第 3 个朋友。
     * 4）第 3 个朋友接到两次球，游戏结束。
     * 示例 2：
     *
     * 输入：n = 4, k = 4
     * 输出：[2,3,4]
     * 解释：以下为游戏进行情况：
     * 1）第 1 个朋友接球，第 1 个朋友将球传给距离他顺时针方向 4 步的玩家 —— 第 1 个朋友。
     * 2）第 1 个朋友接到两次球，游戏结束。
     *
     * 提示：
     * 1 <= k <= n <= 50
     */
    public static int[] circularGameLosers(int n, int k) {  // 硬把游戏模拟了一遍，最多只有50个人，其实可以用数组而不是set
        Set<Integer> set = new HashSet<>();
        int cur = 1;
        int now = 1;
        set.add(now);
        while (true) {
            now += (cur * k) % n;
            if (now > n) {
                now %= n;
            }
            if (set.contains(now)) {
                break;
            } else {
                set.add(now);
            }
            cur++;
        }
        int index = 0;
        int[] answer = new int[n - set.size()];
        for (int i = 1; i <= n; i++) {
            if (!set.contains(i)) {
                answer[index++] = i;
            }
        }
        return answer;
    }

    /**
     * 56. 合并区间
     * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
     * 请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
     *
     * 示例 1：
     * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
     * 输出：[[1,6],[8,10],[15,18]]
     * 解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
     * 示例 2：
     *
     * 输入：intervals = [[1,4],[4,5]]
     * 输出：[[1,5]]
     * 解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。
     *
     * 提示：
     * 1 <= intervals.length <= 104
     * intervals[i].length == 2
     * 0 <= starti <= endi <= 104
     *
     * 我们首先将区间按照起始值进行排序，然后遍历每个区间，如果当前区间的起始值小于等于前一个区间的结束值，
     * 说明它们有重叠，我们更新当前区间的结束值为较大的结束值。如果没有重叠，我们将当前区间添加到合并后的区间列表中。
     * 最后，将合并后的区间列表转换为二维数组并返回。
     */
    public static int[][] merge2(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        ArrayList<int[]> list = new ArrayList<>();
        for (int[] interval : intervals) {
            if (list.isEmpty()) {
                list.add(interval);
            } else {
                int[] preInterval = list.get(list.size() - 1);
                if (interval[0] <= preInterval[1]) {
                    preInterval[1] = Math.max(preInterval[1], interval[1]);
                } else {
                    list.add(interval);
                }
            }
        }

        int[][] result = new int[list.size()][];
        int i = 0;
        for (int[] interval : list) {
            result[i++] = interval;
        }
        return result;
    }

    /**
     * 最小路径和
     * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     *
     * 说明：每次只能向下或者向右移动一步。
     *
     *
     * 输入：grid = [[1,3,1],
     *              [1,5,1],
     *              [4,2,1]]
     * 输出：7
     * 解释：因为路径 1→3→1→1→1 的总和最小。
     * 示例 2：
     *
     * 输入：grid = [[1,2,3],
     *              [4,5,6]]
     * 输出：12
     * 提示：
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 200
     * 0 <= grid[i][j] <= 200
     */
    public static int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = grid[i][j];
                } else if (i == 0) {
                    dp[i][j] = dp[i][j - 1] + grid[i][j];
                } else if (j == 0) {
                    dp[i][j] = dp[i - 1][j] + grid[i][j];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
                }
            }
        }

        return dp[m - 1][n - 1];
    }

    /**
     * 75. 颜色分类
     * 给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums ，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
     * 我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
     * 必须在不使用库内置的 sort 函数的情况下解决这个问题。
     * 示例 1：
     *
     * 输入：nums = [2,0,2,1,1,0]
     * 输出：[0,0,1,1,2,2]
     * 示例 2：
     *
     * 输入：nums = [2,0,1]
     * 输出：[0,1,2]
     *
     * 提示：
     *
     * n == nums.length
     * 1 <= n <= 300
     * nums[i] 为 0、1 或 2
     *
     * 在遍历数组的过程中，我们维护两个指针low和high，分别表示0和2的位置。同时，我们使用current指针遍历数组，根据不同的情况进行交换操作：
     *
     * 如果nums[current]等于0，说明找到了一个红色，将其交换到数组的前面，并将low和current指针都向后移动一位。
     *
     * 如果nums[current]等于2，说明找到了一个蓝色，将其交换到数组的后面，并将high指针向前移动一位，但current指针不需要移动，因为交换后当前位置的颜色未知。
     *
     * 如果nums[current]等于1，说明找到了一个白色，不需要交换，将current指针向后移动一位。
     *
     * 两边都是0 和 2，那中间自然就是 1 了
     */
    public static void sortColors(int[] nums) {
        int low = 0;
        int high = nums.length - 1;
        int cur = 0;

        while (cur <= high) {
            if (nums[cur] == 0) {
                swap(nums, low, cur);
                low++;
                cur++;
            } else if (nums[cur] == 2) {
                swap(nums, high, cur);
                high--;
            } else {
                cur++;
            }
        }
    }

    public static void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    /**
     * 78. 子集
     * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
     *
     * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
     *
     * 示例 1：
     *
     * 输入：nums = [1,2,3]
     * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     * 示例 2：
     *
     * 输入：nums = [0]
     * 输出：[[],[0]]
     *
     * 提示：
     *
     * 1 <= nums.length <= 10
     * -10 <= nums[i] <= 10
     * nums 中的所有元素 互不相同
     */
    private final List<List<Integer>> sets = new ArrayList<>();
    private final LinkedList<Integer> set = new LinkedList<>();

    public List<List<Integer>> subsets(int[] nums) {
        backtracking(0, nums);
        return sets;
    }

    private void backtracking(int index, int[] nums) {
        sets.add(new ArrayList<>(set));
        if (set.size() == nums.length) {
            return;
        }
        for (int i = index; i < nums.length; i++) {
            set.add(nums[i]);
            backtracking(i + 1, nums);
            set.removeLast();
        }
    }

    /**
     * 79. 单词搜索
     * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
     * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
     *
     * 示例 1：
     * 输入：board = [["A","B","C","E"],
     *              ["S","F","C","S"],
     *              ["A","D","E","E"]], word = "ABCCED"
     * 输出：true
     *
     * 示例 2：
     * 输入：board = [["A","B","C","E"],
     *              ["S","F","C","S"],
     *              ["A","D","E","E"]], word = "SEE"
     * 输出：true
     *
     * 示例 3：
     * 输入：board = [["A","B","C","E"],
     *              ["S","F","C","S"],
     *              ["A","D","E","E"]], word = "ABCB"
     * 输出：false
     *
     * 提示：
     * m == board.length
     * n = board[i].length
     * 1 <= m, n <= 6
     * 1 <= word.length <= 15
     * board 和 word 仅由大小写英文字母组成
     */
    public boolean exist(char[][] board, String word) {  // dfs + 回溯【我写不来】
        return false;
    }

    /**
     * winter@posturemedia.com----snowflake1289
     * caralaisure2@gmail.com----Rylee1015!
     * claoliv@hotmail.com----Cl@u1906
     * aidan@heggerud.com----,Toasty12
     * contato@thiagofranco.com----Thi@go3003
     */
    public static void main(String[] args) {
//        int[][] intervals = new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}};
//        int[][] merge = merge2(intervals);
//        for (int[] item : merge) {
//            System.out.println(Arrays.toString(item));
//        }

//        int[] nums = new int[]{2,0,2,1,1,0};
//        sortColors(nums);
//
//        System.out.println(Arrays.toString(nums));
    }
}
