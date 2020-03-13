import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
    FastScanner in;
    PrintWriter out;
    boolean systemIO = true;

    public class DSU {
        int[] sz;
        int[] p;

        public DSU(int n) {
            sz = new int[n];
            p = new int[n];
            for (int i = 0; i < p.length; i++) {
                p[i] = i;
                sz[i] = 1;
            }
        }

        public int get(int x) {
            if (x == p[x]) {
                return x;
            }
            int par = get(p[x]);
            p[x] = par;
            return par;
        }

        public boolean unite(int a, int b) {
            int pa = get(a);
            int pb = get(b);
            if (sz[pa] == 1) {
                p[pa] = pb;
                sz[pb] += sz[pa];
                return true;
            }
            if (sz[pb] == 1) {
                p[pb] = pa;
                sz[pa] += sz[pb];
                return true;
            }
            return false;
        }
    }

    public class SegmentTree {
        int pow;
        long[] sum;
        long[] maxPrefSum;
        long[] maxSufSum;
        long[] maxSum;

        public SegmentTree(long[] a) {
            pow = 1;
            while (pow < a.length) {
                pow *= 2;
            }
            sum = new long[2 * pow];
            maxPrefSum = new long[2 * pow];
            maxSum = new long[2 * pow];
            maxSufSum = new long[2 * pow];
            for (int i = 0; i < a.length; i++) {
                sum[pow + i] = a[i];
                maxSum[pow + i] = Math.max(a[i], 0);
                maxPrefSum[pow + i] = maxSum[pow + i];
                maxSufSum[pow + i] = maxSum[pow + i];
            }
            for (int i = pow - 1; i > 0; i--) {
                update(i);
            }
        }

        public long[] get(int v, int tl, int tr, int l, int r) {
            if (r <= tl || l >= tr) {
                long[] ans = {0, 0, 0, 0};
                return ans;
            }
            if (l <= tl && r >= tr) {
                long[] ans = {maxPrefSum[v], maxSum[v], maxSufSum[v], sum[v]};
                return ans;
            }
            int tm = (tl + tr) / 2;
            long[] left = get(2 * v, tl, tm, l, r);
            long[] right = get(2 * v + 1, tm, tr, l, r);
            long[] ans = {Math.max(left[0], right[0] + left[3]), Math.max(left[1], Math.max(right[1], left[2] + right[0])), Math.max(right[2], left[2] + right[3]), left[3] + right[3]};
            return ans;
        }

        public void update(int i) {
            sum[i] = f(sum[2 * i], sum[2 * i + 1]);
            maxSum[i] = Math.max(maxSum[2 * i], Math.max(maxSum[2 * i + 1], maxSufSum[2 * i] + maxPrefSum[2 * i + 1]));
            maxPrefSum[i] = Math.max(maxPrefSum[2 * i], maxPrefSum[2 * i + 1] + sum[2 * i]);
            maxSufSum[i] = Math.max(maxSufSum[2 * i + 1], maxSufSum[2 * i] + sum[2 * i + 1]);
        }

        public long f(long a, long b) {
            return a + b;
        }
    }

    public class SegmentTreeAdd {
        int pow;
        long[] sum;
        long[] delta;
        boolean[] flag;

        public SegmentTreeAdd(long[] a) {
            pow = 1;
            while (pow < a.length) {
                pow *= 2;
            }
            flag = new boolean[2 * pow];
            sum = new long[2 * pow];
            delta = new long[2 * pow];
            for (int i = 0; i < a.length; i++) {
                sum[pow + i] = a[i];
            }
            for (int i = pow - 1; i > 0; i--) {
                sum[i] = f(sum[2 * i], sum[2 * i + 1]);
            }
        }

        public long get(int v, int tl, int tr, int l, int r) {
            push(v, tl, tr);
            if (l > r) {
                return 0;
            }
            if (l == tl && r == tr) {
                return sum[v];
            }
            int tm = (tl + tr) / 2;
            return f(get(2 * v, tl, tm, l, Math.min(r, tm)), get(2 * v + 1, tm + 1, tr, Math.max(l, tm + 1), r));
        }

        public void set(int v, int tl, int tr, int l, int r, long x) {
            push(v, tl, tr);
            if (l > tr || r < tl) {
                return;
            }
            if (l <= tl && r >= tr) {
                delta[v] += x;
                flag[v] = true;
                push(v, tl, tr);
                return;
            }
            int tm = (tl + tr) / 2;
            set(2 * v, tl, tm, l, r, x);
            set(2 * v + 1, tm + 1, tr, l, r, x);
            sum[v] = f(sum[2 * v], sum[2 * v + 1]);
        }

        public void push(int v, int tl, int tr) {
            if (flag[v]) {
                if (v < pow) {
                    flag[2 * v] = true;
                    flag[2 * v + 1] = true;
                    delta[2 * v] += delta[v];
                    delta[2 * v + 1] += delta[v];
                }
                flag[v] = false;
                sum[v] += delta[v] * (tr - tl + 1);
            }
        }

        public long f(long a, long b) {
            return a + b;
        }
    }

    public class SegmentTreeSet {
        int pow;
        int[] sum;
        int[] delta;
        boolean[] flag;

        public SegmentTreeSet(int[] a) {
            pow = 1;
            while (pow < a.length) {
                pow *= 2;
            }
            flag = new boolean[2 * pow];
            sum = new int[2 * pow];
            delta = new int[2 * pow];
            for (int i = 0; i < a.length; i++) {
                sum[pow + i] = a[i];
            }
        }

        public int get(int v, int tl, int tr, int l, int r) {
            push(v, tl, tr);
            if (l > r) {
                return 0;
            }
            if (l == tl && r == tr) {
                return sum[v];
            }
            int tm = (tl + tr) / 2;
            return f(get(2 * v, tl, tm, l, Math.min(r, tm)), get(2 * v + 1, tm + 1, tr, Math.max(l, tm + 1), r));
        }

        public void set(int v, int tl, int tr, int l, int r, int x) {
            push(v, tl, tr);
            if (l > tr || r < tl) {
                return;
            }
            if (l <= tl && r >= tr) {
                delta[v] = x;
                flag[v] = true;
                push(v, tl, tr);
                return;
            }
            int tm = (tl + tr) / 2;
            set(2 * v, tl, tm, l, r, x);
            set(2 * v + 1, tm + 1, tr, l, r, x);
            sum[v] = f(sum[2 * v], sum[2 * v + 1]);
        }

        public void push(int v, int tl, int tr) {
            if (flag[v]) {
                if (v < pow) {
                    flag[2 * v] = true;
                    flag[2 * v + 1] = true;
                    delta[2 * v] = delta[v];
                    delta[2 * v + 1] = delta[v];
                }
                flag[v] = false;
                sum[v] = delta[v] * (tr - tl + 1);
            }
        }

        public int f(int a, int b) {
            return a + b;
        }
    }

    public class Pair implements Comparable<Pair> {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Pair clone() {
            return new Pair(x, y);
        }

        public String toString() {
            return x + " " + y;
        }

        @Override
        public int compareTo(Pair o) {
            if (x > o.x) {
                return 1;
            }
            if (x < o.x) {
                return -1;
            }
            return -(y - o.y);
        }
    }

    long mod = 1000000007;
    Random random = new Random(566);

    public void shuffle(Pair[] a) {
        for (int i = 0; i < a.length; i++) {
            int x = random.nextInt(i + 1);
            Pair t = a[x];
            a[x] = a[i];
            a[i] = t;
        }
    }

    public void sort(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            Arrays.sort(a[i]);
        }
    }

    public static class Fenvik {
        long[] t;

        public Fenvik(int n) {
            t = new long[n];
        }

        public void add(int x, long delta) {
            for (int i = x; i < t.length; i = (i | (i + 1))) {
                t[i] += delta;
            }
        }

        private long sum(int r) {
            long ans = 0;
            int x = r;
            while (x >= 0) {
                ans += t[x];
                x = (x & (x + 1)) - 1;
            }
            return ans;
        }

        public long sum(int l, int r) {
            return sum(r) - sum(l - 1);
        }
    }

    public int gcd(int x, int y) {
        if (x == 0) {
            return y;
        }
        return gcd(y % x, x);
    }

    public class Vector {
        int x;
        int y;

        public Vector(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Vector add(Vector v) {
            return new Vector(x + v.x, y + v.y);
        }
    }

    public long cp(Vector v0, Vector v1, Vector v2) {
        return (v1.x - v0.x) * (v2.y - v0.y) - (v1.y - v0.y) * (v2.x - v0.x);
    }

    public void add(TreeMap<Integer, Integer> map, Integer s) {
        if (map.containsKey(s)) {
            map.put(s, map.get(s) + 1);
        } else {
            map.put(s, 1);
        }
    }

    public void remove(TreeMap<Integer, Integer> map, Integer s) {
        if (map.get(s) > 1) {
            map.put(s, map.get(s) - 1);
        } else {
            map.remove(s);
        }
    }

    public long dfs(int v, int prev) {
        long sum = 0;
        if (to[v].length == 1 && prev >= 0) {
            sum++;
        }
        for (int i : to[v]) {
            if (i != prev) {
                long x = dfs(i, v);
                p[v] += x * (sum);
                sum += x;
            }
        }
        p[v] += sum * (leaves - sum);
//		System.out.println(v + " " + sum + " " + p[v] + " " + leaves);
        return sum;
    }

    long[] p;
    int[][] to;
    long leaves = 0;

    @SuppressWarnings("unchecked")
    public void readGraph() {
        int t = in.nextInt();
        for (; t > 0; t--) {
            leaves = 0;
            int n = in.nextInt();
            p = new long[n];
            to = new int[n][];
            long[] a = new long[n];
            for (int i = 0; i < a.length; i++) {
                a[i] = in.nextLong();
            }
            Arrays.sort(a);
            int[] sz = new int[n];
            int[] x = new int[n - 1];
            int[] y = new int[n - 1];
            for (int i = 0; i < x.length; i++) {
                x[i] = in.nextInt() - 1;
                y[i] = in.nextInt() - 1;
                sz[x[i]]++;
                sz[y[i]]++;
            }
            for (int i = 0; i < to.length; i++) {
                if (sz[i] == 1) {
                    leaves++;
                }
                to[i] = new int[sz[i]];
                sz[i] = 0;
            }
            for (int i = 0; i < x.length; i++) {
                to[x[i]][sz[x[i]]++] = y[i];
                to[y[i]][sz[y[i]]++] = x[i];
            }
            dfs(0, -1);
//			for (int i = 0; i < p.length; i++) {
//				System.out.println(p[i]);
//			}
//			System.out.println();
            long ans = 0;
            Arrays.sort(p);
            for (int i = 0; i < p.length; i++) {
                ans += (p[i] % mod) * a[i];
                ans %= mod;
            }
            out.println(ans);
        }
    }

    public long pow(long x, long p) {
        if (p <= 0) {
            return 1;
        }
        long ans = pow(x, p / 2);
        ans = ans * ans % mod;
        if (p % 2 == 1) {
            ans *= x;
            ans %= mod;
        }
        return ans;
    }

    public void solve() {
        int t = in.nextInt();
        w : while (t > 0) {
            t--;
            int n = in.nextInt();
            long[] a = new long[2 * n];
            long max = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                a[i] = in.nextLong();
                max = Math.max(max, a[i]);
            }
            for (int i = 0; i < n; i++) {
                a[n + i] = a[i];
            }
            if (max < 0) {
                for (int i = 0; i < n; i++) {
                    out.print(max + " ");
                }
                out.println();
                continue w;
            }
            SegmentTree st = new SegmentTree(a);
            for (int i = 0; i < n; i++) {
                out.print(st.get(1, 0, st.pow, i, i + n)[1] + " ");
            }
            out.println();
        }
    }

    public void stress() {
        int c = 0;
        while (true) {
            c++;
            System.out.println(c);
            int n = 100;
            long[] a = new long[2 * n];
            for (int i = 0; i < n; i++) {
                a[i] = random.nextInt(2000000000) - 1000000000;
            }
            long[] slow = solveFast(n, a);
            long[] fast = solveSlow(n, a);
            for (int i = 0; i < fast.length; i++) {
                if (fast[i] != slow[i]) {
                    System.out.println(n);
                    for (int j = 0; j < n; j++) {
                        System.out.print(a[j] + " ");
                    }
                    System.out.println();
                    for (int j = 0; j < slow.length; j++) {
                        System.out.print(slow[j] + " ");
                    }
                    System.out.println();
                    for (int j = 0; j < fast.length; j++) {
                        System.out.print(fast[j] + " ");
                    }
                    System.out.println();
                    System.exit(0);
                }
            }
        }
    }

    public long[] solveFast(int n, long[] a) {
        SegmentTree st = new SegmentTree(a);
        long[] ans = new long[n];
        for (int i = 0; i < n; i++) {
            ans[i] = st.get(1, 0, st.pow, i, i + n)[1];
        }
        return ans;
    }

    public long[] solveSlow(int n, long[] a) {
        long[] ans = new long[n];
        long[] sum = new long[2 * n + 1];
        for (int i = 0; i < a.length; i++) {
            sum[i + 1] = sum[i] + a[i];
        }
        for (int i = 0; i < n; i++) {
            for (int l = i; l < i + n; l++) {
                for (int r = l; r <= i + n; r++) {
                    ans[i] = Math.max(ans[i], sum[r] - sum[l]);
                }
            }
        }
        return ans;
    }

    public void run() {
        try {
            if (systemIO) {
                in = new FastScanner(System.in);
                out = new PrintWriter(System.out);
            } else {
                in = new FastScanner(new File("frequent.in"));
                out = new PrintWriter(new File("frequent.out"));
            }
            solve();

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        FastScanner(InputStream f) {
            br = new BufferedReader(new InputStreamReader(f));
        }

        String nextLine() {
            try {
                return br.readLine();
            } catch (IOException e) {
                return null;
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

    }

    // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    public static void main(String[] arg) {
        new Main().run();
    }
}