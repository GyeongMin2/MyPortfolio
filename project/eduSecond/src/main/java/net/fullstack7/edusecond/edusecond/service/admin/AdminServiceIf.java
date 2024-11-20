package net.fullstack7.edusecond.edusecond.service.admin;

import net.fullstack7.edusecond.edusecond.dto.AdminLoginDTO;  

public interface AdminServiceIf {
    boolean loginAdmin(AdminLoginDTO loginDTO);
}
