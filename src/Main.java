import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger countLength1  = new AtomicInteger();
    public static AtomicInteger countLength2  = new AtomicInteger();
    public static AtomicInteger countLength3 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindrome = new Thread(() ->
        {
            for (String text : texts) {
                if (isPalindrome(text) && !isSameLetter(text) && !isAscending(text)) {
                    incrementCounter(text.length());

                }
            }
        });
        palindrome.start();

        Thread sameLetter = new Thread(() -> {
            for (String text : texts) {
                if (!isPalindrome(text) && isSameLetter(text) && !isAscending(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        sameLetter.start();

        Thread ascending = new Thread(() -> {
            for (String text : texts) {
                if (!isPalindrome(text) && !isSameLetter(text) && isAscending(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        ascending.start();

        sameLetter.join();
        ascending.join();
        palindrome.join();

        System.out.println("�������� ���� � ������ 3: " + countLength1 + " ��");
        System.out.println("�������� ���� � ������ 4: " + countLength2 + " ��");
        System.out.println("�������� ���� � ������ 5: " + countLength3 + " ��");
    }

    public static boolean isPalindrome(String text) {
        return text.equals(new StringBuilder(text).reverse().toString());
    }

    public static boolean isSameLetter(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static boolean isAscending(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) > text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static void incrementCounter(int text) {
        switch (text) {
            case 3 -> countLength1.incrementAndGet();
            case 4 -> countLength2.incrementAndGet();
            case 5 -> countLength3.incrementAndGet();
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}