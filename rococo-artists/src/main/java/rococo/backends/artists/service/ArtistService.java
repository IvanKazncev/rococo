package rococo.backends.artists.service;

import com.shared.proto.artists.*;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import rococo.backends.artists.entity.ArtistEntity;
import rococo.backends.artists.repository.ArtistRepository;

import java.util.UUID;

@GRpcService
public class ArtistService extends ArtistServiceGrpc.ArtistServiceImplBase {

    final ArtistRepository repository;

    @Autowired
    public ArtistService(ArtistRepository repository) {
        this.repository = repository;
    }

    public void getArtists(GetAllArtistRequest request, StreamObserver<GetAllArtistResponse> responseObserver) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        String byName = request.getName();
        long totalCount = 0;

        Iterable<ArtistEntity> artists;
        if (!byName.isEmpty()) {
            artists = repository.findByNameContainingIgnoreCase(byName, pageable);
            totalCount = repository.countByNameContainingIgnoreCase(byName);
        } else {
            artists = repository.findAll(pageable);
            totalCount = repository.count();
        }

        GetAllArtistResponse.Builder responseBuilder = GetAllArtistResponse.newBuilder();

        artists.forEach(artist -> {
            Artist artistProto = Artist.newBuilder()
                    .setId(artist.getId())
                    .setName(artist.getName())
                    .setBiography(artist.getBiography())
                    .setPhoto(artist.getPhoto()).build();
            responseBuilder.addArtists(artistProto);
        });

        GetAllArtistResponse response = responseBuilder.setTotalCount(totalCount).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void getArtistById(GetArtistByIdRequest request, StreamObserver<GetArtistByIdResponse> responseObserver) {
        ArtistEntity artist = repository.findById(request.getId());

        Artist artistProto = Artist.newBuilder()
                .setId(artist.getId())
                .setName(artist.getName())
                .setBiography(artist.getBiography())
                .setPhoto(artist.getPhoto())
                .build();

        GetArtistByIdResponse response = GetArtistByIdResponse.newBuilder()
                .setArtists(artistProto)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void addArtist(AddArtistRequest request, StreamObserver<AddArtistResponse> responseObserver) {
        Artist artistProto = Artist.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setName(request.getName())
                .setBiography(request.getBiography())
                .setPhoto(request.getPhoto())
                .build();

        ArtistEntity artist = ArtistEntity.fromProto(artistProto);

        ArtistEntity artistResponse = repository.save(artist);

        AddArtistResponse response = AddArtistResponse.newBuilder()
                .setArtist(ArtistEntity.fromEntity(artistResponse))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void updateArtist(Artist request, StreamObserver<UpdateArtistResponse> responseObserver) {
        Artist artistProto = Artist.newBuilder()
                .setId(request.getId())
                .setName(request.getName())
                .setBiography(request.getBiography())
                .setPhoto(request.getPhoto())
                .build();

        ArtistEntity artist = ArtistEntity.fromProto(artistProto);

        ArtistEntity artistResponse = repository.save(artist);

        UpdateArtistResponse response = UpdateArtistResponse.newBuilder()
                .setArtist(ArtistEntity.fromEntity(artistResponse))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
