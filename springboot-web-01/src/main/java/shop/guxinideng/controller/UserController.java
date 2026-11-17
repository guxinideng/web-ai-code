package shop.guxinideng.controller;

import cn.hutool.core.io.IoUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.guxinideng.pojo.User;

import javax.net.ssl.StandardConstants;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@RestController
public class UserController {
    @RequestMapping(value = "/list", produces = "application/json;charset=UTF-8")
    public List<User> list() throws Exception {
        //1.加载读取test.txt文件
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("test1.txt");
        ArrayList<String> lines = IoUtil.readLines(in, StandardCharsets.UTF_8,new ArrayList<>());

        //2.解析用户信息，分装User用户对象
        List<User> userList = lines.stream().map(
                line ->{
                    String[] part = line.split(",");
                    Integer id = Integer.valueOf(part[0]);
                    String username = part[1];
                    String password = part[2];
                    String name = part[3];
                    Integer age = Integer.valueOf(part[4]);
                    LocalDateTime updateTime = LocalDateTime.parse(part[5], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    return new User(id, username, password, name, age, updateTime);
                }
        ).toList();

        for (String line : lines) {
            String[] part = line.split(",");
            System.out.println("username:" + part[1]);
            System.out.println("password:" + part[2]);
            System.out.println("name:" + part[3]);
            System.out.println("age:" + part[4]);
            System.out.println("UpdateTime:" + part[5]);
        }

        return userList;
    }

}
