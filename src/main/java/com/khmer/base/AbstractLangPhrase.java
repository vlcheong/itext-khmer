package com.khmer.base;

import com.itextpdf.text.pdf.languages.LanguageProcessor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;

import java.nio.charset.Charset;

public abstract class AbstractLangPhrase implements LangPhrase {

    public static boolean isAscii(char ch) {
        return Charset.forName("US-ASCII").newEncoder().canEncode(Character.toString(ch));
    }

    protected Phrase collect(Font asciiFont, Font langFont, String str) {
        Phrase phrase = null;
        boolean ascii = false;

        final StringBuilder sb = new StringBuilder();
        final char[] chars = str.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (i == 0) {
                ascii = isAscii(chars[i]);
                sb.append(chars[i]);
                continue;
            }
            if (ascii != isAscii(chars[i])) {
                final Phrase ph = ascii ? newPhrase(sb, asciiFont) : newPhrase(process(sb), langFont);
                if (phrase == null) {
                    phrase = ph;
                } else {
                    phrase.add(ph);
                }
                ascii = !ascii;
                sb.delete( 0, sb.length() );
            }
            sb.append(chars[i]);
        }
        final Phrase ph = ascii ? newPhrase(sb, asciiFont) : newPhrase(process(sb), langFont);
        if (phrase == null) {
            phrase = ph;
        } else {
            phrase.add(ph);
        }
        return phrase;
    }

    private String process(StringBuilder sb) {
        final LanguageProcessor processor = getProcessor();
        return (processor != null ? processor.process(sb.toString()) : sb.toString());
    }

    private Phrase newPhrase(StringBuilder sb, Font font) {
        return newPhrase(sb.toString(), font);
    }

    private Phrase newPhrase(String str, Font font) {
        return new Phrase(str, font);
    }

    protected abstract LanguageProcessor getProcessor();
}