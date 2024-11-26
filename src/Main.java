import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

class Nicknames {

    public static final AtomicInteger countLength3 = new AtomicInteger(0);
    public static final AtomicInteger countLength4 = new AtomicInteger(0);
    public static final AtomicInteger countLength5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];

        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> checkBeautifulWords(texts));
        Thread thread2 = new Thread(() -> checkBeautifulWords(texts));
        Thread thread3 = new Thread(() -> checkBeautifulWords(texts));

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("Красивых слов с длиной 3: " + countLength3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + countLength4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + countLength5.get() + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void checkBeautifulWords(String[] texts) {
        for (String text : texts) {
            if (isBeautiful(text)) {
                switch (text.length()) {
                    case 3:
                        countLength3.incrementAndGet();
                        break;
                    case 4:
                        countLength4.incrementAndGet();
                        break;
                    case 5:
                        countLength5.incrementAndGet();
                        break;
                }
            }
        }
    }

    public static boolean isBeautiful(String text) {
        return isPalindrome(text) || isSameLetter(text) || isSorted(text);
    }

    public static boolean isPalindrome(String text) {
        int len = text.length();
        for (int i = 0; i < len / 2; i++) {
            if (text.charAt(i) != text.charAt(len - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSameLetter(String text) {
        char firstChar = text.charAt(0);
        for (char c : text.toCharArray()) {
            if (c != firstChar) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSorted(String text) {
        char[] chars = text.toCharArray();
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] < chars[i - 1]) {
                return false;
            }
        }
        return true;
    }
}
