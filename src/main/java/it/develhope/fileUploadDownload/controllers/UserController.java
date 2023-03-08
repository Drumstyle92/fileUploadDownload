package it.develhope.fileUploadDownload.controllers;

import it.develhope.fileUploadDownload.dto.DownloadProfilePictureDTO;
import it.develhope.fileUploadDownload.entities.User;
import it.develhope.fileUploadDownload.repositories.UserRepository;
import it.develhope.fileUploadDownload.services.UserService;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * @author Drumstyle92
 * REST controller class that handles HTTP requests related to CRUD operations on user data
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     *  Autowired annotated dependency that handles some persistence and business logic
     */
    @Autowired
    private UserRepository userRepository;

    /**
     *  Autowired annotated dependency that handles some persistence and business logic
     */
    @Autowired
    private UserService userService;

    /**
     * @param user Post call body parameter in json
     * @return Returns the created user
     * post method which creates a new user
     */
    @PostMapping
    public User create(@RequestBody User user){
        user.setId(null);
        return userRepository.save(user);
    }

    /**
     * @param id             Selected user id parameter
     * @param profilePicture Parameter where you enter the image you want to upload
     * @return Returns the user with the image
     * post method that allows you to upload a user's profile picture,
     * makes use of the "DownloadProfilePictureDTO" DTO to manage the user's profile picture
     */
    @SneakyThrows
    @PostMapping("/{id}/profile")
    public User uploadProfileImage(@PathVariable Long id, @RequestParam MultipartFile profilePicture){

        return userService.uploadProfilePicture(id, profilePicture);
    }

    /**
     * @return Returns the list of all users
     * get method that returns a list of all users
     */
    @GetMapping
    public List<User> getAll(){
        return userRepository.findAll();
    }

    /**
     * @param id Selected user id parameter
     * @return the selected user returns
     * get method that returns a single user by ID
     */
    @GetMapping("/{id}")
    public Optional<User> getOne(@PathVariable  Long id){

        return userRepository.findById(id);

    }

    /**
     * @param id       Selected user id parameter
     * @param response HttpServletResponse parameter where to
     * set the content type of the response and the name of the output file
     * @return Returns the image in bytes
     * get method that returns the profile picture of a user based on the ID
     */
    @SneakyThrows
    @GetMapping("/{id}/profile")
    public @ResponseBody byte[] getProfileImage(@PathVariable Long id, HttpServletResponse response){

        DownloadProfilePictureDTO downloadProfileDTO = userService.downloadProfilePicture(id);

        String fileName = downloadProfileDTO.getUser().getProfilePicture();

        if(fileName  == null) throw new Exception("User does not have a profile picture");

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

        return downloadProfileDTO.getProfileImage();

    }

    /**
     * @param user Parameter where the user is entered for modification
     * @param id   Selected user id parameter
     * put method that updates an existing user by ID
     */
    @PutMapping("/{id}")
    public void update(@RequestBody User user, @PathVariable Long id){

        user.setId(id);

        userRepository.save(user);

    }

    /**
     * @param id Selected user id parameter
     * delete method which removes an existing user by ID
     */
    @SneakyThrows
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.remove(id);
    }

}
