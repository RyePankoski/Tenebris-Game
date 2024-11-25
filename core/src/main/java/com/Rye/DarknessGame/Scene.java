package com.Rye.DarknessGame;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

public class Scene {

    Sound ambience;
    SoundPlayer soundPlayer;
    String stageName;
    Player player;
    Texture image;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;
    Matrix4 projection;

    public Scene(String stageName, Sound ambience, SoundPlayer soundPlayerRef, Player player, Texture image) {
        this.stageName = stageName;
        this.ambience = ambience;
        this.image = image;
        this.player = player;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        soundPlayer = soundPlayerRef;

        projection = player.getCamera().combined;
        shapeRenderer.setProjectionMatrix(projection);
    }

    public void renderScene() {
        batch.begin();
        batch.setProjectionMatrix(projection);

        float margin = player.cameraZoom * 0.25f;

// Calculate the area to draw
        float drawWidth = player.cameraZoom + margin * 2.5f;  // Add margin
        float drawHeight = player.cameraZoom + margin * 2.5f;

// Calculate the starting texture coordinates
        float startXInTexture = player.getCoorX() - drawWidth / 2;
        float startYInTexture = player.getCoorY() + drawHeight / 2;

// Draw the image with a larger texture region
        batch.draw(image,
            (int)(player.getCoorX() - drawWidth / 2),  // Adjust for margin
            (int)(player.getCoorY() - drawHeight / 2), // Adjust for margin
            (int)drawWidth,                            // Adjusted width
            (int)drawHeight,                           // Adjusted height
            (int)startXInTexture,                      // Start X in texture
            (int)(image.getHeight() - startYInTexture),// Start Y in texture
            (int)drawWidth,                            // Width of texture region
            (int)drawHeight,                           // Height of texture region
            false, false);
        batch.end();

        soundPlayer.playSound(stageName, ambience);
    }
}
