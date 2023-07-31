package leet;

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
    public void moveZeroes(int[] nums) {

    }


    public static void main(String[] args) {
//        LeetCode leetCode = new LeetCode();
//
////        int[] heights = new int[]{1,8,6,2,5,4,8,3,7};
////        System.out.println(leetCode.maxArea2(heights));
//
//        int[] nums = new int[]{2, 2, 0, 4, 3, 1};
//        leetCode.nextPermutation(nums);
//        Arrays.stream(nums).forEach(System.out::println);

//        int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                System.out.print(matrix[i][j] + " ");
//            }
//            System.out.println();
//        }
//
//        rotate(matrix);
//
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                System.out.print(matrix[i][j] + " ");
//            }
//            System.out.println();
//        }

//        String[] strs = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
//
//        List<List<String>> lists = groupAnagrams(strs);
//
//        for (List<String> list : lists) {
//            System.out.println(list);
//        }
        // HashTable 不能有null的key 和 value，HashMap 可以

//        Map<String, String> map = new HashMap<>();
//        String put = map.put(null, "1");  // put是老值，没有就是null
//        System.out.println(map.get(null));
//        System.out.println(put);
//
//        String put1 = map.put("1", null);
//        System.out.println(map.get("1"));
//        System.out.println(put1);

//        int[] nums = new int[]{0,3,7,2,5,8,4,6,0,1};
//        int i = longestConsecutive(nums);
//        System.out.println(i);

//        int[] nums = new int[]{100,4,200,1,3,2};
//        int i = longestConsecutive2(nums);
//        System.out.println(i);
    }
}
