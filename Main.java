import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

class Main {
    private static int width;
    private static int height;
    private static boolean enableText = false;

    private static Color[] colors = new Color[] {
        new Color(105,210,231),
        new Color(167,219,216),
        new Color(224,228,204),
        new Color(243,134,48),
        new Color(250,105,0),
        new Color(236,208,120),
        new Color(217,91,67),
        new Color(192,41,66),
        new Color(250,208,137),
        new Color(83,119,122)
    };
    private static int colorIndex = 0;

    public static void main(String[] args) throws IOException {
        if(args.length < 3) {
            printUsage();
            return;
        }
        if(args[2] == null || args[2].equals("")) {
            System.out.println(
                    "Please specify an output filename. Exiting now.");
            return;
        }
        for(int i=0; i<2; i++) {
            if(args[i] == null || args[i].equals("")) {
                printUsage();
                return;
            }
        }

        width = Integer.valueOf(args[0]);
        height = Integer.valueOf(args[1]);

        final BufferedImage img = new BufferedImage(
                width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics g = img.getGraphics();
        final Graphics2D g2 = (Graphics2D)g;

        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);

        //testDrawRect(g);
        //drawRects(g);
        drawRatios(g);

        ImageIO.write(img, "png", new File(args[2]));
    }

    private static void printUsage() {
        System.out.println("Usage: java Main 2048 1536 2048x1536.png");
    }

    private static void testDrawRect(Graphics g) {
        width = height = 4;
        drawRect(g, 2, 2);
    }
    private static void drawRects(Graphics g) {
        drawRect(g, 2048, 1536, "iPad Air");
        drawRect(g, 1920, 1200, "Nexus 7 2013");
        drawRect(g, 1920, 1080, "Galaxy S4");
        drawRect(g, 1420, 964);
        drawRect(g, 1280, 800, "Nexus 7 2012");
        drawRect(g, 1280, 720, "Galaxy Note II");
        drawRect(g, 1024, 768);
        drawRect(g, 1136, 640, "iPhone 5/5s");
        drawRect(g, 960, 640, "iPhone 4/4s");
        drawRect(g, 800, 480, "HTC Desire");
        drawRect(g, 480, 320, "iPhone 3G/3GS");
        drawRect(g, 5120, 2160, "4K"); 
        drawRect(g, 4096, 2160, "DCI 4K");
    }
    private static void drawRatios(Graphics g) {
        drawRatio(g, 2048, 1536, "iPad Air");
        drawRatio(g, 1920, 1200, "Nexus 7 2013");
        drawRatio(g, 1920, 1080, "Galaxy S4");
        drawRatio(g, 1420, 964);
        drawRatio(g, 1280, 800, "Nexus 7 2012");
        drawRatio(g, 1280, 720, "Galaxy Note II");
        drawRatio(g, 1024, 768);
        drawRatio(g, 1136, 640, "iPhone 5/5s");
        drawRatio(g, 960, 640, "iPhone 4/4s");
        drawRatio(g, 800, 480, "HTC Desire");
        drawRatio(g, 480, 320, "iPhone 3G/3GS");
        //drawRatio(g, 5120, 2160, "4K"); 
        //drawRatio(g, 4096, 2160, "DCI 4K");
    }

    private static void drawRect(Graphics g, int w, int h) {
        drawRect(g, w, h, "");
    }
    private static void drawRect(Graphics g, int w, int h, String comment) {
        int x = (width - w) / 2;
        int y = (height - h) / 2;
        final Color c = colors[colorIndex++ % colors.length];
        g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 16));
        g.fillRect(x, y, w-1, h-1);
        g.setColor(c);
        g.drawRect(x, y, w-1, h-1);
        final String addon = (enableText && !isNullOrEmpty(comment))?
                " ("+comment+") ":"";
        g.drawString(w+"x"+h+addon, x+2, y+14);
    }

    private static void drawRatio(Graphics g, int w, int h) {
        drawRatio(g, w, h, "");
    }
    private static void drawRatio(
            Graphics g, int w, int h, String comment) {
        float screen = (float)width / height;
        System.out.println(
                String.format("drawing %dx%d: %f", w, h, (float)w/h));
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
            if(isNullOrEmpty(comment)) {
                g.drawString(
                    w+" x "+h+" - "+ratio, nx + 2, ny + 14);
            } else {
                g.drawString(
                    w+" x "+h+" - "+ratio+" ("+comment+")", nx + 2, ny + 14);
            }
        }
    }

    public static boolean isNullOrEmpty(String s) {
        if(s == null) return true;
        if(s.equals("")) return true;
        return false;
    }
}
