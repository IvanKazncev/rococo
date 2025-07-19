package guru.qa.rococo.test;

import com.google.protobuf.StringValue;
import com.shared.proto.artists.*;
import com.shared.proto.paintings.*;
import guru.qa.rococo.jupiter.annotation.CreatingArtist;
import guru.qa.rococo.jupiter.annotation.CreatingPicture;
import guru.qa.rococo.service.GrpcArtistService;
import guru.qa.rococo.service.GrpcPictureService;
import guru.qa.rococo.utils.FakerData;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ApiTest")
public class ApiTest {
    GrpcArtistService artistService = new GrpcArtistService();
    GrpcPictureService pictureService = new GrpcPictureService();

    @Test
    @DisplayName("Проверка добавление артиста через grpc")
    void addArtistGrpcCheck() throws IOException {
        String name  = FakerData.getLogin();
        var response = artistService.addArtist(name);
        assertEquals(response.getArtist().getName(), name);
    }

    @Test
    @CreatingArtist
    @DisplayName("Проверка получения данных Артиста по id через grpc")
    void getArtistByIdCheck(@NotNull AddArtistResponse addArtistResponse) {
        var response = artistService.getArtistById(GetArtistByIdRequest.newBuilder()
                        .setId(addArtistResponse.getArtist().getId())
                .build());
        assertNotNull(response);
        assertEquals(addArtistResponse.getArtist().getName(),response.getArtists().getName());
    }

    @Test
    @CreatingArtist
    @DisplayName("Проверка обновления данных артиста через grpc")
    void updateArtistCheck(@NotNull AddArtistResponse addArtistResponse){
        String name  = FakerData.getLogin();
        var response =  artistService.updateArtist(Artist.newBuilder()
                        .setId(addArtistResponse.getArtist().getId())
                        .setName(name)
                        .setBiography(addArtistResponse.getArtist().getBiography())
                        .setPhoto(addArtistResponse.getArtist().getPhoto())
                .build());
        assertNotNull(response);
        assertEquals(name,response.getArtist().getName());
    }

    @Test
    @CreatingPicture
    @DisplayName("Проверка получение картины по id через grpc")
    void getPictureByIdCheck(@NotNull AddPaintingResponse picture) throws IOException {
        var response = pictureService.getPaintingById(GetPaintingByIdRequest.newBuilder()
                        .setId(picture.getPainting().getId())
                .build());
        assertNotNull(response);
        assertEquals(picture.getPainting().getDescription(),response.getPaintings().getDescription());
    }

    @Test
    @DisplayName("Проверка получения картины по художнику через grpc")
    @CreatingPicture
    void getPaintingByArtistCheck(@NotNull AddPaintingResponse addPaintingResponse) throws IOException {
        var response = pictureService.getPaintingByArtist(GetPaintingByArtistRequest.newBuilder()
                        .setArtistId(addPaintingResponse.getPainting().getArtist().getId())
                        .setSize(3)
                .build());
        assertNotNull(response);
        response.getPaintingsList().forEach(painting -> {
            assertNotNull(painting.getArtist());
            assertEquals(addPaintingResponse.getPainting().getArtist().getId(),painting.getArtist().getId());
        });
    }

    @Test
    @DisplayName("Проверка добавления картины через grpc")
    void addPictureCheck() throws IOException {
        String title  = FakerData.getLogin();
        var response = pictureService.addPicture(title);
        assertNotNull(response);
        assertEquals(title,response.getPainting().getTitle());
    }

    @Test
    @CreatingPicture
    @DisplayName("Проверка обновления картины через grpc")
    void updatePaintingCheck(@NotNull AddPaintingResponse addPaintingResponse) throws IOException {
        String title  = FakerData.getLogin();
        var response = pictureService.updatePainting(UpdatePaintingRequest.newBuilder()
                .setId(addPaintingResponse.getPainting().getId())
                .setTitle(title)
                .setDescription(addPaintingResponse.getPainting().getDescription())
                .setContent(addPaintingResponse.getPainting().getContent())
                .setArtist(ArtistId.newBuilder()
                        .setId(addPaintingResponse.getPainting().getArtist().getId())
                        .build())
                .setMuseum(MuseumId.newBuilder()
                        .setId(addPaintingResponse.getPainting().getMuseum().getId())
                        .build())
                .build());
        assertNotNull(response);
        assertEquals(title,response.getPainting().getTitle());
    }
}
