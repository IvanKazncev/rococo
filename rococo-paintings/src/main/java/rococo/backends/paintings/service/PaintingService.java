package rococo.backends.paintings.service;

import com.shared.proto.artists.ArtistServiceGrpc;
import com.shared.proto.artists.GetArtistByIdRequest;
import com.shared.proto.artists.GetArtistByIdResponse;
import com.shared.proto.museums.*;
import com.shared.proto.paintings.*;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import rococo.backends.paintings.entity.PaintingEntity;
import rococo.backends.paintings.repository.PaintingRepository;

import java.util.UUID;

@GRpcService
public class PaintingService extends PaintingServiceGrpc.PaintingServiceImplBase {
    private final Channel artistsChannel;
    private final Channel museumsChannel;
    final PaintingRepository repository;

    @Autowired
    public PaintingService(
            PaintingRepository repository,
            @Value("${rococo-artists.host}") String artistsHost,
            @Value("${rococo-artists.port}") int artistsPort,
            @Value("${rococo-museums.host}") String museumsHost,
            @Value("${rococo-museums.port}") int museumsPort
    ) {
        this.repository = repository;
        this.artistsChannel = ManagedChannelBuilder.forAddress(artistsHost, artistsPort)
                .usePlaintext()
                .build();
        this.museumsChannel = ManagedChannelBuilder.forAddress(museumsHost, museumsPort)
                .usePlaintext()
                .build();
    }

    public void getPaintings(GetAllPaintingRequest request, StreamObserver<GetAllPaintingResponse> responseObserver) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        String byTitle = request.getTitle();
        long totalCount = 0;

        Iterable<PaintingEntity> paintings;
        if (!byTitle.isEmpty()) {
            paintings = repository.findByTitleContainingIgnoreCase(byTitle, pageable);
            totalCount = repository.countByTitleContainingIgnoreCase(byTitle);
        } else {
            paintings = repository.findAll(pageable);
            totalCount = repository.count();
        }

        GetAllPaintingResponse.Builder responseBuilder = GetAllPaintingResponse.newBuilder();

        paintings.forEach(painting -> {
            ArtistServiceGrpc.ArtistServiceBlockingStub artistStub = ArtistServiceGrpc.newBlockingStub(artistsChannel);
            GetArtistByIdRequest artistRequest = GetArtistByIdRequest.newBuilder()
                    .setId(painting.getArtistId())
                    .build();
            GetArtistByIdResponse artistResponse = artistStub.getArtistById(artistRequest);

            MuseumServiceGrpc.MuseumServiceBlockingStub museumStub = MuseumServiceGrpc.newBlockingStub(museumsChannel);
            GetMuseumByIdRequest museumRequest = GetMuseumByIdRequest.newBuilder()
                    .setId(painting.getMuseumId())
                    .build();
            GetMuseumByIdResponse museumResponse = museumStub.getMuseumById(museumRequest);

            Painting paintingProto = Painting.newBuilder()
                    .setId(painting.getId())
                    .setTitle(painting.getTitle())
                    .setDescription(painting.getDescription())
                    .setContent(painting.getContent())
                    .setArtist(artistResponse.getArtists())
                    .setMuseum(museumResponse.getMuseums())
                    .build();
            responseBuilder.addPaintings(paintingProto);
        });

