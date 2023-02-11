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
    private String email;
    private String tel;
}