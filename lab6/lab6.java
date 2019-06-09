
/*
	Authors     : Carmina Cruz, Gonzalo Arana
	File        : lab6.java
	Description : Cache simulator
                  2KB, direct mapped, 1-word blocks
                  2KB, direct mapped, 2-word blocks
                  2KB, direct mapped, 4-word blocks
                  2KB, 2-way set associative, 1-word blocks
                  2KB, 4-way set associative, 1-word blocks
                  2KB, 4-way set associative, 4-word blocks
                  4KB, direct mapped, 1-word blocks
*/

import java.util.*;
import java.io.*;
import java.lang.Math;

public class lab6 {
    static List<String> addresses = new ArrayList<String>();
    public static void main(String args[]){
        int KB = 1024;
        /* File parsing that Scans in lines of the file and splits at the tab
           to grab just the addresses and then adds them to a List */
        Scanner readFile;

        try {
            readFile = new Scanner(new File(args[0]));

            while (readFile.hasNext()) {
                String line = readFile.nextLine();
                String[] lineSplit = line.split("\t");

                addresses.add(lineSplit[1]);
                String addr = lineSplit[1];
            }
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist: " + args[0]);
        }

        // 2KB, direct mapped, 1-word blocks
        directMappedCache(1, addresses, (2 * KB), 1);

        // 2KB, direct mapped, 2-word blocks
        directMappedCache(2, addresses, (2 * KB), 2);

        // 2KB, direct mapped, 4-word blocks
        directMappedCache(3, addresses, (2 * KB), 4);

        // 2KB, 2-way set associative, 1-word blocks
        setAssociativeCache1(4, addresses, (2 * KB), 1, 2);

        // 2KB, 4-way set associative, 1-word blocks
        setAssociativeCache2(5, addresses, (2 * KB), 1, 4);

        // 2KB, 4-way set associative, 4-word blocks
        setAssociativeCache2(6, addresses, (2 * KB), 4, 4);

        // 4KB, direct mapped, 1-word blocks
        directMappedCache(7, addresses, (4 * KB), 1);
    }

    static void printCache(int cacheNum, int cacheSize, int associativity, int blockSize, int numHits, double hitRate) {
        System.out.println("Cache #" + cacheNum);
        System.out.print("Cache size: " + cacheSize + "B\t");
        System.out.print("Associativity: " + associativity);
        System.out.print("\tBlock size: " + blockSize);
        System.out.print("\nHits: " + numHits);
        System.out.print(String.format("\tHit Rate: %.2f%%\n", hitRate));
        System.out.println("---------------------------");
    }

    static void directMappedCache(int cacheNum, List<String> addressList, int cacheSize, int wordBlocks) {
        // hit counter
        int hits = 0;

        // multiply wordBlocks by 4 because it's byte-addressable
        int idxSize = (cacheSize) / (wordBlocks * 4);
        int blockOffset = idxSize % wordBlocks;

        // initialize cache
        int[][] cache = new int[idxSize][1];

        // puts addresses into cache
        int counter = 0;
        while (counter != addressList.size()) {
            String address = addressList.get(counter);
            int intAddress = Integer.parseInt(address, 16);
            intAddress = intAddress / (wordBlocks * 4);
            int idx = intAddress % idxSize;

            // if the current index is empty, put address in; else, increase hit count
            if (cache[idx][blockOffset] != intAddress) {
                cache[idx][blockOffset] = intAddress;
            } else {
                hits += 1;
            }
            counter++;
        }
        double hitRate = ((double)hits/(double)counter) * (double)100;
        printCache(cacheNum, cacheSize, 1, wordBlocks, hits, hitRate);
    }

