package day09;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class Main {
    /**
     * 贪心
     *
     * 贪心的本质是选择每一阶段的局部最优，从而达到全局最优
     * 动态规划中每一个状态一定是由上一个状态推导出来的，这一点就区分于贪心，贪心没有状态推导，而是从局部直接选最优的
     *
     * 手动模拟一下感觉可以局部最优推出整体最优，而且想不到反例，那么就试一试贪心。
     * 贪心没有套路，说白了就是常识性推导加上举反例
     * 所以这也是为什么很多同学通过（accept）了贪心的题目，但都不知道自己用了贪心算法，
     * 因为贪心有时候就是常识性的推导，所以会认为本应该就这么做！
     */
    public static void main(String[] args) {
//        int[] profits = new int[]{7,1,5,3,6,4};
        int[] profits = new int[]{5,2,3,2,6,6,2,9,1,0,7,4,5,0};
        Main main = new Main();
        int i = main.maxProfit(profits);
        System.out.println(i);

        int i1 = main.largestSumAfterKNegations(profits, 56);
        System.out.println(i1);
    }

    /**
     * 分发饼干
     *
     * 假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。
     *
     * 对每个孩子 i，都有一个胃口值 g[i]，这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 j，都有一个尺寸 s[j] 。如果 s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i ，这个孩子会得到满足。你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。
     *
     * 示例 1:
     *
     * 输入: g = [1,2,3], s = [1,1]
     * 输出: 1 解释:你有三个孩子和两块小饼干，3个孩子的胃口值分别是：1,2,3。虽然你有两块小饼干，由于他们的尺寸都是1，你只能让胃口值是1的孩子满足。所以你应该输出1。
     * 示例 2:
     *
     * 输入: g = [1,2], s = [1,2,3]
     * 输出: 2
     * 解释:你有两个孩子和三块小饼干，2个孩子的胃口值分别是1,2。你拥有的饼干数量和尺寸都足以让所有孩子满足。所以你应该输出2.
     */
//    这里的局部最优就是大饼干喂给胃口大的，充分利用饼干尺寸喂饱一个，全局最优就是喂饱尽可能多的小孩。
    public int findContentChildren(int[] g, int[] s) {  // 用大的先去喂饱大的
        Arrays.sort(g);
        Arrays.sort(s);
        int j = 0;
        int total = 0;
        for (int i = 0; i < s.length; i++) {
            if (j >= g.length) {
                break;
            }
            if (s[i] >= g[j]) {
                j++;
                total++;
            }
        }
        return total;
    }
    public int findContentChildren2(int[] g, int[] s) {  // 用小的先去喂饱小的
        Arrays.sort(g);
        Arrays.sort(s);
        int j = s.length - 1;
        int total = 0;
        for (int i = g.length - 1; i >= 0; i--) {
            if (j < 0) {
                break;
            }
            if (g[i] <= s[j]) {
                j--;
                total++;
            }
        }
        return total;
    }

    /**
     * 摆动序列
     *
     * 如果连续数字之间的差严格地在正数和负数之间交替，则数字序列称为摆动序列。第一个差（如果存在的话）可能是正数或负数。少于两个元素的序列也是摆动序列。
     *
     * 例如， [1,7,4,9,2,5] 是一个摆动序列，因为差值 (6,-3,5,-7,3) 是正负交替出现的。相反, [1,4,7,2,5] 和 [1,7,4,5,5] 不是摆动序列，第一个序列是因为它的前两个差值都是正数，第二个序列是因为它的最后一个差值为零。
     *
     * 给定一个整数序列，返回作为摆动序列的最长子序列的长度。 通过从原始序列中删除一些（也可以不删除）元素来获得子序列，剩下的元素保持其原始顺序。
     *
     * 示例 1:
     *
     * 输入: [1,7,4,9,2,5]
     * 输出: 6
     * 解释: 整个序列均为摆动序列。
     * 示例 2:
     *
     * 输入: [1,17,5,10,13,15,10,5,16,8]
     * 输出: 7
     * 解释: 这个序列包含几个长度为 7 摆动序列，其中一个可为[1,17,10,13,10,16,8]。
     * 示例 3:
     *
     * 输入: [1,2,3,4,5,6,7,8,9]
     * 输出: 2
     *
     * 局部最优：删除单调坡度上的节点（不包括单调坡度两端的节点），那么这个坡度就可以有两个局部峰值。
     *
     * 整体最优：整个序列有最多的局部峰值，从而达到最长摆动序列。
     */
    public int wiggleMaxLength(int[] nums) {  // 这是我以前写的，真鸡儿6
        if (nums.length < 2) {
            return nums.length;
        }
        int result = 1;
        int flag = -(nums[1] - nums[0]);
        for (int i = 1; i < nums.length; i++) {
            if ((flag <= 0 && nums[i] - nums[i - 1] > 0) ||
                    (flag >= 0 && nums[i] - nums[i - 1] < 0)) {
                result++;
                flag = nums[i] - nums[i - 1];
            }
        }
        return result;
    }
    public int wiggleMaxLength2(int[] nums) {  // 代码随想录的，和我差不多
        if (nums.length <= 1) {
            return nums.length;
        }
        //当前差值
        int curDiff = 0;
        //上一个差值
        int preDiff = 0;
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            //得到当前差值
            curDiff = nums[i] - nums[i - 1];
            //如果当前差值和上一个差值为一正一负
            //等于0的情况表示初始时的preDiff
            if ((curDiff > 0 && preDiff <= 0) || (curDiff < 0 && preDiff >= 0)) {
                count++;
                preDiff = curDiff;
            }
        }
        return count;
    }

    /**
     * 最大子序和
     *
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     *
     * 示例:
     *
     * 输入: [-2,1,-3,4,-1,2,1,-5,4]
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
     *
     * 当前“连续和”为负数的时候立刻放弃，
     * 从下一个元素重新计算“连续和”，因为负数加上下一个元素 “连续和”只会越来越小
     */
