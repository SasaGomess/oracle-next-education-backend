package modulo2.ex3;

public class Main {
    public static void main(String[] args) {
        Uppercase uppercase = String::toUpperCase;

        System.out.println(uppercase.upperWords("chocolate"));
    }
}
