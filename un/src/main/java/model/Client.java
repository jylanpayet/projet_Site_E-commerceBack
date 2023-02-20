package model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Client {
    private long id;
    private String nom;
    private String adresse;
    private String mail;
    private String telephone;
    private String motdepasse;
    private long admin;
}