package ezenweb.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service // 서비스 컴포넌트 빈 등록
public class FileService {
/* 파일관련 메소드 정의 : */

    // 0. 파일 경로 [ 배포전 ]
    private String fileRootPath = "c:\\java\\";

    // 1. 업로드
    public String fileUpload(MultipartFile multipartFile ){
        // 0.유효성검사 ,
        if( multipartFile.isEmpty() ){ // 파일이 비어 있으면
            return null; // 비어 있으면 null 리턴
        }
        // 1. 파일명[ 파일명은 식별자가 될수 없다. 1.UUID 조합 2.날짜/시간 조합 3.상위컨텐츠PK 등등 ]
        String fileName =
                UUID.randomUUID().toString()+"_"+ // UUID 이용한 파일 식별자 만들기
                 multipartFile.getOriginalFilename().replaceAll("_","-"); // 만일 식별을 위해 '_'를  '-' 변경
        // 2. 파일 경로
        File file = new File( fileRootPath + fileName );
        // 3. 업로드
        try{
            multipartFile.transferTo( file );
            return fileName; // 성공시 파일명 리턴 [ db에 저장할려고 ]
        }catch (Exception  e ){
            System.out.println("업로드 실패 "+e);
            return null; // 실패시 null 리턴
        }
    }


    // 2. 다운로드

}
