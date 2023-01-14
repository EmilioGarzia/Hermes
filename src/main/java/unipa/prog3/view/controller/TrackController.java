package unipa.prog3.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import unipa.prog3.MainApplication;
import unipa.prog3.controller.service.PackageService;
import unipa.prog3.controller.service.ServiceProvider;
import unipa.prog3.model.relation.Collo;

public class TrackController extends Controller {
    @FXML
    private TextField codeField;
    private static Collo collo;

    @FXML
    public void onTrace() {
        PackageService packageService = (PackageService) ServiceProvider.getService(Collo.class);
        collo = packageService.select(codeField.getText());
        MainApplication.getMainController().showView("/unipa/prog3/user-view.fxml");
    }

    public static Collo getCollo() {
        return collo;
    }

    @Override
    public void onResume() {}
}
