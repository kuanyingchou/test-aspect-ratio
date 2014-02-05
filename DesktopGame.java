import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopGame {
    public static void main (String[] args) {
        LwjglApplication app = new LwjglApplication(
                new Game(), "test-aspect-ratio", 1600, 600, false);
    }
}
