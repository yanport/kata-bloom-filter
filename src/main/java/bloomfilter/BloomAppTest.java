package bloomfilter;

import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Assert;
import org.junit.Test;
import sun.rmi.runtime.Log;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * Created by melvyn on 1/14/16.
 */
public class BloomAppTest {

    private static final Logger LOG = LoggerFactory.getLogger(BloomAppTest.class);

    @Test
    public void test_1() throws Exception {
        DictionnaryService dictionnaryService = new DictionnaryService();

        BloomApp bloomApp = new BloomApp(dictionnaryService, 100000, 5);

        int[] res = bloomApp.computeBloomKeys("john");
        LOG.info("keyjohn {}", res);
        res = bloomApp.computeBloomKeys("john");
        LOG.info("keyjohn {}", res);
        bloomApp.SetBloomKeys(res);
        assertThat(bloomApp.isPresent("john")).isEqualTo(true);

        LOG.info("hash {}", Hashing.murmur3_128().newHasher().putString("abc", StandardCharsets.UTF_8).hash());
        LOG.info("hash {}", Hashing.murmur3_128().newHasher().putString("agbc", StandardCharsets.UTF_8).hash());
        LOG.info("hash {}", Hashing.murmur3_128().newHasher().putString("agbc", StandardCharsets.UTF_8).hash());



        //assertThat(bloomApp.computeBloomKeys("john")).isEqualTo(false);
        bloomApp.runner();

        assertThat(bloomApp.isPresent("john")).isEqualTo(true);
        assertThat(bloomApp.isPresent("Aalesund")).isEqualTo(true);
        assertThat(bloomApp.isPresent("Aaronical")).isEqualTo(true);
        assertThat(bloomApp.isPresent("12342342342john")).isEqualTo(false);
        assertThat(bloomApp.isPresent("123jo434242hn")).isEqualTo(false);

    }
}