import java.util.*;
import java.lang.*;

public class branchPredictor {

    private static final int ST = 3;
    private static final int WT = 2;
    private static final int SNT = 0;
    private static final int WNT = 1;

    static int[] ghr;
    static int[] predictTable;

    static int totalPredict = 0;
    static int correctPredict = 0;
    static int ghrSize = 2;

    static boolean ifTaken = false;

    public branchPredictor() {}

    public static void doPrediction(boolean taken) {
        int currIdxElem = getPrediction();
        //check if prediction correct
        if (taken) {
            if ((currIdxElem == ST) || (currIdxElem == WT)) {
                correctPredict += 1;
            }
        } else {
            if ((currIdxElem == SNT) || (currIdxElem == WNT)) {
                correctPredict += 1;
            }
        }
        updatePredictor(getIndex(ghr), taken);
        updateGHR(taken);
    }

    public void printTable() {
        for (int i = 0; i < predictTable.length; i++) {
            System.out.println(predictTable[i]);
        }
    }

    public void initializeGHR(int bits) {
        ghr = new int[bits];
        for (int i = 0; i < ghr.length; i++) {
            ghr[i] = SNT;
        }
        initializeTable(bits);
    }

    // table = 2^(bits)
    public void initializeTable(int bits) {
        predictTable = new int[(int)Math.pow(2, bits)];
        for(int idx = 0; idx < predictTable.length; idx++){
            predictTable[idx] = 0;
        }
    }

    public static int getPrediction() {
        int idx = getIndex(ghr);
        int predictIdx = predictTable[idx];
        return predictIdx;
    }

    public static int getIndex(int[] arr) {
        int bin;
        String temp;
        String binStr = "";
        for (int num = 0; num < arr.length; num++) {
            binStr += ghr[num];
        }
        bin = Integer.parseInt(binStr, 2);
        temp = Integer.toString(bin);
        return Integer.parseInt(temp, 10);
    }

    public static void updatePredictor(int idx, boolean actualPredict) {
        int current = predictTable[idx];
        if (actualPredict) {
            if (current < ST) {
                predictTable[idx] = current + 1;
            }
        } else {
            if (current > SNT) {
                predictTable[idx] = current - 1;
            }
        }
    }

    public static void updateGHR(boolean branchRes) {
        int bit = 0;
        int last = ghr.length;
        if (branchRes) {
            bit = 1;
        }
        for (int i = 0; i < ghr.length - 1; i++) {
            ghr[i] = ghr[i+1];
        }
        ghr[last - 1] = bit;
    }

    public static void printAccuracy() {
        float percent = ((float)correctPredict/(float)totalPredict) * 100;
        //DecimalFormat df = new DecimalFormat("#.00");
        System.out.printf("\naccuracy %.2f", percent);
        System.out.print("% (" + correctPredict + " correct predictions, " + totalPredict + " predictions)\n\n");
    }
}
