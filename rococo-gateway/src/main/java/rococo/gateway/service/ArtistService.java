package rococo.gateway.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.shared.proto.artists.*;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rococo.gateway.dto.artist.ArtistCreateDto;
import rococo.gateway.dto.artist.ArtistJsonDto;

import java.util.List;

@Service
public class ArtistService {

    private final Channel channel;

    @Autowired
    public ArtistService(@Value("${rococo-artists.host}") String host,
                         @Value("${rococo-artists.port}") int port) {
        int newMaxMessageSize = 52_428_800;
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .maxInboundMessageSize(newMaxMessageSize)
                .usePlaintext()
                .build();
    }

    public PageImpl<ArtistJsonDto> getArtistsAll(String name, Pageable pageable) throws InvalidProtocolBufferException {
        ArtistServiceGrpc.ArtistServiceBlockingStub stub = ArtistServiceGrpc.newBlockingStub(channel);
        GetAllArtistRequest request = GetAllArtistRequest.newBuilder()
                .setPage(pageable.getPageNumber())
                .setSize(pageable.getPageSize())
                .setName((name != null && !name.isEmpty()) ? name : "")
                .build();

        GetAllArtistResponse response = stub.getArtists(request);
        List<ArtistJsonDto> artistJsonDtoList = response.getArtistsList()
                .stream()
                .map(ArtistJsonDto::fromGrpcMessage)
                .toList();

        return new PageImpl<>(artistJsonDtoList, pageable, response.getTotalCount());
    }

    public String getArtistById(String id) throws InvalidProtocolBufferException {
        ArtistServiceGrpc.ArtistServiceBlockingStub stub = ArtistServiceGrpc.newBlockingStub(channel);
        GetArtistByIdRequest request = GetArtistByIdRequest.newBuilder()
                .setId(id)
                .build();

        GetArtistByIdResponse response = stub.getArtistById(request);
        Artist artist = response.getArtists();

        return JsonFormat.printer().print(artist);
    }

    public String addArtist(ArtistCreateDto artist) throws InvalidProtocolBufferException {
        ArtistServiceGrpc.ArtistServiceBlockingStub stub = ArtistServiceGrpc.newBlockingStub(channel);
        AddArtistRequest request = AddArtistRequest.newBuilder()
                .setName(artist.getName())
                .setBiography(artist.getBiography())
                .setPhoto(artist.getPhoto())
                .build();

        Artist response = stub.addArtist(request).getArtist();

        return JsonFormat.printer().print(response);
    }

    public String updateArtist(ArtistJsonDto artist) throws InvalidProtocolBufferException {
        ArtistServiceGrpc.ArtistServiceBlockingStub stub = ArtistServiceGrpc.newBlockingStub(channel);
        Artist request = Artist.newBuilder()
                .setId(artist.getId())
                .setName(artist.getName())
                .setBiography(artist.getBiography())
                .setPhoto(artist.getPhoto())
                .build();

        Artist response = stub.updateArtist(request).getArtist();

        return JsonFormat.printer().print(response);
    }
}