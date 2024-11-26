package io.urdego.content_service.external.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.drew.imaging.ImageMetadataReader;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;
import io.urdego.content_service.common.exception.ExceptionMessage;
import io.urdego.content_service.common.exception.aws.AwsException;
import io.urdego.content_service.domain.entity.user.constant.ContentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 s3Client;

    // 파일 업로드 (단일 파일)
    public String uploadSingleContent(Long userId, MultipartFile multipartFile) {
        String filename = createFilename(userId, multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            s3Client.putObject(
                    new PutObjectRequest(bucket, filename, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (IOException e) {
            throw new AwsException(ExceptionMessage.FILE_UPLOAD_FAILED);
        }

        // 업로드된 파일의 URL 반환
        return s3Client.getUrl(bucket, filename).toString();
    }

    // 파일이름 생성
    public String createFilename(Long userId, String filename) {
        try {
            String extension = getFileExtension(filename);
            return userId + "_" + UUID.randomUUID() + extension; // 유저 ID + UUID + 확장자

        } catch (IllegalArgumentException e) {
            throw new AwsException(ExceptionMessage.INVALID_FILE_FORMAT);
        }
    }

    /*
       Todo: 추후 .webp 확장자로 변경 로직 추가 (크기문제)
    */
    // 파일 확장자 추출
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new AwsException(ExceptionMessage.INVALID_FILE_FORMAT);
        }
        String extension = filename.substring(filename.lastIndexOf(".")).toLowerCase();

        // 유효한 확장자
        List<String> validExtensions = List.of(".jpg", ".jpeg", ".png", ".mp4", ".mov");

        // 확장자가 유효하지 않으면 예외 발생
        if (!validExtensions.contains(extension.toLowerCase())) {
            throw new AwsException(ExceptionMessage.INVALID_FILE_FORMAT);
        }

        return extension;
    }

    // 사진 메타데이터(위치) 추출
    public double[] extractLocation(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            // 이미지에서 메타데이터 읽기
            Metadata metadata = ImageMetadataReader.readMetadata(inputStream);

            // GPS 디렉토리 가져오기
            GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
            if (gpsDirectory != null) {
                GeoLocation geoLocation = gpsDirectory.getGeoLocation();
                if (geoLocation != null && !geoLocation.isZero()) {
                    // 위도(latitude)와 경도(longitude) 반환
                    return new double[]{geoLocation.getLatitude(), geoLocation.getLongitude()};
                }
            }
        } catch (Exception e) {
            throw new AwsException(ExceptionMessage.IMAGE_METADATA_FAILED);
        }
        return null; // GPS 정보가 없으면 null 반환
    }

    // 사진 메타데이터(크기) 추출
    public ContentInfo extractMetadata(MultipartFile multipartFile) {
        // 위치 정보 추출
        double[] location = extractLocation(multipartFile);
        Double latitude = null;
        Double longitude = null;
        if (location != null) {
            latitude = location[0];
            longitude = location[1];
        }

        // ContentInfo 객체 생성
        return ContentInfo.builder()
                .size(multipartFile.getSize())
                .contentType(multipartFile.getContentType())
                .metaLatitude(latitude)
                .metaLongitude(longitude)
                .build();
    }
}
