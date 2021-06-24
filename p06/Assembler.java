package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;


public class Assembler {
    private File assemblyCode;
    private BufferedWriter machineCode;
    private ConvertingToBinaryCode encoder;
    private ConvertingTable convertingTable;

    /**
     * @param source file where to read the code from
     * @param target file where to put the translated code
     * @throws IOException in case one or both of the files were not found
     */
    public Assembler(File source, File target) throws IOException {
        this.assemblyCode = source;
        FileWriter fw = new FileWriter(target);
        this.machineCode = new BufferedWriter(fw);
        this.encoder = new ConvertingToBinaryCode();
        this.convertingTable = new ConvertingTable();
    }

    public void translate() throws IOException {
        this.recordLabelAddress();
        this.parse();
    }

    private void recordLabelAddress() throws IOException {
        Parser parser = new Parser(this.assemblyCode);
        while (parser.hasMoreCommands()) {
            parser.advance();

            Command commandType = parser.commandType();

            if (commandType.equals(Command.L_COMMAND)) {
                String symbol = parser.symbol();
                int address = this.convertingTable.getProgramAddress();
                this.convertingTable.addEntry(symbol, address);
            } else {
                this.convertingTable.nextProgramAddress();
            }
        }
        parser.close();
    }

    // Parse source file.
    private void parse() throws IOException {
        Parser parser = new Parser(this.assemblyCode);
        while (parser.hasMoreCommands()) {
            parser.advance();

            Command commandType = parser.commandType();
            String instruction = null;

            if (commandType.equals(Command.A_COMMAND)) {
                // Format A-Instruction.
                String symbol = parser.symbol();
                Character firstCharacter = symbol.charAt(0);
                boolean isSymbol = (!Character.isDigit(firstCharacter));

                String address = null;
                if (isSymbol) {
                    boolean symbolExists = this.convertingTable.isContains(symbol);

                    // Record address if symbol does not exist (variable).
                    if (!symbolExists) {
                        int dataAddress = this.convertingTable.getDataAddress();
                        this.convertingTable.addEntry(symbol, dataAddress);
                        this.convertingTable.nextDataAddress();
                    }

                    // Get address  of symbol.
                    address = Integer.toString(
                            this.convertingTable.getAddressBySymbol(symbol));
                } else {
                    address = symbol;
                }

                instruction = this.formatAInstruction(address);
            } else if (commandType.equals(Command.C_COMMAND)) {
                // Format C-Instruction
                String comp = parser.comp();
                String dest = parser.dest();
                String jump = parser.jump();
                instruction = this.formatCInstruction(comp, dest, jump);
            }

            if (!commandType.equals(Command.L_COMMAND)) {
                // Write binary instruction to file.
                this.machineCode.write(instruction);
                this.machineCode.newLine();
            }
        }

        // Release resources.
        parser.close();
        this.machineCode.flush();
        this.machineCode.close();
    }

    // Machine-format an A-Instruction.
    private String formatAInstruction(String address) {
        String formattedNumber = this.encoder.getBinary(address);
        return "0" + formattedNumber;
    }

    // Machine-format a C-Instruction.
    private String formatCInstruction( String comp, String dest, String jump) {
        StringWriter instruction = new StringWriter();
        instruction.append("111");
        instruction.append(this.encoder.getComp(comp));
        instruction.append(this.encoder.getDest(dest));
        instruction.append(this.encoder.getJump(jump));
        return instruction.toString();
    }
}
