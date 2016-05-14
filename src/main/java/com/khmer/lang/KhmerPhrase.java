package com.khmer.lang;

import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;

import com.itextpdf.text.pdf.languages.KhmerLigaturizer;
import com.itextpdf.text.pdf.languages.LanguageProcessor;

import com.khmer.base.AbstractLangPhrase;
import com.khmer.base.LangPhrase;

public class KhmerPhrase extends AbstractLangPhrase {

    private final KhmerLigaturizer khmerLang;

    private static final LangPhrase INSTANCE = new KhmerPhrase();

    private KhmerPhrase() {
        this.khmerLang = KhmerLigaturizer.getInstance();
    }

    public static LangPhrase getInstance() {
        return INSTANCE;
    }

    @Override
    public Phrase get(Font asciiFont, Font khmerFont, String str) {
        return (str == null ? EMPTY_PHRASE : collect(asciiFont, khmerFont, str));
    }

    @Override
    public LanguageProcessor getProcessor() {
        return khmerLang;
    }
}