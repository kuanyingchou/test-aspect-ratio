import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

class Main {
    //private static int width = 1420, height = 964; 
    //private static int width = 2048, height = 1536; 
    private static int width = 2048, height = 1280;
    public static String filename = "ratios2048-1.6.png";

    public static boolean enableText = true;
    private static Color[] colors = new Color[] {
        /*
        Color.white,
        Color.red,
        Color.green,
        Color.yellow,
        Color.cyan,
        Color.magenta,
        Color.orange,
        */
        new Color(105,210,231),
        new Color(167,219,216),
        new Color(224,228,204),
        new Color(243,134,48),
        new Color(250,105,0)
    };
    private static int colorIndex = 0;

    public static void main(String[] args) throws IOException {
        final BufferedImage img = new BufferedImage(
                width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics g = img.getGraphics();
        final Graphics2D g2 = (Graphics2D)g;

        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);

        //test(g);
        //drawRects(g);
        drawRatios(g);

        ImageIO.write(img, "png", new File(filename));
    }

    private static void test(Graphics g) {
        width = height = 4;
        drawRect(g, 2, 2);
    }
    private static void drawRects(Graphics g) {
        //drawRect(g, 2048, 1536);
        //drawRect(g, 1920, 1200);
        //drawRect(g, 1920, 1080);
        drawRect(g, 1420, 964);
        drawRect(g, 1280, 800);
        //drawRect(g, 1280, 720);
        //drawRect(g, 1024, 768);
        drawRect(g, 1136, 640);
        drawRect(g, 960, 640);
        //drawRect(g, 800, 480);
        //drawRect(g, 480, 320);
        //drawRect(g, 1420, 964);
    }
    private static void drawRatios(Graphics g) {
        drawRatio(g, 2048, 1536, "iPad Air");
        //drawRatio(g, 1920, 1200);
        ////drawRatio(g, 1920, 1080, "Galaxy S4");
        drawRatio(g, 1280, 800, "Kizipad I");
        //drawRatio(g, 1280, 720);
        //drawRatio(g, 1024, 768);
        drawRatio(g, 1136, 640);
        ////drawRatio(g, 960, 640, "iPhone 4s");
        ////drawRatio(g, 800, 480);
        ////drawRatio(g, 1024, 600);
        //drawRatio(g, 1, 1);
        ////drawRatio(g, 5120, 2160, "4K"); 
        ////drawRatio(g, 4096, 2160, "DCI 4K");
        drawRatio(g, 1420, 964);
        //drawRatio(g, 480, 320);
    }

    private static void drawRect(Graphics g, int w, int h) {
        int x = (width - w) / 2;
        int y = (height - h) / 2;
        g.setColor(colors[colorIndex++ % colors.length]);
        g.drawRect(x, y, w-1, h-1);
        g.drawString(w+"x"+h, x+2, y+14);
    }
    private static void drawRatio(Graphics g, int w, int h) {
        drawRatio(g, w, h, "");
    }
    private static void drawRatio(Graphics g, int w, int h, String comment) {
        float screen = (float)width / height;
        System.out.println(String.format("drawing %dx%d: %f", w, h, (float)w/h));
        float ratio = (float)w/h;
        g.setColor(colors[colorIndex++ % colors.length]);
        int nx, ny, nw, nh;
        nx = ny = nw = nh = 0;
        if(ratio > screen) {
            nx = 0;
            nh = Math.round(width / ratio);
            ny = (height - nh) / 2;
            nw = width;
        } else if(ratio < screen) {
            nw = Math.round(height * ratio);
            nx = (width - nw) / 2;
            ny = 0;
            nh = height;
        } else {
            nx = ny = 0;
            nw = width;
            nh = height;
        }
        g.drawRect(nx, ny, nw - 1, nh - 1);
        if(enableText) {
            //g.setColor(Color.black);
            g.drawString(w+" x "+h+" - "+ratio+" ("+comment+")", nx + 2, ny + 14);
        }
    }
}
