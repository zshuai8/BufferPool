/**
 * @author Shuaicheng Zhang
 * @version 2016.10.23
 */
public class Buffer {
    private byte[] buf;
    private int currentIndex;
    private boolean dirty;
    
    /**
     * Buffer constructor
     * initialize the buffer
     * @param index is the current index
     * @param inputArray is the input array
     */
    public Buffer(int index, byte[] inputArray) {
        
        buf = inputArray;
        currentIndex = index;
        dirty = false;
    }
    
    /**
     * 
     * @return the current index
     */
    public int getIndex() {
        
        return currentIndex;
    }
    
    /**
     * 
     * @return the dirty bit
     */
    public boolean getDirty() {
        
        return dirty;
    }
    
    /**
     * 
     * set the dirty bit to inputB
     * @param inputB is what we want to set the dirty bit to
     */
    public void setDirty(boolean inputB) {
        
        dirty = inputB;
    }
    
    /**
     * 
     * @return byte stored in the buffer
     */
    public byte[] getByte() {
        
        return buf;
    }
}
