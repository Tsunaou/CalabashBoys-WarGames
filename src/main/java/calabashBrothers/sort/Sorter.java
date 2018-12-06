package calabashBrothers.sort;

/**
 * @ Author     ：Young
 * @ Description：排序器
 */

abstract public class Sorter {
    abstract public void sort(int arraySize);
    abstract void swapWithReport(int i,int j);
    abstract boolean compareSmaller(int i,int j);
}

