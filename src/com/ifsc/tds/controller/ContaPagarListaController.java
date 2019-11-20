package com.ifsc.tds.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.ifsc.tds.dao.ContaPagarDAO;
import com.ifsc.tds.entity.ContaPagar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ContaPagarListaController implements Initializable {

	@FXML
	private TableView<ContaPagar> tbvContasPagar;

	@FXML
	private TableColumn<ContaPagar, Integer> tbcCodigo;

	@FXML
	private TableColumn<ContaPagar, String> tbcDescricao;

	@FXML
	private Label lblDescricao;

	@FXML
	private Label lblLogin;

	@FXML
	private Label lblDescricaoValor;

	@FXML
	private Label lblUsuarioValor;

	@FXML
	private Button btnIncluir;

	@FXML
	private Button btnEditar;

	@FXML
	private Button btnExcluir;

	/**
	 * Lista de usuários.
	 */
	private List<ContaPagar> listaContasPagar;
	private ObservableList<ContaPagar> observableListaContasPagar = FXCollections.observableArrayList();
	private ContaPagarDAO contaPagarDAO;

	public static final String CONTA_PAGAR_EDITAR = " - Editar";
	public static final String CONTA_PAGAR_INCLUIR = " - Incluir";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.setContaPagarDAO(new ContaPagarDAO());
		this.carregarTableViewContasPagar();
		this.selecionarItemTableViewContasPagar(null);

		// Adicionado evento diante de quaisquer alteração na seleção de itens do
		// TableView
		this.tbvContasPagar.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewContasPagar(newValue));

	}

	public void carregarTableViewContasPagar() {

		// preparando as colunas que irão aparecer na tabela
		this.tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tbcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

		// Consulta os usuários da base e depois joga para tela
		this.setListaContasPagar(this.getContaPagarDAO().getAll());
		this.setObservableListaContasPagar(FXCollections.observableArrayList(this.getListaContasPagar()));

		this.tbvContasPagar.setItems(this.getObservableListaContasPagar());
	}

	public void selecionarItemTableViewContasPagar(ContaPagar contaPagar) {
		if (contaPagar != null) {
			this.lblDescricaoValor.setText(contaPagar.getDescricao());
			this.lblUsuarioValor.setText(contaPagar.getUsuario().getNome());
		} else {
			this.lblDescricaoValor.setText("");
			this.lblUsuarioValor.setText("");
		}
	}

	public boolean onCloseQuery() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Pergunta");
		alert.setHeaderText("Deseja sair da lista de contas a pagar?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alert.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}

	public ContaPagarDAO getContaPagarDAO() {
		return contaPagarDAO;
	}

	public void setContaPagarDAO(ContaPagarDAO contaPagarDAO) {
		this.contaPagarDAO = contaPagarDAO;
	}

	public List<ContaPagar> getListaContasPagar() {
		return listaContasPagar;
	}

	public void setListaContasPagar(List<ContaPagar> listaContasPagar) {
		this.listaContasPagar = listaContasPagar;
	}

	public ObservableList<ContaPagar> getObservableListaContasPagar() {
		return observableListaContasPagar;
	}

	public void setObservableListaContasPagar(ObservableList<ContaPagar> observableListaContasPagar) {
		this.observableListaContasPagar = observableListaContasPagar;
	}

	@FXML
	public void onClickBtnEditar(ActionEvent event) {

	}

	@FXML
	public void onClickBtnExcluir(ActionEvent event) {

	}

	@FXML
	public void onClickBtnIncluir(ActionEvent event) {
		ContaPagar contaPagar = new ContaPagar();
		boolean btnConfirmarClic = this.showTelaContaPagarEditar(contaPagar,
				ContaPagarListaController.CONTA_PAGAR_INCLUIR);
		if (btnConfirmarClic) {
			this.getContaPagarDAO().save(contaPagar);
			this.carregarTableViewContasPagar();
		}
	}

	public boolean showTelaContaPagarEditar(ContaPagar contaPagar, String operacao) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ifsc/tds/view/ContaPagarEdit.fxml"));
			Parent contaPagarEditXML = loader.load();

			// Criando uma janela e colocando o layout do xml nessa janela
			Stage janelaContaPagarEditar = new Stage();
			janelaContaPagarEditar.setTitle("Cadastro de conta a pagar" + operacao);
			janelaContaPagarEditar.initModality(Modality.APPLICATION_MODAL);
			janelaContaPagarEditar.resizableProperty().setValue(Boolean.FALSE);

			Scene contaPagarEditLayout = new Scene(contaPagarEditXML);
			janelaContaPagarEditar.setScene(contaPagarEditLayout);

			// Setando o cliente no Controller.
			ContaPagarEditController contaPagarEditController = loader.getController();
			contaPagarEditController.setJanelaContaPagarEdit(janelaContaPagarEditar);
			contaPagarEditController.setContaPagar(contaPagar);

			// Mostra o Dialog e espera até que o usuário feche
			janelaContaPagarEditar.showAndWait();

			return contaPagarEditController.isOkClick();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
