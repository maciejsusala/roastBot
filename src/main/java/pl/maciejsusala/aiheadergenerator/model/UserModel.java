package pl.maciejsusala.aiheadergenerator.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    //getters and setter must be used to transfer char[] to string
    @Getter
    @Setter
    @Column(nullable = false)
    private char[] password;

    @Column(unique = true)
    private String email;

}
