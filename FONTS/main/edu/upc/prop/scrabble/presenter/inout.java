/* Aquesta es una adaptacio d'un conjunt de classes
   per a fer entrada/sortida de texte amb Java.
   La versio original va ser creada per Per Brinch
   Hansen (s'inclou el copyright de la versio original)
*/

/*                 THE JAVA TEXT PROGRAM
                       28 April 1999
            Copyright (c) 1999 Per Brinch Hansen

This program defines a set of Java classes for text
processing. These text classes enable Java programs to
output text to screen and disk files and input text from
keyboard and disk files. The present version runs on
Macintosh, Unix, and Windows 95 systems.
*/

package edu.upc.prop.scrabble.presenter;

import java.io.*;

public class inout
{ private final char cr = '\r',  eof = '\uFFFF',
        nl = '\n', sp = ' ';
    private final String EOF = "eof";


    public inout() { }

    /* instruccions d'escriptura */

    public void write(boolean value) throws Exception
    { write(value, 1); }

    public void write(boolean value, int width)
            throws Exception
    { String word = (String)(value?"true":"false");
        writespace(width - word.length());
        write(word);
    }

    public void write(char value) throws Exception
    { System.out.print(value); }

    public void write(char value, int width) throws Exception
    { write(value); writespace(width - 1); }

    public void write(double value) throws Exception
    { write(value, 1); }

    public void write(double value, int width)
            throws Exception
    { String numeral = String.valueOf(value);
        writespace(width - numeral.length());
        write(numeral);
    }

    public void write(int value) throws Exception
    { write(value, 1); }

    public void write(int value, int width) throws Exception
    { String numeral = String.valueOf((int)value);
        writespace(width - numeral.length());
        write(numeral);
    }

    public void write(String value) throws Exception
    { write(value, 1); }

    public void write(String value, int width)
            throws Exception
    { int length = value.length();
        for (int i = 0; i < length; i++)
            write(value.charAt(i));
        writespace(width - length);
    }

    public void writeln() throws Exception
    { write(nl); }

    public void writeln(boolean value) throws Exception
    { writeln(value, 1); }

    public void writeln(boolean value, int width)
            throws Exception
    { write(value, width); writeln(); }

    public void writeln(char value) throws Exception
    { writeln(value, 1); }

    public void writeln(char value, int width)
            throws Exception
    { write(value, width); writeln(); }

    public void writeln(double value) throws Exception
    { writeln(value, 1); }

    public void writeln(double value, int width)
            throws Exception
    { write(value, width); writeln(); }

    public void writeln(int value) throws Exception
    { writeln(value, 1); }

    public void writeln(int value, int width) throws Exception
    { write(value, width); writeln(); }

    public void writeln(String value) throws Exception
    { writeln(value, 1); }

    public void writeln(String value, int width)
            throws Exception
    { write(value, width); writeln(); }

    public void writesame(char value, int number)
            throws Exception
    { for (int i = 1; i <= number; i++) write(value); }

    public void writespace(int number) throws Exception
    { writesame(sp, number); }


    /* instruccions de lectura */

    private BufferedReader in = new BufferedReader(
            new InputStreamReader(System.in));
    private char[] buffer = new char[80];
    private int typed= 0, used= 0;

    private char readkey() throws Exception
    { if (used == typed)
    { String line = readkeyline();
        if (line.equals(EOF))
        { typed = 0; buffer[typed] = eof; }
        else
        { typed = line.length();
            for (int i = 0; i < typed; i++)
                buffer[i] = line.charAt(i);
            buffer[typed] = nl;
        }
        typed = typed + 1; used = 0;
    }
        char ch = buffer[used];
        if (ch == cr) ch = nl;
        used = used + 1; return ch;
    }

    private String readkeyline() throws Exception
    { String line = in.readLine();
        return (line == null?"":line);
    }

    private boolean ahead = false;
    private char ch /*next character (if ahead)*/;


    public boolean blank() throws Exception
    { getahead();
        return (ch == cr) | (ch == nl) | (ch == sp);
    }

    public boolean digit() throws Exception
    { getahead();
        return ('0' <= ch) & (ch <= '9');
    }

    private void getahead() throws Exception
    { if (!ahead) readnext(); }

    public boolean letter() throws Exception
    { getahead();
        return (('a' <= ch) & (ch <= 'z')) |
                (('A' <= ch) & (ch <= 'Z'));
    }

    public boolean more() throws Exception
    { getahead(); return ch != eof; }

    public char next() throws Exception
    { getahead(); return ch; }

    public char read() throws Exception
    { getahead(); ahead = false; return ch; }

    public void readblanks() throws Exception
    { while (blank()) readnext(); }

