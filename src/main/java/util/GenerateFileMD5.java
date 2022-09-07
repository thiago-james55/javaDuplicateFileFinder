package util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


public class GenerateFileMD5 {

    public static String generateMD5(File file) {

        try  {
            InputStream inputStream = new FileInputStream(file);
            return DigestUtils.md5Hex(inputStream.readAllBytes());

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;

    }
}
