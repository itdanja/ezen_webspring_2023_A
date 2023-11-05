package ezenweb.service;

import ezenweb.model.dto.FileDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    // * 첨부파일이 저장 될 경로 [ 1. 배포 전 2.배포 후 ]
    String path = "C:\\java\\";

    // 1.
    public FileDto fileupload(MultipartFile multipartFile ){
        // 1. 첨부파일 존재하는지 확인
        if( !multipartFile.getOriginalFilename().equals("") ){ // 첨부파일이 존재하면
            // * 만약에 다른 이미지인데 파일이 동일하면 중복발생[ 식별 불가 ] : UUID + 파일명
            String fileName =
                    UUID.randomUUID().toString() +"_"+
                            multipartFile.getOriginalFilename();
            // 2.  경로 + UUID파일명  조합해서 file 클래스의 객체 생성 [ 왜?? 파일?? transferTo ]
            File file = new File( path + fileName );
            // 3. 업로드 // multipartFile.transferTo( 저장할 File 클래스의 객체 );
            try { multipartFile.transferTo(file);
            }catch ( Exception e ) {  }
            // 4. 반환
            return FileDto.builder()
                    .originalFilename( multipartFile.getOriginalFilename() )
                    .uuidFile( fileName )
                    .sizeKb( multipartFile.getSize()/1024 + "kb" )
                    .build();
        }
        return null;
    }
    // 2.

    // 3.

    // 4.

}
