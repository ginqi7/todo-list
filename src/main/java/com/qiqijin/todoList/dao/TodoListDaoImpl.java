package com.qiqijin.todoList.dao;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.qiqijin.todoList.model.Status;
import com.qiqijin.todoList.model.TodoDTO;
import com.qiqijin.todoList.util.CsvUtil;


@Component
public class TodoListDaoImpl implements TodoListDao {


    private final String todoListFilePath;
    
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public TodoListDaoImpl(@Value("${todo-list-file-path}") final String todoListFilePath) {
	this.todoListFilePath = todoListFilePath;
    }

    @Override
    public List<TodoDTO> list(final String page, final String size) {
	final List<String[]> rows = CsvUtil.load(todoListFilePath);
	final Map<String, TodoDTO> todoMap = new HashMap<>();
	for (final String[] row : rows) {
	    final TodoDTO todoDTO = row2TodoDTO(row);
	    todoMap.put(todoDTO.getId(), todoDTO);
	}
	return todoMap.values().stream().collect(Collectors.toList());
    }

    private TodoDTO row2TodoDTO(final String[] row) {
	final TodoDTO todoDTO = new TodoDTO();
	todoDTO.setDate(LocalDateTime.parse(row[0], this.formatter));
	todoDTO.setId(row[1]);
	todoDTO.setStatus(Status.valueOf(row[2]));
	todoDTO.setData(row[3]);
	return todoDTO;
    }

    @Override
    public TodoDTO add(final TodoDTO todoDto) {
	CsvUtil.append(todoListFilePath, TodoDTO2Row(todoDto));
	return todoDto;
    }

    private String[] TodoDTO2Row(final TodoDTO todoDto) {
	return new String[] {
	    todoDto.getDate().format(this.formatter),
	    todoDto.getId(), 
	    todoDto.getStatus().toString(), 
	    todoDto.getData()
	};
    }

    @Override
    public TodoDTO update(final TodoDTO todoDto) {
	CsvUtil.append(todoListFilePath, TodoDTO2Row(todoDto));
	return todoDto;


    }

    @Override
    public TodoDTO done(final TodoDTO todoDto) {
	CsvUtil.append(todoListFilePath, TodoDTO2Row(todoDto));
	return todoDto;
    }

    @Override
    public TodoDTO toggleStatus(final TodoDTO todoDto) {
	CsvUtil.append(todoListFilePath, TodoDTO2Row(todoDto));
	return todoDto;
    }

    @Override
    public TodoDTO get(String todoId) {
	final List<String[]> rows = CsvUtil.load(todoListFilePath);
	final Map<String, TodoDTO> todoMap = new HashMap<>();
	for (final String[] row : rows) {
	    final TodoDTO todoDTO = row2TodoDTO(row);
	    todoMap.put(todoDTO.getId(), todoDTO);
	}
	return todoMap.get(todoId);
    }
    
}
