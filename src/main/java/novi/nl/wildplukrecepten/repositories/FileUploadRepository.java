package novi.nl.wildplukrecepten.repositories;

import novi.nl.wildplukrecepten.models.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileUploadRepository extends JpaRepository<FileUpload, String> {
    Optional<FileUpload> findByFileName(String fileName);
}
