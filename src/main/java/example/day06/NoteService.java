package example.day06;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Service 사용처 Controller
@Service
public class NoteService {
    @Autowired
    private NoteEntityRepository noteEntityRepository;

    // 1.C
    @Transactional
    public boolean bWrite( NoteDto noteDto  ){
        // 1. dto--> 엔티티 변경
        noteEntityRepository.save( noteDto.toEntity() );
        return false;
    }
    // 2.R
    @Transactional
    public List<NoteDto> bList(){
        // 1. 모든 엔티티 리스트를 호출하기
        List<NoteEntity> entities = noteEntityRepository.findAll();
        // 2. 모든 엔티티 리스트->  dto 리스트 변환
        List<NoteDto> noteDtos = new ArrayList<>();
        // 3. 엔티티 -> dto 변경 후 리스트에 담기
        entities.forEach( e ->{ noteDtos.add( e.toDto());  });
        return noteDtos;
    }
    // 3.U
    // @Transactional : 해당 주입된 함수내에서 사용중인 SQL를 트랜잭션 단위로 적용
    @Transactional // 트랜잭션 : 하나/여럿 작업들을 묶어서 최소단위 업무처리
    public boolean bUpdate( NoteDto noteDto ){
        // 1. 수정할 pk번호에 해당하는 엔티티 찾기. ( 엔티티를 포장[Optional안전 = null  방지 ]해서 반환 )
        Optional<NoteEntity> optionalNoteEntity
                = noteEntityRepository.findById( noteDto.getNo() );
        // 2. 포장내 내용물이 있는지 체크 = 안전하게 검토.. 과정        .isPresent()
        if( optionalNoteEntity.isPresent() ) {
            // 3. 포장내 내용물 꺼내기 = 포장된곳에서 엔티티 꺼내는 과정   .get();
            NoteEntity noteEntity = optionalNoteEntity.get();
            // 4. 수정 : 별도의 수정함수가 없고요.. 엔티티 객체 의 필드를 수정하면 DB도 같이 수정 [ 매핑이 된 상태니까 ]
            noteEntity.setTitle(noteDto.getTitle());    // update
            noteEntity.setWriter(noteDto.getWriter());  // update
        }
        return false;
    }
    // 4.D
    @Transactional
    public boolean bDelete( int no ){
        noteEntityRepository.deleteById( no ); // 1. 삭제할 pk번호를 대입하여 엔티티 삭제
        return false;
    }

}
