package calabashBrothers.sort;

/**
 * @ Author     ：Young
 * @ Description：二分插入排序
 */
abstract public class BinarySorter extends Sorter{
    @Override
    public void sort(int arraySize) {
        for (int i = 0; i < arraySize; i++) {
            int left = 0;
            int right = i - 1;
            int mid = (left + right) / 2;

            while (left <= right) {
                if (compareSmaller(i,mid)==true) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
                mid = (left + right) / 2;
            }

            for (int j = i; j > left; j--) {
                swapWithReport(j,j-1);
            }
        }
    }
}