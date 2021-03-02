import com.project.entity.User;
import com.project.service.UserService;
import com.project.service.impl.UserServiceImpl;
import com.project.util.LogUtil;
import com.project.util.MailUtil;
import com.project.util.ServiceFactory;
import com.project.util.UUIDUtil;
import org.slf4j.Logger;

import java.time.LocalDateTime;

public class Test {
    private Logger logger= LogUtil.getInstance(Test.class);
    @org.junit.jupiter.api.Test
    public void test(){
        System.out.println(1);
    }
    @org.junit.jupiter.api.Test
    public void testEmail(){
        String email="916295483@qq.com";
        MailUtil.sendEmail(email,"66666");
    }
    @org.junit.jupiter.api.Test
    public void testCode(){
        System.out.println(MailUtil.createCode());
    }
    @org.junit.jupiter.api.Test
    public void testInsert(){
        User user=new User();
        user.setUserName("Lucy");//用户名去空格
        user.setEmail("666@com");
        user.setPassword("666");

        user.setRole("1");

        user.setId("2");
        user.setFileRepositoryId("2");
        user.setRegisterTime(LocalDateTime.now());
        UserService userService= (UserService) ServiceFactory.getService(new UserServiceImpl());
        if(userService.register(user)){
            logger.info("成功");
        }
        else{
            logger.info("失败");
        }

    }

}
