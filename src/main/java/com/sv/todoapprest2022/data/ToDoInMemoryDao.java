
package com.sv.todoapprest2022.data;

import com.sv.todoapprest2022.models.ToDo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author: Steven Vallarsa
 *   email: stevenvallarsa@gmail.com
 *    date: 2022-01-23
 * purpose: 
 */
@Repository
public class ToDoInMemoryDao implements ToDoDao {

    private static final List<ToDo> todos = new ArrayList<>();
    
    @Override
    public ToDo add(ToDo todo) {
        
        // find biggest todo id and increate by 1 for new todo
        int nextId = todos.stream()
                .mapToInt(i -> i.getId())
                .max()
                .orElse(0) + 1;
        
        todo.setId(nextId);
        todos.add(todo);
        return todo;
        
    }

    @Override
    public List<ToDo> getAll() {
        return new ArrayList<>(todos);
    }

    @Override
    public ToDo findById(int id) {
        return todos.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean update(ToDo todo) {
        
        int index = 0;
        while (index < todos.size() && todos.get(index).getId() != todo.getId()) {
            index++;
        }
        
        if (index < todos.size()) {
            todos.set(index, todo);
        }
        
        return index < todos.size();
                
    }

    @Override
    public boolean deleteById(int id) {
        return todos.removeIf(i -> i.getId() == id);
    }
    
}
