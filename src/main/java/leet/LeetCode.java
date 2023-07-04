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
    public void rotate(int[][] matrix) {
//        int length = matrix.length;
//        for (int edge = 0; edge < length / 2; edge++) {
//            for (int i = edge; i < length - edge; i++) {
//                int temp = matrix[i][edge];
//                matrix[i][edge] =
//            }
//        }
    }

    public static void main(String[] args) {
        LeetCode leetCode = new LeetCode();


//
//        int[] heights = new int[]{1,8,6,2,5,4,8,3,7};
//        System.out.println(leetCode.maxArea2(heights));

        int[] nums = new int[]{2, 2, 0, 4, 3, 1};
        leetCode.nextPermutation(nums);
        Arrays.stream(nums).forEach(System.out::println);
    }
}
