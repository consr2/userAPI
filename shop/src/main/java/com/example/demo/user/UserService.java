package com.example.demo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
	//이렇게 할 시 페이징 처리를 할 수 없다. db입장에서는 조회되는 양이
	//뻥튀기 되기 때문에... 처리는 가능하지만 모든 결과를 메모리에 저장해버리는 방식이라
	//용량이 커지면 메모리가 터진다.
	//인라고 설명을 들었는데?? 지금 sysout으로 찍어보니 1개만 출력된다?
	//뭐지????? 메모리에 전부 저장되면 2개 출력되야하는게 아닌가? 일단 보류
	public List<SiteUser> findAllByQuery(){
		String sql = "select distinct u from SiteUser u" +
					" join fetch u.orderList o" +
					" join fetch o.book" + 
					" order by u.id desc";
		List<SiteUser> userList = em.createQuery(sql, SiteUser.class)
				.setFirstResult(0)
				.setMaxResults(1)
				.getResultList();
		
		return userList;
	}
	
	//이렇게 하면 orderList의 boo정보를 위한 쿼리가 다시 날라간다.
	//아래의 장 : 페이징 처리 가능
	//		단: 쿼리가 좀 더 많이 나감
	public List<SiteUser> findAllByQuery2(){
		String sql = "select distinct u from SiteUser u";
		List<SiteUser> userList = em.createQuery(sql, SiteUser.class)
				.setFirstResult(0)
				.setMaxResults(100)
				.getResultList();
		return userList;
	}
	
}
