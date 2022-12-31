package com.example.demo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.order.OrderDto;
import com.example.demo.order.OrderDto2;
import com.example.demo.order.OrderService;
import com.example.demo.user.dto.UserDto2;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final EntityManager em;
	
	//유저 등록
	@Transactional
	public Integer save(UserForm userF) {
		SiteUser user = new SiteUser();
		user.setName(userF.getName());
		user.setAge(userF.getAge());
		
		userRepository.save(user);
		return user.getId();
	}


	//유저 1명 찾기
	public SiteUser findByid(Integer id) {
		Optional<SiteUser> user = userRepository.findById(id);
		return user.get();
	}

	//유저 정보 수정
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
	
	
	//유저 리스트 불러오기
	public List<SiteUser> findAll(){
		return userRepository.findAll();
	}
	
	//유저이름으로 찾기
	public SiteUser findByName(String name) {
		return userRepository.findByName(name).get();
	}
	
	//유저의 주문목록까지 가져오기
	//오더리스트의 갯수만큼 유저가 중복된다.
	public List<SiteUser> findAllByQuery(){
		String sql = "select distinct u from SiteUser u" +
					" left join fetch u.orderList o" +
					" left join fetch o.book";
		List<SiteUser> userList = em.createQuery(sql, SiteUser.class)
				.setFirstResult(0)
				.setMaxResults(10)
				.getResultList();
		
		return userList;
	}
	
	//유저가져오기 1번 주문 가져오기 1번으로
	//두 정보를 메모리에서 합쳐서 사용하기
	public List<SiteUser> findAllByQuery2(){
		String sql = "select u from SiteUser u";
		List<SiteUser> userList = em.createQuery(sql, SiteUser.class)
				.getResultList();
		
		return userList;
	}
	
	
	
	
	
	
}
