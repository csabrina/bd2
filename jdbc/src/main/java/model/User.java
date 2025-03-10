package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private String cpf;

    public User(String name, String username, String email, String cpf) {
       
        this.name = name;
        this.username = username;
        this.email = email;
        this.cpf = cpf;
    }
}
