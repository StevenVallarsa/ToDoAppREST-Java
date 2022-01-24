package com.sv.todoapprest2022.data;


import com.sv.todoapprest2022.data.ToDoDao;
import com.sv.todoapprest2022.models.ToDo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author: Steven Vallarsa
 *   email: stevenvallarsa@gmail.com
 *    date: 2022-01-23
 * purpose: 
 */
@Repository
public class ToDoDBDao implements ToDoDao {
    
    private final JdbcTemplate jdbc;
    
    @Autowired
    public ToDoDBDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public ToDo add(ToDo todo) {
        final String sql = "INSERT INTO todo(todo, note) VALUES(?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, todo.getTodo());
            statement.setString(2, todo.getNote());
            return statement;
        }, keyHolder);
        
        todo.setId(keyHolder.getKey().intValue());
        return todo;
    }

    @Override
    public List<ToDo> getAll() {
        final String sql = "SELECT * FROM todo ";
        return jdbc.query(sql, new ToDoMapper());
    }

    @Override
    public ToDo findById(int id) {
        final String sql = "SELECT * FROM todo WHERE id = ?";
        return jdbc.queryForObject(sql, new ToDoMapper(), id);
    }

    @Override
    public boolean update(ToDo todo) {
        final String sql = "UPDATE todo SET todo = ?, note = ?, finished = ? WHERE id = ?";
        return jdbc.update(sql, todo.getTodo(), todo.getNote(), todo.isFinished(), todo.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM todo WHERE id = ?";
        return jdbc.update(sql, id) > 0;
    }

    private static class ToDoMapper implements RowMapper<ToDo> {

        @Override
        public ToDo mapRow(ResultSet rs, int i) throws SQLException {
            ToDo todo = new ToDo();
            todo.setId(rs.getInt("id"));
            todo.setTodo(rs.getString("todo"));
            todo.setNote(rs.getString("note"));
            todo.setFinished(rs.getBoolean("finished"));
            return todo;
        }

       
    }
    
    

}
