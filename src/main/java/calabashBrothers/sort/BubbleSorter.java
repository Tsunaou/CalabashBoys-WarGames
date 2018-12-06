package calabashBrothers.sort;

/**
 * @ Author     ：Young
 * @ Description：冒泡排序法
 */

abstract public class BubbleSorter extends Sorter{
    @Override
    public void sort(int arraySize){
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize - 1 - i; j++) {
                if (compareSmaller(j,j+1)==false) {
                    swapWithReport(j,j+1);
                }
            }
        }
    }
}