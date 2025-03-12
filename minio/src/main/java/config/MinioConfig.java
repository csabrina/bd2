package config;

import io.minio.MinioClient;

public class MinioConfig {
    private static final String MINIO_URL = "http://localhost:9000";
    private static final String ACCESS_KEY = "652gzpa3VgQduUQ3ReW5";
    private static final String SECRET_KEY = "0nKUsw0tRUejCAR97WTAFEg8qbF1F41srR08qvFf";

    public static MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(MINIO_URL)
                .credentials(ACCESS_KEY, SECRET_KEY)
                .build();
    }
}