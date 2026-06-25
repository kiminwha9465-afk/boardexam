package com.example.board0625.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Integer id;
    private Integer boardId;
    private Integer memberId;
    private String memberNickname;
    private String content;
    private String createdAt;
    private String boardTitle;
}
