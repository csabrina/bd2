package service;

import com.fasterxml.jackson.core.type.TypeReference;
import io.minio.GetObjectArgs;
import model.Music;
import config.MinioConfig;
import exceptions.FailedToSaveFileException;
import exceptions.UnableToCreateBucketException;
import exceptions.UnableToListMusicsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.messages.Item;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MusicService {
    private final MinioClient minioClient;
    private final String bucketName = "musics";

    public MusicService() {
        this.minioClient = MinioConfig.getMinioClient();
        createBucket();
    }

    private void createBucket() {
        try {
            if (!minioClient.bucketExists(io.minio.BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(io.minio.MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new UnableToCreateBucketException("Erro ao criar bucket: " + e.getMessage());
        }
    }

    public void save(Music musics) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(musics);

            try (InputStream inputStream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))) {
                String objectName = "music-" + musics.getTitle() + "_" + musics.getId() + ".json";

                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .stream(inputStream, json.getBytes().length, -1)
                                .contentType("application/json")
                                .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FailedToSaveFileException("Não foi possivel salvar o arquivo: " + e.getMessage());
        }
    }

    public List<Music> getMusics() {
        List<Music> musics = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();

            for (io.minio.Result<Item> result : minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucketName).build())) {

                Item item = result.get();
                try (InputStream inputStream = minioClient.getObject(
                        GetObjectArgs.builder().bucket(bucketName).object(item.objectName()).build());
                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                    String json = reader.lines().collect(Collectors.joining("\n"));
                    Music music = mapper.readValue(json, Music.class);
                    musics.add(music);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnableToListMusicsException("Erro ao listar as músicas: " + e.getMessage());
        }
        return musics;
    }

    public void saveAllFromJson(String resourcePath) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Arquivo JSON não encontrado no classpath: " + resourcePath);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            List<Music> musics = objectMapper.readValue(inputStream, new TypeReference<>() {
            });

            for (Music music : musics) {
                save(music);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FailedToSaveFileException("Erro ao salvar músicas em lote: " + e.getMessage());
        }
    }
}