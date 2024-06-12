package com.example.theater.controllers;

import com.example.theater.converters.TheaterPlayConverter;
import com.example.theater.dto.TheaterPlayIn;
import com.example.theater.dto.TheaterPlayOut;
import com.example.theater.entities.TheaterPlay;
import com.example.theater.services.TheaterPlayService;
import com.example.theater.validations.ValidationError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/theater-plays")
public class TheaterPlayController {

    private final TheaterPlayService theaterPlayService;

    @Operation(summary = "Get list of all theater plays in the database", description = "No authentication required")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "204", description = "No content", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(hidden = true)))
    @GetMapping
    public ResponseEntity<List<TheaterPlayOut>> getTheaterPlays() {
        log.info("Request getTheaterPlays received");
        List<TheaterPlay> theaterPlays = theaterPlayService.getAllPlays();
        if (theaterPlays == null) {
            log.info("getTheaterPlays NOT FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (theaterPlays.isEmpty()) {
            log.info("getTheaterPlays NO CONTENT");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        log.info("Found {} theater plays", theaterPlays.size());
        return ResponseEntity.status(HttpStatus.OK)
                             .body(TheaterPlayConverter.convertEntityListToTheaterPlayOutList(theaterPlays));
    }


    @Operation(summary = "Add new theater play", description = "Admin role required")
    @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(hidden = true)))
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Object> addTheaterPlay(@Valid @RequestBody TheaterPlayIn theaterPlayIn,
                                                 BindingResult bindingResult) {
        log.info("Request addTheaterPlay received");
        log.debug("theaterPlayIn: {}", theaterPlayIn);
        if (bindingResult.hasErrors()) {
            log.warn("Validation error for POST addTheaterPlay: {}", bindingResult.getFieldErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(new ValidationError(bindingResult.getFieldErrors()));
        }

        TheaterPlay theaterPlayDb = theaterPlayService.getPlayByName(theaterPlayIn.getTitle());
        if (theaterPlayDb != null) {
            log.info("addTheaterPlay CONFLICT, theater play already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        TheaterPlayOut theaterPlayOut = TheaterPlayConverter.convertEntityToTheaterPlayOut(
                theaterPlayService.addTheaterPlay(theaterPlayIn));
        log.debug("Added new play: {}", theaterPlayOut);
        return ResponseEntity.status(HttpStatus.CREATED).body(theaterPlayOut);
    }

    @Operation(summary = "Get theater play by id", description = "No authentication required")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(hidden = true)))
    @GetMapping(value = "/{id}")
    public ResponseEntity<TheaterPlayOut> getTheaterPlayById(@PathVariable Long id) {
        log.info("Request getTheaterPlayById received");
        TheaterPlay theaterPlay = theaterPlayService.getPlayById(id);
        if (theaterPlay == null) {
            log.info("getTheaterPlayById NOT FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Found theaterPlay with id {}", theaterPlay.getId());
        return ResponseEntity.status(HttpStatus.OK)
                             .body(TheaterPlayConverter.convertEntityToTheaterPlayFullOut(theaterPlay));
    }

    @Operation(summary = "Update theater play details", description = "Admin role required")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(hidden = true)))
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<TheaterPlayOut> updateTheaterPlayById(@PathVariable Long id,
                                                                @RequestBody TheaterPlayIn theaterPlayIn) {
        log.info("Request updateTheaterPlayById received");
        log.debug("Updating with theaterPlayIn for update: {}", theaterPlayIn);

        if (theaterPlayIn.getTitle() == null && theaterPlayIn.getPremiere() == null) {
            log.warn("Validation error for PATCH updateTheaterPlayById");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        TheaterPlay theaterPlay = theaterPlayService.updateTheaterPlay(id, theaterPlayIn);

        if (theaterPlay == null) {
            log.info("updateTheaterPlayById NOT FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        TheaterPlayOut theaterPlayOut = TheaterPlayConverter.convertEntityToTheaterPlayOut(theaterPlay);
        log.debug("Updated play: {}", theaterPlayOut);
        return ResponseEntity.status(HttpStatus.OK).body(theaterPlayOut);
    }


    @Operation(summary = "Delete theater play and all related scheduled plays and reservations", description = "Admin role required")
    @ApiResponse(responseCode = "202", description = "Accepted", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "406", description = "Not Acceptable", content = @Content(schema = @Schema(hidden = true)))
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTheaterPlayById(@PathVariable Long id) {
        log.info("Request deleteTheaterPlay received");
        if (theaterPlayService.getPlayById(id) == null) {
            log.info("Theater play with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            theaterPlayService.deleteTheaterPlayById(id);
        } catch (Exception e) {
            log.error("Error while deleting theater play with id {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        log.info("Theater play with id {} deleted", id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
