package novi.nl.wildplukrecepten.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter

@Entity
@Table(name = "file_uploads")
public class FileUpload {
    @Id
    private String fileName;
    private String contentType;
    private String url;
}
