package rococo.backends.museums.service;

import com.shared.proto.geos.CountryServiceGrpc;
import com.shared.proto.geos.Geo;
import com.shared.proto.geos.GetCountryByIdRequest;
import com.shared.proto.geos.GetCountryByIdResponse;
import com.shared.proto.museums.*;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import rococo.backends.museums.dto.CountryDto;
import rococo.backends.museums.dto.GeoDto;
import rococo.backends.museums.entity.MuseumEntity;
import rococo.backends.museums.repository.MuseumRepository;

import java.util.UUID;

@GRpcService
public class MuseumService extends MuseumServiceGrpc.MuseumServiceImplBase {
    private final Channel channel;
    final MuseumRepository repository;

    @Autowired
    public MuseumService(MuseumRepository repository, @Value("${rococo-geos.host}") String host,
                         @Value("${rococo-geos.port}") int port) {
        this.repository = repository;
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
    }

    public void getMuseums(GetAllMuseumRequest request, StreamObserver<GetAllMuseumResponse> responseObserver) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        String byTitle = request.getTitle();
        long totalCount = 0;

        Iterable<MuseumEntity> museums;
        if (!byTitle.isEmpty()) {
            museums = repository.findByTitleContainingIgnoreCase(byTitle, pageable);
            totalCount = repository.countByTitleContainingIgnoreCase(byTitle);
        } else {
            museums = repository.findAll(pageable);
            totalCount = repository.count();
        }

        GetAllMuseumResponse.Builder responseBuilder = GetAllMuseumResponse.newBuilder();

        museums.forEach(museum -> {
            CountryServiceGrpc.CountryServiceBlockingStub stub = CountryServiceGrpc.newBlockingStub(channel);
            GetCountryByIdRequest countryByIdRequest = GetCountryByIdRequest.newBuilder()
                    .setId(museum.getCountry_id())
                    .build();

            GetCountryByIdResponse response = stub.getCountryById(countryByIdRequest);

            MuseumRelation museumProto = MuseumRelation.newBuilder()
                    .setId(museum.getId())
                    .setTitle(museum.getTitle())
                    .setPhoto(museum.getPhoto())
                    .setGeo(Geo.newBuilder().setCity(museum.getCity()).setCountry(response.getCountry()))
                    .build();
            responseBuilder.addMuseum(museumProto);
        });

        GetAllMuseumResponse response = responseBuilder.setTotalCount(totalCount).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void getMuseumById(GetMuseumByIdRequest request, StreamObserver<GetMuseumByIdResponse> responseObserver) {
        MuseumEntity museum = repository.findById(request.getId());

        CountryServiceGrpc.CountryServiceBlockingStub stub = CountryServiceGrpc.newBlockingStub(channel);
        GetCountryByIdRequest countryByIdRequest = GetCountryByIdRequest.newBuilder()
                .setId(museum.getCountry_id())
                .build();

        GetCountryByIdResponse countryResponse = stub.getCountryById(countryByIdRequest);

        MuseumRelation museumProto = MuseumRelation.newBuilder()
                .setId(museum.getId())
                .setTitle(museum.getTitle())
                .setDescription(museum.getDescription())
                .setPhoto(museum.getPhoto())
                .setGeo(Geo.newBuilder().setCity(museum.getCity()).setCountry(countryResponse.getCountry()))
                .build();

        GetMuseumByIdResponse response = GetMuseumByIdResponse.newBuilder()
                .setMuseums(museumProto)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void addMuseum(Museum request, StreamObserver<AddMuseumResponse> responseObserver) {
        Museum museumProto = Museum.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setTitle(request.getTitle())
                .setDescription(request.getDescription())
                .setPhoto(request.getPhoto())
                .setCity(request.getCity())
                .setCountryId(request.getCountryId())
                .build();

        MuseumEntity museum = MuseumEntity.fromProto(museumProto);

        MuseumEntity museumResponse = repository.save(museum);

        CountryServiceGrpc.CountryServiceBlockingStub stub = CountryServiceGrpc.newBlockingStub(channel);
        GetCountryByIdRequest countryByIdRequest = GetCountryByIdRequest.newBuilder()
                .setId(museumResponse.getCountry_id())
                .build();

        GetCountryByIdResponse countryResponse = stub.getCountryById(countryByIdRequest);

        AddMuseumResponse response = AddMuseumResponse.newBuilder()
                .setMuseum(MuseumEntity.fromEntity(museumResponse, countryResponse.getCountry()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void updateMuseum(Museum request, StreamObserver<UpdateMuseumResponse> responseObserver) {
        Museum museumProto = Museum.newBuilder()
                .setId(request.getId())
                .setTitle(request.getTitle())
                .setDescription(request.getDescription())
                .setPhoto(request.getPhoto())
                .setCity(request.getCity())
                .setCountryId(request.getCountryId())
                .build();

        MuseumEntity museum = MuseumEntity.fromProto(museumProto);
        MuseumEntity museumResponse = repository.save(museum);

        CountryServiceGrpc.CountryServiceBlockingStub stub = CountryServiceGrpc.newBlockingStub(channel);
        GetCountryByIdRequest countryByIdRequest = GetCountryByIdRequest.newBuilder()
                .setId(museumResponse.getCountry_id())
                .build();

        GetCountryByIdResponse countryResponse = stub.getCountryById(countryByIdRequest);

        UpdateMuseumResponse response = UpdateMuseumResponse.newBuilder()
                .setMuseum(MuseumEntity.fromEntity(museumResponse, countryResponse.getCountry()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
