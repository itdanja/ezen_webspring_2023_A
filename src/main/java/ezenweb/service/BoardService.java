package ezenweb.service;

import example.day06.NoteDto;
import example.day06.NoteEntity;
import ezenweb.model.dto.BoardDto;
import ezenweb.model.entity.BoardEntity;
import ezenweb.model.repository.BoardEntityRepository;
import ezenweb.model.repository.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardEntityRepository boardEntityRepository;

    @Autowired
    private MemberService memberService;

    // 1.
    @Transactional // 함수내 여럿 SQL를 하나의 일처리 단위로 처리
    public boolean write( BoardDto boardDto ){

        // 로그인 된 회원번호 가져오기



        // 1. dto -> entity 변환후 저장된 엔티티 반환
        BoardEntity boardEntity
                = boardEntityRepository.save( boardDto.saveToEntity() );
        // 2.

        // 양방향 관계

        if( boardEntity.getBno() >= 1) { return true; } return false;
    }
    // 2.
    @Transactional
    public List<BoardDto> getAll(){
        // 1. 모든 게시물 호출한다.
        List<BoardEntity> boardEntities = boardEntityRepository.findAll();
        // 2.  List<BoardEntity> --> List<BoardDto>
        List<BoardDto> boardDtos = new ArrayList<>();
        boardEntities.forEach( e -> {   boardDtos.add( e.allToDto() );  });
        // 3.
        return boardDtos;
    }
    // 3.
    @Transactional // !!!!!! 필수 : 수정은 하나의 함수에 sql 여러개 실행될 경우가 있어서 !!!!!!!!!
    public boolean update( BoardDto boardDto ){
        // 1. 수정할 엔티티 찾기 [ bno 해서 ]
        Optional<BoardEntity> optionalBoardEntity = boardEntityRepository.findById( boardDto.getBno() );
        // 2. 만약에 수정할 엔티티가 존재하면
        if( optionalBoardEntity.isPresent() ) {
            // 3. 엔티티 꺼내기
            BoardEntity boardEntity = optionalBoardEntity.get();
            // 4. 엔티티 객체를 수정하면 테이블내 레코드도 같이 수정 [ * 매핑 => ORM ]
            boardEntity.setBcontent( boardDto.getBcontent() );
            boardEntity.setBtitle( boardDto.getBtitle() );
            boardEntity.setBfile( boardDto.getBfile() );
            return true;
        }
        return false;
    }
    // 4
    @Transactional
    public boolean delete( int bno){
        // 1. 엔티티 호출
        Optional<BoardEntity> optionalBoardEntity = boardEntityRepository.findById( bno );
        // 2. 엔티티 가 호출 되었는지 확인
        if(optionalBoardEntity.isPresent() ){
            // 3. 삭제
            boardEntityRepository.deleteById( bno );
            return true;
        }
        return false;
    }
}
