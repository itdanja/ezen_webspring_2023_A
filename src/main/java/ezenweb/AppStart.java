package ezenweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication // 하위폴더내 컴포넌트스캔 빈 등록
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class);
    }
}