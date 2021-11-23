package priv.torestrain.iocdemo;

/**
 * @author Mr.xiao
 * @version 1.0
 * @description BeanDefinition
 * @date 2021/11/22 16:39
 */
public class BeanDefinition {
    private String beanName;
    private Class<?> beanClass;

    public BeanDefinition(String beanName, Class<?> beanClass) {
        this.beanName = beanName;
        this.beanClass = beanClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }
}
