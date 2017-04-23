package com.xiabaike.excel2word.test;

import static org.junit.Assert.assertEquals;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.w3c.dom.ranges.Range;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by xiabaike on 2017/4/23.
 */
public class TestWord {

    private XWPFDocument doc = null;
    private File docFile = null;

    private static final int SHEET_NUM = 0;
    private static final int ROW_NUM = 0;
    private static final int CELL_NUM = 0;
    private static final double NEW_VALUE = 100.98D;
    private static final String BINARY_EXTENSION = "xls";
    private static final String OPENXML_EXTENSION = "xlsx";

    public static void main(String[] args) throws IOException, OpenXML4JException {
//        TestWord ued = new TestWord("E:\\test\\ssssssss.docx");
//        ued.updateEmbeddedDoc();
//        ued.checkUpdatedDoc();
        test();
    }

    public static void test() throws IOException {
        File docFile = new File("E:\\\\test\\\\ssssssss.docx");
        FileInputStream fis = null;
        XWPFDocument doc = null;

        if (!docFile.exists()) {
            throw new FileNotFoundException("The Word dcoument " + " does not exist.");
        }
        try {
            // Open the Word document file and instantiate the XWPFDocument
            // class.
            fis = new FileInputStream(docFile);
            doc = new XWPFDocument(fis);
        } finally {
            IOUtils.closeQuietly(fis);
        }

        XWPFComment[] xwpfComments = doc.getComments();
        for(XWPFComment xwpfComment : xwpfComments) {
            System.out.println(xwpfComment.getText());
        }

        CTDocument1 ctDocument1 = doc.getDocument();
        CTBody ctBody = ctDocument1.getBody();
        System.out.println(ctBody.toString());
//        XWPFParagraph p1 = doc.createParagraph();
//        p1.setAlignment(ParagraphAlignment.CENTER);
//        p1.setBorderBottom(Borders.DOUBLE);
//        p1.setBorderTop(Borders.DOUBLE);
//
//        p1.setBorderRight(Borders.DOUBLE);
//        p1.setBorderLeft(Borders.DOUBLE);
//        p1.setBorderBetween(Borders.SINGLE);
//
//        p1.setVerticalAlignment(TextAlignment.TOP);
//
//        XWPFRun r1 = p1.createRun();
//        r1.setBold(true);
//        r1.setText("The quick brown fox");
//        r1.setBold(true);
//        r1.setFontFamily("Courier");
//        r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
//        r1.setTextPosition(100);
//
//        XWPFParagraph p2 = doc.createParagraph();
//        p2.setAlignment(ParagraphAlignment.RIGHT);
//
//        //BORDERS
//        p2.setBorderBottom(Borders.DOUBLE);
//        p2.setBorderTop(Borders.DOUBLE);
//        p2.setBorderRight(Borders.DOUBLE);
//        p2.setBorderLeft(Borders.DOUBLE);
//        p2.setBorderBetween(Borders.SINGLE);
//
//        XWPFRun r2 = p2.createRun();
//        r2.setText("jumped over the lazy dog");
//        r2.setStrikeThrough(true);
//        r2.setFontSize(20);
//
//        XWPFRun r3 = p2.createRun();
//        r3.setText("and went away");
//        r3.setStrikeThrough(true);
//        r3.setFontSize(20);
//        r3.setSubscript(VerticalAlign.SUPERSCRIPT);
//
//
//        XWPFParagraph p3 = doc.createParagraph();
//        p3.setWordWrapped(true);
//        p3.setPageBreak(true);
//
//        //p3.setAlignment(ParagraphAlignment.DISTRIBUTE);
//        p3.setAlignment(ParagraphAlignment.BOTH);
//        p3.setSpacingBetween(15, LineSpacingRule.EXACT);
//
//        p3.setIndentationFirstLine(600);
//
//
//        XWPFRun r4 = p3.createRun();
//        r4.setTextPosition(20);
//        r4.setText("To be, or not to be: that is the question: "
//                + "Whether 'tis nobler in the mind to suffer "
//                + "The slings and arrows of outrageous fortune, "
//                + "Or to take arms against a sea of troubles, "
//                + "And by opposing end them? To die: to sleep; ");
//        r4.addBreak(BreakType.PAGE);
//        r4.setText("No more; and by a sleep to say we end "
//                + "The heart-ache and the thousand natural shocks "
//                + "That flesh is heir to, 'tis a consummation "
//                + "Devoutly to be wish'd. To die, to sleep; "
//                + "To sleep: perchance to dream: ay, there's the rub; "
//                + ".......");
//        r4.setItalic(true);
////This would imply that this break shall be treated as a simple line break, and break the line after that word:
//
//        XWPFRun r5 = p3.createRun();
//        r5.setTextPosition(-10);
//        r5.setText("For in that sleep of death what dreams may come");
//        r5.addCarriageReturn();
//        r5.setText("When we have shuffled off this mortal coil,"
//                + "Must give us pause: there's the respect"
//                + "That makes calamity of so long life;");
//        r5.addBreak();
//        r5.setText("For who would bear the whips and scorns of time,"
//                + "The oppressor's wrong, the proud man's contumely,");
//
//        r5.addBreak(BreakClear.ALL);
//        r5.setText("The pangs of despised love, the law's delay,"
//                + "The insolence of office and the spurns" + ".......");
//
//        FileOutputStream out = new FileOutputStream("E:\\test\\ssssssss.docx");
//        doc.write(out);
//        out.close();
//        doc.close();
    }

    public TestWord(String filename) throws FileNotFoundException, IOException {
        this.docFile = new File(filename);
        FileInputStream fis = null;
        if (!this.docFile.exists()) {
            throw new FileNotFoundException("The Word dcoument " + filename + " does not exist.");
        }
        try {
            // Open the Word document file and instantiate the XWPFDocument
            // class.
            fis = new FileInputStream(this.docFile);
            this.doc = new XWPFDocument(fis);
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }

    public void updateEmbeddedDoc() throws OpenXML4JException, IOException {
        XWPFComment[] xwpfComments = doc.getComments();
        CTDocument1 ctDocument1 = doc.getDocument();
        CTBody ctBody = ctDocument1.getBody();
        System.out.println(ctDocument1);
    }

    public void checkUpdatedDoc() throws OpenXML4JException, IOException {
        for (PackagePart pPart : this.doc.getAllEmbedds()) {
            String ext = pPart.getPartName().getExtension();
            if (BINARY_EXTENSION.equals(ext) || OPENXML_EXTENSION.equals(ext)) {
                InputStream is = pPart.getInputStream();
                Workbook workbook = null;
                try {
                    workbook = WorkbookFactory.create(is);
                    Sheet sheet = workbook.getSheetAt(SHEET_NUM);
                    Row row = sheet.getRow(ROW_NUM);
                    Cell cell = row.getCell(CELL_NUM);
                    assertEquals(cell.getNumericCellValue(), NEW_VALUE, 0.0001);
                } finally {
                    IOUtils.closeQuietly(workbook);
                    IOUtils.closeQuietly(is);
                }
            }
        }
    }

}
