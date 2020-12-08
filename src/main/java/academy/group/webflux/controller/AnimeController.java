package academy.group.webflux.controller;

import academy.group.webflux.domain.Anime;
import academy.group.webflux.service.AnimeService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("animes")
@Slf4j
@RequiredArgsConstructor
public class AnimeController {
  @Autowired private AnimeService animeService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Flux<Anime> listAll() {
    return this.animeService.findAll();
  }

  @GetMapping(path = "{id}")
  public Mono<Anime> findById(@PathVariable int id) {
    return this.animeService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Anime> save(@Valid @RequestBody Anime anime) {
    return animeService.save(anime);
  }

  @PutMapping(path = "{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> update(@PathVariable int id, @Valid @RequestBody Anime anime) {
    anime.setId(id);
    return animeService.update(anime);
  }

  @DeleteMapping(path = "{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> delete(@PathVariable int id) {
    return animeService.delete(id);
  }
}
