package bloomfilter;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class BloomHash {

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    static final int getHahWordForRange(int range, String word) {
        int h;
        int val = (word == null) ? 0 : (h = word.hashCode()) ^ (h >>> 16);

        return Math.abs(val % range);
    }

    static final int getHahWordForRange(int range, int kIndice, String word) {
        int h;
        //int val = (word == null) ? 0 : (h = word.hashCode()) ^ (h >>> 16);

        int val = (word == null) ? 0 : Hashing.murmur3_128(kIndice).newHasher().putString((String) word, Charsets.UTF_8).hashCode();

        return Math.abs(val % range);
    }
}
