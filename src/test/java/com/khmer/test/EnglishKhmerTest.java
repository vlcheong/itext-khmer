package com.khmer.test;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import com.khmer.lang.KhmerPhrase;

import org.junit.Test;
import static org.junit.Assert.*;

public class EnglishKhmerTest {

    private static final String DEST = "english-khemer.pdf";

    private static final String KHMER_FONT = "kh_battambang.ttf";

    private static final String NEW_LINE = System.getProperty("line.separator");

    @Test
    public void testCreatePdf() throws IOException, DocumentException {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new BufferedOutputStream( new FileOutputStream(DEST) ));
            document.open();

            Font courierFont = new Font(FontFamily.COURIER, 14);
            Font khmerFont = new Font(BaseFont.createFont(KHMER_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 14F, Font.NORMAL);

            Phrase phrase = KhmerPhrase.getInstance().get(courierFont, khmerFont, readData());

            document.add(new Paragraph(phrase));
        } finally {
            document.close();
        }
        assertTrue(new File(DEST).exists());
    }

    private String readData() throws IOException {
        try (Reader reader =
                new InputStreamReader(
                    EnglishKhmerTest.class.getResourceAsStream("/hello.txt"),
                    Charset.forName("UTF-8"));
             BufferedReader bufReader = new BufferedReader(reader)) {

            String data = null;
            StringBuilder str = new StringBuilder();
            while ( (data = bufReader.readLine()) != null ) {
                str.append(data).append(NEW_LINE);
            }
            return str.toString();
        }
    }
}