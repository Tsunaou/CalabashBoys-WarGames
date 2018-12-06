package calabashBrothers.sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 0:47 2018/12/5
 * @ Description：用单元测试对二分排序进行测试
 */
public class BinarySorterTest {
    private int[] arrayTest;
    private int arraySize;
    private Random rand;
    private BinarySorter sorter = new BinarySorter() {
        @Override
        void swapWithReport(int i, int j) {
            swap(i, j);
        }

        @Override
        boolean compareSmaller(int i, int j) {
            return cmp(i, j);
        }
    };

    private void swap(int i, int j) {
        int temp = arrayTest[i];
        arrayTest[i] = arrayTest[j];
        arrayTest[j] = temp;
    }

    private boolean cmp(int i, int j) {
        return arrayTest[i] < arrayTest[j];
    }

    private void setArrayRandom() {
        int randTimes = rand.nextInt(50)+20;
        int m;
        int n;
        for (int i = 0; i < randTimes; i++) {
            m = rand.nextInt(arraySize);
            n = rand.nextInt(arraySize);
            swap(m,n);
        }
    }

    @Test
    public void binaryRandomTest() {
        rand = new Random();
        this.arraySize = rand.nextInt(200);
        arrayTest = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            arrayTest[i] = rand.nextInt(100) + 10;
        }

        int testTimes=rand.nextInt(20);

        for (int i = 0; i < testTimes; i++) {
            setArrayRandom();
            int[] standardArray = arrayTest.clone();
            sorter.sort(arraySize);
            Arrays.sort(standardArray);
            assertArrayEquals(standardArray,arrayTest);
        }
    }

    @Test
    public void binarySettedTest() {
        rand = new Random();
        this.arraySize = 100;
        arrayTest = new int[100];
        for (int i = 0; i < arraySize; i++) {
            arrayTest[i] = rand.nextInt(100) + 10;
        }

        int testTimes = 50;

        for (int i = 0; i < testTimes; i++) {
            setArrayRandom();
            int[] standardArray = arrayTest.clone();
            sorter.sort(arraySize);
            Arrays.sort(standardArray);
            assertArrayEquals(standardArray,arrayTest);
        }
    }
}