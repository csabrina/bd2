import java.util.List;

import model.User;
import repository.UserRepository;

public class Main {
    public static void main(String[] args) {

        UserRepository repository = new UserRepository();

        User taylor = new User( "Taylor Swift", "taylorswift", "taylor.swift@email.com", "123.456.789-00");
        User travis = new User("Travis Kelce", "traviskelce", "travis.kelce@email.com", "987.654.321-00");

        repository.save(taylor);
        repository.save(travis);

        User foundTaylor = repository.getByEmail("taylor.swift@email.com");
        User foundTravis = repository.getByEmail("travis.michaelkelce@email.com");

 
        // Teste de exibição dos dados de Taylor Swift
        if (foundTaylor != null) {
            System.out.println("Taylor Swift:");
            System.out.println("ID: " + foundTaylor.getId());
            System.out.println("Name: " + foundTaylor.getName());
            System.out.println("Email: " + foundTaylor.getEmail());
        }
        // Teste de exibição dos dados de Travis Kelce
        if (foundTravis != null) {
            System.out.println("Travis Kelce:");
            System.out.println("ID: " + foundTravis.getId());
            System.out.println("Name: " + foundTravis.getName());
            System.out.println("Email: " + foundTravis.getEmail());
            }
            
        // Modificação dos dados de Taylor Swift
        if (foundTaylor != null) {
            foundTaylor.setName("Taylor Alison Swift");
            foundTaylor.setEmail("taylor.alisonswift@email.com");
            repository.update(foundTaylor.getId(), foundTaylor);
            System.out.println("\nAtualizado Taylor Swift:");
            System.out.println("ID: " + foundTaylor.getId());
            System.out.println("Name: " + foundTaylor.getName());
            System.out.println("Email: " + foundTaylor.getEmail());
        }

        // Modificação dos dados de Travis Kelce
        if (foundTravis != null) {
            foundTravis.setName("Travis Michael Kelce");
            foundTravis.setEmail("travis.michaelkelce@email.com");
            repository.update(foundTravis.getId(), foundTravis);
            System.out.println("\nAtualizado Travis Kelce:");
            System.out.println("ID: " + foundTravis.getId());
            System.out.println("Name: " + foundTravis.getName());
            System.out.println("Email: " + foundTravis.getEmail());
        }
 
        // Deletando o usuário Travis Kelce
        if (foundTravis != null) {
            repository.delete(foundTravis.getId());
            System.out.println("Deletado Travis Kelce com ID: " + foundTravis.getId());
        }
         // Listando todos os usuários
        System.out.println("\nListando todos os usuários:");
        List<User> allUsers = repository.getAll();
        for (User user : allUsers) {
            System.out.println("ID: " + user.getId() + " | Name: " + user.getName() + " | Email: " + user.getEmail());
        }
    }
}
