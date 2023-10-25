package day07;

import java.util.*;

public class Main {
    /**
     * 树
     * 跟树相关的数据结构还有栈和队列，递归想不来的时候不要忘了
     */
    public static void main(String[] args) {
        Main main = new Main();
        TreeNode node1 = new TreeNode(1);

        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        node1.left = node2;
        node1.right = node3;
        node2.right = node4;

        List<String> list = main.binaryTreePaths(node1);
        for (String p : list) {
            System.out.println(p);
        }
    }

    /**
     * 数的前中后序遍历
     * 递归三部曲：
     * 1.确定参数和返回值
     * 2.确定递归的终止条件
     * 3.确定单层递归的逻辑
     */
    public void preOrder(TreeNode root, List<Integer> list) {  // 前
        if (root == null) {
            return;
        }
        list.add(root.val);
        preOrder(root.left, list);
        preOrder(root.right, list);
    }

    public void inOrder(TreeNode root, List<Integer> list) {  // 中
        if (root == null) {
            return;
        }
        inOrder(root.left, list);
        list.add(root.val);
        inOrder(root.right, list);
    }

    public void postOrder(TreeNode root, List<Integer> list) {  // 后
        if (root == null) {
            return;
        }
        postOrder(root.left, list);
        postOrder(root.right, list);
        list.add(root.val);
    }

    /**
     * 二叉树的前中后序遍历（使用栈）
     */
    public List<Integer> preOrder2(TreeNode root) {  // 前
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return result;
    }

