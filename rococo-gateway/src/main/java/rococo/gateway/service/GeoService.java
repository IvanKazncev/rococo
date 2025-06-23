package rococo.gateway.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.shared.proto.geos.*;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rococo.gateway.dto.*;
import rococo.gateway.dto.geo.GeoRelationJsonDto;

import java.util.List;

@Service
public class GeoService {
    private final Channel channel;

    @Autowired
    public GeoService(@Value("${rococo-geos.host}") String host,
                         @Value("${rococo-geos.port}") int port) {
        int newMaxMessageSize = 52_428_800;
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .maxInboundMessageSize(newMaxMessageSize)
                .usePlaintext()
                .build();
    }

    public PageImpl<GeoRelationJsonDto> getGeosAll(String city, Pageable pageable) throws InvalidProtocolBufferException {
        GeoServiceGrpc.GeoServiceBlockingStub stub = GeoServiceGrpc.newBlockingStub(channel);
        GetAllGeoRequest request = GetAllGeoRequest.newBuilder()
                .setPage(pageable.getPageNumber())
                .setSize(pageable.getPageSize())
                .setCity((city != null && !city.isEmpty()) ? city : "")
                .build();

        GetAllGeoResponse response = stub.getGeos(request);
        List<GeoRelationJsonDto> geoJsonDtoList = response.getGeosList()
                .stream()
                .map(GeoRelationJsonDto::fromGrpcMessage)
                .toList();

        return new PageImpl<>(geoJsonDtoList, pageable, response.getTotalCount());
    }

    public PageImpl<CountryJsonDto> getCountriesAll(String name, Pageable pageable) throws InvalidProtocolBufferException {
        CountryServiceGrpc.CountryServiceBlockingStub stub = CountryServiceGrpc.newBlockingStub(channel);
        GetAllCountryRequest request = GetAllCountryRequest.newBuilder()
                .setPage(pageable.getPageNumber())
                .setSize(pageable.getPageSize())
                .setName((name != null && !name.isEmpty()) ? name : "")
                .build();

        GetAllCountryResponse response = stub.getCountries(request);
        List<CountryJsonDto> countryJsonDtoList = response.getCountriesList()
                .stream()
                .map(CountryJsonDto::fromGrpcMessage)
                .toList();

        return new PageImpl<>(countryJsonDtoList, pageable, response.getTotalCount());
    }

//    public String addGeo(GeoCreateDto geo) throws InvalidProtocolBufferException {
//        GeoServiceGrpc.GeoServiceBlockingStub stub = GeoServiceGrpc.newBlockingStub(channel);
//
//        CountryId countryId = CountryId.newBuilder().setId(geo.getCountry_id()).build();
//
//        AddGeoRequest request = AddGeoRequest.newBuilder()
//                .setCity(geo.getCity())
//                .setCountry(countryId)
//                .build();
//
//        Geo response = stub.addGeo(request).getGeo();
//
//        return JsonFormat.printer().print(response);
//    }
//
//    public String updateGeo(GeoJsonDto geo) throws InvalidProtocolBufferException {
//        GeoServiceGrpc.GeoServiceBlockingStub stub = GeoServiceGrpc.newBlockingStub(channel);
//
//        CountryId countryId = CountryId.newBuilder().setId(geo.getCountry_id()).build();
//
//        UpdateGeoRequest request = UpdateGeoRequest.newBuilder()
//                .setId(geo.getId())
//                .setCity(geo.getCity())
//                .setCountry(countryId)
//                .build();
//
//        Geo response = stub.updateGeo(request).getGeo();
//
//        return JsonFormat.printer().print(response);
//    }
}