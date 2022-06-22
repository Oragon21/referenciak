package alf.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import alf.webshop.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}