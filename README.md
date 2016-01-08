# Kata Bloom Filter

http://codekata.com/kata/kata05-bloom-filters/
https://en.wikipedia.org/wiki/Bloom_filter

## Step 1

Given a word apply a hash function on it that map into an index of an array of size **n** 

```
Given "foo"
When hash("foo")
Then return a number between 0 and n
```

## Step 2

Given a list of words apply your previously defined hash function and store the value 1 into an array of size [0,n]

```
Given "foo", "bar" and an array[10]
When hash("foo") = 5 and hash("bar") = 2
Then put in the array [0, 0, 1, 0, 0, 1, 0, 0, 0, 0]
```

## Step 3

Apply **k** hash function for each words

https://en.wikipedia.org/wiki/Bloom_filter#/media/File:Bloom_filter.svg

## Step 4

Read the dictionnary in /usr/share/dict/words or download it from http://codekata.com/data/wordlist.txt

```
Given a list of words
When reading each word
Then fill the Bloom Filter
```

## Step 5

Implement a basic spell-checker

```
Given "foo"
When is "foo" an existing word ?
Then return true if the word exists using the bloom filter
```

## Bonus

* Compute the false positive ratio and print it.
* Play with **k** (number of hash functions) and **n** (size of the array) to optimize to have a false positive ratio < 0.01



