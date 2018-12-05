package com.wgc.todo.service.Imp;

import com.wgc.todo.dao.TodoMapper;
import com.wgc.todo.entity.Todo;
import com.wgc.todo.exception.DatabaseException;
import com.wgc.todo.service.ToDoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/*如果要测试类的头部一定要加上这两个注解*/
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class ToDoServiceImpTest {
    @Autowired
    private ToDoService toDoService;

    @Autowired
    private TodoMapper todoMapper;
    @Test
    public void deleteByPrimaryKey() {
        int[] ints = new int[]{12, 345, 567, 45, 04, 34, 2323, 434};
        Arrays.sort(ints);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

    @Test
    public void insert() {
        String[] s = new String[]{"b", "c", "a", "e", "s", "a"};
        Arrays.sort(s, Collections.reverseOrder());
        for (String anInt : s) {
            System.out.println(anInt);
        }
        Arrays.stream(s).sorted().toArray(String[]:: new);
    }

    @Test
    public void selectByPrimaryKey() {

    }

    @Test
    public void selectAll() throws DatabaseException {
       //System.out.println(todoMapper.selectAll());
        List<Todo> todos = toDoService.selectAll();
        for (Todo todo : todos) {
            System.out.println(todo.getName());
            System.out.println(todo.getCreatedAt());
        }
    }

    @Test
    public void updateByPrimaryKey() {
        Date date = new Date();
        System.out.println(date);
    }

    @Test
    public void selectState() {
        List<String> objects = new ArrayList<>();
        objects.add("ddd");
        objects.add("bbb");
        objects.add(0, "eee");
        for (String object : objects) {
            System.out.println(object);
        }


    }
  /*  private List<String> ret(int i, String str) {
        List<String> list = new ArrayList<>();

    }*/
}