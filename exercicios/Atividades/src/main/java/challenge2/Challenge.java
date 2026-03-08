package challenge2;

public class Challenge {
    public static String escolheTaxi(String tf1,String vqr1,String tf2,String vqr2) {

        Double v1 = Double.valueOf(vqr1);
        Double t1 = Double.valueOf(tf1);
        Double v2 = Double.valueOf(vqr2);
        Double t2 = Double.valueOf(tf2);


        double subTaxa = t2 - t1;
        double subValor = v1 - v2;
        double valorDistanciaMedia = subTaxa / subValor;

        if(v1 == v2 && t1 == t2 || v1 == v2 || t1 == t2){
            return "Tanto faz";
        }

        if(t1 < t2 && v1 < v2){
            return "Empresa 1";
        }
        if(t2 < t1 && v2 < v1){
            return "Empresa 2";
        }

        System.out.println(valorDistanciaMedia);

        String vDistanciaMediaFmt = String.format("%.1f", valorDistanciaMedia).replaceAll(",", ".");

        if (v1 > v2){
            return "Empresa 1 quando a distância < " + vDistanciaMediaFmt + ", Tanto faz quando a distância = " + vDistanciaMediaFmt + ", Empresa 2 quando a distância > " + vDistanciaMediaFmt;
        }else {
            return "Empresa 2 quando a distância < " + vDistanciaMediaFmt + ", Tanto faz quando a distância = " + vDistanciaMediaFmt + ", Empresa 1 quando a distância > " + vDistanciaMediaFmt;
        }

    }

    public static void main(String[] args) {
        System.out.println(escolheTaxi("2.5","1.0","5.0","0.75"));
    }
}
