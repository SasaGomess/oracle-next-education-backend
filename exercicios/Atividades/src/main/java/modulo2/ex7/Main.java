package modulo2.ex7;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        DivisorOperator dv = (a, b) -> a / b;

        try {
            System.out.println(dv.division(45, 0));
        }catch (ArithmeticException e){
            System.out.println(e.getMessage());
        }
        System.out.println(dv.division(45, 5));

    }
}
