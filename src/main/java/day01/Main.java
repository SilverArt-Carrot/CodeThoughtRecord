package day01;

/**
 * 数组相关
 */
public class Main {
    public static void main(String[] args) { }

    /**
     * 二分查找
     */
    public static int test01(int[] arr, int target) {
        if (arr[0] < target || arr[arr.length - 1] > target) {
            return -1;
        }
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] < target) {
                left = mid + 1;
            } else if (arr[mid] > target) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 移除数组中的val
     * 你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
     * 不要使用额外的数组空间，你必须仅使用O(1)额外空间并原地修改输入数组。
     * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     */
    public static int test02(int[] arr, int val) {
        int slow = 0;
        for (int fast = 0; fast < arr.length; fast++) {
            if (arr[fast] != val) {
                arr[slow++] = arr[fast];
            }
        }
        return slow;
    }

    /**
     * 给你一个按 递增顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 递增顺序 排序。
     * 第一种解法就是暴力解法，全部平方都排序一边
     * 第二种双指针
     */
    public static int[] test03(int[] arr) {
        int left = 0, right = arr.length - 1;
        int index = arr.length - 1;
        int[] result = new int[arr.length];

        while (left <= right) {
            int l = arr[left] * arr[left];
            int r = arr[right] * arr[right];

            if (l < r) {
                result[index] = r;
                right--;
            } else {
                result[index] = l;
                left++;
            }
            index--;
        }
        return result;
    }

    /**
     * 给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的 连续 子数组，
     * 并返回其长度。如果不存在符合条件的子数组，返回 0。
     * 示例：
     * 输入：s = 7, nums = [2,3,1,2,4,3] 输出：2 解释：子数组 [4,3] 是该条件下的长度最小的子数组。
     */
    public static int test04(int[] arr, int val) {
        int sum = 0, start = 0;
        int len = Integer.MAX_VALUE;
        for (int end = 0; end < arr.length; end++) {
            sum += arr[end];
            while (sum >= val) {
                if (sum - arr[start] >= val) {
                    sum -= arr[start++];
                } else {
                    len = Math.min(len, (end - start) + 1);
                    break;
                }
            }
        }
        return len == Integer.MAX_VALUE ? 0 : len;
    }

    /**
     * 螺旋矩阵
     */
    public static int[][] learn06(int num) {
        int[][] result = new int[num][num];
        int i = 0, j = 0, edge = 0;
        int val = 1, total = num * num;

        while (val <= total) {
            while (j < num - edge) {
                result[i][j++] = val++;
            }
            j--;
            i++;

            while (i < num - edge) {
                result[i++][j] = val++;
            }
            i--;
            j--;

            while (j >= edge) {
                result[i][j--] = val++;
            }
            j++;
            i--;

            while (i > edge) {
                result[i--][j] = val++;
            }
            i++;
            j++;

            edge++;
        }

        return result;
    }
}
