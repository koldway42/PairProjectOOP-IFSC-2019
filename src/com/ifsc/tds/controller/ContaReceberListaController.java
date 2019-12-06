package com.ifsc.tds.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.ifsc.tds.dao.ContaPagarDAO;
import com.ifsc.tds.dao.ContaReceberDAO;
import com.ifsc.tds.dao.FavorecidoDAO;
import com.ifsc.tds.dao.TipoContaDAO;
import com.ifsc.tds.dao.UsuarioDAO;
import com.ifsc.tds.entity.ContaPagar;
import com.ifsc.tds.entity.ContaReceber;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ContaReceberListaController implements  Initializable{

    @FXML
    private TableView<ContaReceber> tbvContasReceber;

    @FXML
    private TableColumn<ContaReceber, Integer> tbcCodigo;

    @FXML
    private TableColumn<ContaReceber, String> tbcDescricao;

    @FXML
    private Label txtDetalhesReceber;

    @FXML
    private GridPane txtDataPagto;

    @FXML
    private Label lblDescricao;

    @FXML
    private Label lblDataPagto;

    @FXML
    private Label lblDataCad;

    @FXML
    private Label lblDataVenc;

    @FXML
    private Label lblValorTot;

    @FXML
    private Label lblUsuario;

    @FXML
    private Label lblTipoConta;

    @FXML
    private Label lblFavorecido;

    @FXML
    private Label txtDescricao;

    @FXML
    private Label txtDatapgto;

    @FXML
    private Label txtDataCad;

    @FXML
    private Label txtDataVenc;

    @FXML
    private Label txtValorTotal;

    @FXML
    private Label txtUsuario;

    @FXML
    private Label txtTipoConta;

    @FXML
    private Label txtFavorecido;

    @FXML
    private Button btnIncluir;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnExcluir;

    
    private ContaReceberDAO contasReceberDao;
    private List<ContaReceber> listaContasReceber;
    private ObservableList<ContaReceber> observableListContasReceber = FXCollections.observableArrayList();
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	this.setContasReceberDao(new ContaReceberDAO());
		this.carregarTableViewContasReceber();
		this.selecionarItemTableViewContasReceber(null);

		// Adicionado evento diante de quaisquer alteração na seleção de itens do
		// TableView
		this.tbvContasReceber.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewContasReceber(newValue));
	}
    
    public void carregarTableViewContasReceber() {

		// preparando as colunas que irão aparecer na tabela
		this.tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tbcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

		// Consulta os usuários da base e depois joga para tela
		this.setListaContasReceber(this.getContasReceberDao().getAll());
		this.setObservableListContasReceber(FXCollections.observableArrayList(this.getListaContasReceber()));

		this.tbvContasReceber.setItems(this.getObservableListContasReceber());
	}

	public void selecionarItemTableViewContasReceber(ContaReceber contaReceber) {
		if (contaReceber != null) {
			this.txtDescricao.setText(contaReceber.getDescricao());
			this.txtDatapgto.setText(contaReceber.getDataPagamento().toString());
			this.txtDataVenc.setText(contaReceber.getDataVencimento().toString());
			this.txtDataCad.setText(contaReceber.getDataCadastro().toString());
			this.txtValorTotal.setText(contaReceber.getValorTotal().toString());
			this.txtUsuario.setText(contaReceber.getUsuario().getNome());
			this.txtTipoConta.setText(contaReceber.getTipoConta().getNome());
			this.txtFavorecido.setText(contaReceber.getFavorecido().getNome());
		} else {
			this.txtDescricao.setText("");
			this.txtDatapgto.setText("");
			this.txtDataVenc.setText("");
			this.txtDataCad.setText("");
			this.txtValorTotal.setText("");
			this.txtUsuario.setText("");
			this.txtTipoConta.setText("");
			this.txtFavorecido.setText("");
		}
	}

    @FXML
    void onClickBtnEditar(ActionEvent event) {
    	ContaReceber contasReceber = this.tbvContasReceber.getSelectionModel().getSelectedItem();
		if (contasReceber != null) {
			boolean btnConfirmarClic = this.showTelaTipoContaEditar(contasReceber,UsuarioListaController.USUARIO_INCLUIR);
			if (btnConfirmarClic) {
				this.getContasReceberDao().update(contasReceber, null);
				this.carregarTableViewContasReceber();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Por favor, escolha um usuário na Tabela!");
			alert.show();
		}
    	
    }

    @FXML
    void onClickBtnExcluir(ActionEvent event) {
    	ContaReceber contasReceber = this.tbvContasReceber.getSelectionModel().getSelectedItem();    
    	if (contasReceber != null) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Pergunta");
    		alert.setHeaderText("Confirma a exclusão do usuario?\n" + contasReceber.getDescricao());
    	

			ButtonType buttonTypeNO = ButtonType.NO;
			ButtonType buttonTypeYES = ButtonType.YES;
			alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);

			Optional<ButtonType> resultado = alert.showAndWait();
			if (resultado.get() == ButtonType.YES) {
				this.getContasReceberDao().delete(contasReceber);
				this.carregarTableViewContasReceber();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Por favor, escolha um filme ou livro!");
			alert.show();
		}
	}
    
    	

    
    @FXML
    void onClickBtnIncluir(ActionEvent event) {
    	ContaReceber contaReceber = new ContaReceber();
		boolean btnConfirmarClic = this.showTelaTipoContaEditar(contaReceber, UsuarioListaController.USUARIO_INCLUIR);
		if (btnConfirmarClic) {
			this.getContasReceberDao().save(contaReceber);
			this.carregarTableViewContasReceber();
		}
    }
    
    public boolean showTelaTipoContaEditar(ContaReceber contasReceber, String operacao) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ifsc/tds/view/ContaReceberEdit.fxml"));
			Parent usuarioEditXML = loader.load();

			// Criando uma janela e colocando o layout do xml nessa janela
			Stage janelaContaReceber = new Stage();
			janelaContaReceber.setTitle("Cadastro de usuário" + operacao);
			janelaContaReceber.initModality(Modality.APPLICATION_MODAL);
			janelaContaReceber.resizableProperty().setValue(Boolean.FALSE);

			Scene contasPagarEditLayout = new Scene(usuarioEditXML);
			janelaContaReceber.setScene(contasPagarEditLayout);

			// Setando o cliente no Controller.
			ContaReceberEditController contaReceberEditController = loader.getController();
			contaReceberEditController.setJanelaContaPagarEdit(janelaContaReceber);
			contaReceberEditController.setContaReceber(contasReceber);

			// Mostra o Dialog e espera até que o usuário feche
			janelaContaReceber.showAndWait();

			return contaReceberEditController.isOkClick();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	public ContaReceberDAO getContasReceberDao() {
		return contasReceberDao;
	}

	public void setContasReceberDao(ContaReceberDAO contasReceberDao) {
		this.contasReceberDao = contasReceberDao;
	}

	public ObservableList<ContaReceber> getObservableListContasReceber() {
		return observableListContasReceber;
	}

	public void setObservableListContasReceber(ObservableList<ContaReceber> observableListContasReceber) {
		this.observableListContasReceber = observableListContasReceber;
	}

	public List<ContaReceber> getListaContasReceber() {
		return listaContasReceber;
	}

	public void setListaContasReceber(List<ContaReceber> listaContasReceber) {
		this.listaContasReceber = listaContasReceber;
	}

	public boolean onCloseQuery() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Pergunta");
		alert.setHeaderText("Deseja sair da lista de usuário?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alert.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}


}
