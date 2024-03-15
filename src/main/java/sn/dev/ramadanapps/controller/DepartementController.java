package sn.dev.ramadanapps.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sn.dev.ramadanapps.entities.Departement;
import sn.dev.ramadanapps.entities.Region;

import javax.persistence.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DepartementController implements Initializable {
    EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = managerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    @FXML
    private Button btnSave;

    @FXML
    private TableColumn<Departement, Integer> idTbl;

    @FXML
    private TableColumn<Departement, String> nomTbl;

    @FXML
    private TextField nomTfd;

    @FXML
    private TableColumn<Departement, Integer> populationTbl;

    @FXML
    private TextField populationTfd;

    @FXML
    private ComboBox<String> regionCmb;

    @FXML
    private TableColumn<Departement, String> regionTbl;

    @FXML
    private TableColumn<Departement, Double> superficieTbl;

    @FXML
    private TextField superficieTfd;
    @FXML
    private TableView<Departement> departementTbl;

    @FXML
    void delete(ActionEvent event) {
        try{
            transaction.begin();
            Departement departement = entityManager.find(Departement.class,id);
            entityManager.remove(departement);
            transaction.commit();
            resetForm();
            loadTable();
            btnSave.setDisable(false);
        }catch (Exception e) {
            if (transaction.isActive())
                transaction.rollback();
        }
    }
    int id;
    @FXML
    void getData(MouseEvent event) {
        Departement departement = departementTbl.getSelectionModel().getSelectedItem();
        id =departement.getIdD();
        nomTfd.setText(departement.getNomD());
        superficieTfd.setText(String.valueOf(departement.getSuperficie()));
        populationTfd.setText(String.valueOf(departement.getPopulation()));
        regionCmb.getSelectionModel().select(departement.getRegionId().getNomR());
        btnSave.setDisable(true);
    }

    @FXML
    void save(ActionEvent event) {
        try{
            transaction.begin();
            Departement departement = new Departement();
            departement.setNomD(nomTfd.getText());
            departement.setSuperficie(Double.parseDouble(superficieTfd.getText()));
            departement.setPopulation(Integer.parseInt(populationTfd.getText()));
            String regionSelected = regionCmb.getValue();
            TypedQuery<Region> regionQuery =entityManager.createQuery("SELECT s FROM Region s WHERE s.nomR = :name", Region.class);
            regionQuery.setParameter("name", regionSelected);
            Region region = regionQuery.getSingleResult();
            departement.setRegionId(region);
            entityManager.persist(departement);
            transaction.commit();
            loadTable();
            resetForm();
            departementTbl.refresh();
        }finally {
            if (transaction.isActive())
                transaction.rollback();
        }
    }

    @FXML
    void update(ActionEvent event) {
        try {
            transaction.begin();
            Departement departement = entityManager.find(Departement.class,id);
            departement.setNomD(nomTfd.getText());
            departement.setSuperficie(Double.parseDouble(superficieTfd.getText()));
            departement.setPopulation(Integer.parseInt(populationTfd.getText()));
            String regionSelected = regionCmb.getValue();
            TypedQuery<Region> regionQuery =entityManager.createQuery("SELECT s FROM Region s WHERE s.nomR = :name", Region.class);
            regionQuery.setParameter("name", regionSelected);
            Region region = regionQuery.getSingleResult();
            departement.setRegionId(region);
            entityManager.persist(departement);
            transaction.commit();
            loadTable();
            resetForm();
            btnSave.setDisable(false);
            departementTbl.refresh();
        }finally {
            if (transaction.isActive())
                transaction.rollback();
        }
    }
    public void setListeSpecialite() {
        List<String> specialiteList = new ArrayList<>();
        try {
            transaction.begin();
            TypedQuery<String> query = entityManager.createQuery("SELECT s.nomR FROM Region s ", String.class);
            List<String> resultList = query.getResultList();
            specialiteList.addAll(resultList);
            transaction.commit();
        }finally {
            if (transaction.isActive())
                transaction.rollback();
        }
        regionCmb.getItems().addAll(specialiteList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setListeSpecialite();
        loadTable();
    }
    public ObservableList<Departement> getDepartement()
    {
        ObservableList<Departement> departements = FXCollections.observableArrayList();
        TypedQuery<Departement> query=entityManager.createNamedQuery("listeDepartement",Departement.class);
        departements.addAll(query.getResultList());
        return departements;
    }
    public void loadTable()
    {
        departementTbl.setItems(getDepartement());
        idTbl.setCellValueFactory(new PropertyValueFactory<Departement,Integer>("idD"));
        nomTbl.setCellValueFactory(new PropertyValueFactory<Departement,String>("nomD"));
        superficieTbl.setCellValueFactory(new PropertyValueFactory<Departement,Double>("superficie"));
        populationTbl.setCellValueFactory(new PropertyValueFactory<Departement,Integer>("population"));
        regionTbl.setCellValueFactory(cellData ->{
            SimpleStringProperty property = new SimpleStringProperty();
            Departement departement =cellData.getValue();
            if (departement.getRegionId() != null)
            {
                property.setValue(departement.getRegionId().getNomR());
            }else {
                property.setValue("Bakhoul");
            }
            return property;
        });
    }
    public void resetForm()
    {
        nomTfd.setText("");
        superficieTfd.setText("");
        populationTfd.setText("");
        regionCmb.getSelectionModel().clearSelection();
    }
}
