package com.khmer.test;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.languages.KhmerLigaturizer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import org.junit.Test;
import static org.junit.Assert.*;

public class KhemerFontTest {

    private static final String DEST = "khemer.pdf";

    private static final String FONT = "kh_battambang.ttf";

    private static final String NEW_LINE = System.getProperty("line.separator");

    @Test
    public void testCreatePdf() throws IOException, DocumentException {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new BufferedOutputStream( new FileOutputStream(DEST) ));
            document.open();
            BaseFont baseFont = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font khemerFont = new Font(baseFont, 14F, Font.NORMAL);
            KhmerLigaturizer kh = new KhmerLigaturizer();
            String data = kh.process( readData() );
            document.add(new Paragraph(data, khemerFont));
        } finally {
            document.close();
        }
        assertTrue(new File(DEST).exists());
    }

    private String readData() throws IOException {
        try (Reader reader =
                new InputStreamReader(
                    KhemerFontTest.class.getResourceAsStream("/hello.txt"),
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