package Iniro.kTrip.repository;


import Iniro.kTrip.domain.Member;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer>
{
    Boolean existsById(String id);
    Member findById(String id);
    Boolean existsByRefreshToken(String refreshToken);
    Member findByRefreshToken(String refreshtoken);
    @Transactional
    void deleteByRefreshToken(String refreshToken);



}
