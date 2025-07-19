package guru.qa.rococo.service;

import com.google.protobuf.ByteString;
import com.shared.proto.artists.*;
import guru.qa.rococo.grpc.ArtistGrpc;
import guru.qa.rococo.utils.FakerData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class GrpcArtistService {

    ArtistGrpc artistGrpc = new ArtistGrpc();

    public AddArtistResponse addArtist() throws IOException {
        File image = new File("src\\test\\resources\\avatar.png");
        byte[] data = Files.readAllBytes(image.toPath());
        var con = Base64.getEncoder().encodeToString(data);
        var a = ByteString.copyFrom(con.getBytes()).toStringUtf8();
        AddArtistRequest request = AddArtistRequest.newBuilder()
                .setName(FakerData.getLogin())
                .setBiography(FakerData.randomString(20))
                .setPhoto("data:image/png;base64,"+a)
                .build();
        return ArtistServiceGrpc.newBlockingStub(artistGrpc.getStub()).addArtist(request);
    }

    public AddArtistResponse addArtist(String name) throws IOException {
        File image = new File("src\\test\\resources\\avatar.png");
        byte[] data = Files.readAllBytes(image.toPath());
        var con = Base64.getEncoder().encodeToString(data);
        var a = ByteString.copyFrom(con.getBytes()).toStringUtf8();
        AddArtistRequest request = AddArtistRequest.newBuilder()
                .setName(name)
                .setBiography(FakerData.randomString(20))
                .setPhoto("data:image/png;base64,"+a)
                .build();
        return ArtistServiceGrpc.newBlockingStub(artistGrpc.getStub()).addArtist(request);
    }

    public GetArtistByIdResponse getArtistById(GetArtistByIdRequest request){
        return ArtistServiceGrpc.newBlockingStub(artistGrpc.getStub()).getArtistById(request);
    }

    public UpdateArtistResponse updateArtist(Artist request){
        return ArtistServiceGrpc.newBlockingStub(artistGrpc.getStub()).updateArtist(request);
    }

    public GetAllArtistResponse getArtists(GetAllArtistRequest request){
        return ArtistServiceGrpc.newBlockingStub(artistGrpc.getStub()).getArtists(request);
    }

}
