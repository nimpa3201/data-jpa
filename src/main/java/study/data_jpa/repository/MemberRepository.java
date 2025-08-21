package study.data_jpa.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> ,MemberRepositoryCustom , JpaSpecificationExecutor<Member> {

    // 메서드 이름으로 쿼리 생성
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    @Query(" select  m from Member  m where m.username = :username and m.age = :age")
        // 이름 없는 NamedQuery, 애플리케이션 로딩시 문법 오류 검사
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.data_jpa.dto.MemberDto( m.id,m.username ,t.name)from Member m join m.team t ")
    List<MemberDto> findMemberDto();


    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    List<Member> findListByUsername(String username); // 컬렉션

    Member findMemberByUsername(String username); // 단건

    Optional<Member> findOptionalMemberByUsername(String username); // 옵셔널

    @Query(value = "select m from Member m left join m.team t ",
        countQuery = " select count(m.username) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true) // 벌크 연산 끝나고 영속성 컨텍스트 초기화
    @Query("update Member m set m.age = m.age +1 where m.age>= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();


    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();


    //@EntityGraph("Member.all") 간단하면 사용
    @EntityGraph(attributePaths = {"team"}) //복잡하면 사용
    List<Member> findEntityGraphByUsername(@Param("username") String username);


    @QueryHints(value = @QueryHint( name = "org.hibernate.readonly",value = "true"))
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);

   <T> List<T> findProjectionsByUsername(@Param("username")String username,Class<T> type);

   @Query(value = "select * from member where username =?" , nativeQuery = true)
   Member findByNativeQuery(String username);


   @Query(value = " select m.member_id as id ,m.username, t.name as teamName " +
       "from member m left join team t",
       countQuery = "select count(*) from member",
       nativeQuery = true)

   Page<MemberProjection>findByNativeProjection(Pageable pageable);


}