        GetAllPaintingResponse response = responseBuilder.setTotalCount(totalCount).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void getPaintingById(GetPaintingByIdRequest request, StreamObserver<GetPaintingByIdResponse> responseObserver) {
        PaintingEntity painting = repository.findById(request.getId());

        ArtistServiceGrpc.ArtistServiceBlockingStub artistStub = ArtistServiceGrpc.newBlockingStub(artistsChannel);
        GetArtistByIdRequest artistRequest = GetArtistByIdRequest.newBuilder()
                .setId(painting.getArtistId())
                .build();
        GetArtistByIdResponse artistResponse = artistStub.getArtistById(artistRequest);

        MuseumServiceGrpc.MuseumServiceBlockingStub museumStub = MuseumServiceGrpc.newBlockingStub(museumsChannel);
        GetMuseumByIdRequest museumRequest = GetMuseumByIdRequest.newBuilder()
                .setId(painting.getMuseumId())
                .build();
        GetMuseumByIdResponse museumResponse = museumStub.getMuseumById(museumRequest);

        Painting paintingProto = Painting.newBuilder()
                .setId(painting.getId())
                .setTitle(painting.getTitle())
                .setDescription(painting.getDescription())
                .setContent(painting.getContent())
                .setArtist(artistResponse.getArtists())
                .setMuseum(museumResponse.getMuseums())
                .build();

        GetPaintingByIdResponse response = GetPaintingByIdResponse.newBuilder()
                .setPaintings(paintingProto)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void getPaintingByArtist(
            GetPaintingByArtistRequest request,
            StreamObserver<GetPaintingByArtistResponse> responseObserver
    ) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        long totalCount = repository.countByArtistId(request.getArtistId());

        Iterable<PaintingEntity> paintings = repository.findByArtistId(request.getArtistId(), pageable);

        GetPaintingByArtistResponse.Builder responseBuilder = GetPaintingByArtistResponse.newBuilder();

        paintings.forEach(painting -> {
            ArtistServiceGrpc.ArtistServiceBlockingStub artistStub = ArtistServiceGrpc.newBlockingStub(artistsChannel);
            GetArtistByIdRequest artistRequest = GetArtistByIdRequest.newBuilder()
                    .setId(painting.getArtistId())
                    .build();
            GetArtistByIdResponse artistResponse = artistStub.getArtistById(artistRequest);

            MuseumServiceGrpc.MuseumServiceBlockingStub museumStub = MuseumServiceGrpc.newBlockingStub(museumsChannel);
            GetMuseumByIdRequest museumRequest = GetMuseumByIdRequest.newBuilder()
                    .setId(painting.getMuseumId())
                    .build();
            GetMuseumByIdResponse museumResponse = museumStub.getMuseumById(museumRequest);

            Painting paintingProto = Painting.newBuilder()
                    .setId(painting.getId())
                    .setTitle(painting.getTitle())
                    .setDescription(painting.getDescription())
                    .setContent(painting.getContent())
                    .setArtist(artistResponse.getArtists())
                    .setMuseum(museumResponse.getMuseums())
                    .build();
            responseBuilder.addPaintings(paintingProto);
        });

        GetPaintingByArtistResponse response = responseBuilder.setTotalCount(totalCount).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void addPainting(AddPaintingRequest request, StreamObserver<AddPaintingResponse> responseObserver) {
        UpdatePaintingRequest paintingProto = UpdatePaintingRequest.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setTitle(request.getTitle())
                .setDescription(request.getDescription())
                .setContent(request.getContent())
                .setArtist(request.getArtist())
                .setMuseum(request.getMuseum())
                .build();

        PaintingEntity painting = PaintingEntity.fromProto(paintingProto);
        PaintingEntity paintingResponse = repository.save(painting);

        ArtistServiceGrpc.ArtistServiceBlockingStub artistStub = ArtistServiceGrpc.newBlockingStub(artistsChannel);
        GetArtistByIdRequest artistRequest = GetArtistByIdRequest.newBuilder()
                .setId(painting.getArtistId())
                .build();
        GetArtistByIdResponse artistResponse = artistStub.getArtistById(artistRequest);

        AddPaintingResponse response = AddPaintingResponse.newBuilder()
                .setPainting(PaintingEntity.fromEntity(paintingResponse, artistResponse.getArtists()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void updatePainting(UpdatePaintingRequest request, StreamObserver<UpdatePaintingResponse> responseObserver) {
        UpdatePaintingRequest paintingProto = UpdatePaintingRequest.newBuilder()
                .setId(request.getId())
                .setTitle(request.getTitle())
                .setDescription(request.getDescription())
                .setContent(request.getContent())
                .setArtist(request.getArtist())
                .setMuseum(request.getMuseum())
                .build();

        PaintingEntity museum = PaintingEntity.fromProto(paintingProto);
        PaintingEntity paintingResponse = repository.save(museum);

        ArtistServiceGrpc.ArtistServiceBlockingStub artistStub = ArtistServiceGrpc.newBlockingStub(artistsChannel);
        GetArtistByIdRequest artistRequest = GetArtistByIdRequest.newBuilder()
                .setId(paintingResponse.getArtistId())
                .build();
        GetArtistByIdResponse artistResponse = artistStub.getArtistById(artistRequest);

        UpdatePaintingResponse response = UpdatePaintingResponse.newBuilder()
                .setPainting(PaintingEntity.fromEntity(paintingResponse, artistResponse.getArtists()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
