package myy803.socialbookstore.mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.socialbookstore.datamodel.User;


public interface UserMapper extends JpaRepository<User, Integer> {
	
	Optional<User> findByUsername(String username);

}
