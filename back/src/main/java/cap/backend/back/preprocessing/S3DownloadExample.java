
package cap.backend.back.preprocessing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.nio.file.Paths;

@Component
public class S3DownloadExample {

    @Value("${aws.accesskey}")
    String accesskey;
    @Value("${aws.secaccesskey}")
    String seccesskey;

    public void doDownload() {
        // AWS Credential 설정
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accesskey, seccesskey);

        // S3 클라이언트 생성
        try (S3Client s3 = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.AP_NORTHEAST_2) // 예: Region.US_EAST_1
                .build()) {

            // S3 버킷과 객체 키 설정
            String bucketName = "gwaks3";
            String key = "손식.png";

            // S3에서 이미지 다운로드
            s3.getObject(GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build(),
                    Paths.get("hi.png")); // 다운로드 받을 로컬 경로
        }
        // 예외 처리 및 리소스 관리는 필요에 따라 추가
    }
}

