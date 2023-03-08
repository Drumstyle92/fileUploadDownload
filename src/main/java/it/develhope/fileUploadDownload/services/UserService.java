package it.develhope.fileUploadDownload.services;

import it.develhope.fileUploadDownload.dto.DownloadProfilePictureDTO;
import it.develhope.fileUploadDownload.entities.User;
import it.develhope.fileUploadDownload.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

/**
 * @author Drumstyle92
 * class which is a service within a Spring application
 * and contains three methods to handle uploading,
 * downloading and removing a user's profile picture.
 */
@Service
public class UserService {
    /**
     * Instance with @Autowired annotation to take advantage of the methods related to the repository
     */
    @Autowired
    private UserRepository userRepository;
    /**
     * Instance with @Autowired annotation in order to take advantage of
     * the methods related to the fileStoreService class
     */
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * @param userId Selected user id parameter
     * @return Returns an Object of type User
     * Get method to display the user if there isn't an exception with a custom message
     */
    @SneakyThrows
    private User getUser(Long userId){

        Optional<User> optionalUser = userRepository.findById(userId);

        if(!optionalUser.isPresent()) throw new Exception("User is not present");

        return optionalUser.get();

    }

    /**
     * @param userId Selected user id parameter
     * @param profilePicture Parameter where the user contains the image
     * @return Returns the user object with the image
     * The method takes a user ID and a MultipartFile object containing the profile picture.
     * The getUser method is called to get the User object associated with the user ID.
     * If the User object already contains a profile picture,
     * it is removed using the fileStorageService service.
     * The profile picture is then uploaded using the fileStorageService service, and the file name
     * is saved in the User object. Finally, the User object is saved to the database via the UserRepository
     */
    @SneakyThrows
    public User uploadProfilePicture(Long userId, MultipartFile profilePicture) {

        User user = getUser(userId);

        if(user.getProfilePicture() != null){

            fileStorageService.remove(user.getProfilePicture());
        }

        String fileName = fileStorageService.upload(profilePicture);

        user.setProfilePicture(fileName);

        return userRepository.save(user);

    }

    /**
     * @param userId Selected user id parameter
     * @return Returns the image of the selected userid in bytes
     * The method that takes a user ID and returns a DownloadProfilePictureDTO
     * instance, which contains the User
     * object associated with the user ID and the user's profile picture bytes,
     * obtained through the fileStorageService service.
     */
    @SneakyThrows
    public DownloadProfilePictureDTO downloadProfilePicture(Long userId) {

        User user = getUser(userId);

        DownloadProfilePictureDTO dto = new DownloadProfilePictureDTO();

        dto.setUser(user);

        if(user.getProfilePicture() == null) return dto;

        byte[] profilePictureBytes = fileStorageService.download(user.getProfilePicture());

        dto.setProfileImage(profilePictureBytes);

        return dto;

    }

    /**
     * @param userId Selected user id parameter
     * Method that takes a user ID and removes the corresponding User
     * object from the database via the UserRepository.
     * If the User object contains a profile picture, it is removed using the fileStorageService service
     * The @SneakyThrows annotation indicates that the exception will be handled
     * in the method calling the annotated
     * method and is not handled in this method. This allows you to avoid having
     * to declare the Exception in your methods
     */
    @SneakyThrows
    public void remove(Long userId) {

        User user = getUser(userId);

        if(user.getProfilePicture() != null) {

            fileStorageService.remove(user.getProfilePicture());

        }

        userRepository.deleteById(userId);

    }

}
