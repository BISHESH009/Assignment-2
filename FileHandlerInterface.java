/**
 * FileHandlerInterface.java
 * 
 * KIT107 Assignment 2 -- FileHandler Specification
 * 
 * @author Julian Dermoudy
 * @version	3/8/2026
 * 
 * FILE IS COMPLETE
 */


public interface FileHandlerInterface
{
    // Constructor
    //public FileHandler(final String FILENAME, final int LIMIT);

    // Other (Doer) methods
    public void openFile();
    public void closeFile();
    public void readFile(Collection collection);
}