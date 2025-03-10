package repository;

import java.sql.*;
import db.ConnectionManager;
import model.User;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public void save(User user) {
        String sql = "INSERT INTO public.\"user\" (\"name\", username, email, cpf) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getCpf());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getByEmail(String email) {
        String sql = "SELECT * FROM public.\"user\" WHERE email = ?";
        User user = null;
        try (Connection conn = ConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("cpf")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getById(int id) {
        String sql = "SELECT * FROM public.\"user\" WHERE id = ?";
        User user = null;
        try (Connection conn = ConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(
                    rs.getInt("id"),  // Preenchendo o campo id
                    rs.getString("name"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("cpf")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> getAll() {
        String sql = "SELECT * FROM public.\"user\"";
        List<User> users = new ArrayList<>();
        try (Connection conn = ConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),  // Preenchendo o campo id
                    rs.getString("name"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("cpf")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void update(int id, User user) {
        String sql = "UPDATE public.\"user\" SET \"name\" = ?, username = ?, email = ?, cpf = ? WHERE id = ?";
        try (Connection conn = ConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getCpf());
            stmt.setInt(5, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM public.\"user\" WHERE id = ?";
        try (Connection conn = ConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
