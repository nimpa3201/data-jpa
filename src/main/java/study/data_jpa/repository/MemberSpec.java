package study.data_jpa.repository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import study.data_jpa.entity.Member;
import study.data_jpa.entity.Team;

public class MemberSpec {

    /**
     * 팀 이름으로 필터링하는 Specification.
     * teamName이 비어있으면(null/blank) 조건을 추가하지 않음(return null).
     */
    public static Specification<Member> teamName(String teamName) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(teamName)) {
                return null; // 조건 미적용
            }
            // 회원(Member) ↔ 팀(Team) 조인
            Join<Member, Team> team = root.join("team", JoinType.INNER);
            return builder.equal(team.get("name"), teamName);
        };
    }

    /**
     * (옵션) 이름에 키워드가 포함되는지 LIKE 검색
     */
    public static Specification<Member> usernameContains(String keyword) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(keyword)) {
                return null;
            }
            return builder.like(root.get("username"), "%" + keyword + "%");
        };
    }

    /**
     * (옵션) 나이가 기준 이상인지 필터
     */
    public static Specification<Member> ageGoe(Integer age) {
        return (root, query, builder) -> {
            if (age == null) {
                return null;
            }
            return builder.greaterThanOrEqualTo(root.get("age"), age);
        };
    }
}