    static void setAssociativeCache1(int cacheNum, List<String> addressList, int cacheSize, int wordBlocks, int numWays) {
        int hits = 0;
        int idxSize = (cacheSize / numWays) / (wordBlocks * 4);
        int cache[][] = new int[idxSize][numWays];
        int lru[][] = new int[idxSize][numWays];

        int counter = 0;
        boolean equalTag = true;
        while (counter != addressList.size()) {
            String address = addressList.get(counter);
            counter++;
            int intAddress = Integer.parseInt(address, 16);
            intAddress = intAddress / (wordBlocks * 4);
            int idx = intAddress % idxSize;
            int tag = intAddress / idxSize;
            int ways = 0;

            if (cache[idx][0] == tag) {
				lru[idx][0] = counter;
                hits += 1;
			} else if (cache[idx][1] == tag) {
				lru[idx][1] = counter;
                hits += 1;
			} else {
                int minIdx = findLRU(numWays, idx, lru);
				cache[idx][minIdx] = tag;
				lru[idx][minIdx] = counter;
			}
        }
        double hitRate = ((double)hits/(double)counter) * (double)100;
        printCache(cacheNum, cacheSize, numWays, wordBlocks, hits, hitRate);
    }

    static void setAssociativeCache2(int cacheNum, List<String> addressList, int cacheSize, int wordBlocks, int numWays) {
        int hits = 0;
        int idxSize = (cacheSize / numWays) / (wordBlocks * 4);
        int cache[][] = new int[idxSize][numWays];
        int lru[][] = new int[idxSize][numWays];

        int counter = 0;
        boolean equalTag = true;
        while (counter != addressList.size()) {
            String address = addressList.get(counter);
            counter++;
            int intAddress = Integer.parseInt(address, 16);
            intAddress = intAddress / (wordBlocks * 4);
            int idx = intAddress % idxSize;
            int tag = intAddress / idxSize;
            int ways = 0;

            if (cache[idx][0] == tag) {
				lru[idx][0] = counter;
                hits += 1;
			} else if (cache[idx][1] == tag) {
				lru[idx][1] = counter;
                hits += 1;
			} else if (cache[idx][2] == tag) {
				lru[idx][2] = counter;
                hits += 1;
			} else if (cache[idx][3] == tag) {
				lru[idx][3] = counter;
                hits += 1;
			} else {
                int minIdx = findLRU(numWays, idx, lru);
				cache[idx][minIdx] = tag;
				lru[idx][minIdx] = counter;
			}
        }
        double hitRate = ((double)hits/(double)counter) * (double)100;
        printCache(cacheNum, cacheSize, numWays, wordBlocks, hits, hitRate);
    }

    static void setAssociativeCache3(int cacheNum, List<String> addressList, int cacheSize, int wordBlocks, int numWays) {
        int hits = 0;
        int idxSize = (cacheSize / numWays) / (wordBlocks * 4);
        int cache[][] = new int[idxSize][numWays];
        int lru[][] = new int[idxSize][numWays];

        int counter = 0;
        boolean equalTag = true;
        while (counter != addressList.size()) {
            String address = addressList.get(counter);
            counter++;
            int intAddress = Integer.parseInt(address, 16);
            intAddress = intAddress / (wordBlocks * 4);
            int idx = intAddress % idxSize;
            int tag = intAddress / idxSize;
            int ways = 0;

            if (cache[idx][0] == tag) {
				lru[idx][0] = counter;
                hits += 1;
			} else if (cache[idx][1] == tag) {
				lru[idx][1] = counter;
                hits += 1;
			} else if (cache[idx][2] == tag) {
				lru[idx][2] = counter;
                hits += 1;
			} else if (cache[idx][3] == tag) {
				lru[idx][3] = counter;
                hits += 1;
			} else {
                int minIdx = findLRU(numWays, idx, lru);
				cache[idx][minIdx] = tag;
				lru[idx][minIdx] = counter;
			}
        }
        double hitRate = ((double)hits/(double)counter) * (double)100;
        printCache(cacheNum, cacheSize, numWays, wordBlocks, hits, hitRate);
    }

    static int findLRU(int numWays, int idx, int[][] lru) {
        ArrayList<Integer> lineNums = new ArrayList<Integer>();
        int minIndex = 0;

        for(int i = 0; i < numWays; i++)
        {
            lineNums.add(lru[idx][i]);
        }
        minIndex = lineNums.indexOf(Collections.min(lineNums));
        return minIndex;
    }
}
