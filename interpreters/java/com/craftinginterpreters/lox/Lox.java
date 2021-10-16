package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class Lox {
    // Indicates that there was an erro while interpreting the source string.
    static boolean hadError = false;

    /**
     * Entry point for the Lox interpreter
     * @param args The arguments for the Lox interpreter execution
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // the Lox interpreter only support either 1 (path to the file script) 
        // or 0 (interactive mode) arguments
        if(args.length > 1) {

            System.out.println("Usage: jlox [script]");
            System.exit(64);
        }
        else if(args.length == 1) {

            // read the files content and run it.
            runFile(args[0]);

            if(hadError) {
                System.exit(65);
            }
        }
        else {

            // interactive mode
            runPrompt();
            hadError = false;
        }
    }

    /**
     * Reads a file located at the provided path and runs the interpeter against
     * its content.
     * @param path the path to the file that needs to be read.
     * @throws IOException
     */
    private static void runFile(String path) throws IOException {

        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
    }

    /**
     * Starts the interactive mode of the interpreter, where the user can enter
     * individual commands to execute.
     * @throws IOException
     */
    private static void runPrompt() throws IOException {

        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        // while no line is entered, this loop will run, capturing the input
        // lines and interpreting one at a time.
        for(;;) {
            System.out.print("> ");
            String line = reader.readLine();
            if(line == null) break;
            run(line);
        }
    }

    /**
     * Wrapper around the scanner implementation which will tokenize the source
     * string.
     * @param source the string to tokenize.
     */
    private static void run(String source) {

        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        for(Token token: tokens) {
            System.out.println(token);
        }
    }

    static void error(int line, String message) {

        report(line, "", message);
    }

    private static void report(int line, String where, String message) {

        System.err.println(
            "[line " + line + "] Error " + where + ": " + message
        );

        hadError = true;
    }
}