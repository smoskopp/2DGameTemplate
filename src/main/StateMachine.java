package main;

import java.awt.Graphics2D;
import java.util.Map;

import main.entity.*;
import main.state.entity.EntityState;

/**
 *
 */
public class StateMachine {

    public Map<String, StateGenerator> states;
    public EntityState current;
    public StateGenerator stateGenerator;

    public StateMachine(Map<String, StateGenerator> states) {
        this.states = states;
    }

    public void change(String stateName, Map<String, Object> enterParams) {
        if(this.current != null)
            this.current.exit();
        stateGenerator = states.get(stateName);
        this.current = stateGenerator.generate();
        this.current.enter(enterParams);
    }

    public void processAI(Map<String, Object> params, float dt){

    }

    public void update(float dt) {
        this.current.update(dt);
    }

    public void render(Graphics2D g2d) {
        current.render(g2d);
    }
}
