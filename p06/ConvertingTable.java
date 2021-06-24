package com.company;

import java.util.Hashtable;


public class ConvertingTable {
    private static int START_OF_DADDRESS =16;
    private static int END_OF_DADDRESS = 16384;
    private static int START_OF_PADDRESS =0;
    private static int END_OF_PADDRESS = 32767;
    private Hashtable<String, Integer> symboltobinary;
    private int daddress;
    private int paddress;

    public ConvertingTable() {
        this.setConvertingTable();
        this.daddress = START_OF_DADDRESS;
        this.paddress = START_OF_PADDRESS;
    }
    /**
     * initialize and set an hashtable that holds symbol names memory addresses and their related addresses
     */


    private void setConvertingTable(){
        this.symboltobinary = new Hashtable<String, Integer>();
        this.symboltobinary.put("SP", Integer.valueOf(0));
        this.symboltobinary.put("LCL", Integer.valueOf(1));
        this.symboltobinary.put("ARG", Integer.valueOf(2));
        this.symboltobinary.put("THIS", Integer.valueOf(3));
        this.symboltobinary.put("THAT", Integer.valueOf(4));
        this.symboltobinary.put("R0", Integer.valueOf(0));
        this.symboltobinary.put("R1", Integer.valueOf(1));
        this.symboltobinary.put("R2", Integer.valueOf(2));
        this.symboltobinary.put("R3", Integer.valueOf(3));
        this.symboltobinary.put("R4", Integer.valueOf(4));
        this.symboltobinary.put("R5", Integer.valueOf(5));
        this.symboltobinary.put("R6", Integer.valueOf(6));
        this.symboltobinary.put("R7", Integer.valueOf(7));
        this.symboltobinary.put("R8", Integer.valueOf(8));
        this.symboltobinary.put("R9", Integer.valueOf(9));
        this.symboltobinary.put("R10", Integer.valueOf(10));
        this.symboltobinary.put("R11", Integer.valueOf(11));
        this.symboltobinary.put("R12", Integer.valueOf(12));
        this.symboltobinary.put("R13", Integer.valueOf(13));
        this.symboltobinary.put("R14", Integer.valueOf(14));
        this.symboltobinary.put("R15", Integer.valueOf(15));
        this.symboltobinary.put("SCREEN", Integer.valueOf(16384));
        this.symboltobinary.put("KBD", Integer.valueOf(24576));
    }

    /**
     *
     * @param symbol to add to the hashtable
     * @param address where the given symbol will relate to
     */

    public void addEntry(String symbol, int address) {
        this.symboltobinary.put(symbol, Integer.valueOf(address));
    }

    /**
     * check existance of a given symbol
     * @param symbol to check
     * @return true if it does contain the symbol
     */

    public boolean isContains(String symbol) {
        return this.symboltobinary.containsKey(symbol);
    }

    /**
     *
     * @param symbol to get is address
     * @return its address if it does exist
     */

    public int getAddressBySymbol(String symbol) {
        return this.symboltobinary.get(symbol);
    }

    public int getDataAddress() {
        return this.daddress;
    }


    public void nextDataAddress() {
        this.daddress++;
        if (this.daddress > END_OF_DADDRESS) {
            throw new RuntimeException();
        }
    }

    public int getProgramAddress() {
        return this.paddress;
    }

    public void nextProgramAddress() {
        this.paddress++;
        if (this.paddress > END_OF_PADDRESS) {
            throw new RuntimeException();
        }
    }
}
