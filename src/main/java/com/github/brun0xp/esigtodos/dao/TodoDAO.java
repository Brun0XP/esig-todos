package com.github.brun0xp.esigtodos.dao;

import com.github.brun0xp.esigtodos.entity.Todo;
import com.github.brun0xp.esigtodos.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO implements CrudDAO<Todo>{

    public TodoDAO() {
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement ps;
            ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS todos(\n" +
                    "    id SERIAL PRIMARY KEY,\n" +
                    "    ip VARCHAR(16) NOT NULL,\n" +
                    "    description TEXT NOT NULL,\n" +
                    "    done BOOLEAN NOT NULL DEFAULT 'false'\n" +
                    ")");
            ps.executeUpdate();
            ConnectionFactory.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Todo todo) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement ps;
            ps = connection.prepareStatement("INSERT INTO todos(ip, description) VALUES (?, ?)");
            ps.setString(1, todo.getIp());
            ps.setString(2, todo.getDescription());
            ps.executeUpdate();
            ConnectionFactory.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Todo> read() {
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM todos");
            ResultSet res = ps.executeQuery();
            List<Todo> todos = new ArrayList<>();
            while (res.next()) {
                Todo todo = new Todo();
                todo.setId(res.getInt("id"));
                todo.setIp(res.getString("ip"));
                todo.setDescription(res.getString("description"));
                todo.setDone(res.getBoolean("done"));
                todos.add(todo);
            }
            ConnectionFactory.closeConnection();
            return todos;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Todo todo) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE todos SET description = ?, done=? WHERE id=?");
            ps.setString(1, todo.getDescription());
            ps.setBoolean(2, todo.isDone());
            ps.setInt(3, todo.getId());
            ps.executeUpdate();
            ConnectionFactory.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Todo todo) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM todos WHERE id=?");
            ps.setInt(1, todo.getId());
            ps.executeUpdate();
            ConnectionFactory.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
