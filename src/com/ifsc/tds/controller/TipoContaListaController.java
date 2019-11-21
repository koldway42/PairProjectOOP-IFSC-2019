package com.ifsc.tds.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.ifsc.tds.dao.TipoContaDAO;
import com.ifsc.tds.dao.UsuarioDAO;
import com.ifsc.tds.entity.TipoConta;
import com.ifsc.tds.entity.Usuario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class TipoContaListaController implements Initializable {

    @FXML
    private TableView<TipoConta> tbvUsuarios;

    @FXML
    private TableColumn<TipoConta, Integer> tbcCodigo;

    @FXML
    private TableColumn<TipoConta, String> tbcTipodeconta;

    @FXML
    private Label lblTipoConta;

    @FXML
    private Label lblNomeValor;

    @FXML
    private Label lblLoginValor;

    @FXML
    private Label lblEmailValor;

    @FXML
    private Label lblDataCadastroValor;

    @FXML
    private TextField txtTipoConta;

    @FXML
    private TextField txtStatus;

    @FXML
    private Label lblStatus;

    @FXML
    private Button btnIncluir;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnExcluir;
    
    private TipoContaDAO tipoContaDao = new TipoContaDAO();
    
    private List<TipoConta> listaTipoConta;
    
    private ObservableList<TipoConta> observableListaTipoConta = FXCollections.observableArrayList();
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		this.carregarTableViewTipoConta();
	}

	public void carregarTableViewTipoConta() {

		// preparando as colunas que irão aparecer na tabela
		this.tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("status"));
		this.tbcTipodeconta.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		// Consulta os usuários da base e depois joga para tela
		this.setListaTipoConta(this.getTipoContaDao().getAll());
		if(listaTipoConta.size() != 0) {
			this.setObservableListaTipoConta(FXCollections.observableArrayList(this.getListaTipoConta()));
			this.tbvUsuarios.setItems(getObservableListaTipoConta());
		}
		
	}
	    
	    public boolean onCloseQuery() {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Pergunta");
			alert.setHeaderText("Deseja sair da lista de Tipos de Conta?");
			ButtonType buttonTypeNO = ButtonType.NO;
			ButtonType buttonTypeYES = ButtonType.YES;
			alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
			Optional<ButtonType> result = alert.showAndWait();
			return result.get() == buttonTypeYES ? true : false;
		}
	    
	    @FXML
	    void onClickBtnEditar(ActionEvent event) {

	    }

	    @FXML
	    void onClickBtnExcluir(ActionEvent event) {
	    	TipoConta tipoConta = this.tbvUsuarios.getSelectionModel().getSelectedItem();
			if (tipoConta != null) {

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Pergunta");
				alert.setHeaderText("Confirma a exclusão do usuário?\n" + tipoConta.getNome());

				ButtonType buttonTypeNO = ButtonType.NO;
				ButtonType buttonTypeYES = ButtonType.YES;
				alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);

				Optional<ButtonType> resultado = alert.showAndWait();
				if (resultado.get() == ButtonType.YES) {
					this.getTipoContaDao().delete(tipoConta);
					this.carregarTableViewTipoConta();
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Por favor, escolha um usuário na Tabela!");
				alert.show();
			}
	    }

	    @FXML
	    void onClickBtnIncluir(ActionEvent event) {
	    	TipoConta tipoConta = new TipoConta();
	    	tipoConta.setNome(txtTipoConta.getText());
	    	tipoConta.setStatus(Integer.parseInt(txtStatus.getText()));
	    	
	    	tipoContaDao.save(tipoConta);
	    	
	    	this.carregarTableViewTipoConta();
	    	
	    	txtTipoConta.setText("");
	    	txtStatus.setText("");
	    	
	    }
	    
	    public TipoContaDAO getTipoContaDao() {
			return tipoContaDao;
		}

		public void setTipoContaDao(TipoContaDAO tipoContaDao) {
			this.tipoContaDao = tipoContaDao;
		}
		
		public List<TipoConta> getListaTipoConta() {
			return listaTipoConta;
		}

		public void setListaTipoConta(List<TipoConta> listaTipoConta) {
			this.listaTipoConta = listaTipoConta;
		}
		
		public ObservableList<TipoConta> getObservableListaTipoConta() {
			return observableListaTipoConta;
		}

		public void setObservableListaTipoConta(ObservableList<TipoConta> observableListaTipoConta) {
			this.observableListaTipoConta = observableListaTipoConta;
		}

	}

	
	

