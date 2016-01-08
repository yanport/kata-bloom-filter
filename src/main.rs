use std::hash::Hasher;
use std::hash::SipHasher;

fn main() {

}

pub fn put(bf: &mut [bool], hasher: &mut SipHasher, word: &str) {
    bf[hash(hasher, word, bf.len())] = true;
}

pub fn hash(hasher: &mut SipHasher, word: &str, size: usize) -> usize {
    let s = String::from(word);

    for w in s.into_bytes() {
        hasher.write_u8(w);
    }

    return (size - 1) & hasher.finish() as usize;
}

#[cfg(test)]
mod tests {
    use super::*;
    use std::hash::SipHasher;

    #[test]
    fn should_hash_and_return_a_number_between_0_and_n() {
        // Given
        let mut hasher = SipHasher::new();

        // When
        let hash = hash(&mut hasher, "toto", 10);

        // Then
        assert!(hash < 10);
    }

    #[test]
    fn should_fill_the_bloom_filter() {
        // Given
        let mut hasher = SipHasher::new();
        let mut bloom_filter: [bool; 10] = [false; 10];
        let word1 = "foo";
        let word2 = "bar";

        // When
        put(&mut bloom_filter, &mut hasher, word1);
        put(&mut bloom_filter, &mut hasher, word2);

        // Then
        assert!(bloom_filter.contains(&true));
    }

    #[test]
    fn should_apply_k_hash_functions_for_a_word() {
        // Given
        let n = 10;
        let mut hasher1 = SipHasher::new_with_keys(0, 1);
        let mut hasher2 = SipHasher::new_with_keys(1, 2);
        let word = "foo";

        // When
        let hash2 = hash(&mut hasher2, word, n);
        let hash1 = hash(&mut hasher1, word, n);

        // Then
        assert!(hash1 != hash2);
    }

}
