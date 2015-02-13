package com.cookbook.cq.utilities;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * User: bvaughn
 * Date: 2/13/15
 */
public class PdfHelper {
    private static final Logger LOG = LoggerFactory.getLogger(PdfHelper.class);

    //http://localhost:4502/services/cookbook/pdf

    public ByteArrayOutputStream createPDF() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PDDocument document = new PDDocument();

            PDPage page = new PDPage();
            document.addPage(page);
            PDFont font = PDType1Font.HELVETICA;

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            final int margin = 30;
            final float pageWidth = page.findMediaBox().getWidth();
            final float tableWidth = page.findMediaBox().getWidth() - margin - margin;
            Color defaultColor = Color.BLACK;

            // Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
            drawText(font, 14, contentStream, margin, 600, "AUA Symptom Score Questionnaire");

            drawText(font, 12, contentStream, margin, 580, "The American Urological Association (AUA) has created this symptom index to give you");

            drawText(font, 12, contentStream, margin, 568, "and your physician an understanding of the severity of your enlarged prostate symptoms.");

            drawText(font, 8, contentStream, margin, 400, "Question");
            drawText(font, 8, contentStream, 360, 400, "None");

            drawText(font, 8, contentStream, 390, 428, "Less");
            drawText(font, 8, contentStream, 390, 414, "than 1");
            drawText(font, 8, contentStream, 390, 400, "time in 5");

            drawText(font, 8, contentStream, 425, 428, "Less");
            drawText(font, 8, contentStream, 425, 414, "than half");
            drawText(font, 8, contentStream, 425, 400, "the time");

            drawText(font, 8, contentStream, 460, 428, "About");
            drawText(font, 8, contentStream, 460, 414, "half the");
            drawText(font, 8, contentStream, 460, 400, "time");

            drawText(font, 8, contentStream, 495, 428, "More");
            drawText(font, 8, contentStream, 495, 414, "than half");
            drawText(font, 8, contentStream, 495, 400, "the time");

            drawText(font, 8, contentStream, 530, 414, "Almost");
            drawText(font, 8, contentStream, 530, 400, "always");


            drawQuad(contentStream, 557, 395, 25, 45, Color.GREEN);
            drawText(font, 8, contentStream, 560, 414, "Your", Color.WHITE);
            drawText(font, 8, contentStream, 560, 400, "score", Color.WHITE);

            drawLine(contentStream, margin, 395, pageWidth - margin, 395, Color.GREEN);

            drawText(font, 11.25f, contentStream, margin, 385,
                "Incomplete emptying:  Over the past month, how often have");
            drawText(font, 11.25f, contentStream, margin, 373, "you had a sensation of not emptying your bladder completely");
            drawText(font, 11.25f, contentStream, margin, 361, "after you finished urinating?");

            drawLine(contentStream, margin, 353, pageWidth - margin, 353, Color.GREEN);

            drawText(font, 11.25f, contentStream, margin, 340, "Frequency:  Over the past month, how often have you had");
            drawText(font, 11.25f, contentStream, margin, 328, "to urinate again less than 2 hours after you finished urinating?");

            drawLine(contentStream, margin, 320, pageWidth - margin, 320, Color.GREEN);

            contentStream.close();

            document.save(outputStream);


        } catch (IOException e) {
            LOG.warn(e.getMessage(), e);
        } catch (COSVisitorException e) {
            LOG.warn(e.getMessage(), e);
        }



        return outputStream;
    }

    private void drawText(PDFont font, float fontSize, PDPageContentStream contentStream, int x, int y, String content) throws IOException {
        drawText(font, fontSize, contentStream, x, y, content, Color.BLACK);
    }

    private void drawText(PDFont font, float fontSize, PDPageContentStream contentStream, int x, int y, String content, Color color) throws IOException {
        contentStream.setNonStrokingColor(color);
        contentStream.beginText();
        contentStream.setFont( font, fontSize );
        contentStream.moveTextPositionByAmount( x, y );
        contentStream.drawString( content );
        contentStream.endText();
    }

    private void drawLine(PDPageContentStream contentStream, float xStart, float yStart, float xEnd, float yEnd, Color color)
        throws IOException {
        contentStream.setStrokingColor(color);
        contentStream.drawLine(xStart, yStart, xEnd, yEnd);
    }

    private void drawQuad(PDPageContentStream contentStream, float xStart, float yStart, float width, float height, Color color)
        throws IOException {
        contentStream.setNonStrokingColor(color);
        contentStream.fillRect(xStart, yStart, width, height);
    }
}