//    局部最优：当前“连续和”为负数的时候立刻放弃，从下一个元素重新计算“连续和”，因为负数加上下一个元素 “连续和”只会越来越小。
//    全局最优：选取最大“连续和”
//    局部最优的情况下，并记录最大的“连续和”，可以推出全局最优。
    public int maxSubArray(int[] nums) {
        int result = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum > result)
                result = sum;
            if (sum <= 0)
                sum = 0;
        }
        return result;
    }

    /**
     * 买卖股票的最佳时机II
     *
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     *
     * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     *
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     *
     * 示例 1:
     *
     * 输入: [7,1,5,3,6,4]
     * 输出: 7
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4。随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
     * 示例 2:
     *
     * 输入: [1,2,3,4,5]
     * 输出: 4
     * 解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
     * 示例 3:
     *
     * 输入: [7,6,4,3,1]
     * 输出: 0
     * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
     * 提示：
     *
     * 1 <= prices.length <= 3 * 10 ^ 4
     * 0 <= prices[i] <= 10 ^ 4
     */
    public int maxProfit(int[] prices) {  // 自己想的，和摆动序列相似，找到所有上坡的路，将坡顶和坡底的相减就ok了
        if (prices.length < 2) {
            return 0;
        }
        int total = 0;
        int start = prices[0];
        int flag = prices[1] - prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] - prices[i - 1] <= 0) {   // 这个 <= 0 是真的妙啊，完美解决了上坡遇到平路的问题
                if (flag > 0) {
                    total += prices[i - 1] - start;
                }
                start = prices[i];
            }

            flag = prices[i] - prices[i - 1];
        }
        if (flag > 0) {  // 收集最后一个坡
            total += prices[prices.length - 1] - start;
        }
        return total;
    }
    /**
     * 看了代码随想录写的，我真是傻逼
     * 如果想到其实最终利润是可以分解的，那么本题就很容易了！
     * 管他上不上坡，把坡上每一个都分解了就ok了
     */
    // 局部最优：收集每天的正利润，全局最优：求得最大利润。
    public int maxProfit2(int[] prices) {
        int result = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i-1]) {  // 卖出有利可图
                result += (prices[i] - prices[i-1]);
            }
//            result += Math.max(prices[i] - prices[i - 1], 0);
        }
        return result;
    }

    /**
     * 跳跃游戏
     *
     * 给定一个非负整数数组，你最初位于数组的第一个位置。
     *
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     *
     * 判断你是否能够到达最后一个位置。
     *
     * 示例 1:
     *
     * 输入: [2,3,1,1,4]
     * 输出: true
     * 解释: 我们可以先跳 1 步，从位置 0 到达 位置 1, 然后再从位置 1 跳 3 步到达最后一个位置。
     * 示例 2:
     *
     * 输入: [3,2,1,0,4]
     * 输出: false
     * 解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。
     */
    // 贪心算法局部最优解：每次取最大跳跃步数（取最大覆盖范围），整体最优解：最后得到整体最大覆盖范围，看是否能到终点。
    public boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        // 覆盖范围, 初始覆盖范围应该是0，因为下面的迭代是从下标0开始的
        int coverRange = 0;
        // 在覆盖范围内更新最大的覆盖范围
        for (int i = 0; i <= coverRange; i++) {  // 不会超出索引，因为coverRange >= nums.length - 1直接return了
            coverRange = Math.max(coverRange, i + nums[i]);
            if (coverRange >= nums.length - 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 跳跃游戏 II
     */
    public int jump(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        int res = 0;
        int curCoverage = 0;  // 当前的覆盖距离
        int nextCoverage = 0;  // 下一步可以覆盖的距离
        for (int i = 0; i < nums.length; i++) {
            nextCoverage = Math.max(nextCoverage, i + nums[i]);  // 每次都保留下一步可以覆盖的最大范围
            if (i == curCoverage) {  // 已经到达当前的最远距离了，必须要走下一步
                res++;
                curCoverage = nextCoverage;
                if (curCoverage >= nums.length - 1) {  // 当前范围可以达到最后一个
                    break;
                }
            }
        }
        return res;
    }

    /**
     * K 次取反后最大化的数组和
     */
    public int largestSumAfterKNegations(int[] nums, int k) {
        // 按照绝对值大到小排序
        nums = IntStream.of(nums).boxed().sorted((o1, o2) -> Math.abs(o2) - Math.abs(o1)).mapToInt(i -> i).toArray();

        for (int i = 0; i < nums.length; i++) {  // 先把负数进行反转
            if (k <= 0) {
                break;
            }
            if (nums[i] < 0) {
                nums[i] = -nums[i];
                k--;
            }
        }
        while (k-- > 0) {  // 对绝对值最小的数进行多次反转
            nums[nums.length - 1] = -nums[nums.length - 1];
        }
        return Arrays.stream(nums).sum();
    }
}
