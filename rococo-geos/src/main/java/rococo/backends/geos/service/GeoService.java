package rococo.backends.geos.service;

import com.shared.proto.artists.Artist;
import com.shared.proto.artists.GetArtistByIdRequest;
import com.shared.proto.artists.GetArtistByIdResponse;
import com.shared.proto.geos.*;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import rococo.backends.geos.entity.CountryEntity;
import rococo.backends.geos.entity.GeoEntity;
import rococo.backends.geos.repository.CountryRepository;
import rococo.backends.geos.repository.GeoRepository;

import java.util.List;
import java.util.UUID;

@GRpcService
public class GeoService extends GeoServiceGrpc.GeoServiceImplBase {

    final GeoRepository repository;
    final CountryRepository countryRepository;

    @Autowired
    public GeoService(GeoRepository repository, CountryRepository countryRepository) {
        this.repository = repository;
        this.countryRepository = countryRepository;
    }

    public void getGeos(GetAllGeoRequest request, StreamObserver<GetAllGeoResponse> responseObserver) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        String byCity = request.getCity();
        long totalCount = 0;

        Iterable<GeoEntity> geos;
        if (!byCity.isEmpty()) {
            geos = repository.findByCityContainingIgnoreCase(byCity, pageable);
            totalCount = repository.countByCityContainingIgnoreCase(byCity);
        } else {
            geos = repository.findAll(pageable);
            totalCount = repository.count();
        }

        GetAllGeoResponse.Builder responseBuilder = GetAllGeoResponse.newBuilder();


        geos.forEach(geo -> {
            Geo geoProto = Geo.newBuilder()
                    .setCity(geo.getCity())
                    .setCountry(CountryEntity.fromEntity(countryRepository.findById(geo.getCountry_id())))
                    .build();
            responseBuilder.addGeos(geoProto);
        });

        GetAllGeoResponse response = responseBuilder.setTotalCount(totalCount).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

//    public void addGeo(AddGeoRequest request, StreamObserver<AddGeoResponse> responseObserver) {
//        GeoAdd geoProto = GeoAdd.newBuilder()
//                .setId(UUID.randomUUID().toString())
//                .setCity(request.getCity())
//                .setCountryId(request.getCountryId())
//                .build();
//
//        GeoEntity geo = GeoEntity.fromProto(geoProto);
//
//        GeoEntity geoResponse = repository.save(geo);
//
//        AddGeoResponse response = AddGeoResponse.newBuilder()
//                .setGeo(GeoEntity.fromEntity(geoResponse))
//                .build();
//
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
//
//    public void updateGeo(UpdateGeoRequest request, StreamObserver<UpdateGeoResponse> responseObserver) {
//        GeoAdd GeoProto = GeoAdd.newBuilder()
//                .setId(request.getId())
//                .setCity(request.getCity())
//                .setCountryId(request.getCountryId())
//                .build();
//
//        GeoEntity geo = GeoEntity.fromProto(GeoProto);
//
//        GeoEntity geoResponse = repository.save(geo);
//
//        UpdateGeoResponse response = UpdateGeoResponse.newBuilder()
//                .setGeo(GeoEntity.fromEntity(geoResponse))
//                .build();
//
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
}
