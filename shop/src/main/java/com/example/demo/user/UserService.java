package com.example.demo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	
	@Transactional
	public Integer save(UserForm userF) {
		SiteUser user = new SiteUser();
		user.setName(userF.getName());
		user.setAge(userF.getAge());
		
		userRepository.save(user);
		return user.getId();
	}


	public SiteUser findByid(Integer id) {
		Optional<SiteUser> user = userRepository.findById(id);
		return user.get();
	}

	@Transactional
	public Integer update(Integer id, @Valid UserForm userF) {
		SiteUser user = userRepository.findById(id).get();
		
		user.setName(userF.getName());
		if(userF.getAge() != null) {
			user.setAge(userF.getAge());
		}
		
		userRepository.save(user);
		return user.getId();
	}
	
	public List<SiteUser> findAll(){
		return userRepository.findAll();
	}
	
	
}
