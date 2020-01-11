package com.leexm.demo.java8.stream;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 单词计数
 *
 * @author leexm
 * @date 2020-01-05 01:20
 */
public class WordCount {

    private static final String SENTENCE = " Nel   mezzo del cammin  di nostra  vita "
            + "mi  ritrovai in una  selva oscura" + " che la  dritta via era   smarrita ";

    public static void main(String[] args) {
        System.out.println(countWordsIteratively(SENTENCE));

        Stream<Character> stream = IntStream.range(0, SENTENCE.length()).mapToObj(SENTENCE::charAt);
        System.out.println(countWords(stream));

        Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
        Stream<Character> parallelStream = StreamSupport.stream(spliterator, true);
        System.out.println(countWords(parallelStream));
    }

    private static int countWordsIteratively(String string) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : string.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) {
                    counter++;
                }
                lastSpace = false;
            }
        }
        return counter;
    }

    private static int countWords(Stream<Character> stream) {
        WordCounter wordCounter =
                stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        return wordCounter.getCounter();
    }

    private static class WordCounter {
        private int counter;
        private boolean lastSpace;

        public WordCounter(int counter, boolean lastSpace) {
            this.counter = counter;
            this.lastSpace = lastSpace;
        }

        public WordCounter accumulate(Character c) {
            if (Character.isWhitespace(c)) {
                return lastSpace ? this : new WordCounter(counter, true);
            } else {
                // 上一个字符是空格，当前字符不是空格时，单词计数器加 1
                return lastSpace ? new WordCounter(counter + 1, false) : this;
            }
        }

        /**
         * 合并两个 wordCounter，把其计数器加起来
         * @param wordCounter
         * @return
         */
        public WordCounter combine(WordCounter wordCounter) {
            // 仅需要计数器的总和，无需关系 lastSpace
            return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
        }

        public int getCounter() {
            return counter;
        }
    }

    private static class WordCounterSpliterator implements Spliterator<Character> {

        private String string;
        private int currentChar = 0;

        public WordCounterSpliterator(String string) {
            this.string = string;
        }

        @Override
        public boolean tryAdvance(Consumer<? super Character> action) {
            // 处理当前字符
            action.accept(string.charAt(currentChar++));
            // 如果还有字符要处理，则返回 true
            return currentChar < string.length();
        }

        @Override
        public Spliterator<Character> trySplit() {
            int currentSize = string.length() - currentChar;
            // 要解析的 String 已经足够小，返回 null，表示可以顺序处理了
            if (currentSize < 10) {
                return null;
            }
            // 将拆分位置设定为要解析的 String 的中间
            for (int splitpos = currentSize / 2 + currentChar; splitpos < string.length(); splitpos++) {
                // 拆分位置前进直到下一个空格
                if (Character.isWhitespace(string.charAt(splitpos))) {
                    // 创建一个先的 WordCounterSpliteror 来解析 String 从开始到拆分位置的部分
                    Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitpos));
                    // 将这个 WordCounterSpliterator 的起始位置设置为拆分位置
                    currentChar = splitpos;
                    return spliterator;
                }
            }
            return null;
        }

        @Override
        public long estimateSize() {
            return string.length() - currentChar;
        }

        @Override
        public int characteristics() {
            return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
        }
    }

}
