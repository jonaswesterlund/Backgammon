/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PelinKayttoliittyma.Graafinen; 

import PelinOsat.Pelikokonaisuus;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;

/**
 * Luokka joka sisältää pelipaneelin jossa itse peli tapahtuu.
 * @author Jonas Westerlund
 */
public class PeliPaneeli extends JPanel { 
    
    Pelikokonaisuus peli;
    GraafinenUI graafinen;
    JLabel noppa1;
    JLabel noppa2;
    JTextArea ilmoitusKentta;
    JTextArea pelaajienKodit;
    JScrollPane ilmoitusKenttaScroll;
    JButton heitaNoppaa;
    JButton luovutaVuoro;
    PelilaudanPaneeli pelilauta;

    /**
     * Luo paneelin joka koostuu piirrettävästä paneelista sekä paneelin alaosasta jossa on toimintoja.
     * @param peli Pelikokonaisuus jota paneeli ohjaa.
     * @param graafinen Käyttöliittymä jossa paneeli on.
     */
    public PeliPaneeli(Pelikokonaisuus peli, GraafinenUI graafinen){
        this.peli = peli;
        this.graafinen = graafinen;
        
        pelilauta = new PelilaudanPaneeli(peli,graafinen,this);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setSize(new Dimension(690, 635));
        
        JPanel alaOsio = new JPanel();
        alaOsio.setLayout(new BoxLayout(alaOsio,BoxLayout.X_AXIS));
        
        JPanel ylaOsio = new JPanel();
        ylaOsio.setLayout(new BoxLayout(ylaOsio,BoxLayout.X_AXIS));
        
        pelaajienKodit = new JTextArea(
                "Pelaajan " + peli.haePelaaja1().haePelaajanNimi() + " koti: \n" + peli.haePelaajan1Koti().size()
                + "/15 nappulaa \n\nPelaajan " + peli.haePelaaja2().haePelaajanNimi() + " koti: \n" + peli.haePelaajan2Koti().size() + "/15 nappulaa" );
        pelaajienKodit.setMaximumSize(new Dimension(170,150));
        pelaajienKodit.setEditable(false);
        
        ylaOsio.add(pelilauta);
        ylaOsio.add(pelaajienKodit);
        
        luovutaVuoro = new JButton("Luovuta vuoro!");
        luovutaVuoro.setPreferredSize(new Dimension(120,30));
        VuoronLuovutusKuuntelija kuuntelija1 = new VuoronLuovutusKuuntelija();
        luovutaVuoro.addActionListener(kuuntelija1);
        luovutaVuoro.setAlignmentX(SwingConstants.LEFT);
        
        ilmoitusKentta = new JTextArea("Pelaaja " + peli.haePelaaja1().haePelaajanNimi() + " aloittaa!");
        ilmoitusKentta.setEditable(true);
        ilmoitusKenttaScroll = new JScrollPane(ilmoitusKentta);
        ilmoitusKenttaScroll.setPreferredSize(new Dimension(150,75));
        
        noppa1 = new JLabel("", SwingConstants.CENTER);
        noppa1.setPreferredSize(new Dimension(50,30));
        noppa2 = new JLabel("", SwingConstants.CENTER);
        noppa2.setPreferredSize(new Dimension(50,30));
        
        heitaNoppaa = new JButton("Heitä noppia!");
        heitaNoppaa.setPreferredSize(new Dimension(120,30));       
        NopanHeittoKuuntelija kuuntelija2 = new NopanHeittoKuuntelija();
        heitaNoppaa.addActionListener(kuuntelija2);
        
        alaOsio.setMaximumSize(new Dimension(690,75));
        
        alaOsio.add(Box.createRigidArea(new Dimension(40,0)));
        alaOsio.add(luovutaVuoro);
        alaOsio.add(Box.createRigidArea(new Dimension(40,0)));
        alaOsio.add(ilmoitusKenttaScroll);
        alaOsio.add(noppa1);
        alaOsio.add(heitaNoppaa);
        alaOsio.add(noppa2);
        
        
        add(ylaOsio);
        add(alaOsio);
        
    }
    
    public JTextArea haeIlmoitusKentta(){
        return this.ilmoitusKentta;
    }
    
    public JTextArea haePelaajienKodit(){
        return this.pelaajienKodit;
    }
    
    public JButton haeHeitaNoppaaNappi(){
        return this.heitaNoppaa;
    }
    
    public PelilaudanPaneeli haePelilaudanPiirtoPaneeli(){
        return this.pelilauta;
    }
    
    /**
     * Kuuntelee Heitä noppaa-napin painalluksia ja päivittää noppien arvot.
     */
    public class NopanHeittoKuuntelija implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            noppa1.setText(""+peli.heitaNoppaa1());
            noppa2.setText(""+peli.heitaNoppaa2());
            if(peli.haeNopan1Arvo() == peli.haeNopan2Arvo()){
                peli.asetaHeittojenMaara(4);
            } else {
                peli.asetaHeittojenMaara(2);
            }
            
            peli.alustaVuoroLaskuri();
            heitaNoppaa.setEnabled(false);
            peli.asetaNoppaaHeitetty(true);
            
        }
        
        
    }
    
    public class VuoronLuovutusKuuntelija implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            peli.asetaPelaajaVuorossa(peli.haePelaajaVuorossa().haeVastustaja());
            ilmoitusKentta.append("\nPelaajan " + peli.haePelaajaVuorossa().haePelaajanNimi() + " vuoro!");
            ilmoitusKentta.setCaretPosition(ilmoitusKentta.getDocument().getLength());
            heitaNoppaa.setEnabled(true);
            peli.asetaNoppaaHeitetty(false);
            if (peli.haePelaajaVuorossa() == peli.haePelaaja1()) {
                        setBackground(Color.BLACK);
                    } else {
                        setBackground(Color.WHITE);
                    }
            
        }
        
        
    }
} 