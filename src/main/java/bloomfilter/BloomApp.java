package bloomfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;


public class BloomApp {

    private static final Logger LOG = LoggerFactory.getLogger(BloomApp.class);

    private DictionnaryService dictionnaryService;
    private int nSize = 8;
    private int kSize = 1;
    boolean[] bloomFilter;

    public static void main(String... args) throws Exception {

        DictionnaryService dictionnaryService = new DictionnaryService();

        try {
            BloomApp bloomApp = new BloomApp(dictionnaryService, 500000, 5);
            bloomApp.runner();
        } catch (Throwable t) {
            LOG.error(t.getMessage(), t);

            System.exit(1);
        }

        System.exit(0);
    }

    BloomApp(DictionnaryService dictionnaryService, int nSize, int kSize) {
        this.nSize = nSize;
        this.kSize = kSize;
        this.dictionnaryService = dictionnaryService;
        bloomFilter = new boolean[nSize];
    }

    public void runner() throws Exception {
        AtomicInteger foundCount = new AtomicInteger();

        dictionnaryService.getAll().stream()
                .forEach(word -> {
                            SetBloomKeys(computeBloomKeys(word));
                        }
                );

        IntStream.range(0, nSize)
                .peek(i -> {
                    //LOG.info("bloomFilter[{}] est {}", i, bloomFilter[i]);
                }).count();
    }

    public int[] computeBloomKeys(String word) {
        return IntStream.range(0, kSize)
                .map(kIndice -> {
                    return BloomHash.getHahWordForRange(nSize, kIndice, word);
                })
                .toArray();
    }

    public void SetBloomKeys(int[] keys) {
        for (int key : keys) {
            bloomFilter[key] = true;
        }
    }

    public boolean isPresent(String word) {
        for (int key : computeBloomKeys(word)) {
            if (bloomFilter[key] == false)
            return false;
        }
        return true;
    }

}
