/**
 * This class provides interface for the bufferpool to use
 * 
 * @author shuaicheng zhang
 * @version 2016/10/19
 *
 */
public interface BufferPoolADT {
    
    /**
     *  Copy "sz" bytes from "space" to position "pos" in the buffered storage
     *  @param space is the source array 
     *  @param sz is the size of bytes space wants to copy 
     *  @param pos is the byte position in the bufferpool space wants to copy
     */
    public void writeIn(byte[] space, int sz, int pos);

    /**
     *  Copy "sz" bytes from position "pos" of the buffered storage to "space"
     *  @param space is the destination byte array 
     *  @param sz is the size of bytes bufferpool wants to copy 
     *  @param pos is the byte position in the array bufferpool wants to copy
     */
    public void writeOut(byte[] space, int sz, int pos);
}
