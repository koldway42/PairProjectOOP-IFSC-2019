package com.ifsc.tds.controller;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.ifsc.tds.dao.FavorecidoDAO;
import com.ifsc.tds.dao.TipoContaDAO;
import com.ifsc.tds.dao.UsuarioDAO;
import com.ifsc.tds.entity.ContaPagar;
import com.ifsc.tds.entity.ContaReceber;
import com.ifsc.tds.entity.Favorecido;
import com.ifsc.tds.entity.TipoConta;
import com.ifsc.tds.entity.Usuario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ContaReceberEditController implements Initializable{

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
    private ComboBox<TipoConta> cbxTipoConta;

    @FXML
    private Label lblTipoConta;

    @FXML
    private ComboBox<Favorecido> cbxFavorecido;

    @FXML
    private Label lblFavorecido;

    @FXML
    private Label lblDataPagamento;

    @FXML
    private DatePicker dtpDataPagamento;

    @FXML
    private Label lblDataCadastro;

    @FXML
    private DatePicker dtpDataCadastro;

    @FXML
    private Label lblValorTotal;

    @FXML
    private TextField txtValorTotal;

    @FXML
    private Button btnOk;

    @FXML
    private Button btnCancela;
    
    private Stage janelaContaReceberEdit;
	private ContaReceber contaReceber;
	private boolean okClick = false;

	private List<Usuario> listaUsuarios;
	private UsuarioDAO UsuarioDAO;
	private ObservableList<Usuario> observableListaUsuarios;
	
	private List<Favorecido> listaFavorecidos;
	private FavorecidoDAO FavorecidoDAO;
	private ObservableList<Favorecido> observableListaFavorecidos;
	
	private List<TipoConta> listaTiposConta;
	private TipoContaDAO TipoContaDAO;
	private ObservableList<TipoConta> observableListaTiposConta;

	@FXML
	void clickCancela(ActionEvent event) {
		this.janelaContaReceberEdit.close();
	}

	@FXML
	void clickOK(ActionEvent event) {
		if (validarCampos()) {
			this.contaReceber.setDescricao(this.txtDescricao.getText());
			this.contaReceber.setUsuario(this.cbxUsuario.getSelectionModel().getSelectedItem());
			this.contaReceber.setTipoConta(this.cbxTipoConta.getSelectionModel().getSelectedItem());
			this.contaReceber.setFavorecido(this.cbxFavorecido.getSelectionModel().getSelectedItem());
			this.contaReceber.setDataVencimento(Date.valueOf(this.dtpDataVencimento.getValue()));
			this.contaReceber.setDataCadastro(Date.valueOf(this.dtpDataCadastro.getValue()));
			this.contaReceber.setDataPagamento(Date.valueOf(this.dtpDataPagamento.getValue()));
			this.contaReceber.setValorTotal(Double.parseDouble(this.txtValorTotal.getText()));

			this.okClick = true;
			this.janelaContaReceberEdit.close();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.UsuarioDAO = new UsuarioDAO();
		this.TipoContaDAO = new TipoContaDAO();
		this.FavorecidoDAO = new FavorecidoDAO();

		this.carregarComboBoxUsuarios();
		this.carregarComboBoxTipoConta();
		this.carregarComboBoxFavorecido();
	}

	/**
	 * Atribui a janela ao controle
	 * 
	 * @param janelaContaPagarEdit
	 */
	public void setJanelaContaPagarEdit(Stage janelaContaPagarEdit) {
		this.janelaContaReceberEdit = janelaContaPagarEdit;
	}

	/**
	 * Atribui a conta a pagar que será editado na janela.
	 * 
	 * @param contaPagar
	 */
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
			alerta.initOwner(this.janelaContaReceberEdit);
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
	
	public void carregarComboBoxTipoConta() {
		this.listaTiposConta = this.TipoContaDAO.getAll();

		this.observableListaTiposConta = FXCollections.observableArrayList(listaTiposConta);
		this.cbxTipoConta.setItems(this.observableListaTiposConta);
	}
	
	public void carregarComboBoxFavorecido() {
		this.listaFavorecidos = this.FavorecidoDAO.getAll();

		this.observableListaFavorecidos = FXCollections.observableArrayList(listaFavorecidos);
		this.cbxFavorecido.setItems(this.observableListaFavorecidos);
	}

	public ContaReceber getContaReceber() {
		return contaReceber;
	}

	public void setContaReceber(ContaReceber contaReceber) {
		this.contaReceber = contaReceber;
		
		if(contaReceber.getId() != null) {
			this.txtDescricao.setText(contaReceber.getDescricao());
			this.txtValorTotal.setText(contaReceber.getValorTotal().toString());
			this.cbxUsuario.setValue(contaReceber.getUsuario());
			this.cbxFavorecido.setValue(contaReceber.getFavorecido());
			this.cbxTipoConta.setValue(contaReceber.getTipoConta());
			this.dtpDataCadastro.setValue(contaReceber.getDataCadastro().toLocalDate());
			this.dtpDataPagamento.setValue(contaReceber.getDataPagamento().toLocalDate());
			this.dtpDataVencimento.setValue(contaReceber.getDataVencimento().toLocalDate());

		}
		
	}

	
	
	
}
