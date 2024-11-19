package com.Rye.DarknessGame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Main extends ApplicationAdapter {

    //region Variables
    Player playcor;
    InputHandler handler;
    Texture image;
    SceneManager sceneManager;
    int sceneNumber = 0;
    Scene sceneToRender;
    Hud hud;
    CollisionMask collisionMask;
    DarknessLayer darknessLayer;
    Monster monster;
    boolean monsterAlive = true;
    SoundPlayer DJ;
    ArrayList<StaticLightSource> staticLightSources;
    //endregion

    public void create() {

        DJ = new SoundPlayer();
        collisionMask = new CollisionMask();
        hud = new Hud();
        handler = new InputHandler();
        monster = new Monster(collisionMask.getPixmap());
        playcor = new Player(100, 5000, 8, DJ, handler, hud, collisionMask, monster, this);

        initLightSources();
        darknessLayer = new DarknessLayer(playcor, staticLightSources);

        monster.setPlayer(playcor);
        collisionMask.setCamera(playcor.getCamera());

        hud.setCamera(playcor.getCamera(), playcor.cameraZoom, playcor.getBattery());
        com.badlogic.gdx.Gdx.input.setInputProcessor(handler);

        initScenes();
        stage();
    }

    public void initScenes() {

        Scene levelOne = new Scene("First Stage", Gdx.audio.newSound(Gdx.files.internal("Ambience/Ambience.mp3")),
            DJ, playcor, image = new Texture("CollisionMap/collisionMap.png"));
        Scene levelTwo = new Scene("Second Stage",Gdx.audio.newSound(Gdx.files.internal(("Music/MenuTheme.mp3"))),
            DJ,playcor, image = new Texture("FloorTex/MenuScreen.jpg"));

        sceneManager = new SceneManager();
        sceneManager.addScene(levelOne);
        sceneManager.addScene(levelTwo);
    }

    public void initLightSources() {

        StaticLightSource testLight = new StaticLightSource(5000, 1000, .2f, MathFunctions.rayCast(1000, 181
            , 90, 1000, 5000, collisionMask.getPixmap()));
        StaticLightSource nextLight = new StaticLightSource(5000, 2000, .2f, MathFunctions.rayCast(1000, 181
            , 90, 2000, 5000, collisionMask.getPixmap()));
        staticLightSources = new ArrayList<>();
        staticLightSources.add(testLight);
        staticLightSources.add(nextLight);
    }

    public void stage() {
        sceneToRender = sceneManager.getScenes().get(sceneNumber);
    }

    public float secondsToNano(float seconds) {
        return (seconds * 1000000000);
    }

    public void killMonster(Monster monster) {
        monster = null;
        monsterAlive = false;
        System.gc();
    }

    public void render() {
        System.out.println("(main) FPS:" + Gdx.graphics.getFramesPerSecond());

        sceneToRender.renderScene();
        playcor.updatePlayer();
        playcor.checkBullets();

        if(Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)){
            sceneNumber += 1;
            stage();
        }


        if (monsterAlive) {
            monster.updateMonster();
        }

        darknessLayer.render(0f);
        hud.renderHud();
    }
}
