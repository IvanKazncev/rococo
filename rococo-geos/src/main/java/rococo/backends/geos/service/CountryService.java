package rococo.backends.geos.service;

import com.shared.proto.geos.*;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import rococo.backends.geos.entity.CountryEntity;
import rococo.backends.geos.entity.GeoEntity;
import rococo.backends.geos.repository.CountryRepository;

@GRpcService
public class CountryService extends CountryServiceGrpc.CountryServiceImplBase {

    final CountryRepository repository;

    @Autowired
    public CountryService(CountryRepository repository) {
        this.repository = repository;
    }

    public void getCountries(GetAllCountryRequest request, StreamObserver<GetAllCountryResponse> responseObserver) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        String byName = request.getName();
        long totalCount = 0;

        Iterable<CountryEntity> countries;
        if (!byName.isEmpty()) {
            countries = repository.findByNameContainingIgnoreCase(byName, pageable);
            totalCount = repository.countByNameContainingIgnoreCase(byName);
        } else {
            countries = repository.findAll(pageable);
            totalCount = repository.count();
        }

        GetAllCountryResponse.Builder responseBuilder = GetAllCountryResponse.newBuilder();

        countries.forEach(country -> {
            Country countryProto = Country.newBuilder()
                    .setId(country.getId())
                    .setName(country.getName())
                    .build();
            responseBuilder.addCountries(countryProto);
        });

        GetAllCountryResponse response = responseBuilder.setTotalCount(totalCount).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void getCountryById(GetCountryByIdRequest request, StreamObserver<GetCountryByIdResponse> responseObserver) {
        CountryEntity country = repository.findById(request.getId());

        Country countryProto = Country.newBuilder()
                .setId(country.getId())
                .setName(country.getName())
                .build();

        GetCountryByIdResponse response = GetCountryByIdResponse.newBuilder()
                .setCountry(countryProto)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
