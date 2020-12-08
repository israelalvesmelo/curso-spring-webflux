package academy.group.webflux.utils;

import academy.group.webflux.builder.AnimeBuilder;
import academy.group.webflux.domain.Anime;

public class AnimeCreator {
    public static Anime createAnimeToBeSaved(){
        return AnimeBuilder.builder()
                .name("Test 1")
                .build();
    }

    public static Anime createValidAnime(){
        return AnimeBuilder.builder()
                .id(1)
                .name("Test 2")
                .build();
    }

    public static Anime createValidUpdateAnime(){
        return AnimeBuilder.builder()
                .id(2)
                .name("Test 3")
                .build();
    }
}
