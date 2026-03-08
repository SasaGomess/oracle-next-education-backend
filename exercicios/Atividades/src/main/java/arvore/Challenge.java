package arvore;

import java.util.ArrayList;
import java.util.List;

public class Challenge {
    static class Arvore{
        char valor;
        Arvore direita;
        Arvore esquerda;

        public Arvore(char valor){
            this.valor = valor;
            direita = null;
            esquerda = null;
        }
    }

    public static List<String> possibilities(String signals) {
        Arvore arvore = constroiArvore();

        List<String> valores = new ArrayList<String>();

        verificaPossibilidade(arvore, signals, 0, valores);


        return valores;
    }

    private static void verificaPossibilidade(Arvore arvore, String sinais, int indice, List<String> valores){

        if(arvore == null) return;

        if(indice == sinais.length()){
            valores.add(String.valueOf(arvore.valor));
            return;
        }

        char sinal = sinais.charAt(indice);

        if ( sinal == '.'){
            verificaPossibilidade(arvore.esquerda, sinais, indice + 1, valores);
        } else if (sinal == '-') {
            verificaPossibilidade(arvore.direita, sinais, indice + 1, valores);
        }else {
            verificaPossibilidade(arvore.esquerda, sinais, indice + 1, valores);
            verificaPossibilidade(arvore.direita, sinais, indice + 1, valores);
        }

    }

    private static Arvore constroiArvore(){
        Arvore raiz = new Arvore(' ');

        raiz.direita = new Arvore('T');
        raiz.esquerda = new Arvore('E');

        raiz.direita.direita = new Arvore('M');
        raiz.direita.esquerda = new Arvore('N');

        raiz.esquerda.direita = new Arvore('A');
        raiz.esquerda.esquerda = new Arvore('I');

        raiz.direita.direita.direita = new Arvore('O');
        raiz.direita.direita.esquerda = new Arvore('G');

        raiz.direita.esquerda.direita = new Arvore('K');
        raiz.direita.esquerda.esquerda = new Arvore('D');

        raiz.esquerda.direita.direita = new Arvore('W');
        raiz.esquerda.direita.esquerda = new Arvore('R');

        raiz.esquerda.esquerda.esquerda = new Arvore('S');
        raiz.esquerda.esquerda.direita = new Arvore('U');

        return raiz;
    }

    public static void main(String[] args) {
        System.out.println(possibilities("?."));
    }
}
