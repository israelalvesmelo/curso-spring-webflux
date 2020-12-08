package academy.group.webflux.integration;

import academy.group.webflux.domain.Anime;
import academy.group.webflux.repository.AnimeRepository;
import academy.group.webflux.service.AnimeService;
import academy.group.webflux.utils.AnimeCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@Import(AnimeService.class)
public class AnimeControlerIT {

  @MockBean private AnimeRepository animeRepository;

  @Autowired private WebTestClient testClient;

  private final Anime anime = AnimeCreator.createValidAnime();

  @BeforeEach
  public void setUp() {
    BDDMockito.when(animeRepository.findAll()).thenReturn(Flux.just(anime));

    BDDMockito.when(animeRepository.findById(ArgumentMatchers.anyInt()))
        .thenReturn(Mono.just(anime));
  }

  @Test
  public void blockHoundWorks() {
    try {
      FutureTask<?> task =
          new FutureTask<>(
              () -> {
                Thread.sleep(0);
                return "";
              });
      Schedulers.parallel().schedule(task);

      task.get(10, TimeUnit.SECONDS);
      Assertions.fail("should fail");
    } catch (Exception e) {
      Assertions.assertTrue(e.getCause() instanceof BlockingOperationError);
    }
  }

  @Test
  @DisplayName("listAll returns a flux of anime")
  public void listAll_ReturnFluxOfAnime_WhenSuccessful(){
    testClient
            .get()
            .uri("/animes")
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody()
            .jsonPath("$.[0].id").isEqualTo(anime.getId())
            .jsonPath("$.[0].name").isEqualTo(anime.getName());
  }

    @Test
    @DisplayName("listAll returns a flux of anime")
    public void listAll2_ReturnFluxOfAnime_WhenSuccessful(){
        testClient
                .get()
                .uri("/animes")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Anime.class)
                .hasSize(1)
                .contains(anime);
    }
}
