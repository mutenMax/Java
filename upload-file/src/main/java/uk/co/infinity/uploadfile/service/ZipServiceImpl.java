package uk.co.infinity.uploadfile.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ZipServiceImpl implements  ZipService{

    public ByteArrayOutputStream createZip(List<MultipartFile> files) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(outStream);
        for (MultipartFile file : files) {
            ZipEntry zipEntry = new ZipEntry(file.getOriginalFilename());
            zipOut.putNextEntry(zipEntry);
            StreamUtils.copy(file.getInputStream(), zipOut);
            zipOut.closeEntry();
        }
        zipOut.finish();
        zipOut.close();
        return outStream;
    }
}
