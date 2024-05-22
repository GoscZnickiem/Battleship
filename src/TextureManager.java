import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class TextureManager {

	public TextureManager() {
        textures = new HashMap<>();
		loadAllTextures("assets");
    }

	private void loadAllTextures(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));

        if (files != null) {
            for (File file : files) {
                try {
                    BufferedImage texture = ImageIO.read(file);
                    String textureName = file.getName().substring(0, file.getName().lastIndexOf('.'));
                    textures.put(textureName, texture);
                } catch (IOException e) {
                    System.err.println("Error loading texture: " + file.getName());
                    e.printStackTrace();
                }
            }
        } else {
            System.err.println("The folder path is invalid or does not contain any PNG files.");
        }
    }

	public BufferedImage getTexture(String name) {
		BufferedImage texture = textures.get(name);
		if (texture == null) {
			texture = textures.get(NULL_TEXTURE_NAME);
		}
		return texture;
	}

	private final String NULL_TEXTURE_NAME = "nulltex";
	private Map<String, BufferedImage> textures;

}
