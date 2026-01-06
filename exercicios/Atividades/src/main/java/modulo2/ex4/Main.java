package modulo2.ex4;

public class Main {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        PalindromeChecker ch = str -> str.equalsIgnoreCase(String.valueOf(sb.append(str).reverse()));

        System.out.println(ch.checker("ovo"));
        System.out.println(ch.checker("Mariana"));
    }
}
