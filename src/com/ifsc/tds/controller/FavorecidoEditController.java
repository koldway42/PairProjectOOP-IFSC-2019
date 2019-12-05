	package com.ifsc.tds.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.ifsc.tds.entity.Favorecido;
import com.ifsc.tds.entity.TipoConta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

	public class FavorecidoEditController {

	    @FXML
	    private Label lblNome;

	    @FXML
	    private TextField txtNomeValor;

	    @FXML
	    private Label lblNumeroTitular;

	    @FXML
	    private TextField txtDataValor;

	    @FXML
	    private Label lblValor;

	    @FXML
	    private TextField txtValorValor;

	    @FXML
	    private TextField txtNumeroTitularValor;

	    @FXML
	    private Button btnOk;

	    @FXML
	    private Button btnCancela;
	    
	    private Stage janelaFavorecidoEdit;
		private Favorecido favorecido;
		private boolean okClick = false;
		
	    @FXML
	    void clickCancela(ActionEvent event) {
	    	this.janelaFavorecidoEdit.close();
	    }

	    @FXML
	    void clickOK(ActionEvent event) {
	    	if (validarCampos()) {
	    		this.favorecido.setNome(this.txtNomeValor.getText());
	    		this.favorecido.setNumeroTitular(this.txtNumeroTitularValor.getText());
	    		this.favorecido.setValor(this.txtValorValor.getText());

				this.okClick = true;
				this.janelaFavorecidoEdit.close();
			}
	    }

		public void initialize(URL location, ResourceBundle resources) {
		}

		/**
		 * Atribui a janela ao controle
		 * 
		 * @param janelaFavorecidoEdit
		 */
		public void setJanelaFavorecidoEdit(Stage janelaFavorecidoEdit) {
			this.janelaFavorecidoEdit = janelaFavorecidoEdit;
		}

		/**
		 * Atribui o favorecido que ser� editado na janela.
		 * 
		 * @param favorecido
		 */

		public void setFavorecido(Favorecido favorecido) {
			this.favorecido = favorecido;

			this.txtNomeValor.setText(favorecido.getNome());
			this.txtNumeroTitularValor.setText(favorecido.getNumeroTitular());
			this.txtValorValor.setText(favorecido.getValor());
		}

		/**
		 * Retorna verdadeiro se o favorecido clicou o bot�o OK, sen�o retorna false.
		 * 
		 * @return boolean
		 */

		public boolean isOkClick() {
			return okClick;
		}

		/**
		 * Valida os dados digitados nos campos da tela.
		 * 
		 * @return true se os dados forem v�lidos
		 */

		private boolean validarCampos() {
			String mensagemErros = "";
			
			if (this.txtNomeValor.getText() == null || this.txtNomeValor.getText().length() == 0) {
				
				mensagemErros += "Informe o nome!\n";
			}
			if (this.txtNumeroTitularValor.getText() == null || this.txtNumeroTitularValor.getText().length() == 0) {
			
				mensagemErros += "Informe o Numero do Titular!\n";
			}
			if (this.txtValorValor.getText() == null || this.txtValorValor.getText().length() == 0) {
				
				mensagemErros += "Informe o valor!\n";
			}
			if (mensagemErros.length() == 0) {
			
				return true;
			} else {
				// Mostrando os erros.
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.initOwner(this.janelaFavorecidoEdit);
				alerta.setTitle("Dados inv�lidos!");
				alerta.setHeaderText("Favor corrigir as seguintes informa��es:");
				alerta.setContentText(mensagemErros);

				alerta.showAndWait();

				return false;
			}
		}

	
		

		public void setJanelaTipoContaEdit(Stage janelaTipoContaEditar) {
			// TODO Auto-generated method stub
			
		}

		public void setTipoConta(TipoConta tipoConta) {
			// TODO Auto-generated method stub
			
		}


	}
