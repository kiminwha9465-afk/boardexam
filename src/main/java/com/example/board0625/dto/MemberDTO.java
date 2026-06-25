package com.example.board0625.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Integer id;
    private String memberId;
    private String password;
    private String email;
    private String nickname;
    private String createdAt;
}
