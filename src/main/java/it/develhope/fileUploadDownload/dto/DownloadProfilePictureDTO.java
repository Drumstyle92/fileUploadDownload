package it.develhope.fileUploadDownload.dto;

import it.develhope.fileUploadDownload.entities.User;

/**
 * @author Drumstyle92
 * Class that represents a DTO for downloading a user profile picture
 */
public class DownloadProfilePictureDTO {

    /**
     * Variable representing the user who owns the profile picture
     */
    private User user;

    /**
     * byte array variable that contains the profile picture itself
     */
    private byte[] profileImage;

    /**
     * Default constructor
     */
    public DownloadProfilePictureDTO() {}

    /**
     * @param user         User object parameter
     * @param profileImage User image parameter
     * Parameterized constructor
     */
    public DownloadProfilePictureDTO(User user, byte[] profileImage) {
        this.user = user;
        this.profileImage = profileImage;
    }

    /**
     * @return the user
     * Method for encapsulation
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user
     * Method for encapsulation
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the byte [ ]
     * Method for encapsulation
     */
    public byte[] getProfileImage() {
        return profileImage;
    }

    /**
     * @param profileImage the profile image
     * Method for encapsulation
     */
    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

}

