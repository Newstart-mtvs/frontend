package com.book.Member.command.domain.repository;

import com.book.Member.command.domain.aggregate.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<MemberEntity, String> {
    boolean existsByMemberEmail(String name);

}
