package myy803.socialbookstore.mappers;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.socialbookstore.datamodel.UserProfile;


public interface UserProfileMapper extends JpaRepository<UserProfile, String> {
	UserProfile findByUsername(String username);
}
