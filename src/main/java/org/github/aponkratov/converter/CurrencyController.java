package org.github.aponkratov.converter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.Currency;
import java.util.ResourceBundle;
import java.util.Set;

public class CurrencyController implements Initializable {

    @FXML
    private JFXComboBox<Currency> currencyFrom;

    @FXML
    private JFXComboBox<Currency> currencyTo;

    @FXML
    private JFXTextField givenAmount;

    @FXML
    private JFXTextField convertedAmount;

    @FXML
    private JFXButton btnConvert;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Currency lists
        Set<Currency> availableCurrencies = Currency.getAvailableCurrencies();
        ObservableList<Currency> countryList = FXCollections.observableArrayList(availableCurrencies).sorted();
        currencyFrom.setItems(countryList);
        currencyTo.setItems(countryList);

        // Validators
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        DoubleValidator doubleValidator = new DoubleValidator();
        doubleValidator.setMessage("Enter numeric value");
        givenAmount.getValidators().addAll(doubleValidator);
        currencyFrom.getValidators().addAll(requiredFieldValidator);
        currencyTo.getValidators().addAll(requiredFieldValidator);

        //
        btnConvert.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED , e->{
            if (validateInput()){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        double doubleAmount = Double.parseDouble(givenAmount.getText());
                        CurrencyConverter converter = CurrencyConverter.getInstance();
                        converter.setProperties(
                                doubleAmount, currencyFrom.getValue(), currencyTo.getValue());
                        try {
                            double converted = converter.Convert();
                            convertedAmount.setText(Double.toString(converted));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            };
        });
    }

    private boolean validateInput() {
        if (currencyFrom.getSelectionModel().getSelectedIndex() != -1
                && currencyTo.getSelectionModel().getSelectedIndex() != -1) {
            return true;
        }
        return false;
    }
}
