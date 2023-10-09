package day10;

public class Main {
    /**
     * 动态规划
     *
     * 动态规划中每一个状态一定是由上一个状态推导出来的，这一点就区分于贪心，
     * 贪心没有状态推导，而是从局部直接选最优的
     *
     * 状态转移公式（递推公式）是很重要，但动规不仅仅只有递推公式。
     *
     * 对于动态规划问题，我将拆解为如下五步曲，这五步都搞清楚了，才能说把动态规划真的掌握了！
     *
     * 1.确定dp数组（dp table）以及下标的含义
     * 2.确定递推公式
     * 3.dp数组如何初始化
     * 4.确定遍历顺序
     * 5.举例推导dp数组
     */
    public static void main(String[] args) {
//        int a, b, c = 3;  // 这样a和b是没有初始化的
//        System.out.println(c);
//        Main main = new Main();
//        int[][] arr = new int[3][3];
//        for (int i = 0; i < arr.length; i++) {
//            for (int j = 0; j < arr[0].length; j++) {
//                System.out.println(arr[i][j]);
//            }
//        }
        int[] weight = {1,3,4};
        int[] value = {15,20,30};
        int bagSize = 4;
        testWeightBagProblem2(weight, value, bagSize);
    }

    /**
     * 斐波那契数
     *
     * 斐波那契数，通常用 F(n) 表示，形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是： F(0) = 0，F(1) = 1 F(n) = F(n - 1) + F(n - 2)，其中 n > 1 给你n ，请计算 F(n) 。
     *
     * 示例 1：
     *
     * 输入：2
     * 输出：1
     * 解释：F(2) = F(1) + F(0) = 1 + 0 = 1
     * 示例 2：
     *
     * 输入：3
     * 输出：2
     * 解释：F(3) = F(2) + F(1) = 1 + 1 = 2
     * 示例 3：
     *
     * 输入：4
     * 输出：3
     * 解释：F(4) = F(3) + F(2) = 2 + 1 = 3
     * 提示：
     *
     * 0 <= n <= 30
     */
    public int fib1(int n) {  // 递归试一试
        if (n <= 1) {
            return n;
        }
        return fib1(n - 1) + fib1(n - 2);
    }
    public int fib2(int n) {  // dp试一试
        if (n <= 1) {
            return n;
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i < dp.length; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
    public int fib3(int n) {  // 只要维护3个数字就ok了
        if (n < 2) return n;
        int a = 0, b = 1, c = 0;
        for (int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }

    /**
     * 爬楼梯
     *
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     *
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     *
     * 注意：给定 n 是一个正整数。
     *
     * 示例 1：
     *
     * 输入： 2
     * 输出： 2
     * 解释： 有两种方法可以爬到楼顶。
     * 1 阶 + 1 阶
     * 2 阶
     * 示例 2：
     *
     * 输入： 3
     * 输出： 3
     * 解释： 有三种方法可以爬到楼顶。
     * 1 阶 + 1 阶 + 1 阶
     * 1 阶 + 2 阶
     * 2 阶 + 1 阶
     */
    public int climbStairs1(int n) {  // 递归过不了，超时了
        if (n <= 2) {
            return n;
        }
        return climbStairs1(n - 1) + climbStairs1(n - 2);
    }
    public int climbStairs2(int n) {  // 递归过不了，超时了
        if (n <= 2) {
            return n;
        }
        int a = 1, b = 2, c = 0;
        for (int i = 3; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }
    public int climbStairs3(int n) {  // 递归过不了，超时了
        if (n <= 2) {
            return n;
        }
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    /**
     * 使用最小花费爬楼梯
     *
     * 旧题目描述：
     *
     * 数组的每个下标作为一个阶梯，第 i 个阶梯对应着一个非负数的体力花费值 cost[i]（下标从 0 开始）。
     *
     * 每当你爬上一个阶梯你都要花费对应的体力值，一旦支付了相应的体力值，你就可以选择向上爬一个阶梯或者爬两个阶梯。
     *
     * 请你找出达到楼层顶部的最低花费。在开始时，你可以选择从下标为 0 或 1 的元素作为初始阶梯。
     *
     * 示例 1：
     *
     * 输入：cost = [10, 15, 20]
     * 输出：15
     * 解释：最低花费是从 cost[1] 开始，然后走两步即可到阶梯顶，一共花费 15 。
     * 示例 2：
     *
     * 输入：cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
     * 输出：6
     * 解释：最低花费方式是从 cost[0] 开始，逐个经过那些 1 ，跳过 cost[3] ，一共花费 6 。
     * 提示：
     *
     * cost 的长度范围是 [2, 1000]。
     * cost[i] 将会是一个整型数据，范围为 [0, 999] 。
     */
    public int minCostClimbingStairs(int[] cost) {
        int[] dp = new int[cost.length];
        dp[0] = cost[0];
        dp[1] = cost[1];
        for (int i = 2; i < dp.length; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i], dp[i - 2] + cost[i]);
        }
        return Math.min(dp[dp.length - 1], dp[dp.length - 2]);
    }

    /**
     * 不同路径
     *
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
     *
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
     *
     * 问总共有多少条不同的路径？
     */
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * 不同路径 II
     *
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
     *
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
     *
     * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            if (obstacleGrid[i][0] == 1) {
                break;
            }
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            if (obstacleGrid[0][j] == 1) {
                break;
            }
            dp[0][j] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
                }
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * 整数拆分
     *
     * 给定一个正整数 n，将其拆分为至少两个正整数的和，并使这些整数的乘积最大化。 返回你可以获得的最大乘积。
     *
     * 示例 1:
     *
     * 输入: 2
     * 输出: 1
     * 解释: 2 = 1 + 1, 1 × 1 = 1。
     * 示例 2:
     *
     * 输入: 10
     * 输出: 36
     * 解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36。
     * 说明: 你可以假设 n 不小于 2 且不大于 58。
     */
//    int integerBreak(int n) {  // 代码随想录
//        vector<int> dp(n + 1);
//        dp[2] = 1;
//        for (int i = 3; i <= n ; i++) {
//            for (int j = 1; j <= i / 2; j++) {
//                dp[i] = max(dp[i], max((i - j) * j, dp[i - j] * j));
//            }
//        }
//        return dp[n];
//    }
    // leetcode 官方动态规划
    // 由于每个正整数对应的最大乘积取决于比它小的正整数对应的最大乘积，因此可以使用动态规划求解
    // https://leetcode.cn/problems/integer-break/solution/zheng-shu-chai-fen-by-leetcode-solution/
    public int integerBreak(int n) {
        int[] dp = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            int curMax = 0;
            for (int j = 1; j < i; j++) {
                curMax = Math.max(curMax, Math.max(j * (i - j), j * dp[i - j]));
            }
            dp[i] = curMax;
        }
        return dp[n];
    }
    public int integerBreak2(int n) {
        int[] dp = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                dp[i] = Math.max(dp[i], Math.max((i - j) * j, dp[i - j] * j));
            }
        }
        return dp[n];
    }

    /**
     * 不同的二叉搜索树
     * 还是需要画图，分析，才能找到递推公式
     */
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[i - j] * dp[j - 1];
            }
        }
        return dp[n];
    }

    /**
     * 01背包问题
     * 有n件物品和一个最多能背重量为w 的背包。第i件物品的重量是weight[i]，得到的价值是value[i] 。
     * 每件物品只能用一次，求解将哪些物品装入背包里物品价值总和最大。
     * int[] weight = {1,3,4};
     * int[] value = {15,20,30};
     * int bagSize = 4;
     */
    public static void testWeightBagProblem(int[] weight, int[] value, int bagSize){
        int[][] dp = new int[value.length][bagSize + 1];

        for (int i = weight[0]; i <= bagSize; i++) {
            dp[0][i] = value[0];
        }

        for (int i = 1; i < weight.length; i++) {
            for (int j = 1; j <= bagSize; j++) {
                if (j < weight[i]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
                }
            }
        }

        // 打印dp数组
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 01背包问题
     * 滚动数组的写法
     * 原二维dp数组
     * 0 15 15 15 15
     * 0 15 15 20 35
     * 0 15 15 20 35
     */
    public static void testWeightBagProblem2(int[] weight, int[] value, int bagWeight){
        int wLen = weight.length;
        //定义dp数组：dp[j]表示背包容量为j时，能获得的最大价值
        int[] dp = new int[bagWeight + 1];
        //遍历顺序：先遍历物品，再遍历背包容量
        for (int i = 0; i < wLen; i++) {
            // 一定要倒序，因为在二维数组来看，使用的是上方和左上的值，也就是上一层的值，倒序遍历不会覆盖上一层的值
            // 如果是正序遍历，覆盖了前面的值就相当于污染了上一层的值
            for (int j = bagWeight; j >= weight[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }
        //打印dp数组
        for (int j = 0; j <= bagWeight; j++) {
            System.out.print(dp[j] + " ");
        }
    }

    /**
     * 分割等和子集
     */
    public boolean canPartition(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return false;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        int[] dp = new int[target + 1];
        for (int i = 0; i < nums.length; i++) {
            for (int j = target; j >= nums[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - nums[i]] + nums[i]);
            }
        }
        return dp[target] == target;
    }

    /**
     * 最后一块石头的重量 II
     */
    public int lastStoneWeightII(int[] stones) {
        int sum = 0;
        for (int stone : stones) {
            sum += stone;
        }
        int target = sum / 2;
        int[] dp = new int[target + 1];
        for (int i = 0; i < stones.length; i++) {
            for (int j = target; j >= stones[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - stones[i]] + stones[i]);
            }
        }
        return sum - 2 * dp[target];
    }
}
