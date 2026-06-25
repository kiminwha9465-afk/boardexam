package com.example.board0625.service;

import com.example.board0625.dto.MemberDTO;
import com.example.board0625.mapper.MemberMapper;
import com.example.board0625.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    public void join(MemberDTO member) {
        if (memberMapper.existsByMemberId(member.getMemberId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (memberMapper.existsByNickname(member.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
        if (memberMapper.existsByEmail(member.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        member.setPassword(PasswordUtil.hash(member.getPassword()));
        memberMapper.insert(member);
    }

    public MemberDTO login(String memberId, String password) {
        MemberDTO member = memberMapper.selectByMemberId(memberId);
        if (member == null || !member.getPassword().equals(PasswordUtil.hash(password))) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다.");
        }
        return member;
    }

    public MemberDTO getById(Integer id) {
        return memberMapper.selectById(id);
    }

    public void update(MemberDTO member) {
        if (member.getPassword() != null && !member.getPassword().isBlank()) {
            member.setPassword(PasswordUtil.hash(member.getPassword()));
        } else {
            member.setPassword(null);
        }
        memberMapper.update(member);
    }
}
