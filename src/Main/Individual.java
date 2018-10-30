package Main;

import java.util.Random;

public class Individual implements Cloneable {
    int brzina;
    int ugao;
    double ugao_rad;
    double odstupanje = 0;

    public Individual() {
        Random rn = new Random();

        // Postavimo oba gena za brzinu i ugao
        brzina = rn.nextInt(Integer.MAX_VALUE);
        ugao = (rn.nextInt(Integer.MAX_VALUE));
        // ugao u radijanima radi lakseg racunanja
        ugao_rad = ugao / (Integer.MAX_VALUE / 1.5);
        calcFitness();
    }

    // Funkcija za racunanje fitnessa
    public void calcFitness() {

        double brzina_mps = brzina / (Integer.MAX_VALUE / 22);
        double mjesto_pada = brzina_mps * brzina_mps / 9.81 * Math.sin(2 * ugao_rad);
        double prepreka = Main.getPrepreka();
        double visina_loptice_prepreka = prepreka * Math.tan(ugao_rad)
                - (9.81 / 2) * Math.pow(prepreka / (brzina_mps * Math.cos(ugao_rad)), 2); // y=x*tg(alfa) -
        // g/(2*(v0*cos(alfa)^2))*x^2
        double rupetina = Main.getRupa();
        System.out.println(mjesto_pada + " " + prepreka + " " + visina_loptice_prepreka + " " + rupetina);

        //vrsimo odredjena poredjenja u odnosu na mjesto gdje se nalazi prepreka i rupa
        if (mjesto_pada < rupetina) {
            odstupanje = rupetina - mjesto_pada;
        } else if (mjesto_pada > (rupetina + 4)) {
            odstupanje = mjesto_pada - (rupetina + 4);
        } else {
            odstupanje = 0;
        }
        if (visina_loptice_prepreka < 6.2) {
            odstupanje += (6.2 - visina_loptice_prepreka);
        } else if (visina_loptice_prepreka > 9.8) {
            odstupanje += (visina_loptice_prepreka - 9.8);
        }
    }

    //funkcija za crossover pomocu koje vrismo rekombinaciju gena
    public void crossover(Individual i2) {
        ugao = (ugao & 0xff0000ff) | (i2.ugao & 0x00ffff00);
        brzina = (brzina & 0xff0000ff) | (i2.brzina & 0x00ffff00);
        ugao_rad = ugao / (Integer.MAX_VALUE / 1.5);
        calcFitness();
    }

    //funkcija za mutaciju gena
    public void mutate() {
        Random rn = new Random();
        ugao = ugao ^ (1 << rn.nextInt(31));
        ugao_rad = ugao / (Integer.MAX_VALUE / 1.5);
        brzina = brzina ^ (1 << rn.nextInt(31));
        calcFitness();
    }

    //kloniranje jedinke
    public Individual clone() {
        Individual i_tmp = new Individual();
        i_tmp.brzina = brzina;
        i_tmp.ugao = ugao;
        i_tmp.odstupanje = odstupanje;
        i_tmp.ugao_rad = ugao_rad;
        return i_tmp;
    }
}
