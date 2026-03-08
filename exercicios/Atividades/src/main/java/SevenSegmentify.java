import java.util.HashMap;
import java.util.Map;

public class SevenSegmentify {
    public static String sevenSegmentify(String time) {

        String[] pontos = new String[]{
                " ",
                " . ",
                " . "
        };

        Map<Character, String[]> digitos = new HashMap<>();

        digitos.put('0', new String[]{"_", "| |", "|_|"});
        digitos.put('1', new String[]{" ", " |", " |"});
        digitos.put('2', new String[]{"_", " _|", "|_"});
        digitos.put('3', new String[]{"_", " _|", "_|"});
        digitos.put('4', new String[]{" ", "|_|", "|"});
        digitos.put('5', new String[]{"_", "|_", "_|"});
        digitos.put('6', new String[]{"_", "|_", "|_|"});
        digitos.put('7', new String[]{"_", " |", " |"});
        digitos.put('8', new String[]{"_", "|_|", "|_|"});
        digitos.put('9', new String[]{"_", "|_|", "_|"});



        StringBuilder[] linha = new StringBuilder[]{
                new StringBuilder(),
                new StringBuilder(),
                new StringBuilder()
        };

        for(int i = 0; i < time.length(); i++){
            char caractere = time.charAt(i);

            if(i == 0 && caractere == '0'){
                for(int n = 0; n < 3; n++){
                    linha[n].append(" ");
                }
                continue;
            }

            if(caractere == ':'){
                for(int n = 0; n < 3; n++){
                    linha[n].append(pontos[n]);
                }
            }

            else{
                String[] numero = digitos.get(caractere);
                for(int n = 0; n < 3; n++){
                    linha[n].append(numero[n]);
                }
            }
        }
        return linha[0] + "\n" + linha[1] + "\n" + linha[2];
    }

    public static void main(String[] args) {
        System.out.println(sevenSegmentify("06:30"));
    }
}