    public boolean readboolean() throws Exception
    { boolean value; String symbol = readname();
        if (symbol.equals("false")) value = false;
        else if (symbol.equals("true")) value = true;
        else
        { String error = "boolean format: ";
            throw new Exception(error + symbol);
        }
        return value;
    }

    public double readdouble() throws Exception
    { StringBuffer symbol = new StringBuffer();
        //----------MODIFICACIO------------
        readblanks();
        if (ch == '+') readnext();
        else if (ch == '-')
            symbol.append(read());
        while (digit()) symbol.append(read());
        //---------------------------------
        // hem substiuit el readint per aquest boci de codi.
        if (ch == '.')
        { symbol.append(read());
            while(digit()) symbol.append(read());
        }
        if ((ch == 'e') | (ch == 'E'))
        { symbol.append(read());
            symbol.append(readint());
        }
        Double numeral =
                new Double(symbol.toString());
        return numeral.doubleValue();
    }

    public int readint() throws Exception
    { StringBuffer symbol = new StringBuffer();
        readblanks();
        if (ch == '+') readnext();
        else if (ch == '-')
            symbol.append(read());
        while (digit()) symbol.append(read());
        Integer numeral =
                new Integer(symbol.toString());
        return numeral.intValue();
    }

    public String readline() throws Exception
    { StringBuffer line = new StringBuffer();
        getahead();
        while (ch != nl)
        { line.append(ch); readnext(); };
        ahead = false;
        return line.toString();
    }

    public void readln() throws Exception
    { String skipped = readline(); }

    public String readname() throws Exception
    { StringBuffer letters = new StringBuffer();
        readblanks();
        while (letter() | digit() | (ch == '_'))
        { letters.append(ch); readnext(); }
        return letters.toString();
    }

    public void readnext() throws Exception
    { ch = (char)readkey(); ahead = true; }

    public String readword() throws Exception
    { StringBuffer word = new StringBuffer();
        readblanks();
        while (!blank())
        { word.append(ch); readnext(); }
        return word.toString();
    }


    // Instruccions per llegir vector i matrius d'enters i reals

    public int[] read_int_array () throws Exception {
        int v[] = new int[100];
        int f[];
        int i=0;
        readblanks();
        while (next() != ';') {
            v[i] = readint();
            readblanks();
            i = i+1;
        }
        read();
        f = new int[i];
        for (i=0; i < f.length; i++) f[i] = v[i];
        return f;
    }

    public void write (int[] v) throws Exception {
        for (int i=0; i < v.length; i++) write (" " + v[i]);
    }

    public void writeln (int[] v) throws Exception {
        write (v); writeln();
    }

    public double[] read_double_array () throws Exception {
        double v[] = new double[100];
        double f[];
        int i=0;
        readblanks();
        while (next() != ';') {
            v[i] = readdouble();
            readblanks();
            i = i+1;
        }
        read();
        f = new double[i];
        for (i=0; i < f.length; i++) f[i] = v[i];
        return f;
    }

    public void write (double[] v) throws Exception {
        for (int i=0; i < v.length; i++) write (" " + v[i]);
    }

    public void writeln (double[] v) throws Exception {
        write (v); writeln();
    }

    public int[][] read_int_matrix () throws Exception {
        int v[][] = new int[100][100];
        int f[][];
        int i=0;
        readblanks();
        while (next() != ';') {
            v[i] = read_int_array();
            readblanks();
            i = i+1;
        }
        read();
        f = new int[i][];
        for (i=0; i < f.length; i++) {
            f[i] = new int[v[i].length];
            for (int j=0; j < v[i].length; j++) f[i][j] = v[i][j];
        }
        return f;
    }

    public void write (int[][] m) throws Exception {
        for (int i=0; i < m.length; i++) {
            write (m[i]);
            write (";");
        }
    }

    public void writeln (int[][] m) throws Exception {
        for (int i=0; i < m.length; i++) {
            write (m[i]);
            writeln (";");
        }
    }

    public double[][] read_double_matrix () throws Exception {
        double v[][] = new double[100][100];
        double f[][];
        int i=0;
        readblanks();
        while (next() != ';') {
            v[i] = read_double_array();
            readblanks();
            i = i+1;
        }
        read();
        f = new double[i][];
        for (i=0; i < f.length; i++) {
            f[i] = new double[v[i].length];
            for (int j=0; j < v[i].length; j++) f[i][j] = v[i][j];
        }
        return f;
    }

    public void write (double[][] m) throws Exception {
        for (int i=0; i < m.length; i++) {
            write (m[i]);
            write (";");
        }
    }

    public void writeln (double[][] m) throws Exception {
        for (int i=0; i < m.length; i++) {
            write (m[i]);
            writeln (";");
        }
    }
}

/********* THE LAST LINE OF THE JAVA TEXT PROGRAM *********/
