import model.Music;
import service.MusicService;

public class Main {
    public static void main(String[] args) {
        MusicService musicService = new MusicService();
        musicService.saveAllFromJson("olivia_rodrigo_songs.json");
        musicService.saveAllFromJson("taylor_swift_songs.json");


    }
}
