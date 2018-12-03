package com.wgc.todo.vo;

import com.github.pagehelper.Page;

//T是 模板 替换，编译期擦除
// new ResultVO<String>(); // java javac 编译
// new ResultVO<Student>();
/*定义这个类的作用是，
* 在是try{
* }catch(Exception ex){
*   return null; //不能这样写，如果数据库内没有数据返回的是一个null
*   //那我们这里写，返回的null就不知道是报错，没数据也报错，表达的意思不明确
*   //后来经过修改，人们想到在服务器与客无端交互是加一层，就是vo层的出现
*   //vo层是为了明确的指出返回的结果是否是正确性，并于返回更多的信息到客无端，
*   //让客无端明白是否成功或报错
* }
* Http协议中，返回状态码
* 1开头表示：信息错误
* 如：
* 100 Continue 服务器仅接收到部分请求，但是一旦服务器并没有拒绝该请求，客户端应该继续发送其余的请求。
* 2开头表示： 发送请求成功，并返回数据
* 3开头表示：url转发
* 4开头表示：客无端错误
* 5开头表示：服务器错误
* */
public class RestVO<T> {
    private int code;
    private T data;
    private Page page;
    private String error;
    private Throwable throwable;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
