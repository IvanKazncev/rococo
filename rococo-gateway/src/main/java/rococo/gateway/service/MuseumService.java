package rococo.gateway.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.shared.proto.museums.*;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rococo.gateway.dto.geo.GeoCreateDto;
import rococo.gateway.dto.museum.MuseumCreateDto;
import rococo.gateway.dto.museum.MuseumJsonDto;
import rococo.gateway.dto.museum.MuseumRelationJsonDto;

import java.util.List;

@Service
public class MuseumService {
    private final Channel channel;

    @Autowired
    public MuseumService(@Value("${rococo-museums.host}") String host,
                      @Value("${rococo-museums.port}") int port) {
        int newMaxMessageSize = 52_428_800;
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .maxInboundMessageSize(newMaxMessageSize)
                .usePlaintext()
                .build();
    }

    public PageImpl<MuseumRelationJsonDto> getMuseumsAll(String title, Pageable pageable) throws InvalidProtocolBufferException {
        MuseumServiceGrpc.MuseumServiceBlockingStub stub = MuseumServiceGrpc.newBlockingStub(channel);
        GetAllMuseumRequest request = GetAllMuseumRequest.newBuilder()
                .setPage(pageable.getPageNumber())
                .setSize(pageable.getPageSize())
                .setTitle((title != null && !title.isEmpty()) ? title : "")
                .build();

        GetAllMuseumResponse response = stub.getMuseums(request);
        List<MuseumRelationJsonDto> geoJsonDtoList = response.getMuseumList()
                .stream()
                .map(MuseumRelationJsonDto::fromGrpcMessage)
                .toList();

        return new PageImpl<>(geoJsonDtoList, pageable, response.getTotalCount());
    }

    public String getMuseumById(String id) throws InvalidProtocolBufferException {
        MuseumServiceGrpc.MuseumServiceBlockingStub stub = MuseumServiceGrpc.newBlockingStub(channel);
        GetMuseumByIdRequest request = GetMuseumByIdRequest.newBuilder()
                .setId(id)
                .build();

        GetMuseumByIdResponse response = stub.getMuseumById(request);
        MuseumRelation museum = response.getMuseums();

        return JsonFormat.printer().print(museum);
    }

    public String addMuseum(MuseumCreateDto museum) throws InvalidProtocolBufferException {
        MuseumServiceGrpc.MuseumServiceBlockingStub stub = MuseumServiceGrpc.newBlockingStub(channel);

        Museum request = Museum.newBuilder()
                .setTitle(museum.getTitle())
                .setDescription(museum.getDescription())
                .setPhoto(museum.getPhoto())
                .setCity(GeoCreateDto.fromEntity(museum.getGeo()).getCity())
                .setCountryId(GeoCreateDto.fromEntity(museum.getGeo()).getCountry().getId())
                .build();

        MuseumRelation response = stub.addMuseum(request).getMuseum();

        return JsonFormat.printer().print(response);
    }

    public String updateMuseum(MuseumJsonDto museum) throws InvalidProtocolBufferException {
        MuseumServiceGrpc.MuseumServiceBlockingStub stub = MuseumServiceGrpc.newBlockingStub(channel);

        Museum request = Museum.newBuilder()
                .setId(museum.getId())
                .setTitle(museum.getTitle())
                .setDescription(museum.getDescription())
                .setPhoto(museum.getPhoto())
                .setCity(GeoCreateDto.fromEntity(museum.getGeo()).getCity())
                .setCountryId(GeoCreateDto.fromEntity(museum.getGeo()).getCountry().getId())
                .build();

        MuseumRelation response = stub.updateMuseum(request).getMuseum();

        return JsonFormat.printer().print(response);
    }
}