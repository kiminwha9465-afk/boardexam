package com.example.board0625.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Integer id;
    private String title;
    private String content;
    private Integer memberId;
    private String memberNickname;
    private int viewCount;
    private int commentCount;
    private String createdAt;
    private String updatedAt;
}
