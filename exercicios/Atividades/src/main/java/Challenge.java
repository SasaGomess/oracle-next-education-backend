import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Challenge {
    public static ArrayList<ArrayList<Integer>> findSnakeOnGrid(ArrayList<String> grid) {

        ArrayList<ArrayList<Integer>> listaPosicoes = new ArrayList<>();
        int tamanho = grid.size();
        int tamanhoItem = grid.get(0).length();
        int coluna = -1;
        int linha = -1;

        for(String posicao : grid){
            if(posicao.contains("h")) {
                for(int i = 0; i < tamanhoItem; i++){
                    if (posicao.charAt(i) == 'h'){
                        coluna = i;
                        linha = grid.indexOf(posicao);
                        listaPosicoes.add(new ArrayList<>(List.of(coluna, linha)));
                    }
                }
            }
        }
        boolean temProximo = true;

        while (temProximo){
            temProximo = false;
            if (coluna - 1 >= 0 && grid.get(linha).charAt(coluna - 1) == '>'){
                coluna = coluna - 1;
                temProximo = true;
            } else if (coluna + 1 < tamanhoItem && grid.get(linha).charAt(coluna + 1) == '<') {
                coluna = coluna + 1;
                temProximo = true;
            } else if (linha + 1 < tamanho && grid.get(linha + 1).charAt(coluna) == '^') {
                linha = linha + 1;
                temProximo = true;
            } else if (linha - 1 >= 0 && grid.get(linha - 1).charAt(coluna) == 'v') {
                linha = linha - 1;
                temProximo = true;
            }
            if (!temProximo) break;

            listaPosicoes.add(new ArrayList<>(List.of(coluna, linha)));
        };


        return listaPosicoes;
    }

    public static void main(String[] args) {
        var grid = new ArrayList<>(List.of(
                "           ",
                " >>v  >>>h ",
                " ^ >>>^ v  ",
                " ^<<<<<<<  ",
                "           "
        ));
        System.out.println(findSnakeOnGrid(grid));
    }
}
