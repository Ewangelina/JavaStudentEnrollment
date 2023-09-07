package com.example.projekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;
import java.net.Socket;
import java.io.*;
import java.util.*;

/**
 * Creates the UI
 */
public class HelloApplication extends Application
{
    int show_mode = 0;
    Sender s;
    Stage primaryStage;
    int width = 700;
    int height = 1000;
    String indexForShowSubjects;

    /**
     * Sets the value of global variable show_mode for use in show()
     * @param val - the new value of show_mode
     * If the show_mode is -1 (fatal error) it will not be changed
     */
    public void set_show_mode (int val)
    {
        if (show_mode != -1)
        {
            show_mode = val;
        }

    }

    /**
     * Prepares the UI to show
     * @param primaryStage the primary stage to set
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primaryStage=primaryStage;
        this.s = new Sender(new Socket("169.254.188.8", 7), this);
        //this.s = new Sender(new Socket("localhost", 7), this);
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        primaryStage.setTitle("Klient aplikacja");
        primaryStage.setScene(createScene());
        primaryStage.show();
    }

    /**
     * Remake the UI, possibly with changes
     */
    public void refreshScene() 
    {
        primaryStage.setScene(createScene());
    }

    public static void main(String[] args)
    {
        try
        {
            launch(args);
        }
        catch (Exception e)
        {
            System.out.println("Connection error");
        }
    }

    /**
     * Forms the UI
     * @return - a new Scene to show the user
     */
    public Scene createScene()
    {
        GridPane grid = new GridPane();
        grid = setupShownElements(grid);
        grid = show(grid);
        return new Scene(grid, width, height);
    }

