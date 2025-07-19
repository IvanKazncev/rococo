package rococo.gateway.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rococo.gateway.dto.*;
import rococo.gateway.service.GeoService;
import rococo.gateway.service.PageImpl;

@RestController
@RequestMapping()
public class GeoController {
    final GeoService service;

    @Autowired
    public GeoController(GeoService service) {
        this.service = service;
    }

    @GetMapping("api/country")
    public ResponseEntity<PageImpl<CountryJsonDto>> readCountries(@RequestParam(value="name", required = false) String name,
                                                         @PageableDefault Pageable pageable) throws InvalidProtocolBufferException {
        PageImpl<CountryJsonDto> data = service.getCountriesAll(name, pageable);
        return ResponseEntity.ok(data);
    }

}
