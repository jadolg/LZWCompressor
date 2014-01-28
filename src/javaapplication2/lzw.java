package javaapplication2;

import java.util.Hashtable;

/**
 *
 * @author jorge
 */
public final class lzw {

    public static int MAX_DICT = 65536;

    public static char characterFor(int i) {
        char c = '\u0000';
        return (char) (c + i);
    }

    public static String compress(char[] cadena, int MAX_DICT, Radio retradio) throws Exception {
        lzw.MAX_DICT = MAX_DICT;
        int dict_size = 256;
        int maxused = 0;
        String result = "";
        Hashtable<String, Character> dict = new Hashtable<String, Character>();
        for (int i = 0; i < dict_size; i++) {
            dict.put(String.valueOf(characterFor(i)), characterFor(i));
        }
        String w = "";
        String wc;

        for (int i = 0; i < cadena.length; i++) {
            char c = cadena[i];
            if (c > 255) {
                throw new Exception("No se pueden comprimir valores fuera de 0..255");
            }
            wc = w + c;
            if (dict.containsKey(wc)) {
                w = wc;
            } else {
                result += dict.get(w);
                if (dict.get(w) > maxused) {
                    maxused = dict.get(w);
                }
                if (dict_size < MAX_DICT) {
                    dict.put(wc, characterFor(dict_size));
                    dict_size++;
                }
                w = String.valueOf(c);
            }
        }
        if (!w.isEmpty()) {
            result += dict.get(w);
        }

        retradio.retradio(cadena.length, result.length(), maxused);

        return result;
    }

    public static String decompress(char[] cadena, int MAX_DICT, Radio retradio) throws Exception {
        lzw.MAX_DICT = MAX_DICT;
        int maxused = 0;
        int dict_size = 256;
        Hashtable<Character, String> dict = new Hashtable<Character, String>();
        for (int i = 0; i < dict_size; i++) {
            dict.put(characterFor(i), String.valueOf(characterFor(i)));
        }
        String result = String.valueOf(cadena[0]);
        String w = String.valueOf(cadena[0]);

        for (int i = 1; i < cadena.length; i++) {
            char k = cadena[i];
            String entry = "";
            if (k > maxused) {
                maxused = k;
            }
            if (dict.containsKey(k)) {
                entry = String.valueOf(dict.get(k));
            } else if (cadena[i] == characterFor(dict_size)) {
                entry = w + w.charAt(0);
            } else {
                throw new Exception("Error de Compresion");
            }
            result += entry;
            if (dict_size < MAX_DICT) {
                dict.put(characterFor(dict_size), w + entry.charAt(0));
                dict_size++;
            }
            w = entry;
        }

        retradio.retradio(result.length(), cadena.length, maxused);

        return result;
    }
}
