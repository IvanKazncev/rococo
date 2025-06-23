package rococo.gateway.controller;

import com.google.protobuf.InvalidProtocolBufferException;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rococo.gateway.dto.artist.ArtistCreateDto;
import rococo.gateway.dto.artist.ArtistJsonDto;
import rococo.gateway.service.ArtistService;
import rococo.gateway.service.PageImpl;

@RestController
@RequestMapping("api/artist")
public class ArtistController {

    final ArtistService service;

    @Autowired
    public ArtistController(ArtistService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageImpl<ArtistJsonDto>> read(@RequestParam(value="name", required = false) String name,
                                                        @PageableDefault Pageable pageable) throws InvalidProtocolBufferException {
        PageImpl<ArtistJsonDto> data = service.getArtistsAll(name, pageable);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> readById(@PathVariable("id") String id) throws InvalidProtocolBufferException {
        String data = service.getArtistById(id);
        return ResponseEntity.ok(data);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<String> create (@RequestBody ArtistCreateDto artist) throws InvalidProtocolBufferException {
        String data = service.addArtist(artist);
        return ResponseEntity.ok(data);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping
    public ResponseEntity<String> update (@RequestBody ArtistJsonDto artist) throws InvalidProtocolBufferException {
        String data = service.updateArtist(artist);
        return ResponseEntity.ok(data);
    }
}
