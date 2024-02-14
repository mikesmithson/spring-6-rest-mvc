package com.smitty.spring.guru.spring6restmvc.service;

import com.smitty.spring.guru.spring6restmvc.model.Beer;
import com.smitty.spring.guru.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class BeerService {
    private static final Map<UUID, Beer> beerCatalog = new LinkedHashMap<>();

    static {
        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        Arrays.asList(beer1, beer2, beer3).forEach(beer -> beerCatalog.put(beer.getId(), beer));
    }

    public List<Beer> listBeers() {
        return new ArrayList<>(beerCatalog.values());
    }

    public Beer getBeerById(UUID id) {
        log.debug("Inside the Beer Service getting beer by id {}", id);
        return beerCatalog.get(id);
    }

    public Beer createABeer(Beer beer) {
        Beer newBeer =   Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        beerCatalog.put(newBeer.getId(), newBeer);
        return newBeer;
    }
}
