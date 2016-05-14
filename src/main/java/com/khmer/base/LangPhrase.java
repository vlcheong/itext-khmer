package com.khmer.base;

import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;

public interface LangPhrase {

    Phrase EMPTY_PHRASE = new Phrase("");

    Phrase get(Font asciiFont, Font langFont, String str);
}