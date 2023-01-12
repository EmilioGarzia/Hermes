package unipa.prog3.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import unipa.prog3.MainApplication;
import unipa.prog3.controller.service.ClientService;
import unipa.prog3.controller.service.ServiceProvider;
import unipa.prog3.model.entity.Cliente;
import unipa.prog3.model.entity.util.ClientBuilder;

public class ClientController extends Controller {
    @FXML
    private TextField nameField, surnameField;
    @FXML
    private TextField countryField, townField, CAPField, addressField;
    @FXML
    private TextField emailField, phoneField;

    @FXML
    public void aggiungiIndirizzo() {
        ClientBuilder builder = new ClientBuilder();
        builder.setNome(nameField.getText())
                .setCognome(surnameField.getText())
                .setStato(countryField.getText())
                .setCittà(townField.getText())
                .setCap(Integer.parseInt(CAPField.getText()))
                .setIndirizzo(addressField.getText())
                .setEmail(emailField.getText())
                .setTelefono(phoneField.getText());
        ClientService service = (ClientService) ServiceProvider.getService(Cliente.class);
        if (service.findRecord(builder.getCliente()) == null)
            service.insert(builder.getCliente());

        MainApplication.getMainController().navigateBack();
    }

    @Override
    public void onResume() {}

    @FXML
    public void annulla() {
        MainApplication.getMainController().navigateBack();
    }
}
