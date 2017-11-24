package com.jy.study.spring.security.controller;

import com.jy.study.spring.security.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RequestMapping("file")
@RestController
public class FileController {

    private static final String folder = "/home/amen/ideaworkspace/study/spring-security-study/security-demo/src/main/resources/upload";

    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {
        System.out.println("file name: " + file.getName());
        System.out.println("original name: " + file.getOriginalFilename());
        System.out.println("file size: " + file.getSize());
        File localFile = new File(folder, System.currentTimeMillis() + ".txt");
        file.transferTo(localFile);
        //file.getInputStream(); 使用此输入流输入的数据写到其他地方
        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("{id:\\d+}")
    public void download(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        try (
            InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
            OutputStream outputStream = response.getOutputStream()
        ) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }catch (Exception e) {

        }

    }

}
