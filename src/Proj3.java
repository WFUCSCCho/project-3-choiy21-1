/**********************************************************************
 * @file: Proj3.java
 * @description:
    * Implement the Bubble Sort, Merge Sort, Quick Sort, Heap Sort, and Odd-Even Transposition Sort algorithms.
    * Perform the sorting algorithms using already-sorted, shuffled, and reversed datasets lists as input.
    * Time the sorting algorithm performance for the different set of inputs.
    * Graph and analyze the performance of the sorting algorithms.
 * @author: Kate Choi
 * @date: 14 November 2024
 **********************************************************************/

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Proj3 {
    // private inner class to store sorting results
    private static class SortResult {
        long time;
        String comparisons;

        SortResult(long time, String comparisons) {
            this.time = time;
            this.comparisons = comparisons;
        }
    }
    // Sorting Method declarations

    // Merge Sort
    public static <T extends Comparable<T>> void mergeSort(ArrayList<T> a, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(a, left, mid);
            mergeSort(a, mid + 1, right);
            merge(a, left, mid, right);
        }
    }

    // merge helper method to recursively merge lists
    public static <T extends Comparable<T>> void merge(ArrayList<T> a, int left, int mid, int right) {
        int n1 = mid - left + 1; // size of left half
        int n2 = right - mid; // size of right half

        // temporary arrays to hold the left and right halves
        ArrayList<T> leftArray = new ArrayList<>(n1);
        ArrayList<T> rightArray = new ArrayList<>(n2);

        // copy data into temporary arrays
        for (int i = 0; i < n1; i++) {
            leftArray.add(a.get(left + i));
        }
        for (int j = 0; j < n2; j++) {
            rightArray.add(a.get(mid + 1 + j));
        }

        // merge the temporary arrays back into the original array
        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (leftArray.get(i).compareTo(rightArray.get(j)) <= 0) {
                a.set(k, leftArray.get(i));
                i++;
            } else {
                a.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }

        // copy any remaining elements from rightArray, if any
        while (j < n2) {
            a.set(k, rightArray.get(j));
            j++;
            k++;
        }

    }

    // Quick Sort
    public static <T extends Comparable<T>> void quickSort(ArrayList<T> a, int left, int right) {
        if (left < right) {
            int pi = partition(a, left, right);
            quickSort(a, left, pi - 1);
            quickSort(a, pi + 1, right);
        }
    }

    // quick sort helper method to partition lists
    public static <T extends Comparable<T>> int partition (ArrayList<T> a, int left, int right) {
        T pivot = a.get(right);
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (a.get(j).compareTo(pivot) <= 0) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, right);
        return i + 1;
    }

    // method to swap list elements
    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    // Heap Sort
    public static <T extends Comparable<T>> void heapSort(ArrayList<T> a, int left, int right) {
        int size = right + 1;
        // build min heap
        for (int i = (size / 2) - 1; i >= left; i--) {
            heapify(a, left, right);
        }

        // extract elements from heap one by one
        for (int i = right; i > left; i--) {
            swap(a, left, i); // move current root (smallest element) to the end
            heapify(a, left, i - 1); // call heapify on the reduced heap
        }
    }

    // heap sort helper method to generate minimum binary heap
    public static <T extends Comparable<T>> void heapify (ArrayList<T> a, int left, int right) {
        int smallest = left; // start from the left index as root
        int leftChild = 2 * left + 1;
        int rightChild = 2 * left + 2;

        // check if the left child exists and is smaller than the root
        if (leftChild <= right && a.get(leftChild).compareTo(a.get(smallest)) < 0) {
            smallest = leftChild;
        }

        // check if right child exists and is smaller than the smallest so far
        if (rightChild <= right && a.get(rightChild).compareTo(a.get(smallest)) < 0) {
            smallest = rightChild;
        }

        // if the smallest is not the root, swap and continue heapifying
        if (smallest != left) {
            swap(a, left, smallest);
            heapify(a, smallest, right);
        }
    }

    // Bubble Sort
    public static <T extends Comparable<T>> int bubbleSort(ArrayList<T> a, int size) {
        int comparisons = 0;

        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                comparisons++;
                if (a.get(j).compareTo(a.get(j + 1)) > 0) {
                    swap(a, j, j + 1);
                }
            }
        }
        return comparisons;
    }

    // Odd-Even Transposition Sort
    public static <T extends Comparable<T>> int transpositionSort(ArrayList<T> a, int size) {
        int comparisons = 0;
        boolean isSorted = false;

        while(!isSorted) {
            isSorted = true;
            // odd indexed elements
            for (int i = 1; i < size - 1; i += 2) {
                comparisons++;
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    isSorted = false;
                }
            }
            // even indexed elements
            for (int i = 0; i < size - 1; i += 2) {
                comparisons++;
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    isSorted = false;
                }
            }
        }
        return comparisons;
    }

    public static void main(String [] args)  throws IOException {
        if (args.length != 3) {
            System.err.println("Error: Input {dataset-file} {sorting-algorithm-type} {number-of-lines}");
            System.exit(1);
        }

        String file = args[0];
        String sort = args[1].toLowerCase();
        int lines = Integer.parseInt(args[2]);

        // load dataset into ArrayList
        ArrayList<Chocolate> data = loadData(file, lines);

        // run and time the specified sorting algorithm on different orders of the dataset
        runSortAndSave(data, sort, lines);

    }

    // helper method to load dataset
    private static ArrayList<Chocolate> loadData(String file, int lines) throws IOException {
        ArrayList<Chocolate> data = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(file))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip the header line
            }

            int count = 0;
            while (scanner.hasNextLine() && count < lines) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue; // Skip empty lines

                String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                // Parse the fields
                String company = fields[0].trim();
                String origin = fields[1].trim();
                int ref = Integer.parseInt(fields[2].trim());
                int reviewDate = Integer.parseInt(fields[3].trim());
                double cocoaPercent = Double.parseDouble(fields[4].replace("%", "").trim());
                String location = fields[5].trim();
                double rating = Double.parseDouble(fields[6].trim());

                Chocolate chocolate = new Chocolate(company, origin, ref, reviewDate, cocoaPercent, location, rating);
                data.add(chocolate);
                count++;
            }
        }
        return data;
    }

    // helper method to run different sorting algorithms for different list types (already sorted, shuffled, reversed) and print results to console
    private static void runSortAndSave(ArrayList<Chocolate> data, String sort, int lines) throws IOException {
        // prepare different dataset orders
        ArrayList<Chocolate> sorted = new ArrayList<>(data);
        ArrayList<Chocolate> shuffled = new ArrayList<>(data);
        ArrayList<Chocolate> reversed = new ArrayList<>(data);

        Collections.sort(sorted);
        Collections.shuffle(shuffled);
        Collections.sort(reversed, Collections.reverseOrder());

        // run sorting and timing for each
        SortResult sortedResult = sortAndTime(sorted, "sorted", sort, lines);
        SortResult shuffledResult = sortAndTime(shuffled, "shuffled", sort, lines);
        SortResult reversedResult = sortAndTime(reversed, "reversed", sort, lines);

        // save results to files
        saveAnalysis(sort, lines, sortedResult, shuffledResult, reversedResult);
        saveSortedData(sorted);

        // print results to console
        System.out.printf("sorting algorithm: %s%n", sort);
        System.out.printf("number of lines: %d%n", lines);

        printResults("sorted list", sortedResult, sort);
        printResults("shuffled list", shuffledResult, sort);
        printResults("reversed list", reversedResult, sort);
    }

    // helper method to print results with conditional comparisons
    private static void printResults(String type, SortResult result, String sort) {
        System.out.printf("%s – time: %d ns", type, result.time);
        if (sort.equals("bubble") || sort.equals("transposition")) {
            System.out.printf(", comparisons: %s%n", result.comparisons);
        } else {
            System.out.println();
        }
    }

    // method to perform sort based on desired sorting algorithm and number of liens to be sorted, returns SortResult object with time and number of comparisons (if applicable)
    private static SortResult sortAndTime(ArrayList<Chocolate> data, String type, String sort, int lines) {
        long start = System.nanoTime();
        int comparisons = 0;

        switch (sort) {
            case "bubble":
                comparisons = bubbleSort(data, lines);
                break;
            case "merge":
                mergeSort(data, 0, lines - 1);
                break;
            case "quick":
                quickSort(data, 0, lines - 1);
                break;
            case "heap":
                heapSort(data, 0, lines - 1);
                break;
            case "transposition":
                comparisons = transpositionSort(data, lines);
                break;
            default:
                System.out.println("Invalid sorting algorithm type.");
                return new SortResult(-1, "N/A");
        }

        long end = System.nanoTime();
        long time = end - start;

        String comparisonsResult = (sort.equals("bubble") || sort.equals("transposition")) ? Integer.toString(comparisons) : "N/A";
        return new SortResult(time, comparisonsResult);
    }

    // write results to file analysis.txt
    private static void saveAnalysis(String sort, int lines, SortResult sortedResult, SortResult shuffledResult, SortResult reversedResult) throws IOException {
        File file = new File("analysis.txt");
        boolean isNewFile = file.createNewFile(); // Check if the file is newly created

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            if (isNewFile) {
                // Write header only if the file is new
                writer.println("Algorithm,Lines,SortedTime(ns),ShuffledTime(ns),ReversedTime(ns),SortedComparisons,ShuffledComparisons,ReversedComparisons");
            }
            // Append the data, including comparisons columns
            writer.printf("%s,%d,%d,%d,%d,%s,%s,%s%n",
                    sort,
                    lines,
                    sortedResult.time,
                    shuffledResult.time,
                    reversedResult.time,
                    sortedResult.comparisons,
                    shuffledResult.comparisons,
                    reversedResult.comparisons
            );
        }
    }

    // write new sorted list to file sorted.txt
    private static void saveSortedData(ArrayList<Chocolate> sorted) throws IOException {
        File file = new File("sorted.txt");
        boolean isNewFile = file.createNewFile(); // Check if the file is newly created

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            // write header for the `Chocolate` fields
            writer.println("Company,Origin,Ref,ReviewDate,CocoaPercent,Location,Rating");
            for (Chocolate chocolate : sorted) {
                writer.println(chocolate);
            }
        }
    }

}