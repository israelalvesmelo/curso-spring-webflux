package controller;

import academy.group.webflux.domain.Anime;
import academy.group.webflux.repository.AnimeRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/animes")
@Slf4j
public class AnimeController {
    private AnimeRepository animeRepository;

    @GetMapping
    public Flux<Anime> listAll(){
        System.out.println("entrou aqui");
        return animeRepository.findAll();
    }
}
