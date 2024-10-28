package disco_bracelet.configurations;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class BarCodeGenerator {

    public static String generateBarCode(String barCodeText) throws WriterException, IOException {
        String filePath = "D:\\Programiranje\\sts\\discoBracelet\\src\\main\\resources\\barCodes\\" + barCodeText + ".png";
        BitMatrix matrix = new MultiFormatWriter().encode(barCodeText, BarcodeFormat.CODE_128, 300, 150);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(matrix, "PNG", path);

    return  filePath;}
}
