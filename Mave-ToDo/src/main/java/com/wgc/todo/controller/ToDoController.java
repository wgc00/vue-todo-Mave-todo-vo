package com.wgc.todo.controller;

import com.google.gson.Gson;
import com.wgc.todo.entity.Todo;
import com.wgc.todo.exception.DatabaseException;
import com.wgc.todo.exception.DateValidateException;
import com.wgc.todo.service.ToDoService;
import com.wgc.todo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
/*要明确跨域的设置*/
@CrossOrigin(origins = {"*"},
        methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST},
        allowedHeaders = {"*"},
        maxAge = 3600)
public class ToDoController {
    @Autowired
    private ToDoService service;


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResultVO selectAll() {
       // ResultVO restVO = new ResultVO<>();
        ResultVO restVO = new ResultVO<>();
        try {
            restVO.setCode(200);
            restVO.setData(service.selectAll());
            return restVO;
        } catch (DatabaseException e) {
            /*不使用链式，可以精确的定位的错误信息。
            * */
           restVO.setCode(205);
           restVO.setError("这是一个错误");
           return restVO;
        } catch (Exception e) {
            /*链式操作*/
            /*缺点： 1、调式的时候不凡便
             *  2、找错误不能精确的定位到错误的位置
             *  3、链式的操作写起来简单了，但vo层再起来又变麻烦了，所以事情都有两面性的，
             *  只是牺牲一面，成全另别面
             * */
            restVO.setCode(225).setError("这是一个错误");
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


    @RequestMapping(value = "/state", method = RequestMethod.PUT)
    public void updateState(@RequestBody String to) {
        Gson gson = new Gson();
        /*json字符串转为对象*/
        Todo todo = gson.fromJson(to, Todo.class);
        if (todo.getState() == null) {
            return;
        }
        service.updateState(todo);
    }

    @RequestMapping(value = "/queryState/{state}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public List<Todo> selectState(@PathVariable String state) {
        List<Todo> todos = service.selectState(state);
        return todos;
    }

    @RequestMapping(value = "/android/all", method = RequestMethod.GET)
    public ResultVO selectJson() {
        ResultVO<Object> vo = new ResultVO<>();
        try {
            List<Todo> todos = service.selectAll();
           /* Gson gson = new Gson();
            String s = gson.toJson(todos);*/
            vo.setCode(200);
            vo.setData(todos);
            return vo;
        } catch (DatabaseException e) {
            vo.setCode(500);
            vo.setError("内部错误，请联系管理人员");
            return vo;
        } catch (Exception ex) {
            vo.setCode(501);
            vo.setError("Not Not Not");
            return vo;
        }
    }
}
