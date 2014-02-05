import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Pixmap;
import java.io.IOException;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Pixmap.Format;
import java.nio.ByteBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Game implements ApplicationListener {
    private OrthographicCamera camera;
    private boolean isTaken = false;
    private ShapeRenderer renderer;
    private int width = 2000; //2048;
    private int height = 600; //1536;

    @Override
    public void create () {
        System.out.println("game created");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        renderer = new ShapeRenderer();
    }

    private void clear() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void render () {
        int w = 50;
        int h = 50;
        clear();
        renderer.begin(ShapeType.Line);
        renderer.setColor(1, 0, 0, 1);
        renderer.rect(0, 0, w, h);
        renderer.rect(width-w, height-h, w, h);
        renderer.rect(width-w, 0, w, h);
        renderer.rect(0, height-h, w, h);
        renderer.end();
        if(!isTaken) {
            saveScreenshot(Gdx.files.local("screenshot.png"));
            isTaken = true;
        }   
    }
    

    @Override
    public void resize (int width, int height) {
        System.out.println("game resized");
    }

    @Override
    public void pause () {
        System.out.println("game paused");
    }

    @Override
    public void resume () {
        System.out.println("game resumed");
    }

    @Override
    public void dispose () {
    }

    //=============
    //modified from https://code.google.com/p/libgdx-users/wiki/Screenshots
    public void saveScreenshot(FileHandle file) {
            Pixmap pixmap = getScreenshot(
                    0, 0, 
                    Gdx.graphics.getWidth(), 
                    Gdx.graphics.getHeight(), true);
            PixmapIO.writePNG(file, pixmap);
    }
    
    public Pixmap getScreenshot(int x, int y, int w, int h, boolean flipY) {
            Gdx.gl.glPixelStorei(GL10.GL_PACK_ALIGNMENT, 1);
            
            final Pixmap pixmap = new Pixmap(w, h, Format.RGBA8888);
            ByteBuffer pixels = pixmap.getPixels();
            Gdx.gl.glReadPixels(x, y, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, pixels);
            
            final int numBytes = w * h * 4;
            byte[] lines = new byte[numBytes];
            if (flipY) {
                    final int numBytesPerLine = w * 4;
                    for (int i = 0; i < h; i++) {
                            pixels.position((h - i - 1) * numBytesPerLine);
                            pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
                    }
                    pixels.clear();
                    pixels.put(lines);
            } else {
                    pixels.clear();
                    pixels.get(lines);
            }
            
            return pixmap;
    }
}