    /**
     * Adds the unchanging elements (Buttons, TextFields and Labels that have constantly the same text) and to the passed GridPane
     * @param grid - the GridPane to change and return
     * @return - GridPane with the unchanging elements
     */
    private GridPane setupShownElements(GridPane grid)
    {
        // ----- DODAJ STUDENTA ----------------------- DODAJ STUDENTA ----------------------- DODAJ STUDENTA -----
        Label addStudentLabel = new Label("Dodaj studenta: ");
        addStudentLabel.setFont(Font.font(20));


        Label addIndexLabel = new Label("Index: ");
        addIndexLabel.setFont(Font.font(14));

        TextField addIndexInput = new TextField();

        Label addNameLabel = new Label("Imię: ");
        addNameLabel.setFont(Font.font(14));

        TextField addNameInput = new TextField();

        Label addSurnameLabel = new Label("Nazwisko: ");
        addSurnameLabel.setFont(Font.font(14));

        TextField addSurnameInput = new TextField();

        Button createStudentButton = new Button("Dodaj studenta");

        // ----- DODAJ STUDENTA ----------------------- DODAJ STUDENTA ----------------------- DODAJ STUDENTA -----
        Label dividerStudentSubjectLabel = new Label(" ");
        dividerStudentSubjectLabel.setFont(Font.font(14));
        // ----- DODAJ PRZEDMIOT ----------------------- DODAJ PRZEDMIOT ----------------------- DODAJ PRZEDMIOT -----

        Label addSubjectLabel = new Label("Dodaj przedmiot: ");
        addSubjectLabel.setFont(Font.font(20));

        Label addSubjectIdentifierLabel = new Label("Identyfikator: ");
        addSubjectIdentifierLabel.setFont(Font.font(14));

        TextField addSubjectIdentifierInput = new TextField();
        //addSubjectIdentifierInput.setPromptText("Wpisz identyfikator przedmiotu");

        Label addSubjectNameLabel = new Label("Nazwa przedmiotu: ");
        addSubjectNameLabel.setFont(Font.font(14));

        TextField addSubjectNameInput = new TextField();

        Button createSubjectButton = new Button("Dodaj przedmiot");
        // ----- DODAJ PRZEDMIOT ----------------------- DODAJ PRZEDMIOT ----------------------- DODAJ PRZEDMIOT -----
        Label dividerSubjectDeleteLabel = new Label(" ");
        dividerSubjectDeleteLabel.setFont(Font.font(14));
        // ----- USUŃ UNIVERSALNY ----------------------- USUŃ UNIVERSALNY ----------------------- USUŃ UNIVERSALNY -----

        Label deleteLabel = new Label("Usuń: ");
        deleteLabel.setFont(Font.font(20));

        Label deleteObjectLabel = new Label("Co usunąć: ");
        deleteObjectLabel.setFont(Font.font(14));

        TextField deleteObjectInput = new TextField();
        deleteObjectInput.setPromptText("s, p, e, o");
        //s - student 1- index 2- NULL
        //p - przedmiot 1- id 2- NULL
        //e - enrollment (wypisać studenta z przedmiotu) 1- index 2- id
        //o - ocena 1- index 2- id

        Label deleteIdentifierLabel = new Label("Identyfikator/index: ");
        deleteIdentifierLabel.setFont(Font.font(14));

        TextField deleteIdentifierInput = new TextField();

        Label deleteSecondaryIdentifierLabel = new Label("Identyfikator/index precyzujący: ");
        deleteSecondaryIdentifierLabel.setFont(Font.font(14));

        TextField deleteSecondaryIdentifierInput = new TextField();

        Button deleteButton = new Button("Usuń");
        // ----- USUŃ UNIVERSALNY ----------------------- USUŃ UNIVERSALNY ----------------------- USUŃ UNIVERSALNY -----
        Label dividerDeleteEnrollLabel = new Label(" ");
        dividerDeleteEnrollLabel.setFont(Font.font(14));
        // ----- POŁĄCZ PRZEDMIOT ----------------------- POŁĄCZ PRZEDMIOT ----------------------- POŁĄCZ PRZEDMIOT -----

        Label enrollLabel = new Label("Zapisz na przedmiot: ");
        enrollLabel.setFont(Font.font(20));

        Label enrollIndexLabel = new Label("Index studenta: ");
        enrollIndexLabel.setFont(Font.font(14));

        TextField enrollIndexInput = new TextField();

        Label enrollIdentifierLabel = new Label("Identyfikator przedmiotu: ");
        enrollIdentifierLabel.setFont(Font.font(14));

        TextField enrollIdentifierInput = new TextField();

        Button enrollButton = new Button("Zapisz studenta");
        // ----- POŁĄCZ PRZEDMIOT ----------------------- POŁĄCZ PRZEDMIOT ----------------------- POŁĄCZ PRZEDMIOT -----
        Label dividerGradeDeleteLabel = new Label(" ");
        dividerGradeDeleteLabel.setFont(Font.font(14));
        // ----- OCEŃ STUDENTA ----------------------- OCEŃ STUDENTA ----------------------- OCEŃ STUDENTA -----

        Label gradeLabel = new Label("Oceń studenta: ");
        gradeLabel.setFont(Font.font(20));

        Label gradeIndexLabel = new Label("Index studenta: ");
        gradeIndexLabel.setFont(Font.font(14));

        TextField gradeIndexInput = new TextField();

        Label gradeIdentifierLabel = new Label("Identyfikator przedmiotu: ");
        gradeIdentifierLabel.setFont(Font.font(14));

        TextField gradeIdentifierInput = new TextField();

        Label gradeGradeLabel = new Label("Ocena studenta: ");
        gradeGradeLabel.setFont(Font.font(14));

        TextField gradeGradeInput = new TextField();

        Button gradeButton = new Button("Zapisz ocenę studenta");
        // ----- OCEŃ STUDENTA ----------------------- OCEŃ STUDENTA ----------------------- OCEŃ STUDENTA -----

        Button showStudentsButton = new Button("Pokaż studentów");
        Label showSubjectsLabel = new Label("Index studenta: ");
        showSubjectsLabel.setFont(Font.font(14));
        TextField showSubjectsInput = new TextField();
        Button showSubjectsButton = new Button("Pokaż przedmioty studenta");

        Button showEnrolledButton = new Button("Pokaż przedmioty ze studentami");


        Button showAllSubjectsButton = new Button("Pokaż przedmioty");
        GridPane.setConstraints(showAllSubjectsButton, 3, 1);


        int row = 1;
        int col = 2;

        GridPane.setConstraints(showStudentsButton, col, row++);
        GridPane.setConstraints(showSubjectsLabel, col, row++);
        GridPane.setConstraints(showSubjectsInput, col, row++);
        GridPane.setConstraints(showSubjectsButton, col, row);
        GridPane.setConstraints(showEnrolledButton, 3, row);


        row = 1;
        col = 1;
        //------
        GridPane.setConstraints(addStudentLabel, col, row++);
        GridPane.setConstraints(addIndexLabel, col, row++);
        GridPane.setConstraints(addIndexInput, col, row++);
        GridPane.setConstraints(addNameLabel, col, row++);
        GridPane.setConstraints(addNameInput, col, row++);
        GridPane.setConstraints(addSurnameLabel, col, row++);
        GridPane.setConstraints(addSurnameInput, col, row++);
        GridPane.setConstraints(createStudentButton, col, row++);
        //------
        GridPane.setConstraints(dividerStudentSubjectLabel, col, row++);
        //------
        GridPane.setConstraints(addSubjectLabel, col, row++);
        GridPane.setConstraints(addSubjectIdentifierLabel, col, row++);
        GridPane.setConstraints(addSubjectIdentifierInput, col, row++);
        GridPane.setConstraints(addSubjectNameLabel, col, row++);
        GridPane.setConstraints(addSubjectNameInput, col, row++);
        GridPane.setConstraints(createSubjectButton, col, row++);
        //------
        GridPane.setConstraints(dividerSubjectDeleteLabel, col, row++);
        //------
        GridPane.setConstraints(deleteLabel, col, row++);
        GridPane.setConstraints(deleteObjectLabel, col, row++);
        GridPane.setConstraints(deleteObjectInput, col, row++);
        GridPane.setConstraints(deleteIdentifierLabel, col, row++);
        GridPane.setConstraints(deleteIdentifierInput, col, row++);
        GridPane.setConstraints(deleteSecondaryIdentifierLabel, col, row++);
        GridPane.setConstraints(deleteSecondaryIdentifierInput, col, row++);
        GridPane.setConstraints(deleteButton, col, row++);
        //------
        GridPane.setConstraints(dividerDeleteEnrollLabel, col, row++);
        //------
        GridPane.setConstraints(enrollLabel, col, row++);
        GridPane.setConstraints(enrollIndexLabel, col, row++);
        GridPane.setConstraints(enrollIndexInput, col, row++);
        GridPane.setConstraints(enrollIdentifierLabel, col, row++);
        GridPane.setConstraints(enrollIdentifierInput, col, row++);
        GridPane.setConstraints(enrollButton, col, row++);
        //------
        GridPane.setConstraints(dividerGradeDeleteLabel, col, row++);
        //------
        GridPane.setConstraints(gradeLabel, col, row++);
        GridPane.setConstraints(gradeIndexLabel, col, row++);
        GridPane.setConstraints(gradeIndexInput, col, row++);
        GridPane.setConstraints(gradeIdentifierLabel, col, row++);
        GridPane.setConstraints(gradeIdentifierInput, col, row++);
        GridPane.setConstraints(gradeGradeLabel, col, row++);
        GridPane.setConstraints(gradeGradeInput, col, row++);
        GridPane.setConstraints(gradeButton, col, row++);
        //------


        createStudentButtonAction(createStudentButton, addIndexInput, addNameInput, addSurnameInput);
        createSubjectButtonAction(createSubjectButton, addSubjectIdentifierInput, addSubjectNameInput);
        deleteButtonAction(deleteButton, deleteObjectInput, deleteIdentifierInput, deleteSecondaryIdentifierInput);
        enrollButtonAction(enrollButton, enrollIndexInput, enrollIdentifierInput);
        gradeButtonAction(gradeButton, gradeIndexInput, gradeIdentifierInput, gradeGradeInput);
        showStudentsButtonAction(showStudentsButton);
        showSubjectsButtonAction(showSubjectsButton, showSubjectsInput);
        showAllSubjectsButtonAction(showAllSubjectsButton);
        showEnrolledButtonAction(showEnrolledButton);


        grid.getChildren().addAll(addStudentLabel, addIndexLabel, addIndexInput, addSurnameLabel, addNameInput,
                addSurnameInput, addNameLabel, createStudentButton, dividerStudentSubjectLabel, addSubjectLabel,
                addSubjectIdentifierLabel, addSubjectIdentifierInput, addSubjectNameLabel, addSubjectNameInput,
                createSubjectButton, dividerSubjectDeleteLabel, deleteLabel, deleteObjectLabel, deleteObjectInput,
                deleteIdentifierLabel, deleteIdentifierInput, deleteSecondaryIdentifierLabel,
                deleteSecondaryIdentifierInput, deleteButton, dividerDeleteEnrollLabel, enrollLabel, enrollIndexLabel,
                enrollIndexInput, enrollIdentifierLabel, enrollIdentifierInput, enrollButton, dividerGradeDeleteLabel,
                gradeLabel, gradeIndexLabel, gradeIndexInput, gradeIdentifierLabel, gradeIdentifierInput,
                gradeGradeLabel, gradeGradeInput, gradeButton, showSubjectsLabel, showSubjectsInput, showSubjectsButton,
                showEnrolledButton, showStudentsButton, showAllSubjectsButton);
        
        return grid;
    }

