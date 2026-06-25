package com.example.board0625.mapper;

import com.example.board0625.dto.BoardDTO;
import com.example.board0625.dto.PageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    void insert(BoardDTO board);
    BoardDTO selectById(Integer id);
    List<BoardDTO> selectAll(PageDTO page);
    int countAll(PageDTO page);
    List<BoardDTO> selectByMemberId(Integer memberId);
    List<BoardDTO> selectByMemberIdPaged(@Param("memberId") Integer memberId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);
    int countByMemberId(Integer memberId);
    void update(BoardDTO board);
    void delete(Integer id);
    void updateViewCount(Integer id);
}
