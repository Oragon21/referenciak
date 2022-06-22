package alf.webshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import alf.webshop.domain.ERole;
import alf.webshop.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
	Optional<Role> findByName(ERole name);
	boolean existsByName(ERole name);
}
