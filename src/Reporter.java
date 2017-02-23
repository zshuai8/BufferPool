import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;


/**
 * @author shuaicheng zhang
 * @version 2016.10.25
 *
 */
public class Reporter {
    private static File stats;
    private String statsF;
    private BufferPool pool;
    private int counter = 0;
    private int time = 0;
    private byte[] leftA;
    private byte[] rightA;
    private byte[] tmp;
    
    /**
     * 
     * @param fileName is the input filename
     * @param numBuffer is the number of buffers
     * @param outputFile is the file to output to
     * @throws FileNotFoundException 
     */
    public Reporter(String fileName, int numBuffer, String outputFile) {
        
        statsF = fileName; 
        stats = new File(outputFile);
        pool = new BufferPool(fileName, numBuffer);
        leftA = new byte[4];
        rightA = new byte[4];
        tmp = new byte[4];
    }
    
    /**
     * 
     * @param input is the input file
     * @throws FileNotFoundException if the file doesn't exist
     */
    public void writeStats(File input) throws IOException {
        PrintWriter writer = new PrintWriter(
            new BufferedWriter(new FileWriter(input, true)));
        writer.println("Sort on " + statsF);
        writer.println("Cache Hits: " + counter);
        writer.println("Disk Reads: " + pool.discRead());
        writer.println("Disk Writes: " + pool.discWrite());
        writer.println("Time is " + time);
        writer.close();
    }
    
    /**
     * sort the file and report the stats
     * @throws IOException if the file doesn't exist
     */
    public void report() throws IOException {
        final long start = System.currentTimeMillis();
        
        quicksort(pool, 0, (pool.getFileLen() - 4) / 4);
        final long end = System.currentTimeMillis();
        pool.flushAll();
        time = (int) (end - start);
        writeStats(stats);
    }
    
    /**
     * 
     * @param a is the bufferpool
     * @param left is the left index
     * @param right is the right index
     * @param pivot is the midindex value
     * @return the first position in the right subarray
     */
    public int partition(BufferPool a, int left, int right, int pivot) {
        
        while (left <= right) {
         
            while (getKey(left) < pivot)  {
                left = left + 1;
            }
            while (right >=  left 
                    && getKey(right) >= pivot) {
                right = right - 1;
            }
            
            if (right > left) {
                
                swap(a, left, right);
            }         
        }
        return left;
    }
    
    /**
     * 
     * @param a is the pool
     * @param i is the left index
     * @param j is the right index
     * @throws IOException if there file doesn't exist
     */
    public void quicksort(BufferPool a, int i, int j) throws IOException {
        
        int pivotindex = findpivot(i, j);
        swap(a, pivotindex, j);
        int k = 0;
        int temp = i;
        if (getKey(temp) == getKey(j)) {
            while (getKey(temp) == getKey(j) && temp <= j) {
                
                temp += 1;
            }
            if (temp > j) {
                return;
            }
        }
        k = partition(a, i, j - 1, getKey(j));
        swap(a, k, j);
        if ((k - i) > 1) {
            if (k - i <= 10) {
                
                for (int index = i; index <= k - 1; index = index + 1) {
                    for (int index2 = index; (index2 > 0) 
                            && getKey(index2) < getKey(index2 - 1); 
                            index2 = index2 - 1) {
                        swap(a, index2, index2 - 1);
                    }
                }
            }
            else {
                quicksort(a, i, k - 1);
            }
        }
        if ((j - k) > 1) {
            if (j - k <= 10) {
                
                for (int index = k + 1; index <= j; index = index + 1) {
                    for (int index2 = index; (index2 > 0) 
                            && getKey(index2) < getKey(index2 - 1); 
                            index2 = index2 - 1) {
                        swap(a, index2, index2 - 1);
                    }
                }
            }
            else {
                quicksort(a, k + 1, j);
            }
        }
    }
    
    /**
     * 
     * @param a is the bufferpool
     * @param left is the left index
     * @param right is the right index
     */
    public void swap(BufferPool a, int left, int right) {
        counter++;
        a.writeOut(leftA, 4, left);       
        a.writeOut(rightA, 4, right);
        a.writeIn(rightA, 4, left);
        a.writeIn(leftA, 4, right);
    }
    
    /**
     * 
     * @param left is the left index
     * @param right is the right index
     * @return the mid index
     */
    public int findpivot(int left, int right) {
        
        return (left + right) / 2;
    }

    /**
     * @param index is the index that we want to get key from
     * @return the position of he index
     */
    public int getKey(int index) {
        
        pool.writeOut(tmp, 4, index);
        ByteBuffer byteBuffer = ByteBuffer.wrap(tmp);
        int length = byteBuffer.getShort();
        return length;
    }
}