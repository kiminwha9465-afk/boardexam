package com.example.board0625.service;

import com.example.board0625.dto.CommentDTO;
import com.example.board0625.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    public void write(CommentDTO comment) {
        commentMapper.insert(comment);
    }

    public List<CommentDTO> getByBoardId(Integer boardId) {
        return commentMapper.selectByBoardId(boardId);
    }

    public List<CommentDTO> getByBoardIdPaged(Integer boardId, int page, int size) {
        return commentMapper.selectByBoardIdPaged(boardId, (page - 1) * size, size);
    }

    public int countByBoardId(Integer boardId) {
        return commentMapper.countByBoardId(boardId);
    }

    public List<CommentDTO> getByMemberIdPaged(Integer memberId, int page, int size) {
        return commentMapper.selectByMemberIdPaged(memberId, (page - 1) * size, size);
    }

    public int countByMemberId(Integer memberId) {
        return commentMapper.countByMemberId(memberId);
    }

    public CommentDTO getById(Integer id) {
        return commentMapper.selectById(id);
    }

    public void update(CommentDTO comment) {
        commentMapper.update(comment);
    }

    public void delete(Integer id) {
        commentMapper.delete(id);
    }
}
