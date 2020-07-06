package com.example.board.board.service;

import com.example.board.board.dto.BoardDto;
import com.example.board.board.dto.BoardFileDto;
import com.example.board.board.mapper.BoardMapper;
import com.example.board.common.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;


@Service
@Transactional
public class BoardServiceImpl implements BoardService{

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private BoardMapper boardMapper;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<BoardDto> selectBoardList() throws Exception {
        return boardMapper.selectBoardList();
    }

    @Override
    public void insertBoard(BoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        boardMapper.insertBoard(boardDto);
        List<BoardFileDto> list = fileUtils.parseFileInfo(boardDto.getBoardIdx(),
                multipartHttpServletRequest);
        if(ObjectUtils.isEmpty(list) == false){
            boardMapper.insertBoardFileList(list);
        }
    }

    @Override
    public BoardDto selectBoardDetail(int boardIdx) throws Exception {
        boardMapper.updateHitCount(boardIdx);
        BoardDto boardDto = boardMapper.selectBoardDetail(boardIdx);
        List<BoardFileDto> fileDtoList = boardMapper.selectBoardFileList(boardIdx);
        boardDto.setFileList(fileDtoList);
        return boardDto;
    }

    @Override
    public void updateBoard(BoardDto boardDto) throws Exception {
        boardMapper.updateBoard(boardDto);
    }

    @Override
    public void deleteBoard(int boardIdx) throws Exception {
        boardMapper.deleteBoard(boardIdx);
    }

    @Override
    public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception {
        return boardMapper.selectBoardFileInformation(idx, boardIdx);
    }
}
