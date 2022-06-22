package alf.webshop.repository;

import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import alf.webshop.domain.Role;
import alf.webshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
	Optional<User> findByUsername(String username);
	@Transactional
	Long deleteByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	boolean existsByRolesIn(Set<Role> roles);
}