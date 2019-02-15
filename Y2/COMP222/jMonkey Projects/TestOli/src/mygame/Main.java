package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(10);
        /*
        float MODEL_SIZE = (float) 0.2;
        //setting materials
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Brown);
        mat2.setColor("Color", ColorRGBA.Pink);
        
        //creating the Oli Object
        Geometry oliObj = (Geometry) assetManager.loadModel("Models/modelOli/modelOli.j3o");
        oliObj.scale(MODEL_SIZE, MODEL_SIZE, MODEL_SIZE);
        oliObj.setMaterial(mat2);
        
        //creating the monkey
        Geometry monkeyObj = (Geometry) assetManager.loadModel("Models/Monkey/Monkey.j3o");
        monkeyObj.scale(MODEL_SIZE, MODEL_SIZE, MODEL_SIZE);
        monkeyObj.setLocalTranslation(0, -1, 0);
        monkeyObj.setMaterial(mat);
        
        //creating the box
        //Box b = new Box(1, 1, 1);
        //Geometry geom = new Geometry("Box", b);
        //geom.setMaterial(mat);
        */
        
        // This material allows you to apply lighting.
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        
        // Setting models (or nodes)
        Node monkey = (Node) assetManager.loadModel("Models/Monkey/Monkey.j3o");
        Node oliObj = (Node) assetManager.loadModel("Models/modelOli/modelOli.j3o");
        
        // Applying the materials
        monkey.setMaterial(mat);
        oliObj.setMaterial(mat);
        
        // Scaling and translating models
        oliObj.scale(0.07f);
        oliObj.setLocalTranslation(0, 0.99f, 0);
        
        // Putting the nodes in the game
        rootNode.attachChild(monkey);
        rootNode.attachChild(oliObj);
        
        // Creating lights
        DirectionalLight sun = new DirectionalLight();
        DirectionalLight sun1 = new DirectionalLight();
        
        // Applying the directoin of the light from both sides
        sun.setDirection(new Vector3f(1, 1, -1));
        sun1.setDirection(new Vector3f(1, 1, 1));
        sun.setColor(ColorRGBA.White);
        sun1.setColor(ColorRGBA.Red);
        
        // Adding the light to the game
        rootNode.addLight(sun);
        rootNode.addLight(sun1);
       
        //Adding object into the scene
        //rootNode.attachChild(oliObj);
        //rootNode.attachChild(monkeyObj);
        //rootNode.attachChild(geom);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
