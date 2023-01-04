package novi.nl.wildplukrecepten.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter

@Entity
@Table(name="messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String message;
    private String emailfrom;
    private String emailto;
    private Date currentdatetime;

//    @OneToOne
//    private User userfrom;
//
//    @OneToOne
//    private User userto;
}
