/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PelinKayttoliittyma;
import PelinKayttoliittyma.Graafinen.GraafinenUI;
import PelinKayttajat.Pelaaja;
import PelinKayttoliittyma.Teksti.LaudanTulostaja;
import PelinKayttoliittyma.Teksti.TekstiUI;
import PelinOsat.Pelilauta;
import PelinOsat.Pelinappula;
import PelinOsat.Noppa;
import PelinOsat.Pelikokonaisuus;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jonas Westerlund
 */
public class Main {
    
    public static void main(String[] args) {
        Pelikokonaisuus peli = new Pelikokonaisuus();
        GraafinenUI kayttoliittyma = new GraafinenUI(peli);
        SwingUtilities.invokeLater(kayttoliittyma);
       
    }
}
