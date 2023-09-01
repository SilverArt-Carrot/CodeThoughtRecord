package day07;

import java.util.*;

public class Main {
    /**
     * 树
     * 跟树相关的数据结构还有栈和队列，递归想不来的时候不要忘了
     */
    public static void main(String[] args) {
//        TreeNode node1 = new TreeNode(4, null, null);
//        TreeNode node2 = new TreeNode(5, null, null);
//        TreeNode node3 = new TreeNode(2, node1, node2);
//        TreeNode node4 = new TreeNode(3, null, null);
//        TreeNode node5 = new TreeNode(1, node3, node4);
//        List<Integer> list = new ArrayList<>();
//        Main main = new Main();
//        List<Integer> list1 = main.inOrder4(node5);
//        System.out.println(list1);
        Node node1 = new Node(1);

        Node node2 = new Node(3);
        Node node3 = new Node(2);
        Node node4 = new Node(4);

        List<Node> list1 = new ArrayList<>();
        list1.add(node2);
        list1.add(node3);
        list1.add(node4);

        Node node5 = new Node(5);
        Node node6 = new Node(6);

        List<Node> list2 = new ArrayList<>();
        list2.add(node5);
        list2.add(node6);

        node2.children = list2;

        node1.children = list1;
        Main main = new Main();
        int i = main.maxDepth(node1);
        System.out.println(i);
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
     *
     * 给定一个二叉树，检查它是否是镜像对称的。
     *
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
     *
     * 给定一个二叉树，找出其最大深度。
     *
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     *
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
        if (root.children != null){
            for (Node child : root.children){
                depth = Math.max(depth, maxDepth(child));
            }
        }
        return depth + 1; //中节点
    }

    /**
     * 二叉树的最小深度
     *
     * 给定一个二叉树，找出其最小深度。
     *
     * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
     *
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

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };
}
