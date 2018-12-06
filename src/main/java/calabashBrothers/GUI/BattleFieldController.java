package calabashBrothers.GUI;

import calabashBrothers.beings.Creature;
import calabashBrothers.beings.enums.CalabashName;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;


/**
 * @ Author     ：Young
 * @ Description：用于展示战场
 */
public class BattleFieldController {

    public Label helloWorld;

    public BattleFieldController() {

    }

    public void sayHelloWorld(ActionEvent actionEvent) {
        this.helloWorld.setText("Hello World");
    }

}
