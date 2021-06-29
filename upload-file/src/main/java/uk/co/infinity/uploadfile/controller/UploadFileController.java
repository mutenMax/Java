package uk.co.infinity.uploadfile.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uk.co.infinity.uploadfile.service.ZipService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Slf4j
public class UploadFileController {
    private ZipService zipService;

    public UploadFileController(ZipService zipService) {
        this.zipService = zipService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "");
        return "home";
    }

    @PostMapping(value = "/zip-download", produces="application/x-zip-compressed")
    public ResponseEntity<byte[]> zipDownload(@RequestParam("files") List<MultipartFile> files, HttpServletResponse response) throws Exception {
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=zipDownload.zip")
                .contentType(new MediaType("application", "x-zip-compressed"))
                .body(zipService.createZip(files).toByteArray());
    }
}
