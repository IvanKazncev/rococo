package rococo.gateway.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rococo.gateway.dto.painting.PaintingCreateDto;
import rococo.gateway.dto.painting.PaintingJsonDto;
import rococo.gateway.dto.painting.PaintingRelationJsonDto;
import rococo.gateway.service.PageImpl;
import rococo.gateway.service.PaintingService;

@RestController
@RequestMapping("api/painting")
public class PaintingController {
    final PaintingService service;

    @Autowired
    public PaintingController(PaintingService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageImpl<PaintingRelationJsonDto>> read(@RequestParam(value="title", required = false) String title,
                                                                  @PageableDefault Pageable pageable) throws InvalidProtocolBufferException {
        PageImpl<PaintingRelationJsonDto> data = service.getPaintingAll(title, pageable);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> readById(@PathVariable("id") String id) throws InvalidProtocolBufferException {
        String data = service.getPaintingById(id);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/author/{artistId}")
    public ResponseEntity<PageImpl<PaintingRelationJsonDto>> readById(@PathVariable("artistId") String artistId, @PageableDefault Pageable pageable) throws InvalidProtocolBufferException {
        PageImpl<PaintingRelationJsonDto> data = service.getPaintingByArtistId(artistId, pageable);
        return ResponseEntity.ok(data);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<String> create(@RequestBody PaintingCreateDto painting) throws InvalidProtocolBufferException {
        String data = service.addPainting(painting);
        return ResponseEntity.ok(data);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping
    public ResponseEntity<String> update(@RequestBody PaintingJsonDto painting) throws InvalidProtocolBufferException {
        String data = service.updatePainting(painting);
        return ResponseEntity.ok(data);
    }
}
