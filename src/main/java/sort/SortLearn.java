package sort;

import java.util.Arrays;

/**
 * 只有快排
 * 其他排序自己去搜一搜吧
 * https://zhuanlan.zhihu.com/p/42586566
 * https://juejin.cn/post/6844903687932887053
 * https://xie.infoq.cn/article/ee95dfe949d2464ae8bc809b4
 */
public class SortLearn {
    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public int partition(int[] arr, int left, int right) {
        int pivot = arr[left];
        while (left < right) {
            while (left < right && arr[right] >= pivot) {
                right--;
            }
            swap(arr, left, right);
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            swap(arr, left, right);
        }
        return left;
    }

    public void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int partition = partition(arr, left, right);
            quickSort(arr, left, partition - 1);
            quickSort(arr, partition + 1, right);
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{8, 3, 4, 1, 10, 2, 5, 11};
        SortLearn quickSort = new SortLearn();
        quickSort.quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}
