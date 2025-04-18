
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;

import java.awt.geom.AffineTransform;


public class CreatePDF {

    private static String PATH = "C:\\Users\\Khushi\\Desktop\\SEM-7\\art_curator\\pdf\\";
    private static String PDF_NAME = "dwnld.pdf";
    private static String IMG_NAME = "lotus_img.png";
    
    public static void main(String[] args) {

        System.out.println("create pdf..");
        try {
            PDDocument document = new PDDocument();

            PDPage page = new PDPage(PDRectangle.A4);

            document.addPage(page);

            Path imgPath = Paths.get(PATH + IMG_NAME);

            PDImageXObject image = PDImageXObject.createFromFile(imgPath.toAbsolutePath().toString(), document);
            addImagetoPageCentered(document, page, image);
            addWatermark(document, "  Art Curator  ");

            
            document.save(new File(PATH + PDF_NAME));

            document.close();
            System.out.println("pdf created!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private static void addWatermark(PDDocument document, String string) throws IOException {
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            PDPage page = document.getPage(i);
            PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true, true);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 150);
    
            PDExtendedGraphicsState extGState = new PDExtendedGraphicsState();
            extGState.setNonStrokingAlphaConstant(0.6f);
            contentStream.setGraphicsStateParameters(extGState);
    
            // Create an AffineTransform to rotate the text by 45 degrees
            AffineTransform at = new AffineTransform();
            at.rotate(Math.PI / 4); // 45 degrees in radians
    
            // Convert the AffineTransform to a Matrix
            Matrix matrix = new Matrix(at);
    
            // Apply the transformation matrix
            contentStream.transform(matrix);
    
            // Add the text
            contentStream.beginText();
            contentStream.newLineAtOffset(30, 50); // Adjust the position if needed
            contentStream.showText(string);
            contentStream.endText();
    
            contentStream.close();
        }
    }
    
    

    private static void addImagetoPageCentered(PDDocument document, PDPage page, PDImageXObject image)
            throws IOException {

        float pageWidth = page.getMediaBox().getWidth();
        float pageHeight = page.getMediaBox().getHeight();
        float imageWidth = image.getWidth();
        float imageHeight = image.getHeight();

        float scale = Math.min(pageWidth / imageWidth, pageHeight / imageHeight);
        float scaledWidth = imageWidth * scale;
        float scaledHeight = imageHeight * scale;

        float x = (pageWidth - scaledWidth) / 2;
        float y = (pageHeight - scaledHeight) / 2;

        addImagetoPage(document, page, image, x, y, scaledWidth, scaledHeight);

    }

    private static void addImagetoPage(PDDocument document, PDPage page, PDImageXObject image, float x, float y,
            float width, float height) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.drawImage(image, x, y, width, height);
        contentStream.close();
        

    }

    

}
