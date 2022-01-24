
package com.sv.todoapprest2022.data;

import com.sv.todoapprest2022.models.ToDo;
import java.util.List;

/**
 *
 * @author StevePro
 */
public interface ToDoDao {
    ToDo add(ToDo todo);
    List<ToDo> getAll();
    ToDo findById(int id);
    boolean update(ToDo todo); // true if item exists and is updated
    boolean deleteById(int id); // true if item exists and is deleted
}
