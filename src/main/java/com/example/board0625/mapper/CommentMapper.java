package com.example.board0625.mapper;

import com.example.board0625.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    void insert(CommentDTO comment);
    List<CommentDTO> selectByBoardId(Integer boardId);
    List<CommentDTO> selectByBoardIdPaged(@Param("boardId") Integer boardId,
                                          @Param("offset") int offset,
                                          @Param("limit") int limit);
    int countByBoardId(Integer boardId);
    CommentDTO selectById(Integer id);
    List<CommentDTO> selectByMemberIdPaged(@Param("memberId") Integer memberId,
                                           @Param("offset") int offset,
                                           @Param("limit") int limit);
    int countByMemberId(Integer memberId);
    void update(CommentDTO comment);
    void delete(Integer id);
}
