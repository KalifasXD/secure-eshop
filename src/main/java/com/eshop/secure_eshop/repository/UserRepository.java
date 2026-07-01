package com.eshop.secure_eshop.repository;
import com.eshop.secure_eshop.model.EshopUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<EshopUser, Long> {

    EshopUser findByUsername(String Username);

}
