package com.example.board0625.mapper;

import com.example.board0625.dto.BoardImageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardImageMapper {
    void insert(BoardImageDTO image);
    List<BoardImageDTO> selectByBoardId(Integer boardId);
    BoardImageDTO selectById(Integer id);
    void deleteByBoardId(Integer boardId);
    void deleteById(Integer id);
}
