package com.example.board0625.mapper;

import com.example.board0625.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void insert(MemberDTO member);
    MemberDTO selectById(Integer id);
    MemberDTO selectByMemberId(String memberId);
    void update(MemberDTO member);
    void delete(Integer id);
    boolean existsByMemberId(String memberId);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}
