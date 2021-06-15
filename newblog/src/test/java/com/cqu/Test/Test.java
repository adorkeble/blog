package com.cqu.Test;

import com.cqu.newblog.MainApplication;
import com.cqu.newblog.utils.Utils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @version 1.0
 * @author:罗天天
 * @date 2021/6/8  19:36
 */
@SpringBootTest(classes = {MainApplication.class})
@RunWith(SpringRunner.class)
public class Test {


    @org.junit.Test
    public void getpassword(){
        String dbPassword = Utils.getDBPassword("123", Utils.salt);
        System.out.println(dbPassword);

    }
}
