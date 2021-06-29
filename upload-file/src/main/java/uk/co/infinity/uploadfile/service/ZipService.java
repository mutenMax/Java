package uk.co.infinity.uploadfile.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface ZipService {
    public ByteArrayOutputStream createZip(List<MultipartFile> files) throws IOException;
}
