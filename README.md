# BufferPool
functionality of quicksort using LRUList

Author:Shuaicheng Zhang

Description: quick sort the inputfile with the given size of buffer.
             return a report text file for sorting time and cache hitting time.
             
Invocation and I/O Files:
The program will be invoked from the command-line as:
java Quicksort <data-file-name> <numb-buffers> <stat-file-name>
The data ﬁle <data-file-name> is the ﬁle to be sorted. The sorting takes place in that ﬁle, so this program does modify the input data ﬁle. The parameter <numb-buffers> determines the number of buﬀers allocated for the buﬀer pool. This value will be in the range 1–20. The parameter <stat-file-name> is the name of a ﬁle that the program will generate to store runtime statistics

File: Ascii file and binary file.

Inside the report text file
(a) The name of the data ﬁle being sorted.
(b) The number of cache hits, or times the program found the data it needed in a buﬀer and did not have to go to the disk.
(c) The number of disk reads, or times the program had to read a block of data from disk into a buﬀer.
(d) The number of disk writes, or times the program had to write a block of data to disk from a buﬀer.
(e) The time that the program took to execute the Quicksort. This method returns a long value. The diﬀerence between the two values will be the total runtime in milliseconds