    /**
     * Adds the changing labels (elements to show to the user based on most recent action)
     * @param grid - the GridPane to change and return
     * @return - GridPane with the added elements
     */
    private GridPane show(GridPane grid)
    {
        List<Label> labels = new ArrayList<>();
        int rowIndex = 6;

        List<String> results = new ArrayList<>();
        switch (show_mode)
        {
            case -1:
                results.add("CONNECTION FAILED");
                break;
            case 0:
                results.add("Ready");
                break;
            case 1:
                results.add("ERROR");
                break;
            case 2:
                results = s.showStudents();
                break;
            case 3:
                results = s.showSubjects(indexForShowSubjects);
                break;
            case 4:
                results = s.showAllSubjects();
                break;
            case 5:
                results = s.showEnrolled();
                break;
        }

        for(String r: results)
        {
            Label tempLabel = new Label(r);
            grid.setConstraints(tempLabel, 2, rowIndex++);
            labels.add(tempLabel);
        }

        grid.getChildren().addAll(labels);
        return grid;
    }

    /**
     * Assigns actions needed to show all students to a button
     * @param showStudentsButton - Button to asign the action to
     */
    private void showStudentsButtonAction (Button showStudentsButton)
    {
        showStudentsButton.setOnAction(a -> {
            set_show_mode(2);
            refreshScene();
        });
    }

