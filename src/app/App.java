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
        //numbers to sort
        int[] feed = {908, 73, 502, 701, 905, 781, 171, 330, 242, 448, 804, 315, 264, 59, 21, 396, 740, 334, 358, 934, 207, 452, 368, 321, 320, 350, 455, 719, 504, 155, 157, 267, 858, 143, 766, 538, 311, 703, 481, 50, 356, 980, 646, 314, 48, 861, 797, 831, 450, 203, 545, 623, 530, 710, 978, 911, 933, 395, 607, 691, 474, 901, 57, 541, 661, 63, 562, 381, 836, 205, 942, 868, 472, 144, 846, 273, 629, 193, 808, 415, 408, 998, 200, 255, 66, 979, 322, 812, 54, 533, 922, 728, 431, 165, 405, 332, 485, 256, 763, 900};

        //run algorithm
        System.out.println("\nRunning...");
        long startTime = System.nanoTime();
        mergeSort(feed); //change sort type
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