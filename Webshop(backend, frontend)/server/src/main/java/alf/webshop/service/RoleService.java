package alf.webshop.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alf.webshop.domain.ERole;
import alf.webshop.domain.Role;
import alf.webshop.repository.RoleRepository;

@Service
public class RoleService {
    Logger logger = LoggerFactory.getLogger(RoleService.class);
    
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRole(ERole role){
        return roleRepository.findByName(role);
    }

    public Role createRole(Role role) throws EntityExistsException {
        if(roleRepository.existsByName(role.getName())){
            logger.warn("Mar letezik");
            throw new EntityExistsException(String.format("Role %s already exists, creation aborted", role.getName()));
        }
        return roleRepository.save(role);
    }

    public void deleteRole(Role role){
        roleRepository.delete(role);
    }
}
