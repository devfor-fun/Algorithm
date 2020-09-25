

/**
 * <p></p>
 *
 * @author wangsizheng
 * @version V1.0.0
 * @since V1.0.0
 */
public class Sorts {


    public static void main(String[] args) {
        int[] s = new int[] {
                2,43,4,3,5,6,1,4,6,87,54,2,5,6,23,6,34,6,7,4,6,5,3,6,4,67,3,34,6,346,24,6,46,35
        };
        Sorts.InsertSort insertSort = new Sorts().new InsertSort();
        insertSort.insertSort(s);

//        Sorts.BubblingSort bubblingSort = new Sorts().new BubblingSort();
//        bubblingSort.bubblingSort(s);

//        Sorts.SelectSort selectSort = new Sorts().new SelectSort();
//        selectSort.selectSort(s);

//        Sorts.QuickSort quickSort = new Sorts().new QuickSort();
//        quickSort.quickSort(s);

//        Sorts.HeapSort heapSort = new Sorts().new HeapSort();
//        heapSort.heapSort(s);


//        Sorts.MergerSort mergerSort = new Sorts().new MergerSort();
//        mergerSort.mergerSort(s);

//        Sorts.ShellSort shellSort = new Sorts().new ShellSort();
//        shellSort.shellSort(s);

        for (int i = 0; i < s.length; i++) {
            System.out.print(s[i] + " ");
        }
    }

    class InsertSort {

        public void insertSort(int[] nums) {
            for (int i = 1; i < nums.length; i++) {
                // 方式一
//                for (int j = i - 1; j >= -1; j--) {
//                    if (j == -1 || nums[i] > nums[j]) {
//                        int temp = nums[i];
//                        System.arraycopy(nums, j + 1, nums, j + 2, i - j - 1);
//                        nums[j + 1] = temp;
//                        break;
//                    }
//
//                }
                // 方式二
                int cur = nums[i];
                int j = i - 1;
                for (; j >=0 && cur < nums[j]; j--) {
                    nums[j + 1] = nums[j];
                }
                nums[j + 1] = cur;

            }
        }
    }


    class BubblingSort {

        public void bubblingSort(int[] nums) {
            for (int i = 0; i < nums.length; i++) {
                boolean update = false;
                for (int j = 0; j < nums.length - i - 1; j++) {
                    if (nums[j] > nums[j + 1]) {
                        int temp = nums[j];
                        nums[j] = nums[j + 1];
                        nums[j + 1] = temp;
                        update = true;
                    }
                }
                if (!update) {
                    break;
                }
            }
        }

    }


    class SelectSort {

        public void selectSort(int[] nums) {
            for (int i = 0; i < nums.length; i++) {
                int index = i;
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[j] < nums[index]) {
                        index = j;
                    }
                }
                int temp = nums[i];
                nums[i] = nums[index];
                nums[index] = temp;
            }
        }
    }

    class QuickSort {

        public void quickSort(int[] nums) {
            sort(nums, 0, nums.length);
        }

        private void sort(int[] nums, int left, int right) {
            if (left < right) {
                int mid = quickSort(nums, left, right);
                sort(nums, left, mid);
                sort(nums, mid + 1, right);
            }
        }

        private int quickSort(int[] nums, int left, int right) {
            int cur = nums[left];
            int le = left;
            int ri = right;
            while (le < ri) {
                while (le < ri && nums[--ri] > cur);
                while (le < ri && nums[++le] < cur);
                if (le > ri) {
                    break;
                }
                int temp = nums[le];
                nums[le] = nums[ri];
                nums[ri] = temp;
            }
            nums[left] = nums[ri];
            nums[ri] = cur;
            return ri;
        }

    }

    class HeapSort {

        public void heapSort(int[] nums) {
            //大顶堆
            for (int i = nums.length / 2 - 1; i >= 0; i--) {
                adjustHeap(nums, nums.length, i);
            }

            // 将最大值往后排，重新构建大顶堆，即最大值将在最后
            for(int i = nums.length-1; i > 0; i--){
                int temp = nums[0];
                nums[0] = nums[i];
                nums[i] = temp;
                adjustHeap(nums,i, 0);//重新对堆进行调整 变成排序后的小顶堆
            }
        }

        private void adjustHeap(int[] nums, int length, int index) {
            int child = index * 2 + 1;
            int temp = nums[index];
            while (child < length) {
                if (child + 1 < length && nums[child] < nums[child + 1]) {
                    child++;
                }
                if (temp >= nums[child]) {
                    break;
                }
                nums[index] = nums[child];
                index = child;
                child = child * 2 + 1;
            }
            nums[index] = temp;
        }
    }

    class MergerSort {

        public void mergerSort(int[] nums) {
            mergerSort(nums, 0, nums.length - 1);
        }

        private void mergerSort(int[] nums, int left, int right) {
            if (left < right) {
                int mid = left + (right - left) / 2;
                mergerSort(nums, left, mid);
                mergerSort(nums, mid + 1, right);
                mergerSort(nums, left, mid, right);
            }
        }

        private void mergerSort(int[] nums, int left, int mid, int right) {
            int leftFirst = left;
            int rightFirst = mid + 1;
            int[] temp = new int[right - left + 1];
            int index = 0;
            while (leftFirst <= mid && rightFirst <= right) {
                if (nums[leftFirst] > nums[rightFirst]) {
                    temp[index++] = nums[rightFirst++];
                } else {
                    temp[index++] = nums[leftFirst++];
                }
            }
            while (leftFirst <= mid) {
                temp[index++] = nums[leftFirst++];
            }
            while (rightFirst <= right) {
                temp[index++] = nums[rightFirst++];
            }
            for (int i = left; i <= right; i++) {
                nums[i] = temp[i - left];
            }
        }

    }

    class ShellSort {

        public void shellSort(int[] nums) {
            for (int step = nums.length / 2; step > 0; step /= 2) {
                for (int i = step; i < nums.length; i++) {
                    insertSort(nums, step, i);
                }
            }
        }

        private void insertSort(int[] nums, int step, int i) {
            int cur = nums[i];
            int j = i - step;
            while (j >=0 && cur < nums[j]) {
                nums[j + step] = nums[j];
                j = j - step;
            }
            nums[j + step] = cur;
        }
    }
}
