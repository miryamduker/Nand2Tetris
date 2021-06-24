package com.company;

import java.util.Hashtable;

public class ConvertingToBinaryCode {
    private Hashtable<String, String> dest;
    private Hashtable<String, String> comp;
    private Hashtable<String, String> jump;

    /**
     *constructor, initialize three hashtables for three types of commands
     */

    public ConvertingToBinaryCode(){
        this.jump = new Hashtable<String, String>();
        this.setJump();
        this.comp = new Hashtable<String, String>();
        this.setComp();
        this.dest = new Hashtable<String, String>();
        this.setDest();
    }

    /**
     * setting the hashtable with property values
     */

    private void setJump(){
        this.jump.put("NULL", "000");
        this.jump.put("JGT", "001");
        this.jump.put("JEQ", "010");
        this.jump.put("JGE", "011");
        this.jump.put("JLT", "100");
        this.jump.put("JNE", "101");
        this.jump.put("JLE", "110");
        this.jump.put("JMP", "111");
    }

    private void setComp(){
        this.comp.put("0", "0101010");
        this.comp.put("1", "0111111");
        this.comp.put("-1", "0111010");
        this.comp.put("D", "0001100");
        this.comp.put("A", "0110000");
        this.comp.put("M", "1110000");
        this.comp.put("!D", "0001101");
        this.comp.put("!A", "0110001");
        this.comp.put("!M", "1110001");
        this.comp.put("-D", "0001111");
        this.comp.put("-A", "0110011");
        this.comp.put("-M", "1110011");
        this.comp.put("D+1", "0011111");
        this.comp.put("A+1", "0110111");
        this.comp.put("M+1", "1110111");
        this.comp.put("D-1", "0001110");
        this.comp.put("A-1", "0110010");
        this.comp.put("M-1", "1110010");
        this.comp.put("D-M", "1010011");
        this.comp.put("A-D", "0000111");
        this.comp.put("M-D", "1000111");
        this.comp.put("D&A", "0000000");
        this.comp.put("D&M", "1000000");
        this.comp.put("D|A", "0010101");
        this.comp.put("D+A", "0000010");
        this.comp.put("D+M", "1000010");
        this.comp.put("D-A", "0010011");
        this.comp.put("D|M", "1010101");
    }

    private void setDest(){
        this.dest.put("NULL", "000");
        this.dest.put("M", "001");
        this.dest.put("D", "010");
        this.dest.put("MD", "011");
        this.dest.put("A", "100");
        this.dest.put("AM", "101");
        this.dest.put("AD", "110");
        this.dest.put("AMD", "111");
    }

    /**
     *
     * @param str
     * @return the matching value of the string that has been accepted
     */
    public String getDest(String str) {
        if (str == null || str.isEmpty()) {
            str = "NULL";
        }
        return this.dest.get(str);
    }

    public String getComp(String str) {
        return this.comp.get(str);
    }

    public String getJump(String str) {
        if (str == null || str.isEmpty()) {
            str = "NULL";
        }
        return this.jump.get(str);
    }

    /**
     *
     * @param number that will be formatted to binary format
     * @return
     */
    public String getBinary(String number) {
        int value = Integer.parseInt(number);
        String binaryNumber = Integer.toBinaryString(value);
        String formattedBinaryNumber = String.format("%15s", binaryNumber).replace(' ', '0');
        return formattedBinaryNumber;
    }
}