    public List<Integer> inOrder2(TreeNode root) {  // 中
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                TreeNode cur = stack.pop();
                result.add(cur.val);
                node = cur.right;
            }
        }
        return result;
    }

    public List<Integer> inOrder4(TreeNode root) { // 中 和上面的一样
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            result.add(node.val);

            node = node.right;
        }
        return result;
    }

    // 后续遍历的取巧方法 中左右 --> 中右左 --> 左右中
    public List<Integer> postOrder2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        Collections.reverse(result);
        return result;
    }

    /**
     * https://programmercarl.com/%E4%BA%8C%E5%8F%89%E6%A0%91%E7%9A%84%E7%BB%9F%E4%B8%80%E8%BF%AD%E4%BB%A3%E6%B3%95.html#%E8%BF%AD%E4%BB%A3%E6%B3%95%E4%B8%AD%E5%BA%8F%E9%81%8D%E5%8E%86
     * 用null节点作为记录，解决了访问和处理不一致的问题
     * 遇到空节点才将下一个出栈的节点加入结果集
     * 将前中后序遍历的顺序调转一遍
     * 真实太nb了，实现了栈遍历树的前中后序统一写法
     */
    public List<Integer> preOrder3(TreeNode root) {  // 前
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node != null) {
                if (node.right != null) {
                    stack.push(node.right);
                }
                if (node.left != null) {
                    stack.push(node.left);
                }
                stack.push(node);
                stack.push(null); // 加入节点访问过的标记
            } else {
                TreeNode next = stack.pop();
                result.add(next.val);
            }
        }
        return result;
    }

    public List<Integer> inOrder3(TreeNode root) {  // 中
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node != null) {
                if (node.right != null) {
                    stack.push(node.right);
                }
                stack.push(node);
                stack.push(null); // 加入节点访问过的标记
                if (node.left != null) {
                    stack.push(node.left);
                }
            } else {
                TreeNode next = stack.pop();
                result.add(next.val);
            }
        }
        return result;
    }

    public List<Integer> postOrder3(TreeNode root) { // 后
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node != null) {
                stack.push(node);
                stack.push(null); // 加入节点访问过的标记
                if (node.right != null) {
                    stack.push(node.right);
                }
                if (node.left != null) {
                    stack.push(node.left);
                }
            } else {
                TreeNode next = stack.pop();
                result.add(next.val);
            }
        }
        return result;
    }

    /**
     * 二叉树的层序遍历
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        while (!deque.isEmpty()) {
            int len = deque.size();  // 记录了当前这一层的节点个数
            List<Integer> layer = new ArrayList<>();

            while (len-- > 0) {
                TreeNode poll = deque.poll();
                layer.add(poll.val);

                if (poll.left != null) {
                    deque.offer(poll.left);
                }
                if (poll.right != null) {
                    deque.offer(poll.right);
                }
            }
            result.add(layer);
        }
        return result;
    }

    /**
     * 反转二叉树
     * 使用递归可以
     * 使用前中后序，层序任意遍历都可以，只要对遍历到的节点进行左右子节点交换即可
     */
    public TreeNode invertTree(TreeNode root) {
        reverse(root);
        return root;
    }

    private void reverse(TreeNode node) {
        if (node == null) {
            return;
        }
        TreeNode t = node.left;
        node.left = node.right;
        node.right = t;
        reverse(node.left);
        reverse(node.right);
    }

    /**
     * 对称二叉树
     * <p>
     * 给定一个二叉树，检查它是否是镜像对称的。
     * <p>
     * 比较的不是左右节点，而是节点的左右子树是否反转
     */
    public boolean isSymmetric(TreeNode root) {  // 这是我写的，层序遍历的方法，将一层的全部放进去，进行头尾比较
        if (root == null) {
            return true;
        }
        Deque<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int len = queue.size();
            List<TreeNode> list = new ArrayList<>();

            while (len-- > 0) {
                TreeNode poll = queue.poll();
                list.add(poll.left);
                list.add(poll.right);

                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }

            int left = 0;
            int right = list.size() - 1;
            while (left < right) {
                TreeNode node1 = list.get(left);
                TreeNode node2 = list.get(right);
                if (node1 != null && node2 != null) {
                    if (node1.val != node2.val) {
                        return false;
                    }
                }
                if (node1 == null && node2 != null) {
                    return false;
                }
                if (node1 != null && node2 == null) {
                    return false;
                }
                left++;
                right--;
            }
        }
        return true;
    }

    // 其实也不用把一层的全部放进去，只要按照内侧和外侧的顺序加入容器，然后两个两个取就好了
    public boolean isSymmetric2(TreeNode root) {
        if (root == null) {
            return true;
        }
        // 任意类型容器即可
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root.left);
        stack.push(root.right);
        while (!stack.isEmpty()) {
            TreeNode node1 = stack.pop();
            TreeNode node2 = stack.pop();
            if (node1 == null && node2 == null) {
                continue;
            }
            if (node1 == null || node2 == null) {
                return false;
            }
            if (node1.val != node2.val) {
                return false;
            }
            stack.push(node1.left);
            stack.push(node2.right);
            stack.push(node1.right);
            stack.push(node2.left);
        }
        return true;
    }

    // 递归
    public boolean isSymmetric3(TreeNode root) {
        if (root == null) {
            return true;
        }
        return compare(root.left, root.right);
    }

    private boolean compare(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        } else if (left == null || right == null) {
            return false;
        } else if (left.val != right.val) {
            return false;
        }
        boolean flag1 = compare(left.right, right.left); // 里侧比较
        boolean flag2 = compare(left.left, right.right); // 外侧比较
        return flag1 && flag2;  // 只要有一个为false，递归出栈就是false
    }

    /**
     * 二叉树的最大深度
     * <p>
     * 给定一个二叉树，找出其最大深度。
     * <p>
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     * <p>
     * 说明: 叶子节点是指没有子节点的节点。
     */
    public int maxDepth(TreeNode root) {  // 自己想的，康康递归三部曲，层序遍历也是可以的
        return depth(root, 0);
    }

    private int depth(TreeNode node, int depth) {
        if (node == null) {
            return depth;
        }
        depth++;
        return Math.max(depth(node.left, depth), depth(node.right, depth));
    }

    /**
     * 递归法 代码随想录的
     */
    public int maxDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = maxDepth2(root.left);
        int rightDepth = maxDepth2(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * 求N叉树的最大深度
     */
    public int maxDepth(Node root) {
        if (root == null) return 0;
        int depth = 0;
        if (root.children != null) {
            for (Node child : root.children) {
                depth = Math.max(depth, maxDepth(child));
            }
        }
        return depth + 1; //中节点
    }

    /**
     * 二叉树的最小深度
     * <p>
     * 给定一个二叉树，找出其最小深度。
     * <p>
     * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
     * <p>
     * 说明: 叶子节点是指没有子节点的节点。
     */
    public int minDepth(TreeNode root) {  // 自己想的，康康递归三部曲
        if (root == null) {
            return 0;
        }
        return depth2(root, 0);
    }

    private int depth2(TreeNode node, int depth) {
        depth++;
        TreeNode left = node.left;
        TreeNode right = node.right;
        if (left == null && right == null) {
            return depth;
        } else if (left == null) {
            return depth2(right, depth);
        } else if (right == null) {
            return depth2(left, depth);
        } else {
            return Math.min(depth2(left, depth), depth2(right, depth));
        }
    }

    /**
     * 代码随想录的，使用的后续遍历
     */
    public int minDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = minDepth2(root.left);
        int rightDepth = minDepth2(root.right);
        if (root.left == null) {
            return rightDepth + 1;
        }
        if (root.right == null) {
            return leftDepth + 1;
        }
        // 左右结点都不为null
        return Math.min(leftDepth, rightDepth) + 1;
    }

    /**
     * 求N叉树最小深度，chatGPT写的
     */
    public int minDepth(Node root) {
        if (root == null) {
            return 0;
        }
        if (root.children.isEmpty()) {
            return 1;
        }
        int minDepth = Integer.MAX_VALUE;
        for (Node child : root.children) {
            minDepth = Math.min(minDepth, minDepth(child));
        }
        return minDepth + 1;
    }

    /**
     * N叉树
     */
    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    /**
     * 完全二叉树的节点个数
     */
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int result = 1;
        result += countNodes(root.left) + countNodes(root.right);
        return result;
    }

    // 针对完全二叉树，如果一直遍历左孩子和一直遍历右孩子的个数相等就是满二叉树，可以直接2 ^ 数的深度 - 1
    public int countNodes2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftL = 0;  // 左子树高度
        int rightL = 0;  // 右子树高度
        TreeNode left = root.left;
        TreeNode right = root.right;
        while (left != null) {
            left = left.left;
            leftL++;
        }
        while (right != null) {
            right = right.right;
            rightL++;
        }
        if (leftL == rightL) {
            return (2 << leftL) - 1;
        }
        return 1 + countNodes2(root.left) + countNodes2(root.right);
    }

    /**
     * 平衡二叉树
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        int leftDepth = getDepth(root.left);
        int rightDepth = getDepth(root.right);
        if (Math.abs(leftDepth - rightDepth) > 1) {
            return false;
        }
        return isBalanced(root.left) && isBalanced(root.right);
    }

    private int getDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = getDepth(root.left);
        int rightDepth = getDepth(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    // 代码随想录的
    public boolean isBalanced2(TreeNode root) {
        return getHeight(root) != -1;
    }

    private int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = getHeight(root.left);
        if (leftHeight == -1) {
            return -1;
        }
        int rightHeight = getHeight(root.right);
        if (rightHeight == -1) {
            return -1;
        }
        // 左右子树高度差大于1，return -1表示已经不是平衡树了
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * 二叉树的所有路径
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        LinkedList<String> path = new LinkedList<>();
        backTracking(result, path, root);
        return result;
    }

    private void backTracking(List<String> result, LinkedList<String> path, TreeNode root) {
        if (root == null) {
            return;
        }
        path.add(String.valueOf(root.val));
        if (root.left == null && root.right == null) {
            result.add(String.join("->", path));
            return;
        }
        if (root.left != null) {
            backTracking(result, path, root.left);
            path.removeLast();
        }
        if (root.right != null) {
            backTracking(result, path, root.right);
            path.removeLast();
        }
    }

    /**
     * 左叶子之和
     */
    public int sumOfLeftLeaves(TreeNode root) {  // 只要能遍历树就行
        int res = 0;
        if (root == null) {
            return res;
        }
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.left != null && node.left.left == null && node.left.right == null) {
                res += node.left.val;
            }
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return res;
    }

    /**
     * 找树左下角的值
     */
    public int findBottomLeftValue(TreeNode root) {  // 层序遍历，用每层最左边的
        LinkedList<TreeNode> queue = new LinkedList<>();
        int left = 0;
        queue.offer(root);
        while (!queue.isEmpty()) {
            int len = queue.size();

            left = queue.peek().val;
            while (len-- > 0) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return left;
    }

    /**
     * 路径总和
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        return hasPathSumTravel(root, targetSum - root.val);
    }
    private boolean hasPathSumTravel(TreeNode root, int count) {
        if (root.left == null && root.right == null && count == 0) {
            return true;
        }
        if (root.left == null && root.right == null) {
            return false;
        }
        if (root.left != null && hasPathSumTravel(root.left, count - root.left.val)) {
            return true;
        }
        if (root.right != null && hasPathSumTravel(root.right, count - root.right.val)) {
            return true;
        }
        return false;
    }

    /**
     * 路径总和 II
     */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        LinkedList<Integer> path = new LinkedList<>();
        path.add(root.val);
        hasPathSumTravel(root, targetSum - root.val, result, path);
        return result;
    }
    private void hasPathSumTravel(TreeNode root, int count, List<List<Integer>> result, LinkedList<Integer> path) {
        if (root.left == null && root.right == null && count == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        if (root.left == null && root.right == null) {
            return;
        }
        if (root.left != null) {
            path.add(root.left.val);
            hasPathSumTravel(root.left, count - root.left.val, result, path);
            path.removeLast();
        }
        if (root.right != null) {
            path.add(root.right.val);
            hasPathSumTravel(root.right, count - root.right.val, result, path);
            path.removeLast();
        }
    }

    /**
     * 从中序与后序遍历序列构造二叉树
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return build(inorder, postorder, 0, inorder.length - 1, 0, inorder.length - 1, map);
    }
    private TreeNode build(int[] inorder, int[] postorder, int inorderLeft, int inorderRight, int postorderLeft, int postorderRight, Map<Integer, Integer> map) {
        if (inorderLeft > inorderRight || postorderLeft > postorderRight) {
            return null;
        }

        // 后续遍历的最后一个节点为根节点
        int rootVal = postorder[postorderRight];
        TreeNode node = new TreeNode();
        node.val = rootVal;

        // 找到这个结点在中序遍历中的位置
        Integer index = map.get(rootVal);

        // 在中序遍历中左子树的长度
        int left_tree_len = index - inorderLeft;

        // 确定中序遍历和后续遍历的左右子树的区间
        node.left = build(inorder, postorder, inorderLeft, inorderLeft + left_tree_len, postorderLeft, postorderLeft + left_tree_len - 1, map);
        node.right = build(inorder, postorder, inorderLeft + left_tree_len + 1, inorderRight, postorderLeft + left_tree_len, postorderRight - 1, map);

        return node;
    }

    /**
     * 最大二叉树
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return construct(nums, 0, nums.length - 1);
    }
    private TreeNode construct(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        if (left == right) {
            return new TreeNode(nums[left]);
        }

        int maxIndex = left;
        int maxVal = nums[left];
        for (int i = left; i <= right; i++) {
            if (nums[i] > maxVal) {
                maxIndex = i;
                maxVal = nums[i];
            }
        }

        TreeNode node = new TreeNode(maxVal);

        node.left = construct(nums, left, maxIndex - 1);
        node.right = construct(nums, maxIndex + 1, right);

        return node;
    }

    /**
     * 合并二叉树
     * LeetCode line 724也有
     */
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }

        root1.val += root2.val;
        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);
        return root1;
    }
    // 迭代法，拿个容器
    public TreeNode mergeTrees2(TreeNode root1, TreeNode root2) {
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root1);
        queue.offer(root2);
        while (!queue.isEmpty()) {
            TreeNode node1 = queue.poll();
            TreeNode node2 = queue.poll();

            node1.val += node2.val;
            if (node1.left != null && node2.left != null) {
                queue.offer(node1.left);
                queue.offer(node2.left);
            }
            if (node1.right != null && node2.right != null) {
                queue.offer(node1.right);
                queue.offer(node2.right);
            }
            if (node1.left == null && node2.left != null) {
                node1.left = node2.left;
            }
            if (node1.right == null && node2.right != null) {
                node1.right = node2.right;
            }
        }
        return root1;
    }

    /**
     * 二叉搜索树中的搜索
     */
    public TreeNode searchBST(TreeNode root, int val) {
        while (root != null) {
            if (root.val == val) {
                return root;
            } else if (root.val > val) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return null;
    }
    // 递归
    public TreeNode searchBST2(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (root.val == val) {
            return root;
        } else if (root.val > val) {
            return searchBST2(root.left, val);
        } else {
            return searchBST2(root.right, val);
        }
    }

    /**
     * 验证二叉搜索树
     * 二叉搜索树在中序遍历下是一个递增序列
     */
    private int pre = Integer.MIN_VALUE;
    private boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }

        if (!isValidBST(root.left)) {
            return false;
        }
        int val = root.val;
        if (val <= pre) {
            return false;
        }
        pre = val;

        return isValidBST(root.right);
    }
}