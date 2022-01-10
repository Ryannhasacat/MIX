package com.mix.lambda.stream;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class UserFactory {


    // 部门服务, rpc 接口
    @Resource
    private DepartmentService departmentService;

    // 主管服务, rpc 接口
    @Resource
    private SupervisorService supervisorService;

    // 权限服务, rpc 接口
    @Resource
    private PermissionService permissionService;

    public User buildUser(long uid) {
        Lazy<String> departmentLazy = Lazy.of(() -> departmentService.getDepartment(uid));
        // 通过部门获得主管
        // department -> supervisor
        Lazy<Long> supervisorLazy = departmentLazy.map(SupervisorService::getSupervisor);
        // 通过部门和主管获得权限
        // department, supervisor -> permission
        Lazy<Set<String>> permissionsLazy = departmentLazy.flatMap(department ->
                supervisorLazy.map(
                        supervisor -> permissionService.getPermissions(department, supervisor)
                )
        );

        User user = new User();
        user.setUid(uid);
        user.setDepartment(departmentLazy);
        user.setSupervisor(supervisorLazy);
        user.setPermissions(permissionsLazy);
        return user;
    }
}
