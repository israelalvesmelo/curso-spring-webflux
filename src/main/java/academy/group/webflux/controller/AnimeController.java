package academy.group.webflux.controller;

import academy.group.webflux.domain.Anime;
import academy.group.webflux.service.AnimeService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("animes")
@Slf4j
@RequiredArgsConstructor
public class AnimeController {
    @Autowired
    private AnimeService animeService;

    @GetMapping
    public Flux<Anime> listAll() {
        return this.animeService.findAll();
    }

    @GetMapping(path = "{id}")
    public Mono<Anime> findById(@PathVariable int id) {
        return this.animeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Anime> save (@Valid @RequestBody Anime anime){
        return animeService.save(anime);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> update (@Valid @RequestBody Anime anime){
        return animeService.update(anime);
    }
}
