package bloomfilter;

import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BloomHash {

    public static int getHahWordForRange(int range, int kIndice, String word) {
        int h;
        //int val = (word == null) ? 0 : (h = word.hashCode()) ^ (h >>> 16);

        long val = (word == null) ? 0 : Hashing.murmur3_128(kIndice).newHasher().putString(word, StandardCharsets.UTF_8).hash().asLong();

        return (int) Math.abs(val % range);
    }
}
