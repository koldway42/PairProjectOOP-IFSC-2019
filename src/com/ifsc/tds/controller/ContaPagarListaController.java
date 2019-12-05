package com.ifsc.tds.controller;

import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

import com.ifsc.tds.dao.ContaPagarDAO;
import com.ifsc.tds.dao.FavorecidoDAO;
import com.ifsc.tds.dao.TipoContaDAO;
import com.ifsc.tds.dao.UsuarioDAO;
import com.ifsc.tds.entity.ContaPagar;
import com.ifsc.tds.entity.TipoConta;
import com.ifsc.tds.entity.Usuario;

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
import javafx.scene.layout.GridPane;
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
    private Label txtDetalhesPagar;

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
    
    private ContaPagarDAO contasPagarDao;
    private List<ContaPagar> listaContasPagar;
    private ObservableList<ContaPagar> observableListContasPagar = FXCollections.observableArrayList();
    private UsuarioDAO usuarioDAO;
    private TipoContaDAO tipoContaDAO;
    private FavorecidoDAO favorecidoDAO;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	this.setContasPagarDao(new ContaPagarDAO());
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
		this.setListaContasPagar(this.getContasPagarDao().getAll());
		this.setObservableListContasPagar(FXCollections.observableArrayList(this.getListaContasPagar()));

		this.tbvContasPagar.setItems(this.getObservableListContasPagar());
	}

	public void selecionarItemTableViewContasPagar(ContaPagar contaPagar) {
		if (contaPagar != null) {
			this.txtDescricao.setText(contaPagar.getDescricao());
			this.txtDatapgto.setText(contaPagar.getDataPagamento().toString());
			this.txtDataVenc.setText(contaPagar.getDataVencimento().toString());
			this.txtDataCad.setText(contaPagar.getDataCadastro().toString());
			this.txtValorTotal.setText(contaPagar.getValorTotal().toString());
			this.txtUsuario.setText(contaPagar.getUsuario().getNome());
			this.txtTipoConta.setText(contaPagar.getTipoConta().getNome());
			this.txtFavorecido.setText(contaPagar.getFavorecido().getNome());
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

    }

    @FXML
    void onClickBtnExcluir(ActionEvent event) {

    }

    @FXML
    void onClickBtnIncluir(ActionEvent event) {
    	ContaPagar contasPagar = new ContaPagar();
		boolean btnConfirmarClic = this.showTelaTipoContaEditar(contasPagar, UsuarioListaController.USUARIO_INCLUIR);
		if (btnConfirmarClic) {
			this.getContasPagarDao().save(contasPagar);
			this.carregarTableViewContasPagar();
		}
    }
    
    public boolean showTelaTipoContaEditar(ContaPagar contaPagar, String operacao) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ifsc/tds/view/ContaPagarEdit.fxml"));
			Parent usuarioEditXML = loader.load();

			// Criando uma janela e colocando o layout do xml nessa janela
			Stage janelaContaPagar = new Stage();
			janelaContaPagar.setTitle("Cadastro de usuário" + operacao);
			janelaContaPagar.initModality(Modality.APPLICATION_MODAL);
			janelaContaPagar.resizableProperty().setValue(Boolean.FALSE);

			Scene tipoContaEditLayout = new Scene(usuarioEditXML);
			janelaContaPagar.setScene(tipoContaEditLayout);

			// Setando o cliente no Controller.
			ContaPagarEditController contaPagarEditController = loader.getController();
			contaPagarEditController.setJanelaContaPagarEdit(janelaContaPagar);
			contaPagarEditController.setContaPagar(contaPagar);

			// Mostra o Dialog e espera até que o usuário feche
			janelaContaPagar.showAndWait();

			return contaPagarEditController.isOkClick();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public ContaPagarDAO getContasPagarDao() {
		return contasPagarDao;
	}

	public void setContasPagarDao(ContaPagarDAO contasPagarDao) {
		this.contasPagarDao = contasPagarDao;
	}

	public List<ContaPagar> getListaContasPagar() {
		return listaContasPagar;
	}

	public void setListaContasPagar(List<ContaPagar> listaContasPagar) {
		this.listaContasPagar = listaContasPagar;
	}

	public ObservableList<ContaPagar> getObservableListContasPagar() {
		return observableListContasPagar;
	}

	public void setObservableListContasPagar(ObservableList<ContaPagar> observableListContasPagar) {
		this.observableListContasPagar = observableListContasPagar;
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
