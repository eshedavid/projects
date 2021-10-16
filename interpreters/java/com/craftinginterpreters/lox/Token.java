package com.craftinginterpreters.lox;

/**
 * The tokens' class that associates each identified lexeme from the scanner
 * with its type and other metadata.
 */
class Token {

    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    /**
     * Constructor
     * @param type the type to which the lexeme is associated.
     * @param lexeme the identified lexeme.
     * @param literal
     * @param line the line where the lexeme was identified.
     */
    Token(TokenType type, String lexeme, Object literal, int line) {

        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;

    }

    /**
     * A string representation of the token.
     */
    public String toString() {

        return type + " " + lexeme + " " + literal;

    }
}
