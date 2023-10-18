package ezenweb.service;

import ezenweb.model.dto.BoardDto;
import ezenweb.model.repository.BoardEntityRepository;
import ezenweb.model.repository.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardEntityRepository boardEntityRepository;
    // 1.
    @Transactional // 함수내 여럿 SQL를 하나의 일처리 단위로 처리
    public boolean write( BoardDto boardDto ){
        return true;
    }
    // 2.
    @Transactional
    public List<BoardDto> getAll(){
        return null;
    }
    // 3.
    @Transactional
    public boolean update( BoardDto boardDto ){
        return true;
    }
    // 4
    @Transactional
    public boolean delete( int bno){
        return true;
    }
}
