package practice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Ex01 {

	static class Stu{
		String name;
		
		Stu(String name){
			this.name = name;
		}
		
		@Override
		public String toString() {
			return "Stu [name=" + name + "]";
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Stu> sList = new ArrayList<>();
		sList.add(new Stu("1번입니다"));
		sList.add(new Stu("2번입니다"));
		sList.add(new Stu("3번입니다"));
		sList.add(new Stu("4번입니다"));
		sList.add(new Stu("5번입니다"));

		
		List<Stu> mm = sList.stream()
				//걸러내기
				.filter(a -> a.name.equals("2번입니다"))
				//객체 생성함(변환시키는 역할)
				.map(stu -> new Stu(stu.name))
				//컬렉션 형태로 변환
				.collect(Collectors.toList());
		
		System.out.println(mm.toString());
		
		
		
		
	}

}
