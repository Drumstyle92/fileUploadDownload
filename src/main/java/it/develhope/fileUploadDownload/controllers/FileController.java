package it.develhope.fileUploadDownload.controllers;

import it.develhope.fileUploadDownload.services.FileStorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Drumstyle92
 * REST controller class that handles file upload and download
 */
@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * dependency with Autowired annotation to perform upload and download operations
     */
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * @param files parameter where are the files to upload
     * @return returns successfully uploaded items
     * @throws Exception the exception
     * method that accepts an array of "MultipartFile" objects
     */
    @PostMapping("/upload")
    public List<String> upload(@RequestParam MultipartFile[] files) throws  Exception{

        List<String> fileNames = new ArrayList<>();

        for (MultipartFile file : files) {

            String singleUploadedFileName = fileStorageService.upload(file);

            fileNames.add(singleUploadedFileName);

        }

        return fileNames;

    }

    /**
     * @param fileName Image name parameter to download
     * @param response HttpServletResponse parameter where to handle the response
     * @return Return the contents of the file as a byte array
     * @throws Exception the exception
     * method that takes the requested file name as a parameter
     */
    @GetMapping("/download")
    public @ResponseBody byte[] download(@RequestParam String fileName, HttpServletResponse response) throws  Exception{

        System.out.println("Downloading "  + fileName);

        String extension = FilenameUtils.getExtension(fileName);

        switch (extension){

            case "gif":

                response.setContentType(MediaType.IMAGE_GIF_VALUE);

                break;

            case "jpg":

            case "jpeg":

                response.setContentType(MediaType.IMAGE_JPEG_VALUE);

                break;

            case "png":

                response.setContentType(MediaType.IMAGE_PNG_VALUE);

                break;

        }

        response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");

        return fileStorageService.download(fileName);

    }

}
