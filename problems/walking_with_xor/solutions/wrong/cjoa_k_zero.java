import java.io.*;
import java.util.*;

public class cjoa_k_zero // implements Runnable
{
   public static void main (String[] args) throws IOException {
      (new cjoa_k_zero()).run();
   }
   
   BufferedReader in;
   PrintWriter out;
   StringTokenizer st = new StringTokenizer("");

   void run() throws IOException {
      try {
         in = new BufferedReader(new FileReader("input.txt"));
         out = new PrintWriter("output.txt");
      } catch (Exception e) {
         in = new BufferedReader(new InputStreamReader(System.in));
         out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
      }

      solve();

      out.close();
   }

   static final int MOD = 1000000007;
   static final int NBITS = 21;

   int[][] psumDP;
   void add(int x, int val) {
      for (int len = NBITS; len > 0; --len) {
         psumDP[len][x] += val;
         if (psumDP[len][x] >= MOD)
            psumDP[len][x] -= MOD;
         x >>= 1;
      }
   }

   void solve() throws IOException {
      int N = nextInt(), K = nextInt();

      if (K == 0) {
         out.println(0);
         return;
      }

      int[] A = new int[N];
      for (int i = 0; i < N; ++i)
         A[i] = nextInt();

      psumDP = new int[NBITS + 1][];
      for (int len = 1; len <= NBITS; ++len)
         psumDP[len] = new int[1<<len];

      int[] DP = new int[N];
      DP[N-1] = 1;
      add(A[N-1], DP[N-1]);

      for (int i = N-2; i >= 0; --i) {
         int ai = A[i];
         for (int b = NBITS-1, prefix = 0; b >= 0; --b) {
            int len = NBITS - b;
            if ((K & (1 << b)) != 0) {
               prefix = (prefix << 1) | ((ai >> b) & 1);
               DP[i] += psumDP[len][prefix];
               if (DP[i] >= MOD)
                  DP[i] -= MOD;
               prefix ^= 1;
            }
            else {
               prefix = (prefix << 1) | ((ai >> b) & 1);
            }
         }
         add(ai, DP[i]);
      }

      out.println(DP[0]);
   }

   // get next token
   String next() throws IOException {
      while (!st.hasMoreTokens())
         st = new StringTokenizer(in.readLine());
      return st.nextToken();
   }

   // get next int
   int nextInt() throws IOException {
      return Integer.parseInt(next());
   }
}

