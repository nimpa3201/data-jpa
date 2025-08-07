package study.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.data_jpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {

    // 메서드 이름으로 쿼리 생성
    List<Member> findByUsernameAndAgeGreaterThan(String username,int age);
    List<Member> findTop3HelloBy();

}
