package com.wgc.todo.controller;

import com.google.gson.Gson;
import com.wgc.todo.entity.Todo;
import com.wgc.todo.exception.DatabaseException;
import com.wgc.todo.exception.DateValidateException;
import com.wgc.todo.service.ToDoService;
import com.wgc.todo.vo.RestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
/*要明确跨域的设置*/
@CrossOrigin(origins = {"http://localhost:8081"},
        methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST},
        allowedHeaders = {"*"},
        maxAge = 3600)
public class ToDoController {
    @Autowired
    private ToDoService service;


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public RestVO selectAll() {
       // RestVO restVO = new RestVO<>();
        RestVO restVO = new RestVO<>();
        try {
            restVO.setCode(200);
            restVO.setData(service.selectAll());
            return restVO;
        } catch (DatabaseException e) {
           restVO.setCode(205);
           restVO.setError("这是一个错误");
           return restVO;
        } catch (Exception e) {
            restVO.setCode(225);
            restVO.setError("这是一个错误");
            return restVO;
        }

    }

    /*Todo对象传过来的是一个字符串*/
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void insert(@RequestBody Todo todo) {
        try {
            service.insert(todo);
        } catch (DateValidateException e) {
            e.getMessage();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    /*@RequestBody接收的都是一个字符串，如果设置int，客户端会认为，类型不一致，
    * 所以找不到此方法*/
    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
    public void delete(@RequestBody String  id) {
            Integer integer = Integer.valueOf(id);
        try {
            service.deleteByPrimaryKey(integer);
        } catch (DateValidateException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public void update(@RequestBody  Todo todo) {
        if (todo != null) {
            service.updateByPrimaryKey(todo);
        }
    }


    @RequestMapping(value = "/state", method = RequestMethod.PUT, produces = "application/json;")
    public void updateState(@RequestBody String to) {
        Gson gson = new Gson();
        /*json字符串转为对象*/
        Todo todo = gson.fromJson(to, Todo.class);
        if (todo.getState() == null) {
            return;
        }
        service.updateState(todo);
    }

    @RequestMapping(value = "/queryState/{state}", method = RequestMethod.GET, produces = "application/json;")
    public List<Todo> selectState(@PathVariable String state) {
        List<Todo> todos = service.selectState(state);
        return todos;
    }
}
