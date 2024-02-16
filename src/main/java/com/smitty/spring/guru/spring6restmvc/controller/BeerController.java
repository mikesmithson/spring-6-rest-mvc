package com.smitty.spring.guru.spring6restmvc.controller;

import com.smitty.spring.guru.spring6restmvc.model.Beer;
import com.smitty.spring.guru.spring6restmvc.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beers")
@Slf4j
public class BeerController {
    private final BeerService beerService;
    @PostMapping
    public ResponseEntity createABeer(@RequestBody Beer beer) throws URISyntaxException {
        Beer createdBeer = beerService.createABeer(beer);
        return ResponseEntity.created(new URI("/api/v1/beers/beer/" + createdBeer.getId())).build();
    }

    @GetMapping("/beer/{id}")
    public Beer getById(@PathVariable("id") UUID id) {
        log.debug("Inside the getById controller {}", id.toString());
        return beerService.getBeerById(id);
    }

    @GetMapping
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }
}
