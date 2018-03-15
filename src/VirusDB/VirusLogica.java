/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VirusDB;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JOptionPane;

/**
 *
 * @author Wouter Sanderse
 * @Creatie datum: 14-03-2018
 * @Functie: Analyseert virusen die geimporteerd worden uit een bestand en laat
 * overeenkomende zien tussen twee selecties.
 * @Known bugs: Cannot find symbol "whichever GUI component". It can't call
 * those parts of the GUI. The GUI is static, as it is generated. It cannot be
 * called upon from a non-static context. No time to solve this and I can't find
 * the solution.
 *
 */
public class VirusLogica {

    public ArrayList<Virus> viruses = new ArrayList<Virus>();
    public ArrayList<String> classifications = new ArrayList<>();

    /**
     * Fills the first text area.
     *
     * @param value1
     */
    public void fillArea1(String value1) {
        try{
            for (Object Virus : viruses) {
            if (Virus.getVirusID == value1) {
                VirusGUI.jTextArea1.append(viruses).toString();
            }
        }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.toString(), "Error, filling the textareas went wrong!",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Fills the second text area.
     *
     * @param value2
     */
    public void fillArea2(String value2) {
        try{
            for (Object Virus : viruses) {
                if (Virus.getVirusID == value2) {
                    VirusGUI.jTextArea2.append(viruses).toString();
                }
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.toString(), "Error, filling the textareas went wrong!",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Wordt aangeroepen vanuit te open knop in de GUI.
     *
     * @param path1 path 1 is geimporteerd uit de bestandsbrowser, of zou dat
     * moeten zijn. Het leest lijnen in de file en zet ze weg in viruses als
     * objecten virus in de array viruses en voegt de classificaties toe aan een
     * array en de combobox in de GUI.
     *
     */
    public void open(String path1) {
        try {
//            String line = VirusGUI.jTextField1.getText();
            String line = "";
            BufferedReader buf = new BufferedReader(new FileReader(path1));
            String[] wordsArray = null;
            int c = 0;
            while ((line = buf.readLine()) != null) {
                try {
                    if (c >= 1) {
                        while (true) {
                            String lineJustFetched = buf.readLine();
                            if (lineJustFetched == null) {
                                break;
                            } else {
                                Virus virus = new Virus();
                                virus.setVirusID(wordsArray[0]);
                                virus.setClassification(wordsArray[2]);
                                virus.setHostID(Integer.parseInt(wordsArray[7]));
                                wordsArray = lineJustFetched.split("/t");
                                if (!viruses.contains(virus)) {
                                    viruses.add(virus);
                                }
                                if (!classifications.contains(wordsArray[2])) {     //Dit had ik kunnen splitten op ";" en dan de locatie [1] toevoegen aan classifications.
                                    classifications.add(wordsArray[2]);
                                    VirusGUI.VClassComboBox.add(wordsArray[2]);              //Het heeft private access, maar het is niet te veranderen want het is met de GUIbuilder gemaakt.
                                }
                            }
                        }
                        c++;
                    }
                } catch (IndexOutOfBoundsException iob) {
                    System.out.println("Index out of bounds, had een custom moeten/kunnen zijn");
                }

            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Er is geen geldige bestandsnaam opgegeven");
        } catch (IOException ioe) {
            System.out.println("Kan niet lezen in bestand");
        }
    }

    /**
     * Vult de comboboxen
     *
     * @param viruses viruses in Virus worden geanalyseert (geteld, hoeveelheid
     * verschillende classifications, aantal dubbele classifications.
     */
    public void classify(ArrayList<Virus> viruses) {
        for (Object Virus : viruses) {
            VirusGUI.jComboBox1.add(Virus.getVirusID);                          //Dit zou de combobox moeten vullen met de virus ids, maar hij "kent het niet".
            VirusGUI.jComboBox2.add(Virus.getVirusID);
        }
    }

    /**
     *
     * @param viruses
     * @throws IOException sorteert de virussen in de tekstvelden
     */
    public void sortID() throws IOException {
        try{Collections.sort(viruses, Virus.virComp);
            for (Object Virus : viruses) {
                VirusGUI.jTextArea1.append(Virus.getVirusID);
                VirusGUI.jTextArea2.append(Virus.getVirusID);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.toString(), "Error, sorting went wrong",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     *
     * @param viruses
     * @throws IOException sorteert de classificaties in de tekstvelden
     */
    public void sortClass() throws IOException {
        Collections.sort(viruses, Virus.virusClassComparator);
        try{
            for (Object Virus : viruses) {
            VirusGUI.jTextArea1.append(Virus.getVirusID);                       //Alsnog de IDs, want er is niet gezegt dat we de classes willen weergeven.
            VirusGUI.jTextArea2.append(Virus.getVirusID);
        }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.toString(), "Error, sorting went wrong",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *
     * @param viruses
     * @throws IOException sorteert de Nr hosts in de tekstvelden
     */
    public void sortHostNr() throws IOException {
        try{Collections.sort(viruses, Virus.amountHosts);
            for (Object Virus : viruses) {
                VirusGUI.jTextArea1.append(Virus.getVirusID);                      //Alsnog de IDs, want er is niet gezegt dat we de classes willen weergeven.
                VirusGUI.jTextArea2.append(Virus.getVirusID);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.toString(), "Error, sorting went wrong",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *
     * @param visuses
     * @throws IOException Exporteert alleen de Virus in viruses met de hoogste
     * base_experience.
     */
    public void exportExp(ArrayList<Virus> Pokemons) throws IOException {
        int maxXp = 0;
        Virus P = new Virus();
        FileWriter fw = new FileWriter("PokemonExpOutput.txt");
        for (int i = 0; dubbels.size() > i; i++) {
            if (dubbels.get(i).getBase_experience() > maxXp) {
                P = dubbels.get(i);
                maxXp = dubbels.get(i).getBase_experience();
            }
        }
        String line = P.getVirusID();
        fw.write(line + "\n");
        fw.close();
    }
}
