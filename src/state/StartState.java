package main.mario.state;

import main.mario.State;
import main.mario.mvc.Controller;
import main.mario.mvc.Model;
import main.mario.mvc.View;
import main.mario.mvc.controller.StandardController;
import main.mario.mvc.model.StartModel;
import main.mario.mvc.view.StartView;

import java.awt.*;

public class StartState implements State {

    public static StandardController controller;
    public static StartView view;
    public static StartModel model;

    public StartState(){
        model = new StartModel();
        controller = new StandardController(model);
        view = new StartView(model);
    }

    @Override
    public void update(float dt) {
        model.update(dt);
    }

    @Override
    public void render(Graphics2D g2d) {
        view.render(g2d);
    }

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public Model getModel() {
        return model;
    }
}