    /**
     * Assigns actions needed to show all subject to a button
     * @param showStudentsButton - Button to asign the action to
     */
    private void showAllSubjectsButtonAction (Button showStudentsButton)
    {
        showStudentsButton.setOnAction(a -> {
            set_show_mode(4);
            refreshScene();
        });
    }

    /**
     * Assigns actions needed to show all subjects that a student is assigned to to a button
     * @param showStudentsButton - Button to asign the action to
     */
    private void showEnrolledButtonAction (Button showStudentsButton)
    {
        showStudentsButton.setOnAction(a -> {
            set_show_mode(5);
            refreshScene();
        });
    }

    /**
     * Assigns actions needed to show all subjects that the student in showStudentsIndexInput is assigned to to a button
     * @param showStudentsButton - Button to asign the action to
     * @param showStudentsIndexInput - TextField with the index of the student
     */
    private void showSubjectsButtonAction (Button showStudentsButton, TextField showStudentsIndexInput)
    {
        showStudentsButton.setOnAction(a -> {
            this.indexForShowSubjects = showStudentsIndexInput.getText();
            set_show_mode(3);
            showStudentsIndexInput.clear();
            refreshScene();
        });
    }

    /**
     * Assigns actions needed to grade a student in a subject to a button
     * @param gradeButton - Button to asign the action to
     * @param gradeIndexInput - TextField with the index of the student
     * @param gradeIdentifierInput - TextField with the identifier of the subject
     * @param gradeGradeInput - TextField with the grade to be assigned
     */
    private void gradeButtonAction(Button gradeButton, TextField gradeIndexInput, TextField gradeIdentifierInput, TextField gradeGradeInput)
    {
        gradeButton.setOnAction(a -> {
            s.gradeStudent(gradeIndexInput.getText(), gradeIdentifierInput.getText(), gradeGradeInput.getText());

            gradeIndexInput.clear();
            gradeIdentifierInput.clear();
            gradeGradeInput.clear();

            refreshScene();
        });
    }

    private void enrollButtonAction(Button enrollButton, TextField enrollIndexInput, TextField enrollIdentifierInput)
    {
        enrollButton.setOnAction(a -> {
            s.enrollStudent(enrollIndexInput.getText(), enrollIdentifierInput.getText());

            enrollIndexInput.clear();
            enrollIdentifierInput.clear();

            refreshScene();
        });
    }

    private void createStudentButtonAction(Button createStudentButton,TextField addIndexInput, TextField addNameInput, TextField addSurnameInput) {
        createStudentButton.setOnAction(a -> {
            s.createStudent(addIndexInput.getText(), addNameInput.getText(), addSurnameInput.getText());

            addIndexInput.clear();
            addNameInput.clear();
            addSurnameInput.clear();

            refreshScene();
        });
    }

    private void deleteButtonAction(Button deleteButton, TextField deleteObjectInput, TextField deleteIdentifierInput, TextField deleteSecondaryIdentifierInput)
    {
        deleteButton.setOnAction(a -> {
            s.delete(deleteObjectInput.getText(), deleteIdentifierInput.getText(), deleteSecondaryIdentifierInput.getText());

            deleteObjectInput.clear();
            deleteIdentifierInput.clear();
            deleteSecondaryIdentifierInput.clear();

            refreshScene();
        });
    }

    private void createSubjectButtonAction(Button createSubjectButton, TextField addSubjectIdentifierInput, TextField addSubjectNameInput) {
        createSubjectButton.setOnAction(a -> {
            s.createSubject(addSubjectIdentifierInput.getText(), addSubjectNameInput.getText());

            addSubjectIdentifierInput.clear();
            addSubjectNameInput.clear();

            refreshScene();
        });
    }
}