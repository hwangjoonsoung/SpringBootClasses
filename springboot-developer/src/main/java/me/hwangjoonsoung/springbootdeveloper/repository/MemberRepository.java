package me.hwangjoonsoung.springbootdeveloper.repository;

import me.hwangjoonsoung.springbootdeveloper.dto.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

}
