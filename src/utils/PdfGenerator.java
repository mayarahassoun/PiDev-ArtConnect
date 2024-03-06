package utils;


import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class PdfGenerator {
    public static Document createPdf(String dest){
        PdfWriter writer;
        PdfDocument pdf;
        Document document =null;

        try {
            writer=new PdfWriter(dest);
            pdf=new PdfDocument(writer);
            document=new Document(pdf, PageSize.A4.rotate());
            document.setMargins(10, 5, 5, 5);
            document.add(new Paragraph(""));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PdfGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return document;

    }
}
