package net.fullstack7.edusecond.edusecond.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.fullstack7.edusecond.edusecond.domain.AdminVO;
import net.fullstack7.edusecond.edusecond.dto.AdminLoginDTO;
import net.fullstack7.edusecond.edusecond.mapper.AdminMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AdminServiceImpl implements AdminServiceIf {
    
    private final AdminMapper adminMapper;
    
    @Override
    public boolean loginAdmin(AdminLoginDTO loginDTO) {
        AdminVO admin = adminMapper.selectAdminById(loginDTO.getAdminId());
        
        if(admin == null) {
            return false;
        }
        
        return loginDTO.getAdminPw().equals(admin.getAdminPw());
    }
} 