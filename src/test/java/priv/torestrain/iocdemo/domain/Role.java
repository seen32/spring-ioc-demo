package priv.torestrain.iocdemo.domain;

import priv.torestrain.iocdemo.annotation.Component;

/**
 * @author Mr.xiao
 * @version 1.0
 * @description Role
 * @date 2021/11/23 9:44
 */
@Component
public class Role {
    String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(512);
        sb.append("Role {");
        sb.append(super.toString());
        sb.append("\n    roleName=").append(this.roleName);
        sb.append("\n}");
        return sb.toString();
    }
}
