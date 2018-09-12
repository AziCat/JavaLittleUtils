import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

import java.io.*;

/**
 * pdf工具类
 *
 * @author yanjunhao
 * @date 2018年9月10日
 */
public class PdfUtil {
    public static void main(String[] args) {
        File pdf = new File("D:\\test\\4028c29961e09d850161e0ce489e0041-A007A.pdf");
        InputStream is = null;
        ByteArrayOutputStream os = null;
        Document document;
        PdfCopy copy;
        try {
            is = new FileInputStream(pdf);
            os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                os.write(buffer, 0, len);
            }
            PdfReader pdfReader = new PdfReader(os.toByteArray());
            int pageSize = pdfReader.getNumberOfPages();
            System.out.println(pageSize);
            //byte[] pageContent =pdfReader.getPageContent(1);
            FileOutputStream fos = new FileOutputStream("D:\\test\\4028c29961e09d850161e0ce489e0041-A007A-1.pdf");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            document = new Document(pdfReader.getPageSize(1));
            copy = new PdfCopy(document,bos);
            document.open();
            document.newPage();
            copy.addPage(copy.getImportedPage(pdfReader,1));
            document.close();

            System.out.println(bos.size());
            fos.write(bos.toByteArray());
            bos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
