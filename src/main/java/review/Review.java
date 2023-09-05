package review;

public class Review {
    public static void main(String[] args) {

    }

    // 二分
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] > target) {
                r = mid - 1;
            } else if (nums[mid] < target) {
                l = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    // 移除元素
    public int removeElement(int[] nums, int val) {
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != val) {
                nums[slow++] = nums[fast];
            }
            fast++;
        }
        return slow;
    }

    // 有序数组的平方
    public int[] sortedSquares(int[] nums) {
        int[] result = new int[nums.length];

        int l = 0, r = nums.length - 1, i = nums.length - 1;
        while (l <= r) {
            int left = nums[l] * nums[l];
            int right = nums[r] * nums[r];

            if (left > right) {
                result[i--] = left;
                l++;
            } else {
                result[i--] = right;
                r--;
            }
        }
        return result;
    }

    // 长度最小的子数组
    public int minSubArrayLen(int s, int[] nums) {
        int left = 0, sum = 0, len = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            while (sum >= s) {
                len = Math.min(len, i - left + 1);
                sum -= nums[left++];
            }
        }
        return len == Integer.MAX_VALUE ? 0 : len;
    }

    // 螺旋矩阵
    public int[][] generateMatrix(int n) {
        int i = 0, j = 0;
        int num = 1;
        int edge = 0;
        int[][] matrix = new int[n][n];

        while (num <= n * n) {

            while (j < n - edge) {
                matrix[i][j++] = num++;
            }
            j--;
            i++;

            while (i < n - edge) {
                matrix[i++][j] = num++;
            }
            i--;
            j--;

            while (j >= edge) {
                matrix[i][j--] = num++;
            }
            j++;
            i--;

            while (i >= edge + 1) {
                matrix[i--][j] = num++;
            }
            i++;
            j++;

            edge++;
        }

        return matrix;
    }
}
