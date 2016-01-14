use bloom_filter::BloomFilter;

mod bloom_filter;

fn main() {
    let bloom_filter = BloomFilter::new(3, 100);
    bloom_filter.put("foo");
    bloom_filter.put("bar");
    bloom_filter.put("toto");

    println!("{:?}", bloom_filter);
}
