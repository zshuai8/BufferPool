import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


/**
 * @author shuaicheng zhang
 * @version 2016.10.16
 *
 */
public class BufferPool implements BufferPoolADT {
    private RandomAccessFile file;
    private final static int SIZE = 4096;
    private LRUList<Buffer> list;
    private int discR = 0;
    private int discW = 0;
    
    /**
     * BufferPool constructor
     * initialize the pool size
     * @param src is the source string
     * @param size is the size of the bufferpool
     */
    public BufferPool(String src, int size) {
        
        list = new LRUList<Buffer>(size);
        try {
            file = new RandomAccessFile(src, "rw");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }     
    }
    
    /**
     *  Copy "sz" bytes from "space" to position "pos" in the buffered storage
     *  @param space is the source array 
     *  @param sz is the size of bytes space wants to copy 
     *  @param index is the byte position in the bufferpool space wants to copy
     */
    public void writeIn(byte[] space, int sz, int index) {
        int pos = index * 4;
        Buffer buf = search(pos);
        if (buf != null) {
            System.arraycopy(space, 0, buf.getByte(), pos % SIZE, 4);
        }
        else {
            byte[] newByte = new byte[SIZE];
            try {
                file.seek((pos / SIZE) * SIZE);
                file.read(newByte);
                discR++;
                buf = new Buffer(pos / SIZE , newByte);
                System.arraycopy(space, 0, buf.getByte(), pos % SIZE, 4);
                Buffer returned;
                if (list.isFull()) {
                    returned = list.get(list.size() - 1);
                    if (returned.getDirty()) {
                        file.seek(returned.getIndex() * SIZE);
                        file.write(returned.getByte());
                        discW++;
                        returned.setDirty(false);
                    }
                    list.removeEnd();
                }
                list.add(buf);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            
        }
        buf.setDirty(true);
    }
    
    /**
     *  Copy "sz" bytes from position "pos" of the buffered storage to "space"
     *  @param space is the destination byte array 
     *  @param sz is the size of bytes bufferpool wants to copy 
     *  @param index is the byte position in the array bufferpool wants to copy
     */
    public void writeOut(byte[] space, int sz, int index) {
        int pos = index * 4;
        Buffer buf = search(pos);
        if (buf != null) {
            System.arraycopy(buf.getByte(), pos % SIZE, space, 0, 4);
        }
        else {
            byte[] newByte = new byte[SIZE];
            try {
                file.seek((pos / SIZE) * SIZE);
                file.read(newByte);
                discR++;
                buf = new Buffer(pos / SIZE , newByte);
                System.arraycopy(buf.getByte(), pos % SIZE, space, 0,  4);
                Buffer returned;
                if (list.isFull()) {
                    returned = list.get(list.size() - 1);
                    if (returned.getDirty()) {
                        file.seek(returned.getIndex() * SIZE);
                        file.write(returned.getByte());
                        discW++;
                        returned.setDirty(false);
                    }
                    list.removeEnd();
                }
                list.add(buf);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }
    
    /**
     * 
     * @param index is the search index
     * @return the buffer with the search index
     */
    public Buffer search(int index) {
        
        for (int i = 0; i < list.size(); i++) {
            Buffer newBuffer = list.get(i);
            if (newBuffer.getIndex() == index / SIZE) {
                return newBuffer;
            }
        }
        return null;
    }
    
    /**
     * 
     * @param position is the position of the section
     * @return the section number
     */
    public int getSection(int position) {
        
        return position / SIZE;
    }
    
    /**
     * 
     * @return the length of the file
     * @throws IOException when the file doesn't exist
     */
    public int getFileLen() throws IOException {
        
        return (int)file.length();
    }
    
    /**
     * flushes all the dirty bit back to the memory
     * @throws IOException 
     * 
     */
    public void flushAll() throws IOException {
        
        Buffer cur;
        for (int i = 0; i < list.size(); i++) {
            cur = list.get(i);
            if (cur.getDirty()) {
                file.seek(cur.getIndex() * SIZE);
                file.write(cur.getByte());
                discW++;
            }
        }
    }
    
    /**
     * 
     * @return number of disk reads
     */
    public int discRead() {
        return discR;
    }
    
    /**
     * 
     * @return number of disk writes
     */
    public int discWrite() {
        return discW;
    }
}