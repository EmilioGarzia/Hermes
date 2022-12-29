package unipa.prog3.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import unipa.prog3.controller.service.SendingService;
import unipa.prog3.model.entity.Cliente;

public class SendController {
    // Mittente
    @FXML
    private TextField senderNameField, senderSurnameField;
    @FXML
    private TextField senderCountryField, senderTownField, senderCAPField, senderAddressField;
    @FXML
    private TextField senderEmailField, senderPhoneField;

    // Collo
    @FXML
    private TextField widthField, heightField, depthField, weightField;

    // Destinatario
    @FXML
    private TextField receiverNameField, receiverSurnameField;
    @FXML
    private TextField receiverCountryField, receiverTownField, receiverCAPField, receiverAddressField;
    @FXML
    private TextField receiverEmailField, receiverPhoneField;

    private final SendingService service;

    public SendController() {
        service = new SendingService();
    }

    @FXML
    public void send() {
        // Mittente
        String senderName = senderNameField.getText();
        String senderSurname = senderSurnameField.getText();
        String senderCountry = senderCountryField.getText();
        String senderTown = senderTownField.getText();
        int senderCAP = Integer.parseInt(senderCAPField.getText());
        String senderAddress = senderAddressField.getText();
        String senderEmail = senderEmailField.getText();
        String senderPhone = senderPhoneField.getText();
        Cliente sender = new Cliente(senderName, senderSurname);
        sender.setStato(senderCountry);
        sender.setCittà(senderTown);
        sender.setCap(senderCAP);
        sender.setIndirizzo(senderAddress);
        sender.setEmail(senderEmail);
        sender.setTelefono(senderPhone);

        // Collo
        float width = Float.parseFloat(widthField.getText());
        float height = Float.parseFloat(heightField.getText());
        float depth = Float.parseFloat(depthField.getText());
        float weight = Float.parseFloat(weightField.getText());

        // Destinatario
        String receiverName = receiverNameField.getText();
        String receiverSurname = receiverSurnameField.getText();
        String receiverCountry = receiverCountryField.getText();
        String receiverTown = receiverTownField.getText();
        int receiverCAP = Integer.parseInt(receiverCAPField.getText());
        String receiverAddress = receiverAddressField.getText();
        String receiverEmail = receiverEmailField.getText();
        String receiverPhone = receiverPhoneField.getText();
        Cliente receiver = new Cliente(receiverName, receiverSurname);
        receiver.setStato(receiverCountry);
        receiver.setCittà(receiverTown);
        receiver.setCap(receiverCAP);
        receiver.setIndirizzo(receiverAddress);
        receiver.setEmail(receiverEmail);
        receiver.setTelefono(receiverPhone);
        service.send(sender, receiver, weight);
    }
}
