package bloomfilter;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


public class BloomApp {

    private static final Logger LOG = LoggerFactory.getLogger(BloomApp.class);

    private DictionnaryService dictionnaryService;
    private int nSize = 8;
    private int kSize = 1;
    byte[] bloomFilter;

    public static void main(String... args) throws Exception {

        Injector injector = Guice.createInjector(new JobModule());
        //Client client = new RandomProxyClientProvider(new ProxyListProvider("").get()).get();
        DictionnaryService dictionnaryService = new DictionnaryService();

        try {
            BloomApp bloomApp = new BloomApp(dictionnaryService, 200000, 1);
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
        bloomFilter = new byte[nSize];
    }

    public void runner() throws Exception {
        AtomicInteger foundCount = new AtomicInteger();

        dictionnaryService.getAll().stream()
                .forEach(word -> {
                            SetBloomKeys(computeBloomKeys(word));
                        }
                );

        IntStream.range(0,nSize)
                .peek(i -> {
                    LOG.info("bloomFilter[{}] est {}",i,bloomFilter[i]);
                }).count();


    }

    public List<Integer> computeBloomKeys(String word) {
        return IntStream.range(0,kSize)
                .map(i -> {
                    return BloomHash.getHahWordForRange(nSize, kSize,  word)
                })
                .collect(toList());
    }

    public void SetBloomKeys(List<Integer> keys) {
        keys.stream().forEach(key -> bloomFilter[key] = 1);
    }

    public boolean isPresent(String word) {
        computeBloomKeys(word).stream()
                .map(indice -> bloomFilter[indice])
                .
    }

}
