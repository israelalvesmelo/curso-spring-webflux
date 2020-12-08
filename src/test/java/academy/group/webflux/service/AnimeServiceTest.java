package academy.group.webflux.service;

import academy.group.webflux.domain.Anime;
import academy.group.webflux.repository.AnimeRepository;
import academy.group.webflux.utils.AnimeCreator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {

  @InjectMocks private AnimeService animeService;

  @Mock private AnimeRepository animeRepository;

  private final Anime anime = AnimeCreator.createValidAnime();

  @BeforeAll
  public static void bloclHoundSetup() {
    BlockHound.install();
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

  @BeforeEach
  public void setUp(){
    BDDMockito.when(animeRepository.findAll())
            .thenReturn(Flux.just(anime));

    BDDMockito.when(animeRepository.findById(ArgumentMatchers.anyInt()))
            .thenReturn(Mono.just(anime));

    BDDMockito.when(animeRepository.save(AnimeCreator.createAnimeToBeSaved()))
            .thenReturn(Mono.just(anime));

    BDDMockito.when(animeRepository.delete(ArgumentMatchers.any(Anime.class)))
            .thenReturn(Mono.empty());

    BDDMockito.when(animeRepository.deleteById(ArgumentMatchers.anyInt()))
            .thenReturn(Mono.empty());

    BDDMockito.when(animeRepository.save(AnimeCreator.createValidUpdateAnime()))
            .thenReturn(Mono.empty());
  }

  @Test
  @DisplayName("findAll returns a flux of anime")
  public void findAll_ReturnsAFluxOfAnime_WhenSucessful() {
    StepVerifier.create(animeService.findAll())
            .expectSubscription()
            .expectNext(anime)
            .verifyComplete();
  }

  @Test
  @DisplayName("findById returns Mono with anime it exists")
  public void findById_returnMonoAnime_WhenSucessful(){
    StepVerifier.create(animeService.findById(1))
            .expectSubscription()
            .expectNext(anime)
            .verifyComplete();
  }

  @Test
  @DisplayName("findById returns Mono error when does not exist")
  public void findById_ReturnsMonoError_WhenEmptyMonoIsReturned(){
    BDDMockito.when(animeRepository.findById(ArgumentMatchers.anyInt()))
        .thenReturn(Mono.empty());

    StepVerifier.create(animeService.findById(1))
            .expectSubscription()
            .expectError(ResponseStatusException.class)
            .verify();
  }

  @Test
  @DisplayName("save creates an Anime successful")
  public void save_createsAnime_WhenSuccessful(){
    Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

    StepVerifier.create(animeService.save(animeToBeSaved))
            .expectSubscription()
            .expectNext(anime)
            .verifyComplete();
  }


  @Test
  @DisplayName("delete remove the anime when successful")
  public void delete_RemovesAnime_WhenSuccessful(){
    StepVerifier.create(animeService.delete(1))
            .expectSubscription()
            .verifyComplete();
  }

  @Test
  @DisplayName("update an Anime successful")
  public void update_updateAnime_WhenSuccessful(){
    Anime animeToBeSaved = AnimeCreator.createValidUpdateAnime();
    BDDMockito.when(animeService.findById(animeToBeSaved.getId()))
            .thenReturn(Mono.just(animeToBeSaved));
    StepVerifier.create(animeService.update(animeToBeSaved))
            .expectSubscription()
            .verifyComplete();
  }

  @Test
  @DisplayName("update an Anime return error when anime not exist")
  public void update_returnError_WhenAnimeNotExist(){
    Anime animeToBeSaved = AnimeCreator.createValidUpdateAnime();
    BDDMockito.when(animeService.findById(animeToBeSaved.getId()))
            .thenReturn(Mono.empty());
    StepVerifier.create(animeService.update(animeToBeSaved))
            .expectSubscription()
            .expectError(ResponseStatusException.class)
            .verify();
  }
}
