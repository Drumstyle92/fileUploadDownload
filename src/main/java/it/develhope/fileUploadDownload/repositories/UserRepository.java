package it.develhope.fileUploadDownload.repositories;

import it.develhope.fileUploadDownload.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Drumstyle92
 * repository interface for handling the User entity.
 * It is annotated with @Repository to tell Spring that it is a
 * repository type bean and is used to access and manage User entity data.
 * The interface extends JpaRepository, which is a Spring Data JPA interface.
 * This means that UserRepository has access to all methods defined in this interface
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
