import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.util.GraphicsRenderingHints;

public class Start {
    public static void main(String[] args) {
        String filepath = "D:\\yan Work\\code\\CNN\\check_sign\\cl\\test\\房国浩盗窃案\\01.pdf";
        String imagePath = "D:\\yan Work\\code\\CNN\\check_sign\\cl\\test\\房国浩盗窃案\\01";
        float zoom = 1.0F;
        try {
            tranfer(filepath,imagePath,zoom);
        } catch (PDFException e) {
            e.printStackTrace();
        } catch (PDFSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将指定pdf文件的首页转换为指定路径的缩略图
     *
     * @param filepath  原文件路径，例如d:/test.pdf
     * @param imagepath 图片生成路径，例如 d:/
     * @param zoom      缩略图显示倍数，1表示不缩放，0.3则缩小到30%
     * @return 生成图片名称列表
     */
    public static void tranfer(String filepath, String imagepath, float zoom) throws PDFException,
            PDFSecurityException, IOException, InterruptedException {
        // ICEpdf document class
        Document document = null;
        float rotation = 0f;
        document = new Document();
        document.setFile(filepath);
        // maxPages = document.getPageTree().getNumberOfPages();
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            BufferedImage img =
                    (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,
                            org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX, rotation, zoom);
            Iterator iter = ImageIO.getImageWritersBySuffix("jpg");
            ImageWriter writer = (ImageWriter) iter.next();
            File outFile =
                    new File(imagepath + (filepath.replace(imagepath, "").split("\\.")[0]) + "-" + (i + 1)
                            + ".jpg");
            FileOutputStream out = new FileOutputStream(outFile);
            ImageOutputStream outImage = ImageIO.createImageOutputStream(out);
            writer.setOutput(outImage);
            writer.write(new IIOImage(img, null, null));
        }
    }
}
