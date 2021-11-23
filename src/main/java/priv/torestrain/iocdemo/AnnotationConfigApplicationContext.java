package priv.torestrain.iocdemo;

import priv.torestrain.iocdemo.annotation.*;
import priv.torestrain.iocdemo.util.ClazzUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Mr.xiao
 * @version 1.0
 * @description AnnotationConfigApplicationContext
 * @date 2021/11/22 16:42
 */
public class AnnotationConfigApplicationContext {
    private Map<String, Object> ioc = new HashMap<>();
    private List<String> beanNames = new ArrayList<>();

    public AnnotationConfigApplicationContext(String pack) {
        // 获取所有组件
        Set<BeanDefinition> beanDefinitions = findBeanDefinitions(pack);
        // 创建组件实例
        createObject(beanDefinitions);

        autowireObject(beanDefinitions);
    }

    public Set<BeanDefinition> findBeanDefinitions(String pack) {
        Set<BeanDefinition> beanDefinitions = new HashSet<>();
        // 获取包下的所有类
        Set<Class<?>> classes = ClazzUtil.getClasses(pack);
        Iterator<Class<?>> iterator = classes.iterator();
        while (iterator.hasNext()) {
            Class<?> clazz = iterator.next();
            Component annotation = clazz.getAnnotation(Component.class);
            if (annotation != null) {
                String beanName = annotation.value();
                if ("".equals(beanName)) {
                    // 获取类名首字母小写
                    beanName = clazz.getSimpleName().toLowerCase();
                    // String className = clazz.getName().replaceAll(clazz.getPackage().getName() + ".", "");
                    // beanName = className.substring(0, 1).toLowerCase()+className.substring(1);
                }
                beanDefinitions.add(new BeanDefinition(beanName, clazz));
                beanNames.add(beanName);
            }
        }
        return beanDefinitions;
    }

    public void createObject(Set<BeanDefinition> beanDefinitions) {
        Iterator iterator = beanDefinitions.iterator();
        while (iterator.hasNext()) {
            BeanDefinition beanDefinition = (BeanDefinition) iterator.next();
            Class<?> clazz = beanDefinition.getBeanClass();
            String beanName = beanDefinition.getBeanName();

            try {
                Object object = clazz.getConstructor().newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    Value valueAnnotation = field.getAnnotation(Value.class);
                    if (valueAnnotation != null) {
                        // 获取Value注解的值
                        String value = valueAnnotation.value();
                        String fieldName = field.getName();
                        String methodeName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

                        // 获取对应Setter方法
                        Method declaredMethod = clazz.getDeclaredMethod(methodeName, field.getType());

                        // TODO 数据类型转化
                        declaredMethod.invoke(object, value);
                    }
                }
                ioc.put(beanDefinition.getBeanName(), object);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
    }

    public void autowireObject(Set<BeanDefinition> beanDefinitions) {
        Iterator iterator = beanDefinitions.iterator();
        while (iterator.hasNext()) {
            BeanDefinition beanDefinition = (BeanDefinition) iterator.next();
            Class<?> clazz = beanDefinition.getBeanClass();
            String beanName = beanDefinition.getBeanName();

            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                // 判断是否有Autowired注解
                Autowired autowired = declaredField.getAnnotation(Autowired.class);
                if (autowired != null) {
                    Qualifier qualifier = declaredField.getAnnotation(Qualifier.class);
                    Object obj = null;
                    if (qualifier != null) {
                        String value = qualifier.value();
                        obj = getBean(value);
                    } else {
                        String name = declaredField.getType().getSimpleName();
                        String objName = name.substring(0, 1).toLowerCase() + name.substring(1);
                        obj = getBean(objName);
                    }
                    Object bean = ioc.get(beanName);

                    try {
                        String methodName = "set" + declaredField.getType().getSimpleName();
                        Method declaredMethod = clazz.getDeclaredMethod(methodName, declaredField.getType());
                        try {
                            declaredMethod.invoke(bean, obj);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                }
            }


        }
    }

    public Object getBean(String beanName) {
        return ioc.get(beanName);
    }
}
