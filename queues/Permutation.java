/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int max = 0;
        int count = 0;
        if (args.length > 0) { max = Integer.parseInt(args[0]); }
        RandomizedQueue<String> list = new RandomizedQueue<String>();

        while (!StdIn.isEmpty() && (max == 0 || count < max)) {
            String item = StdIn.readString();
            count++;
            list.enqueue(item);
        }

        for (String i : list) {
            StdOut.println(i);
        }
    }
}
