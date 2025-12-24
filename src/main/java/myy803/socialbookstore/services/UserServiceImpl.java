package myy803.socialbookstore.services;

import java.util.List;
import java.util.Optional;

import myy803.socialbookstore.datamodel.BookCategory;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.UserProfileDto;
import myy803.socialbookstore.mappers.BookAuthorMapper;
import myy803.socialbookstore.mappers.BookCategoryMapper;
import myy803.socialbookstore.mappers.UserProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import myy803.socialbookstore.datamodel.User;
import myy803.socialbookstore.mappers.UserMapper;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private BookCategoryMapper bookCategoryMapper;

	@Autowired
	private UserProfileMapper userProfileMapper;

	@Autowired
	private BookAuthorMapper bookAuthorMapper;

	@Autowired
	private BookCategoryMapper bookCategoriesMapper;


	@Override
	public void saveUser(User user) {
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userMapper.save(user);	
    }

	@Override
	public boolean isUserPresent(User user) {
		Optional<User> storedUser = userMapper.findByUsername(user.getUsername());
		return storedUser.isPresent();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 return userMapper.findByUsername(username).orElseThrow(
	                ()-> new UsernameNotFoundException(
	                        String.format("USER_NOT_FOUND", username)
	                ));
	}
	
	public User findById(String username) {
		return userMapper.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException(
                        String.format("USER_NOT_FOUND", username)
                ));
	}

	public List<BookCategory> retrieveCategories(){
		List<BookCategory> categories = bookCategoryMapper.findAll();
		return categories;
	}

	public UserProfileDto retrieveUserProfile(String username){

		Optional<UserProfile> optUserProfile = userProfileMapper.findById(username);
		UserProfile userProfile = null;
		UserProfileDto userProfileDto = null;
		if(optUserProfile.isPresent()) {
			userProfile = optUserProfile.get();
			userProfileDto = userProfile.buildProfileDto();
		} else {
			userProfileDto = new UserProfileDto();
			userProfileDto.setUsername(username);
		}
		return userProfileDto;
	}

	public void saveUserProfile(UserProfileDto userProfileDto){

		Optional<UserProfile> optUserProfile = userProfileMapper.findById(userProfileDto.getUsername());
		UserProfile userProfile = null;
		if(optUserProfile.isPresent())
			userProfile = optUserProfile.get();
		else
			userProfile = new UserProfile();

		userProfileDto.buildUserProfile(userProfile, bookAuthorMapper, bookCategoriesMapper);

		userProfileMapper.save(userProfile);
	}

}
