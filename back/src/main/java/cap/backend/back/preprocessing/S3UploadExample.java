package cap.backend.back.preprocessing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class S3UploadExample {

    @Value("${aws.accesskey}")
    private String accessKey;

    @Value("${aws.secaccesskey}")
    private String secretKey;

    public void doUpload(String filename, String dir_path) throws FileNotFoundException {
        // AWS Credential 설정
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);


        Path filePath= Paths.get(dir_path,filename);

        if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
            throw new FileNotFoundException("파일을 찾을 수 없거나 디렉토리입니다: " + filePath);
        }

        // S3 클라이언트 생성
        try (S3Client s3 = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.AP_NORTHEAST_2) // 예: Region.US_EAST_1
                .build()) {

            // S3에 파일 업로드
            s3.putObject(PutObjectRequest.builder() //34번째 줄
                            .bucket("gwaks3")
                            .key(filename)
                            .build(),
                    RequestBody.fromFile(filePath));
        }
        // 예외 처리 및 리소스 관리는 필요에 따라 추가
    }
}
