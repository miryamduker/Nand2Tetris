package com.company;

import java.io.*;

public class Parser {
    private BufferedReader reader;
    private String currentLine;
    private String nextLine;

    /**
     *
     * @param source file to read from
     * @throws IOException in case the file does not exist
     */

    public Parser(File source) throws IOException {
        if (source == null) {
            throw new NullPointerException("source");
        }
        if (!source.exists()) {
            throw new FileNotFoundException(source.getAbsolutePath());
        }

        this.reader = new BufferedReader(new FileReader(source));
        this.currentLine = null;
        this.nextLine = this.getNextLine();
    }

    /**
     * check if the next line is not a comment so it will skip it and read the next one
     * @return the next line to read
     * @throws IOException
     */
    private String getNextLine() throws IOException {
        String nextLine;

        do {
            nextLine = this.reader.readLine();

            if (nextLine == null) {
                return null;
            }
        } while (nextLine.trim().isEmpty() || this.isComment(nextLine));

        int commentIndex = nextLine.indexOf("//");
        if (commentIndex != -1) {
            nextLine = nextLine.substring(0, commentIndex - 1);
        }

        return nextLine;
    }

    /**
     *
     * @param input to check
     * @return if the input line starts withe '//' which indicates that this line is a comment
     */
    private boolean isComment(String input) {
        return input.trim().startsWith("//");
    }

    @Override
    public void finalize() {
        this.close();
    }

    /**
     * close the connection to the file
     */

    public void close() {
        try {
            this.reader.close();
        } catch (IOException e) {
        }
    }

    /**
     *
     * @return if there are more commands to read
     */
    public boolean hasMoreCommands() {
        return (this.nextLine != null);
    }

    /**
     * proceeding to the next line
     * @throws IOException
     */
    public void advance() throws IOException {
        this.currentLine = this.nextLine;
        this.nextLine = this.getNextLine();
    }

    /**
     * check what kind of command a=has been given
     * @return a command type according to the symboles in the beginning of the given line
     */

    public Command commandType() {
        String trimmedLine = this.currentLine.trim();

        if (trimmedLine.startsWith("(") && trimmedLine.endsWith(")")) {
            return Command.L_COMMAND;
        } else if (trimmedLine.startsWith("@")) {
            return Command.A_COMMAND;
        } else {
            return Command.C_COMMAND;
        }
    }

    public String symbol() {
        String trimmedLine = this.currentLine.trim();

        if (this.commandType().equals(Command.L_COMMAND)) {
            return trimmedLine.substring(1, this.currentLine.length() - 1);
        } else if (this.commandType().equals(Command.A_COMMAND)) {
            return trimmedLine.substring(1);
        } else {
            return null;
        }
    }

    public String dest() {
        String trimmedLine = this.currentLine.trim();
        int destIndex = trimmedLine.indexOf("=");

        if (destIndex == -1) {
            return null;
        } else {
            return trimmedLine.substring(0, destIndex);
        }
    }

    public String comp() {
        String trimmedLine = this.currentLine.trim();
        int destIndex = trimmedLine.indexOf("=");
        if (destIndex != -1) {
            trimmedLine = trimmedLine.substring(destIndex + 1);
        }
        int compIndex = trimmedLine.indexOf(";");

        if (compIndex == -1) {
            return trimmedLine;
        } else {
            return trimmedLine.substring(0, compIndex);
        }
    }

    public String jump() {
        String trimmedLine = this.currentLine.trim();
        int compIndex = trimmedLine.indexOf(";");

        if (compIndex == -1) {
            return null;
        } else {
            return trimmedLine.substring(compIndex + 1);
        }
    }
}
