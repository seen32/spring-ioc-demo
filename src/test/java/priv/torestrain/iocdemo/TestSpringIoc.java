package priv.torestrain.iocdemo;

import org.junit.jupiter.api.Test;

/**
 * @author Mr.xiao
 * @version 1.0
 * @description TestSpringIoc
 * @date 2021/11/23 11:02
 */
public class TestSpringIoc {
    @Test
    public void testIoc() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("priv.torestrain.iocdemo");
        // applicationContext.findBeanDefinitions("priv.torestrain.iocdemo");
        System.out.println(applicationContext.getBean("user").toString());
        System.out.println(applicationContext.getBean("role"));
    }
}
