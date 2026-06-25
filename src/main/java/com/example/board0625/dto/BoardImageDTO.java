package com.example.board0625.dto;

import lombok.Data;

@Data
public class BoardImageDTO {
    private Integer id;
    private Integer boardId;
    private String imagePath;
    private int sortOrder;
}
