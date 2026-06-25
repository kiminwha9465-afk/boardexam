package com.example.board0625.service;

import com.example.board0625.dto.BoardDTO;
import com.example.board0625.dto.BoardImageDTO;
import com.example.board0625.dto.PageDTO;
import com.example.board0625.mapper.BoardImageMapper;
import com.example.board0625.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper      boardMapper;
    private final BoardImageMapper boardImageMapper;

    public void write(BoardDTO board) {
        boardMapper.insert(board);
    }

    public BoardDTO getById(Integer id) {
        boardMapper.updateViewCount(id);
        return boardMapper.selectById(id);
    }

    public BoardDTO getByIdNoCount(Integer id) {
        return boardMapper.selectById(id);
    }

    public List<BoardDTO> getAll(PageDTO page) {
        return boardMapper.selectAll(page);
    }

    public int getTotalCount(PageDTO page) {
        return boardMapper.countAll(page);
    }

    public List<BoardDTO> getByMemberId(Integer memberId) {
        return boardMapper.selectByMemberId(memberId);
    }

    public List<BoardDTO> getByMemberIdPaged(Integer memberId, int page, int size) {
        return boardMapper.selectByMemberIdPaged(memberId, (page - 1) * size, size);
    }

    public int countByMemberId(Integer memberId) {
        return boardMapper.countByMemberId(memberId);
    }

    public void update(BoardDTO board) {
        boardMapper.update(board);
    }

    public void delete(Integer id) {
        boardMapper.delete(id);
    }

    public void saveImages(Integer boardId, List<String> imagePaths) {
        for (int i = 0; i < imagePaths.size(); i++) {
            BoardImageDTO img = new BoardImageDTO();
            img.setBoardId(boardId);
            img.setImagePath(imagePaths.get(i));
            img.setSortOrder(i);
            boardImageMapper.insert(img);
        }
    }

    public List<BoardImageDTO> getImages(Integer boardId) {
        return boardImageMapper.selectByBoardId(boardId);
    }

    public BoardImageDTO getImageById(Integer id) {
        return boardImageMapper.selectById(id);
    }

    public void deleteImages(Integer boardId) {
        boardImageMapper.deleteByBoardId(boardId);
    }

    public void deleteImageById(Integer id) {
        boardImageMapper.deleteById(id);
    }
}
