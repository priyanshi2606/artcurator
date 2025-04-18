// import fi.iki.elonen.NanoHTTPD;
// import java.io.IOException;

// public class PdfServer extends NanoHTTPD {

//     public PdfServer() throws IOException {
//         super(8080); // Start server on port 8080
//         start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
//         System.out.println("Server started, listening on port 8080");
//     }

//     @Override
//     public Response serve(IHTTPSession session) {
//         if ("/generate-pdf".equalsIgnoreCase(session.getUri())) {
//             CreatePDF.generatePDF();  // Call the PDF generation method
//             return newFixedLengthResponse(Response.Status.OK, "text/plain", "PDF Generated Successfully");
//         }
//         return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Not Found");
//     }

//     public static void main(String[] args) {
//         try {
//             new PdfServer();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }
