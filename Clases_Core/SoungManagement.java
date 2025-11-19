package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

public class SoundManagement {

    private static SoundManagement instance;
    private Sound explosionSound;
    private Sound hurtSound;
    private Sound shootSound;
    private Music menuMusic;
    private Music gameMusic;

    private SoundManagement() {
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        hurtSound      = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        shootSound     = Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"));
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("cancionMenu.wav"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("cancionJuego.wav"));
        menuMusic.setLooping(true);
        gameMusic.setLooping(true);
        menuMusic.setVolume(0.5f);
        gameMusic.setVolume(0.5f);
    }

    public static SoundManagement getInstance() {
        if (instance == null) {
            instance = new SoundManagement();
        }
        return instance;
    }

    public void playExplosion() { explosionSound.play(0.4f); }
    public void playHurt()      { hurtSound.play(0.7f); }
    public void playShoot()     { shootSound.play(0.6f); }

    public void playMenuMusic() {
        stopAllMusic();
        menuMusic.play();
    }

    public void playGameMusic() {
        stopAllMusic();
        gameMusic.play();
    }

    public void stopAllMusic() {
        menuMusic.stop();
        gameMusic.stop();
    }

    public void dispose() {
        explosionSound.dispose();
        hurtSound.dispose();
        shootSound.dispose();
        menuMusic.dispose();
        gameMusic.dispose();
    }
}
