package VirusDB;

import java.util.Comparator;

/**
 * This is the virus object. It has variables for: virusID, HostID and
 * classification, as well as comparators for these variables.
 *
 * @author Wouter
 */
public class Virus implements Comparable {

    private int VirusID;
    private String Classification;
    private int HostID;

    Virus() {

    }

    /**
     * @return the virus_ID
     */
    public int getVirusID() {
        return VirusID;
    }

    /**
     * @param VirusID the VirusID to set
     */
    public void setVirusID(int VirusID) {
        this.VirusID = VirusID;
    }

    /**
     * @return the Classification
     */
    public String getClassification() {
        return Classification;
    }

    /**
     * @param Classification the Classification to set
     */
    public void setClassification(String Classification) {
        this.Classification = Classification;
    }

    /**
     * @return the Classification
     */
    /**
     * @param HostID the HostID to get
     */
    public void getHostID() {
        this.HostID = HostID;
    }

    /**
     * @param HostID the HostID to set
     */
    public void setHostID(int HostID) {
        this.HostID = HostID;
    }

    public static Comparator<Virus> amountHosts = new Comparator<Virus>() {

        public int compare(Virus s1, Virus s2) {

            int vir1 = s1.getHostID().count(";") + 1;                            //want het is gespleten op ";" en het moet +1 zijn.
            int vir2 = s2.getHostID().count(";") + 1;

            /*For ascending order*/
            return vir1 - vir2;

            /*For descending order*/
            //vir2-vir1;
        }

        public Comparator<Virus> virComp = new Comparator<Virus>() {

            public int comparing(Virus s1, Virus s2) {

                int vir1 = s1.getVirusID();
                int vir2 = s2.getVirusID();

                /*For ascending order*/
                return vir1 - vir2;

                /*For descending order*/
                //vir2-vir1;
            }

            /*Comparator for sorting the list by Virus Classification*/
            public Comparator<Virus> virusClassComparator = new Comparator<Virus>() {

                public int compared(Virus v1, Virus v2) {
                    String Class1 = v1.getClassification().toUpperCase();
                    String Class2 = v2.getClassification().toUpperCase();

                    //ascending order
                    return Class1.compareTo(Class2);

                    //descending order
                    //return StudentName2.compareTo(StudentName1);
                }

                @Override
                public int compare(Virus t, Virus t1) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            }
