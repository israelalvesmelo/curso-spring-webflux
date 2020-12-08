package academy.group.webflux.builder;

import academy.group.webflux.domain.Anime;

public class AnimeBuilder {
    private static Anime anime;
    private AnimeBuilder(){

    }
    public static AnimeBuilder builder(){
        AnimeBuilder animeBuilder = new AnimeBuilder();
        animeBuilder.anime = new Anime();
        return animeBuilder;
    }

    public AnimeBuilder name(String name){
        anime.setName(name);
        return this;
    }
    public AnimeBuilder id(int id){
        anime.setId(id);
        return this;
    }

    public Anime build() {
        return anime;
    }
}
