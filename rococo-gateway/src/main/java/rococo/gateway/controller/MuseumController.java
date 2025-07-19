package rococo.gateway.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rococo.gateway.dto.museum.MuseumCreateDto;
import rococo.gateway.dto.museum.MuseumJsonDto;
import rococo.gateway.dto.museum.MuseumRelationJsonDto;
import rococo.gateway.service.MuseumService;
import rococo.gateway.service.PageImpl;

@RestController
@RequestMapping("api/museum")
public class MuseumController {
    final MuseumService service;

    @Autowired
    public MuseumController(MuseumService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageImpl<MuseumRelationJsonDto>> read(@RequestParam(value="title", required = false) String title,
                                                                @PageableDefault Pageable pageable) throws InvalidProtocolBufferException {
        PageImpl<MuseumRelationJsonDto> data = service.getMuseumsAll(title, pageable);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> readById(@PathVariable("id") String id) throws InvalidProtocolBufferException {
        String data = service.getMuseumById(id);
        return ResponseEntity.ok(data);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<String> create(@RequestBody MuseumCreateDto museum) throws InvalidProtocolBufferException {
        String data = service.addMuseum(museum);
        return ResponseEntity.ok(data);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping
    public ResponseEntity<String> update(@RequestBody MuseumJsonDto museum) throws InvalidProtocolBufferException {
        String data = service.updateMuseum(museum);
        return ResponseEntity.ok(data);
    }
}
