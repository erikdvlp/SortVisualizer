package app;

import java.util.Random;
import java.util.Arrays;
import javax.swing.*;

public class App
{
    public static void main(String[] args) throws Exception
    {
        initWindow();
        runTests();
    }

    public static void initWindow()
    {
        JFrame frame = new JFrame();
        frame.setSize(500,500);
        frame.setVisible(true);
    }

    public static void runTests()
    {
        //create tests
        int[] test1 = {3, 4, 2, 6, 9, 3, 0};
        int[] test2 = {53,89,150,36,633,233};
        int[] test3 = {0,-22,5,13,55,-980,7,110};
        int[] test4 = {100,100,0,5,95,1050};
        int[] test5 = {89,55,57,54,12,51,54,93,55,6,26,45,79,99,40,84,67,78,5,6,57,14,56,56,25,11,10,35,83,65,11,12,84,92,45,58,49,38,39,4,40,12,29,91,18,85,13,65,65,100,46,47,100,58,42,38,1,54,87,8,66,9,8,28,68,86,25,17,91,91,23,51,75,38,99,24,69,93,11,14,56,42,28,41,18,92,83,85,66,42,13,71,7,49,100,12,74,65,58,25};
        int[] test6 = new int[50];
        int[] test7 = {0};
        Random rand = new Random();
        for (int i = 0; i < 50; i++)
        {
            int n = rand.nextInt(10000);
            test6[i] = n;
        }
        int[][] tests = {test1,test2,test3,test4,test5,test6,test7};

        //run algorithm
        String runMsg = String.format("\nRunning %d tests...", tests.length);
        System.out.println(runMsg);
        long startTime = System.nanoTime();
        for (int[] test : tests)
            radixSort(test); //change sort type
        long stopTime = System.nanoTime();
        double runTime = (stopTime-startTime) * 0.000001;
        String timeMsg = String.format("Execution time: %.2f ms\n", runTime);
        System.out.println(timeMsg);
    }

    //print array to console
    public static void printArray(int[] nums)
    {
        String print = "";
        for (int num : nums)
            print += num + " ";
        System.out.println(print + "\n");
    }

    //swap two elements in an array by index
    public static int[] swap(int[] arr, int a, int b)
    {
        int hold = arr[a];
        arr[a] = arr[b];
        arr[b] = hold;
        return arr;
    }

    //progressively swap two elements
    public static int[] bubbleSort(int[] nums)
    {
        for (int i = 0; i < nums.length; i++)
        {
            for (int j = i+1; j < nums.length; j++)
            {
                if (nums[i] > nums[j]) nums = swap(nums, i, j);
            }
        }
        return nums;
    }

    //swap smallest element to front
    public static int[] selectionSort(int[] nums)
    {
        for (int i = 0; i < nums.length; i++)
        {
            int minIndex = i;
            for (int j = i+1; j < nums.length; j++)
                if (nums[j] < nums[minIndex]) minIndex = j;
            if (minIndex != i) nums = swap(nums, i, minIndex);
        }

        return nums;
    }

    //progressively divide, sort, and merge
    public static int[] mergeSort(int[] nums)
    {
        if (nums.length <= 1) return nums;

        //divide
        int mid = nums.length / 2;
        int[] left = mergeSort(Arrays.copyOfRange(nums, 0, mid));
        int[] right = mergeSort(Arrays.copyOfRange(nums, mid, nums.length));

        //merge
        for (int l = 0, r = 0; l < left.length || r < right.length;)
        {
            if (r == right.length || l < left.length && left[l] < right[r])
            {
                nums[l+r] = left[l];
                l++;
            }
            else
            {
                nums[l+r] = right[r];
                r++;
            }
        }

        return nums;
    }

    //get pivot and swap values on either side
    public static int[] quickSort(int[] nums)
    {
        return quickSort(nums, 0, nums.length-1);
    }

    public static int[] quickSort(int[] nums, int low, int high)
    {
        if (low >= high) return nums;

        int pivot = nums[high];
        int l = low;
        int r = high;

        while (l <= r)
        {
            while (nums[l] < pivot) l++;
            while (nums[r] > pivot) r--;
            if (l <= r) 
            {
                nums = swap(nums, l, r);
                l++;
                r--;
            }
        }

        if (low < r) nums = quickSort(nums, low, r);
        if (l < high) nums = quickSort(nums, l, high);

        return nums;
    }

    //count occurrences of each element and reorder
    public static int[] countingSort(int[] nums)
    {
        return countingSort(nums, 1, 1);
    }

    public static int[] countingSort(int[] nums, int div, int mod)
    {
        int min = 0;
        int max = 9;
        if (mod == 1)
        {
            min = Arrays.stream(nums).min().getAsInt();
            max = Arrays.stream(nums).max().getAsInt();
        }
        int[] count = new int[max-min+1];

        //count digits
        for (int i = 0; i < nums.length; i++)
        {
            if (mod == 1) count[(nums[i])-min]++;
            else count[Math.abs((nums[i]/div%mod)-min)]++;
        }

        //modify count to sum of preceding values
        for (int i = 1; i < count.length; i++)
            count[i] = count[i] + count[i-1];
        
        //reorder elements
        int[] output = new int[nums.length];
        for (int i = nums.length-1; i >= 0; i--)
        {
            if (mod == 1)
            {
                output[count[(nums[i])-min]-1] = nums[i]-min;
                count[(nums[i])-min]--;
            }
            else
            {
                output[count[Math.abs((nums[i]/div%mod)-min)]-1] = nums[i]-min;
                count[Math.abs((nums[i]/div%mod)-min)]--;
            }
        }

        return output;
    }

    //sort by digits
    //not working for negative numbers
    public static int[] radixSort(int[] nums)
    {
        //get max digits
        int maxNum = Arrays.stream(nums).max().getAsInt();
        int maxDigits = 0;
        while (maxNum > 0)
        {
            maxNum /= 10;
            maxDigits++;
        }

        //counting sort by digit
        int[] tempNums = nums.clone();
        for (int i = 1; i < Math.pow(10, maxDigits); i *= 10)
            tempNums = countingSort(tempNums, i, 10);

        return tempNums;
    }
}