package com.ifsc.tds.controller;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.ifsc.tds.dao.UsuarioDAO;
import com.ifsc.tds.entity.ContaPagar;
import com.ifsc.tds.entity.Usuario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ContaPagarEditController implements Initializable {

	@FXML
	private Label lblDescricao;

	@FXML
	private TextField txtDescricao;

	@FXML
	private Label lblUsuario;

	@FXML
	private ComboBox<Usuario> cbxUsuario;

	@FXML
	private Label lblDataVencimento;

	@FXML
	private DatePicker dtpDataVencimento;

	@FXML
	private Button btnOk;

	@FXML
	private Button btnCancela;

	private Stage janelaContaPagarEdit;
	private ContaPagar contaPagar;
	private boolean okClick = false;

	private List<Usuario> listaUsuarios;
	private UsuarioDAO UsuarioDAO;
	private ObservableList<Usuario> observableListaUsuarios;

	@FXML
	void clickCancela(ActionEvent event) {
		this.janelaContaPagarEdit.close();
	}

	@FXML
	void clickOK(ActionEvent event) {
		if (validarCampos()) {
			this.contaPagar.setDescricao(this.txtDescricao.getText());
			this.contaPagar.setUsuario(this.cbxUsuario.getSelectionModel().getSelectedItem());
			this.contaPagar.setDataVencimento(Date.valueOf(this.dtpDataVencimento.getValue()));

			this.okClick = true;
			this.janelaContaPagarEdit.close();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.UsuarioDAO = new UsuarioDAO();

		this.carregarComboBoxUsuarios();
	}

	/**
	 * Atribui a janela ao controle
	 * 
	 * @param janelaContaPagarEdit
	 */
	public void setJanelaContaPagarEdit(Stage janelaContaPagarEdit) {
		this.janelaContaPagarEdit = janelaContaPagarEdit;
	}

	/**
	 * Atribui a conta a pagar que será editado na janela.
	 * 
	 * @param contaPagar
	 */
	public void setContaPagar(ContaPagar contaPagar) {
		this.contaPagar = contaPagar;

		this.txtDescricao.setText(contaPagar.getDescricao());

	}

	/**
	 * Retorna verdadeiro se o usuário clicou o botão OK, senão retorna false.
	 * 
	 * @return boolean
	 */
	public boolean isOkClick() {
		return okClick;
	}

	/**
	 * Valida os dados digitados nos campos da tela.
	 * 
	 * @return true se os dados forem válidos
	 */
	private boolean validarCampos() {
		String mensagemErros = "";

		if (this.txtDescricao.getText() == null || this.txtDescricao.getText().length() == 0) {
			mensagemErros += "Informe a descrição!\n";
		}

		if (mensagemErros.length() == 0) {
			return true;
		} else {
			// Mostrando os erros.
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.initOwner(this.janelaContaPagarEdit);
			alerta.setTitle("Dados inválidos!");
			alerta.setHeaderText("Favor corrigir as seguintes informações:");
			alerta.setContentText(mensagemErros);

			alerta.showAndWait();

			return false;
		}
	}

	/**
	 * Carrega a tela com os dados disponíveis dos usuários para servir como chave estrangeira da conta a pagar
	 * no campo usuario_id.
	 * 
	 * @return void
	 */
	public void carregarComboBoxUsuarios() {
		this.listaUsuarios = this.UsuarioDAO.getAll();

		this.observableListaUsuarios = FXCollections.observableArrayList(listaUsuarios);
		this.cbxUsuario.setItems(this.observableListaUsuarios);
	}
}
