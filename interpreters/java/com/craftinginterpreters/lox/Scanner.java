package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Scanner {

    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    // scanner tracking fields
    private int start = 0;
    private int current = 0;
    private int line = 1;

    // keywords
    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("and",    TokenType.AND);
        keywords.put("class",  TokenType.CLASS);
        keywords.put("else",   TokenType.ELSE);
        keywords.put("false",  TokenType.FALSE);
        keywords.put("for",    TokenType.FOR);
        keywords.put("fun",    TokenType.FUN);
        keywords.put("if",     TokenType.IF);
        keywords.put("nil",    TokenType.NIL);
        keywords.put("or",     TokenType.OR);
        keywords.put("print",  TokenType.PRINT);
        keywords.put("return", TokenType.RETURN);
        keywords.put("super",  TokenType.SUPER);
        keywords.put("this",   TokenType.THIS);
        keywords.put("true",   TokenType.TRUE);
        keywords.put("var",    TokenType.VAR);
        keywords.put("while",  TokenType.WHILE);
    }

    /**
     * Constructor
     * @param source the source code to scan.
     */
    Scanner(String source) {

        this.source = source;
    }

    /**
     * Begins the process of reading each character, identifying lexemes, and
     * assigning its token type and metadata to it.
     * @return the list of identified tokens.
     */
    List<Token> scanTokens() {

        while(!isAtEnd()) {
            // we are at the beggining of the next lexeme.
            start = current;
            scanToken();
        }

        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch(c) {
            // single character lexemes.
            case '(': addToken(TokenType.LEFT_PAREN); break;
            case ')': addToken(TokenType.RIGHT_PAREN); break;
            case '{': addToken(TokenType.LEFT_BRACE); break;
            case '}': addToken(TokenType.RIGHT_BRACE); break;
            case ',': addToken(TokenType.COMMA); break;
            case '.': addToken(TokenType.DOT); break;
            case '-': addToken(TokenType.MINUS); break;
            case '+': addToken(TokenType.PLUS); break;
            case ';': addToken(TokenType.SEMICOLON); break;
            case '*': addToken(TokenType.STAR); break;
            // operators with two possible interpretations.
            case '!':
                addToken(match('=') ? TokenType.BANG_EQUAL: TokenType.BANG);
                break;
            case '=':
                addToken(match('=') ? TokenType.EQUAL_EQUAL: TokenType.EQUAL);
                break;
            case '<':
                addToken(match('=') ? TokenType.LESS_EQUAL: TokenType.LESS);
                break;
            case '>':
                addToken(match('=') ? TokenType.GREATER_EQUAL: TokenType.GREATER);
                break;
            // comments or division.
            case '/':
                if(match('/')) {

                    // A comment goes until the end of the line.
                    while(peek() != '\n' && !isAtEnd()) {
                        advance();
                    }
                }
                else {
                    
                    addToken(TokenType.SLASH);
                }
                break;
            // whitespace;
            case ' ':
            case '\r':
            case '\t':
                break;
            // new line, increase the line indicator.
            case '\n':
                line++;
                break;
            // literals
            case '"': string(); break;
            default:
                // instead of handling individual number cases, we handle
                // numbers here.
                if(isDigit(c)) {

                    number();
                }
                else if(isAlpha(c)) {

                    identifier();
                }
                else {

                    Lox.error(line, "Unexpected character.");
                }
                break;
        }
    }

    /**
     * Reads characters to identify an identifier or a keyword.
     */
    private void identifier() {
        
        while(isAlphaNumeric(peek())) {

            advance();
        }

        String text = source.substring(start, current);
        TokenType type = keywords.get(text);

        if(type == null) { 

           type = TokenType.IDENTIFIER;
        }

        addToken(type);
    }

    /**
     * Reads the characters identified for a number.
     */
    private void number() {
       
        while(isDigit(peek())) {
            
            advance();
        }

        // look for the fractional part.
        // [NOTE] when are unsupported trailing dots handled?
        if(peek() == '.' && isDigit(peekNext())) {
            // consume the "."
            advance();

            while(isDigit(peek())) {

                advance();
            }
        }

        addToken(
            TokenType.NUMBER, 
            Double.parseDouble(source.substring(start, current))
        );
    }

    /**
     * Reads characters for what has been identified a string literal.
     */
    private void string() {

        while(peek() != '"' && !isAtEnd()) {

            if(peek() == '\n') {

                line++;
            }

            advance();
        }

        if(isAtEnd()) {

            Lox.error(line, "Unterminated string.");
        }
        
        // the closing "
        advance();

        // trim the surrounding quotes
        String value = source.substring(start + 1, current - 1);
        addToken(TokenType.STRING, value);
    }

    /**
     * Looks ahead and determines if the next character matches the expected
     * character. When this happens, the current source index position is
     * increased.
     * @param expected the expected character to look for.
     * @return boolean indicated if the next character is the expected character
     *  or not.
     */
    private boolean match(char expected) {
        
        if(isAtEnd()) {

            return false;
        }

        if(source.charAt(current) != expected) {
            return false;
        }

        current++;
        return true;
    }

    /**
     * Reads the next characte without advancing the current position in the
     * source
     * @return the next character in the source.
     */
    private char peek() {

        if(isAtEnd()) {

            return '\0';
        }

        return source.charAt(current);
    }

    /**
     * Reads the character following the next character.
     * @return the character read from the source.
     */
    private char peekNext() {

        if(current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    /**
     * Determines if the character is alphabetical
     * @param c the character to check.
     * @return boolean that determines if the character is alphabetical.
     */
    private boolean isAlpha(char c) {

        return (c >= 'a' && c <= 'z') ||
            (c >= 'A' && c <= 'Z') ||
            c == '_';
    }

    /**
     * Determines if the character is alphanumeric
     * @param c the character to check.
     * @return boolean that determines if the character is alphanumeric.
     */
    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    /**
     * Determines if the read character is a digit.
     * @param c the character we're checking.
     * @return boolean indicating if c is a digit.
     */
    private boolean isDigit(char c) {

        return c >= '0' && c <= '9';
    }

    /**
     * Helper that simply helps us identify if we're at the end of the source
     * or not.
     * @return boolean indicating if we're at the end of the source or not.
     */
    private boolean isAtEnd() {

        return current >= source.length();
    }

    /**
     * Reads the current character and moves the current index forward.
     * @return the character that was located at the current index.
     */
    private char advance() {

        return source.charAt(current++);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}
