package guru.qa.rococo.service;

import com.google.protobuf.ByteString;
import com.shared.proto.paintings.*;
import guru.qa.rococo.grpc.PictureGrpc;
import guru.qa.rococo.jupiter.annotation.CreatingArtist;
import guru.qa.rococo.utils.FakerData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class GrpcPictureService {

    PictureGrpc pictureGrpc = new PictureGrpc();

    public AddPaintingResponse addPicture() throws IOException {
        File image = new File("src\\test\\resources\\avatar.png");
        byte[] data = Files.readAllBytes(image.toPath());
        var con = Base64.getEncoder().encodeToString(data);
        var a = ByteString.copyFrom(con.getBytes()).toStringUtf8();
        AddPaintingRequest addPaintingRequest = AddPaintingRequest.newBuilder()
                .setDescription(FakerData.randomString(20))
                .setArtist(ArtistId.newBuilder()
                        .setId("104f76ce-0508-49d4-8967-fdf1ebb8cf45")
                        .build())
                .setMuseum(MuseumId.newBuilder()
                        .setId("104f76ce-0508-49d4-8967-fdf1ebb8cf45")
                        .build())
                .setTitle(FakerData.getLogin())
                .setContent("data:image/png;base64,"+a)
                .build();
        return PaintingServiceGrpc.newBlockingStub(pictureGrpc.getStub()).addPainting(addPaintingRequest);
    }

    public AddPaintingResponse addPicture(String title) throws IOException {
        File image = new File("src\\test\\resources\\avatar.png");
        byte[] data = Files.readAllBytes(image.toPath());
        var con = Base64.getEncoder().encodeToString(data);
        var a = ByteString.copyFrom(con.getBytes()).toStringUtf8();
        AddPaintingRequest addPaintingRequest = AddPaintingRequest.newBuilder()
                .setDescription(FakerData.randomString(20))
                .setArtist(ArtistId.newBuilder()
                        .setId("104f76ce-0508-49d4-8967-fdf1ebb8cf45")
                        .build())
                .setMuseum(MuseumId.newBuilder()
                        .setId("104f76ce-0508-49d4-8967-fdf1ebb8cf45")
                        .build())
                .setTitle(title)
                .setContent("data:image/png;base64,"+a)
                .build();
        return PaintingServiceGrpc.newBlockingStub(pictureGrpc.getStub()).addPainting(addPaintingRequest);
    }

    public GetPaintingByIdResponse getPaintingById(GetPaintingByIdRequest request) throws IOException {
        return PaintingServiceGrpc.newBlockingStub(pictureGrpc.getStub()).getPaintingById(request);
    }

    public GetPaintingByArtistResponse getPaintingByArtist(GetPaintingByArtistRequest request) throws IOException {
        return PaintingServiceGrpc.newBlockingStub(pictureGrpc.getStub()).getPaintingByArtist(request);
    }

    public UpdatePaintingResponse updatePainting(UpdatePaintingRequest request) throws IOException {
        return PaintingServiceGrpc.newBlockingStub(pictureGrpc.getStub()).updatePainting(request);
    }

}
