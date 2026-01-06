package modulo2.ex2;

public class Main {
    public static void main(String[] args) {
        Operacao op = n -> n % 2 != 0 && n % 3 != 0 && n % 5 != 0;

        System.out.println(op.verificaPrimo(199));
        System.out.println(op.verificaPrimo(120));
    }
}
