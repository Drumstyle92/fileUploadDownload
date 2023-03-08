package it.develhope.fileUploadDownload.services;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Drumstyle92
 * This is a service class that handles archiving, downloading and removing files from the specified repository
 */
@Service
public class FileStorageService {

    /**
     * The repository specified in this variable where the files are managed
     */
    @Value("${fileRepositoryFolder}")
    private String fileRepositoryFolder;

    /**
     * @param file Parameter of the uploaded file
     * @return Returns the complete file name in String
     * @throws IOException the io exception
     * Method that takes as input a MultipartFile object representing
     * the uploaded file and returns its full name. A new file name based
     * on a Universally Unique Identifier (UUID) is generated and checked
     * for conflicts with files already in the repository.
     * If the file was uploaded successfully, the full file name is returned
     */
    public String upload(MultipartFile file) throws IOException {

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        String newFileName = UUID.randomUUID().toString();

        String completeFileName = newFileName + "." + extension;

        //Verifications on the repository folder
        File finalFolder = new File(fileRepositoryFolder);

        if(!finalFolder.exists()) throw new IOException("Final folder does not exists");

        if(!finalFolder.isDirectory()) throw new IOException("Final folder is not a directory");

        File finalDestination = new File(fileRepositoryFolder + "\\" + completeFileName);

        if(finalDestination.exists()) throw new IOException("File conflict");

        file.transferTo(finalDestination);

        return completeFileName;

    }

    /**
     * @param fileName parameter where the full name of the file resides in string
     * @return Returns the loaded image in bytes
     * @throws IOException the io exception
     * Method that takes the file name as input and returns a byte array
     * representing the file content. Before downloading,
     * it is verified that the file exists in the repository
     */
    public byte[] download(String fileName) throws IOException {
        File fileFromRepository = new File(fileRepositoryFolder + "\\" + fileName);
        if(!fileFromRepository.exists()) throw new IOException("File does not exists");
        return IOUtils.toByteArray(new FileInputStream(fileFromRepository));

    }

    /**
     * @param fileName Parameter where the file resides in string
     * Method that takes the file name as input and removes it from the repository.
     * If the file doesn't exist, no exception is raised. Otherwise, an exception
     * is raised if the file cannot be deleted
     */
    @SneakyThrows
    public void remove(String fileName) {

        File fileFromRepository = new File(fileRepositoryFolder + "\\" + fileName);

        if(!fileFromRepository.exists()) return;

        boolean deleteResult =fileFromRepository.delete();

        if(deleteResult == false) throw new Exception("Cannot delete file");

    }

}
