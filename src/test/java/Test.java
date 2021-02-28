import com.project.util.MailUtil;

public class Test {
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
}
