package academy.group.webflux.repository;

import academy.group.webflux.domain.Anime;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AnimeRepository extends ReactiveCrudRepository<Anime, Integer> {
    Mono<Anime> findAllById(int id);
}
