import java.io.IOException;

import shuaicheng zhang

/**
 * @author zshuai8, jiaheng1
 * @version 2016.10.24
 */
public class QuicksortTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization.
     */
    CheckFile fileChecker;
    
    
    /**
     * run before every test case
     * initialize the filechecker for use in tests
     */
    public void setUp() {
        fileChecker = new CheckFile();
    }

    /**
     * test binary file with 1 blocks and 1 buffer
     * @throws IOException when the file doesn't exist
     */
    public void testB1block1buffer() throws IOException {
        Quicksort tree = new Quicksort();
        assertNotNull(tree);
        Quicksort.generateFile("test1b.bin", "1", 'b');
        try {
            boolean check1 = fileChecker.checkFile("test1b.bin");
            assertFalse(check1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String[] str = {"test1b.bin", "1", "stats.txt"};
        Quicksort.main(str);
        try {
            boolean check = fileChecker.checkFile("test1b.bin");
            assertTrue(check);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * test ascii file with 1 blocks and 1 buffer
     * @throws IOException when the file doesn't exist
     */
    public void testA1block1buffer() throws IOException {
        Quicksort tree = new Quicksort();
        assertNotNull(tree);
        Quicksort.generateFile("test1a.bin", "1", 'a');
        try {
            boolean check1 = fileChecker.checkFile("test1a.bin");
            assertFalse(check1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String[] str = {"test1a.bin", "1", "stats.txt"};
        Quicksort.main(str);
        try {
            boolean check = fileChecker.checkFile("test1a.bin");
            assertTrue(check);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    /**
     * test binary file with 100 blocks and 10 buffer
     * @throws IOException when the file doesn't exist
     */
    public void testB100block10buffer() throws IOException {
        Quicksort tree = new Quicksort();
        assertNotNull(tree);
        Quicksort.generateFile("test1.bin", "100", 'b');
        try {
            boolean check1 = fileChecker.checkFile("test1.bin");
            assertFalse(check1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String[] str = {"test1.bin", "10", "stats.txt"};
        Quicksort.main(str);
        try {
            boolean check = fileChecker.checkFile("test1.bin");
            assertTrue(check);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * test ascii file with 100 blocks and 10 buffers
     * @throws IOException when the file doesn't exist
     */
    public void testA100block10buffer() throws IOException {
        Quicksort tree = new Quicksort();
        assertNotNull(tree);
        Quicksort.generateFile("testAscii.bin", "100", 'a');
        try {
            boolean check1 = fileChecker.checkFile("testAscii.bin");
            assertFalse(check1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String[] str = {"testAscii.bin", "10", "stats.txt"};
        Quicksort.main(str);
        try {
            boolean check = fileChecker.checkFile("testAscii.bin");
            assertTrue(check);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * test binary file with 10 blocks and 1 buffer
     * @throws IOException when the file doesn't exist
     */
    public void testB10block1buffer() throws IOException {
        Quicksort tree = new Quicksort();
        assertNotNull(tree);
        Quicksort.generateFile("test10.bin", "10", 'b');
        try {
            boolean check1 = fileChecker.checkFile("test10.bin");
            assertFalse(check1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String[] str = {"test10.bin", "1", "stats.txt"};
        Quicksort.main(str);
        try {
            boolean check = fileChecker.checkFile("test10.bin");
            assertTrue(check);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * test fail sort
     * @throws IOException when file doesn't exist
     */
    public void testFailSort() throws IOException {
        Exception thrown = new Exception();
        Quicksort.generateFile("test3.bin", "1", 'a');
        try {
            boolean check1 = fileChecker.checkFile("test3.bin");
            assertFalse(check1);
        }
        catch (Exception e) {
            e = thrown;
            assertNotNull(e);
        }        
    }
    
    /**
     * test wrong type of file to generator
     * @throws IOException when file doesn't exist
     */
    public void testWrongType() throws IOException {
        Exception thrown = new Exception();
        try {
            Quicksort.generateFile("test4.bin", "1", 'c');
            boolean check = fileChecker.checkFile("test4.bin");
            assertFalse(check);
        }
        catch (Exception e) {
            e = thrown;
            assertNotNull(e);
        }
    }
    
    /**
     * test some cases in quick sort method
     * @throws IOException when file doesn't exist
     */
    public void testQuickSort() throws IOException {
        BufferPool buffer = new BufferPool("abc", 10);
        assertNotNull(buffer);
        assertNotNull(buffer.getSection(0));
        Reporter report = new Reporter("test1.txt", 10, "test2.txt");
        report.quicksort(buffer, 10, 2); 
    }
    
}
