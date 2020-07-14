package academy.group.webflux.repository;

import academy.group.webflux.domain.Anime;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AnimeRepository extends ReactiveCrudRepository<Anime, Integer> {
}
