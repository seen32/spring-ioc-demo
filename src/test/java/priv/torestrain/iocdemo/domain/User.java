package priv.torestrain.iocdemo.domain;

import priv.torestrain.iocdemo.annotation.Autowired;
import priv.torestrain.iocdemo.annotation.Component;
import priv.torestrain.iocdemo.annotation.Value;

/**
 * @author Mr.xiao
 * @version 1.0
 * @description User
 * @date 2021/11/22 17:03
 */
@Component
public class User {
    @Value("zhangsan")
    private String userName;
    @Value("12345")
    private String password;

    @Autowired
    // @Qualifier("role")
    private Role role;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(512);
        sb.append("User {");
        sb.append(super.toString());
        sb.append("\n    userName=").append(this.userName);
        sb.append("\n    password=").append(this.password);
        sb.append("\n    role=").append(this.role);
        sb.append("\n}");
        return sb.toString();
    }
}
