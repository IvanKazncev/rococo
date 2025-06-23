package rococo.gateway.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.shared.proto.paintings.*;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rococo.gateway.dto.painting.PaintingCreateDto;
import rococo.gateway.dto.painting.PaintingJsonDto;
import rococo.gateway.dto.painting.PaintingRelationJsonDto;

import java.util.List;

@Service
public class PaintingService {
    private final Channel channel;

    @Autowired
    public PaintingService(@Value("${rococo-paintings.host}") String host,
                         @Value("${rococo-paintings.port}") int port) {
        int newMaxMessageSize = 52_428_800;
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .maxInboundMessageSize(newMaxMessageSize)
                .usePlaintext()
                .build();
    }

    public PageImpl<PaintingRelationJsonDto> getPaintingAll(String title, Pageable pageable) {
        PaintingServiceGrpc.PaintingServiceBlockingStub stub = PaintingServiceGrpc.newBlockingStub(channel);
        GetAllPaintingRequest request = GetAllPaintingRequest.newBuilder()
                .setPage(pageable.getPageNumber())
                .setSize(pageable.getPageSize())
                .setTitle((title != null && !title.isEmpty()) ? title : "")
                .build();

        GetAllPaintingResponse response = stub.getPaintings(request);
        List<PaintingRelationJsonDto> paintingJsonDtoList = response.getPaintingsList()
                .stream()
                .map(PaintingRelationJsonDto::fromGrpcMessage)
                .toList();

        return new PageImpl<>(paintingJsonDtoList, pageable, response.getTotalCount());
    }

    public String getPaintingById(String id) throws InvalidProtocolBufferException {
        PaintingServiceGrpc.PaintingServiceBlockingStub stub = PaintingServiceGrpc.newBlockingStub(channel);
        GetPaintingByIdRequest request = GetPaintingByIdRequest.newBuilder()
                .setId(id)
                .build();

        GetPaintingByIdResponse response = stub.getPaintingById(request);
        Painting painting = response.getPaintings();

        return JsonFormat.printer().print(painting);
    }

    public PageImpl<PaintingRelationJsonDto> getPaintingByArtistId(String artistId, Pageable pageable) {
        PaintingServiceGrpc.PaintingServiceBlockingStub stub = PaintingServiceGrpc.newBlockingStub(channel);
        GetPaintingByArtistRequest request = GetPaintingByArtistRequest.newBuilder()
                .setArtistId(artistId)
                .setPage(pageable.getPageNumber())
                .setSize(pageable.getPageSize())
                .build();

        GetPaintingByArtistResponse response = stub.getPaintingByArtist(request);
        List<PaintingRelationJsonDto> paintingJsonDtoList = response.getPaintingsList()
                .stream()
                .map(PaintingRelationJsonDto::fromGrpcMessage)
                .toList();

        return new PageImpl<>(paintingJsonDtoList, pageable, response.getTotalCount());
    }

    public String addPainting(PaintingCreateDto painting) throws InvalidProtocolBufferException {
        PaintingServiceGrpc.PaintingServiceBlockingStub stub = PaintingServiceGrpc.newBlockingStub(channel);

        ArtistId artistId = ArtistId.newBuilder().setId(painting.getArtist().getId()).build();
        MuseumId museumId = MuseumId.newBuilder().setId(painting.getMuseum().getId()).build();

        AddPaintingRequest request = AddPaintingRequest.newBuilder()
                .setTitle(painting.getTitle())
                .setDescription(painting.getDescription())
                .setContent(painting.getContent())
                .setArtist(artistId)
                .setMuseum(museumId)
                .build();

        Painting response = stub.addPainting(request).getPainting();

        return JsonFormat.printer().print(response);
    }

    public String updatePainting(PaintingJsonDto painting) throws InvalidProtocolBufferException {
        PaintingServiceGrpc.PaintingServiceBlockingStub stub = PaintingServiceGrpc.newBlockingStub(channel);

        ArtistId artistId = ArtistId.newBuilder().setId(painting.getArtist().getId()).build();
        MuseumId museumId = MuseumId.newBuilder().setId(painting.getMuseum().getId()).build();

        UpdatePaintingRequest request = UpdatePaintingRequest.newBuilder()
                .setId(painting.getId())
                .setTitle(painting.getTitle())
                .setDescription(painting.getDescription())
                .setContent(painting.getContent())
                .setArtist(artistId)
                .setMuseum(museumId)
                .build();

        Painting response = stub.updatePainting(request).getPainting();

        return JsonFormat.printer().print(response);
    }
}