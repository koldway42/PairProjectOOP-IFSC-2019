package com.ifsc.tds.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;

import com.ifsc.tds.dao.UsuarioDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class UsuarioGraficoController implements Initializable {

	@FXML
	private BarChart<String, Integer> barChart;

	@FXML
	private CategoryAxis categoryAxis;

	@FXML
	private NumberAxis numberAxis;

	private ObservableList<String> observableListMeses = FXCollections.observableArrayList();

	// Atributos para manipula��o de Banco de Dados
	private final UsuarioDAO usuarioDAO = new UsuarioDAO();

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		// Obt�m an array com nomes dos meses em ingl�s.
		String[] arrayMeses = { "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez" };
		// Converte o array em uma lista e adiciona em nossa ObservableList de meses.
		observableListMeses.addAll(Arrays.asList(arrayMeses));

		// Associa os nomes de m�s como categorias para o eixo horizontal.
		categoryAxis.setCategories(observableListMeses);
		Map<Integer, ArrayList> dados = this.getUsuarioDAO().listaUsuarioCadastroPorMes();

		for (Map.Entry<Integer, ArrayList> dadosItem : dados.entrySet()) {
			XYChart.Series<String, Integer> series = new XYChart.Series<>();
			series.setName(dadosItem.getKey().toString());

			for (int i = 0; i < dadosItem.getValue().size(); i = i + 2) {
				String mes;
				Integer quantidade;

				mes = retornaNomeMes((int) dadosItem.getValue().get(i));
				quantidade = (Integer) dadosItem.getValue().get(i + 1);

				series.getData().add(new XYChart.Data<>(mes, quantidade));
			}
			barChart.getData().add(series);
		}
	}

	public String retornaNomeMes(int mes) {
		switch (mes) {
		case 1:
			return "Jan";
		case 2:
			return "Fev";
		case 3:
			return "Mar";
		case 4:
			return "Abr";
		case 5:
			return "Mai";
		case 6:
			return "Jun";
		case 7:
			return "Jul";
		case 8:
			return "Ago";
		case 9:
			return "Set";
		case 10:
			return "Out";
		case 11:
			return "Nov";
		case 12:
			return "Dez";
		default:
			return "";
		}
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

}