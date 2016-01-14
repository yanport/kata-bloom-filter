use std::cell::RefCell;
use std::hash::Hasher;
use std::hash::SipHasher;
use std::fmt;

pub struct BloomFilter {
    bits: RefCell<Vec<bool>>,
    hashers: RefCell<Vec<RefCell<SipHasher>>>
}

impl BloomFilter {

    pub fn new(nb_hashers: usize, size: usize) -> BloomFilter {
        BloomFilter {bits: RefCell::new(vec![false; size]), hashers: RefCell::new(BloomFilter::generate_hashers(nb_hashers))}
    }

    pub fn generate_hashers(n: usize) -> Vec<RefCell<SipHasher>> {
        let mut hashers = Vec::with_capacity(n);

        for i in 0..n {
            hashers.push(RefCell::new(SipHasher::new_with_keys(i as u64, (i + 1) as u64)));
        }

        return hashers;
    }

    pub fn put(&self, word: &str) {
        let mut bits = self.bits.borrow_mut();
        let hashers = self.hashers.borrow();
        let size = bits.len();

        for hasher in hashers.iter() {
            bits.insert(self.hash(hasher, size, word), true);
        }
    }

    pub fn hash(&self, hasher: &RefCell<SipHasher>, size: usize, word: &str) -> usize {
        let s = String::from(word);
        let mut h = hasher.borrow_mut();

        for w in s.into_bytes() {
            h.write_u8(w);
        }

        return (size - 1) & h.finish() as usize;
    }
}

impl fmt::Debug for BloomFilter {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        write!(f, "{:?}", self.bits)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use std::hash::SipHasher;
    use std::cell::RefCell;

    #[test]
    fn should_hash_and_return_a_number_between_0_and_n() {
        // Given
        let bf = BloomFilter::new(1, 10);
        let hasher = RefCell::new(SipHasher::new());

        // When
        let hash = bf.hash(&hasher, 10, "toto");

        // Then
        assert!(hash < 10);
    }

    #[test]
    fn should_fill_the_bloom_filter() {
        // Given
        let bf = BloomFilter::new(1, 10);
        let word1 = "foo";
        let word2 = "bar";

        // When
        bf.put(word1);
        bf.put(word2);

        // Then
        assert!(bf.bits.borrow().contains(&true));
    }

    #[test]
    fn should_apply_k_hash_functions_for_a_word() {
        // Given
        let bf = BloomFilter::new(1, 10);
        let n = 10;
        let mut hashers = BloomFilter::generate_hashers(n);
        let word = "foo";

        // When
        let hash2 = bf.hash(&mut hashers[0], 10, word);
        let hash1 = bf.hash(&mut hashers[1], 10, word);

        // Then
        assert!(hash1 != hash2);
    }

}
