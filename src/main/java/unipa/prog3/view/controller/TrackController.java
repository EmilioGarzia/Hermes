package unipa.prog3.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import unipa.prog3.MainApplication;
import unipa.prog3.controller.service.PackageService;
import unipa.prog3.controller.service.ServiceProvider;
import unipa.prog3.model.relation.Collo;

/**
 * Classe che gestisce le interazioni dell'utente con i widget della track-view.fxml
 * */
public class TrackController extends Controller {
    @FXML
    private TextField codeField;
    @FXML
    private Label errorLabel;
    private static Collo collo;


    /**
     * Analizza il codice di tracciamento inserito, in caso di successo carica la tracking-view.fxml
     * */
    @FXML
    public void onTrace() {
        errorLabel.setTextFill(Color.RED);
        PackageService packageService = (PackageService) ServiceProvider.getService(Collo.class);
        collo = packageService.select(codeField.getText());
        if (collo != null)
            MainApplication.getMainController().showView("/unipa/prog3/view/tracking-view.fxml");
        else errorLabel.setText("Il codice inserito non è valido!");
    }

    public static Collo getCollo() {
        return collo;
    }

    @Override
    public void onResume() {}
}
