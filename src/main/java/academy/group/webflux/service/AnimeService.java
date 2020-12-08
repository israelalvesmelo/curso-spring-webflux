package academy.group.webflux.service;

import academy.group.webflux.domain.Anime;
import academy.group.webflux.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AnimeService {
    @Autowired
    private AnimeRepository animeRepository;

    public Flux<Anime> findAll() {
        return animeRepository.findAll();
    }

    public Mono<Anime> findById(int id) {
        return this.animeRepository.findById(id)
                .switchIfEmpty(this.monoResponseStatusNotFoundException());
    }

    private <T> Mono<T> monoResponseStatusNotFoundException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "anime not found"));
    }

    public Mono<Anime> save(Anime anime) {
        return this.animeRepository.save(anime);
    }

    public Mono<Void> update(Anime anime) {
        return findById(anime.getId())
                .flatMap(this::save)
                .then();
    }

    public Mono<Void> delete(int id) {
        return this.animeRepository.deleteById(id);
    }
}
