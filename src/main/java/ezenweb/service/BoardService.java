package ezenweb.service;

import example.day06.NoteDto;
import example.day06.NoteEntity;
import ezenweb.model.dto.BoardDto;
import ezenweb.model.dto.MemberDto;
import ezenweb.model.dto.PageDto;
import ezenweb.model.entity.BoardEntity;
import ezenweb.model.entity.MemberEntity;
import ezenweb.model.repository.BoardEntityRepository;
import ezenweb.model.repository.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired private BoardEntityRepository boardEntityRepository;
    @Autowired private MemberService memberService;
    @Autowired private MemberEntityRepository memberEntityRepository;
    // 1.
    @Transactional // 함수내 여럿 SQL를 하나의 일처리 단위로 처리
    public boolean write( BoardDto boardDto  ){
        /*
            MemberEntity                                BoardEntity
             [p]  mno                                       [p] bno
                                <--- 단방향 --               [f] mno    @ManyToOne  MemberEntity

                                ---- 양방향 -->
             @OneToMany(mappedBy = "memberEntity")
             []List<BoardEntity> boardEntityList

         */
        // 1. FK 키의 엔티티를 찾는다.
        // ================================= 단방향 ================================================= //
            // [ FK로 사용할 PK를 알고 있어야 있어야한다. 세션,매개변수 가져오기 ]
            // 1. 예 ) 로그인된 회원의 pk번호 호출

                     // ============= 로그인된 멤버 엔티티 찾기 ============= //
        MemberDto loginDto = memberService.getMember();
        if( loginDto == null  ) { return  false; }
            // memberService.getMember().getMno();
            // 2. 회원pk번호를 가지고 pk엔티티 찾기
        Optional<MemberEntity> memberEntityOptional = memberEntityRepository.findById( loginDto.getMno()  );
            // 3. 유효성검사 [ 로그인이 안된상태 글쓰기 실패 ]
        if( !memberEntityOptional.isPresent() ){ return false; }
                     // ============= 로그인된 멤버 엔티티 찾기 end ============= //

            // 4. 단방향 저장  [ 게시물 엔티티에 회원엔티티 넣어주기 ]
                // 1. 게시물 생성 [ fk에 해당하는 레코드 생성 ]
                        // ============= 게시물  엔티티 등록  ============= //
        BoardEntity boardEntity  = boardEntityRepository.save( boardDto.saveToEntity() );
                        // ============= 게시물  엔티티 등록 end  ============= //
                // 2. 생성된 게시물에 작성자엔티티 넣어주기 [ fk 넣어주기 ]
        boardEntity.setMemberEntity( memberEntityOptional.get() );
        // ================================= 단방향 end ================================================= //
        // ================================= 양방향 ================================================= //
        // 5. 양방향 저장 [ 회원엔티티에 게시물 엔티티 넣어주기 ]
        memberEntityOptional.get().getBoardEntityList().add( boardEntity );
        // ================================= 양방향 end ================================================= //
        if( boardEntity.getBno() >= 1) { return true; } return false;
    }
    // 2.
    @Transactional
    public PageDto getAll( int page , String key , String keyword ){
        // 페이징처리
        Pageable pageable = PageRequest.of( page-1 , 2   );
        // 1. 모든 게시물 호출한다.
        //Page<BoardEntity> boardEntities = boardEntityRepository.findAll( pageable );
        Page<BoardEntity> boardEntities = boardEntityRepository.findBySearch( key , keyword , pageable );

        // 2.  List<BoardEntity> --> List<BoardDto>
        List<BoardDto> boardDtos = new ArrayList<>();
        boardEntities.forEach( e -> {   boardDtos.add( e.allToDto() );  });
            // 3. 총 페이지수
        int totalPages = boardEntities.getTotalPages();
            // 4. 총 게시물수
        Long totalCount = boardEntities.getTotalElements(); // 요소 : 게시물 1개
            // 5. pageDto 구성해서 axios에게 전달
        PageDto pageDto = PageDto.builder()
                .boardDtos( boardDtos )
                .totalCount( totalCount )
                .totalPages( totalPages )
                .build();
        return pageDto;  // 3. 리턴
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

    // 5  [2-2] 개별게시물출력
    @Transactional
    public BoardDto doGet( int bno ){
        // 1. pk번호에 해당하는 엔티티 찾기
        Optional<BoardEntity> boardEntityOptional
                = boardEntityRepository.findById( bno );
        // 2. 검색된 엔티티가 존재하면
        if( boardEntityOptional.isPresent() ){
            // 3. 엔티티 꺼내기
            BoardEntity boardEntity = boardEntityOptional.get();
            // 4. 엔티티 -> dto 변환
            BoardDto boardDto = boardEntity.allToDto();
            // 5. dto 반환
            return boardDto;
        }
        return null;
    }


















} // class end



// * JPA 페이징처리 라이브러리 지원
// 1. Pageable : 페이지 인터페이스( 구현체:구현[추상메소드(인터페이스 가지는 함수)를 구현]해주는 객체 )
// 사용이유 : Repository인터페이스가 페이징처리할때 사용하는 인터페이스
// 2. PageRequest : 페이지 구현체
// of( 현재페이지 , 페이지별게시물수
// 현재페이지 : 0 부터 시작
// 페이지별게시물수  : 만약에 2일때는 페이지 마다 게시물 2개씩 출력
// 3. Page : list와 마찬가지로 페이징결과의 여러개 객체를 저장하는 타입[인터페이스]
// list 와 다르게 추가적으로 함수 지원
// 1. .getTotalPages()










