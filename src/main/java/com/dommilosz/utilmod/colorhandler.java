package com.dommilosz.utilmod;

public class colorhandler {

    public static class Color {
        public static String BLACK = fcode('0');
        public static String DARKBLUE = fcode('1');
        public static String DARK_GREEN = fcode('2');
        public static String DARK_AQUA = fcode('3');
        public static String DARKRED = fcode('4');
        public static String DARK_PURPLE = fcode('5');
        public static String GOLD = fcode('6');
        public static String GRAY = fcode('7');
        public static String DARKGRAY = fcode('8');
        public static String BLUE = fcode('9');
        public static String GREEN = fcode('a');
        public static String AQUA = fcode('b');
        public static String RED = fcode('c');
        public static String LIGHT_PURPLE = fcode('d');
        public static String YELLOW = fcode('e');
        public static String WHITE = fcode('f');

        public static String fcode(char c) {
            String a = "";
            a += '\u00a7';
            a += c;
            return a;
        }
    }
}
