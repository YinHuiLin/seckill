package com.lins.seckill.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lins.seckill.entity.Result;
import com.lins.seckill.entity.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserUtil
 * @Description TODO
 * @Author lin
 * @Date 2021/2/19 16:39
 * @Version 1.0
 **/
public class UserUtil {
    private static void createUser(int count) throws Exception {
        List<User> users = new ArrayList<>(count);
        for(int i=0;i<count;i++){
            User user = new User();
            user.setId((int) (13000000000L+i));
            user.setUserName("user"+i);
            user.setSalt("1a2b3c4d");
            user.setPassword(MD5Util.inputPassToDBPass("password","1a2b3c4d"));
            users.add(user);
        }
        System.out.println("Create user...");
        //insertDB(users);
        System.out.println("Insert to db...");
        loginTOGetUserTicket(users);
        System.out.println("write to file");

    }

    private static Connection getConn() throws Exception {
        String url = "jdbc:mysql://localhost:3306/seckill?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8";
        String userName = "root";
        String password = "341282";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        return DriverManager.getConnection(url, userName, password);
    }


    private static void insertDB(List<User> users) throws Exception {
        //插入数据库
        Connection connection = getConn();
        String sql = "insert into t_user(nickname,salt,password,id) values(?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getSalt());
            pstmt.setString(3, user.getPassword());
            pstmt.setLong(4, user.getId());
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        // pstmt.clearParameters();
        pstmt.close();
        connection.close();
    }

    /**
     * 登录，并将userticket写入文件
     * @param users
     */
    private static void loginTOGetUserTicket(List<User> users) throws IOException {
        //登录，获取userTicket
        String urlString = "http://localhost:8080/user/login";
        File file = new File("D:\\userTicket.txt");
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        file.createNewFile();
        raf.seek(0);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            URL url = new URL(urlString);
            HttpURLConnection co = (HttpURLConnection) url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            OutputStream out = co.getOutputStream();
            String params = "mobile=" + user.getId() + "&password=" + MD5Util.inputPassToFormPass("password");
            out.write(params.getBytes());
            out.flush();
            InputStream inputStream = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte buff[] = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buff)) >= 0) {
                bout.write(buff, 0, len);
            }
            inputStream.close();
            bout.close();
            String response = new String(bout.toByteArray());
            ObjectMapper mapper = new ObjectMapper();
            Result result = mapper.readValue(response, Result.class);
            String userTicket = ((String) result.getObj());
            System.out.println(userTicket);
            System.out.println("create userTicket : " + user.getId());
            String row = user.getId() + "," + userTicket;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file : " + user.getId());
        }
        raf.close();
        System.out.println("over ");
    }

    public static void main(String[] args) {
        try {
            createUser(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
