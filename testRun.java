package com.example.ds4;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class testRun extends Application {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    Alert successPopUp = new Alert(Alert.AlertType.CONFIRMATION);
    HashTable<Movie> movieHashTable = new HashTable<>(10);
    TableView<Movie> tableMovie = new TableView<>();
    private ObservableList<Movie> originalMovies = FXCollections.observableArrayList();
    MovieLinkedList HashList = new MovieLinkedList();
    int current=0;

    @Override
    public void start(Stage primaryStage) {
        // Create a StackPane to layer the background and overlay
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setTitle("Movie Catalog Management System");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Create a new stage for movie management
        BorderPane moviesPane = new BorderPane();
        Scene moviesScene = new Scene(moviesPane, 900, 600);
        Stage moviesStage = new Stage();
        moviesStage.setTitle("Movie Management");
        moviesStage.setScene(moviesScene);
        moviesPane.setId("moviesPane");

        //addPane
        Pane addPane = new Pane();
        Stage addStage = new Stage();
        Scene addScene = new Scene(addPane, 800, 500);
        addStage.setScene(addScene);
        addStage.setTitle("Add Movie");

        //updatePane
        Pane updatePane = new Pane();
        Stage updateStage = new Stage();
        Scene updateScene = new Scene(updatePane, 800, 500);
        updateStage.setScene(updateScene);
        updateStage.setTitle("Update Movie");

        // Create the Details Stage
        Pane detailsPane = new Pane();
        Stage detailsStage = new Stage();
        Scene detailsScene = new Scene(detailsPane, 800, 500);
        detailsScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        detailsStage.setScene(detailsScene);
        detailsStage.setTitle("Movie Details");


        // Main contentPane
        BorderPane contentPane = new BorderPane();

        // Transparent black overlay
        Rectangle overlay = new Rectangle(1000, 600);
        overlay.setFill(Color.rgb(0, 0, 0, 0.5)); // Black with 50% transparency

        //Movie Screen


        // signature label
        Label LOGOLabel = new Label("Adam Khabisa 1210475");
        LOGOLabel.setStyle("-fx-text-fill: red; -fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: BebasNeue;");
        LOGOLabel.setAlignment(Pos.TOP_CENTER);
        contentPane.setTop(LOGOLabel);

        //center nodes
        Label centerLabel = new Label("Unlimited movies, TV\nshows, and more");
        centerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 50px; -fx-font-weight: bold; -fx-font-family: BebasNeue;");
        Button getStartedButton = new Button("Get Started >");
        VBox centerMainVBox = new VBox(20, centerLabel, getStartedButton);
        centerMainVBox.setAlignment(Pos.CENTER);
        contentPane.setCenter(centerMainVBox);
        root.getChildren().addAll(overlay, contentPane);

/**************************Movie Screen*****************************************************************************************************************************************/
// Get Started Button Action
        getStartedButton.setOnAction(StartMovie -> {
            primaryStage.hide();
            moviesStage.show();
            moviesScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

            // Create a TableView for movies
            TableColumn<Movie, String> titleColumn = new TableColumn<>("Title");
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            titleColumn.setMinWidth(300);
            titleColumn.setMaxWidth(550);


            TableColumn<Movie, String> descriptionColumn = new TableColumn<>("Description");
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            descriptionColumn.setMinWidth(300);
            descriptionColumn.setMaxWidth(550);

            TableColumn<Movie, Integer> releaseYearColumn = new TableColumn<>("Release Year");
            releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
            releaseYearColumn.setMinWidth(100);
            releaseYearColumn.setMaxWidth(200);

            TableColumn<Movie, Double> ratingColumn = new TableColumn<>("Rating");
            ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
            ratingColumn.setMinWidth(200);
            ratingColumn.setMaxWidth(400);

            TableColumn<Movie, Double> posterColumn = new TableColumn<>("posterUrl");
            posterColumn.setCellValueFactory(new PropertyValueFactory<>("posterUrl"));


            tableMovie.getColumns().addAll(titleColumn, descriptionColumn, releaseYearColumn, ratingColumn, posterColumn);

            Button nextButton = new Button("Next >");
            nextButton.getStyleClass().add("search-button");
            Button previousButton = new Button("< Previous");

            nextButton.setOnAction(NextMovie -> {
                if(current>=movieHashTable.getSize()){
                    nextButton.setDisable(true);
                    previousButton.setDisable(false);
                }else {
                    current++;
                    MovieAvlTree<Movie> tree = (MovieAvlTree<Movie>) movieHashTable.getTable()[current];
                    if (tree == null) {
                        previousButton.setDisable(false);
                        nextButton.setDisable(true);
                    } else {
                        MovieLinkedList list = tree.inOrder();
                        tableMovie.getItems().clear();
                        for (int i = 0; i < list.size(); i++) {
                            tableMovie.getItems().add(list.get(i));
                        }
                        tableMovie.refresh();
                    }
                }
            });

            previousButton.getStyleClass().add("search-button");
            previousButton.setOnAction(PreviousMovie -> {
                if(current<0){
                    previousButton.setDisable(true);
                    nextButton.setDisable(false);
                }else{
                    current--;
                    MovieAvlTree<Movie> tree= (MovieAvlTree<Movie>) movieHashTable.getTable()[current];
                    if (tree == null) {
                        previousButton.setDisable(true);
                        nextButton.setDisable(false);
                    } else {
                        MovieLinkedList list = tree.inOrder();
                        tableMovie.getItems().clear();
                        for (int i = 0; i < list.size(); i++) {
                            tableMovie.getItems().add(list.get(i));
                        }
                        tableMovie.refresh();
                    }
                }
            });
            HBox NB=new HBox(previousButton, nextButton);
            NB.setAlignment(Pos.CENTER);

            VBox centerMovieTableBox = new VBox(10, tableMovie,NB);
            centerMovieTableBox.setAlignment(Pos.CENTER);
            moviesPane.setCenter(centerMovieTableBox);

            // Back Button
            Button backButton = new Button("< Back");
            backButton.setOnAction(back -> {
                moviesStage.close(); // Close the movie management stage
                primaryStage.show(); // Show the initial stage again
            });

            // Search Area (HBox at the top)
            Label searchLabel = new Label("Search:");
            searchLabel.getStyleClass().add("search-label");

            TextField searchField = new TextField();
            searchField.setPromptText("🔎︎Search here"); // Placeholder text
            searchField.getStyleClass().add("search-field");
            DatePicker picker = new DatePicker();


// Layout for the search area
            HBox searchHBox = new HBox(10, searchLabel, picker, searchField);
            searchHBox.setPadding(new Insets(10));
            searchHBox.setAlignment(Pos.CENTER);


            // Actions HBox (add/remove/update, print actions)


            ComboBox<String> printComboBox = new ComboBox<>();
            printComboBox.getItems().addAll("Print Ascending", "Print Decsending", "Print Top Rated", "Print Last Rated", "Print All");
            printComboBox.setPromptText("Print Actions");


            ComboBox<String> fileComboBox = new ComboBox<>();
            fileComboBox.getItems().addAll("Load Movies", "Save Movies");
            fileComboBox.setPromptText("File Actions");

            ComboBox<String> searchComboBox = new ComboBox<>();
            searchComboBox.getItems().addAll("Search by Datepicker", "Search by text", "Refresh Data");
            searchComboBox.setPromptText("Search Actions");

            //combo box actions
            printComboBox.setOnAction(event -> {
                String selectedAction = printComboBox.getValue();
                if (selectedAction == null) return;

                switch (selectedAction) {
                    case "Print Ascending":
                        PrintSorted(tableMovie, true);
                        break;
                    case "Print Decsending":
                        PrintSorted(tableMovie, false);
                        break;
                    case "Print Top Rated":
                        PrintTopRated(tableMovie);
                        break;
                    case "Print Last Rated":
                        PrintLeastRated(tableMovie);
                        break;
                    case "Print All":
                        PrintAll(tableMovie, originalMovies);
                        break;
                    default:
                        alert.setContentText("Invalid action selected.");
                        alert.showAndWait();
                }
            });
            searchComboBox.setOnAction(event -> {
                String selectedAction = searchComboBox.getValue();

                if (selectedAction != null) {
                    switch (selectedAction) {

                        case "Search by Datepicker":
                            if (picker.getValue() != null) {
                                LocalDate date = picker.getValue();
                                String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                String year = formattedDate.split("/")[2];
                                HashList = movieHashTable.getYear(year);
                                System.out.println(HashList);

                                tableMovie.getItems().clear();

                                MovieNode current = HashList.getHead();
                                int count = 0; // To count the size of the list

                                while (current != null) {
                                    tableMovie.getItems().add(current.getData());  // Add each Movie to the table
                                    current = current.getNext();
                                    count++;
                                }

                                System.out.println("Number of movies found: " + count);

// Refresh the TableView
                                tableMovie.refresh();
                            }
                            break;

                        case "Search by text":
                            String title = searchField.getText().trim();
                            String Year = searchField.getText().trim();
                            if (!title.isEmpty()) {
                                Movie found = movieHashTable.get(title);
                                if (found == null) {
                                    if (!Year.isEmpty()) {
                                        MovieLinkedList foundY = movieHashTable.getYear(Year);
                                        if (foundY == null) {
                                            fillTable();
                                        } else {
                                            tableMovie.getItems().clear();
                                            for (int i = 0; i < foundY.size(); i++)
                                                tableMovie.getItems().add(foundY.get(i));
                                            tableMovie.refresh();
                                        }
                                    } else {
                                        fillTable();
                                    }
                                    break;
                                } else {
                                    tableMovie.getItems().clear();
                                    tableMovie.getItems().add(found);
                                    tableMovie.refresh();
                                }
                            } else {
                                fillTable();
                            }


                        case "Refresh Data":
                            fillTable(); // Refill the table with original data
                            searchField.clear(); // Clear the search field
                            picker.setValue(null); // Reset the DatePicker
                            tableMovie.refresh(); // Refresh the TableView to show the reset data
                            break;

                        default:
                            break;
                    }
                }
            });


            HBox actionsHBox = new HBox(20, printComboBox, fileComboBox, searchComboBox);
            actionsHBox.setPadding(new Insets(10));
            actionsHBox.setAlignment(Pos.CENTER);

            // top Menu
            MenuBar moviesMenuBar = new MenuBar();
            Menu fileMenu = new Menu("File");
            MenuItem saveMenuItem = new MenuItem("Save");
            saveMenuItem.setOnAction(event -> saveMoviesToFile(moviesStage, tableMovie));
            MenuItem loadMenuItem = new MenuItem("Load");
            loadMenuItem.setOnAction(event -> loadMoviesFromFile(moviesStage, tableMovie));


            fileMenu.getItems().addAll(saveMenuItem, loadMenuItem);

            Menu operationMenu = new Menu("Operation");
            MenuItem addOperationMenuItem = new MenuItem("Add Operation");
            MenuItem removeOperationMenuItem = new MenuItem("Remove Operation");
            MenuItem updateOperationMenuItem = new MenuItem("Update Operation");
            MenuItem showDetailsMenuItem = new MenuItem("Show Details");
            operationMenu.getItems().addAll(addOperationMenuItem, removeOperationMenuItem, updateOperationMenuItem, showDetailsMenuItem);
            moviesMenuBar.getMenus().addAll(fileMenu, operationMenu);
            moviesMenuBar.setId("moviesMenuBar");

            // File Menu Actions
            saveMenuItem.setOnAction(event -> saveMoviesToFile(moviesStage, tableMovie));
            loadMenuItem.setOnAction(event -> loadMoviesFromFile(moviesStage, tableMovie));
            showDetailsMenuItem.setOnAction(event -> {
                // Retrieve the selected movie
                Movie selectedMovie = tableMovie.getSelectionModel().getSelectedItem();

                if (selectedMovie == null) {
                    alert.setContentText("Please select a movie to view details.");
                    alert.showAndWait();
                    return;
                }

                try {
                    // Hide the Movie Management Stage and show the Details Stage
                    moviesStage.hide();
                    detailsStage.show();

                    // Clear the existing detailsPane content to avoid duplication
                    detailsPane.getChildren().clear();

                    // Create Labels and Fields
                    Label titleLabel = new Label("Title:");
                    titleLabel.getStyleClass().add("search-label");
                    TextField titleField = new TextField(selectedMovie.getTitle());
                    titleField.getStyleClass().add("search-field");
                    titleField.setEditable(false);
                    titleField.setPrefWidth(400);

                    Label releaseYearLabel = new Label("Release Year:");
                    releaseYearLabel.getStyleClass().add("search-label");
                    TextField releaseYearField = new TextField(String.valueOf(selectedMovie.getReleaseYear()));
                    releaseYearField.getStyleClass().add("search-field");
                    releaseYearField.setEditable(false);
                    releaseYearField.setPrefWidth(400);

                    Label ratingLabel = new Label("Rating:");
                    ratingLabel.getStyleClass().add("search-label");
                    TextField ratingField = new TextField(String.valueOf(selectedMovie.getRating()));
                    ratingField.getStyleClass().add("search-field");
                    ratingField.setEditable(false);
                    ratingField.setPrefWidth(400);

                    Label descriptionLabel = new Label("Description:");
                    descriptionLabel.getStyleClass().add("search-label");
                    TextField descriptionField = new TextField(selectedMovie.getDescription());
                    descriptionField.getStyleClass().add("search-field");
                    descriptionField.setEditable(false);
                    descriptionField.setPrefWidth(400);

                    // ImageView for Poster
                    ImageView posterView = new ImageView();
                    posterView.setFitWidth(200);
                    posterView.setPreserveRatio(true); // Maintain aspect ratio

                    // Attempt to load the poster URL
                    try {
                        String posterUrl = selectedMovie.getPosterUrl();
                        if (posterUrl != null && !posterUrl.isEmpty()) {
                            Image posterImage = new Image(posterUrl, true);
                            posterView.setImage(posterImage);
                        } else {
                            // Use a default image if URL is missing
                            posterView.setImage(new Image("file:default_poster.png"));
                        }
                    } catch (Exception e) {
                        // Use a default image if loading the URL fails
                        posterView.setImage(new Image("file:default_poster.png"));
                    }

                    // Back Button (Ensure it remains visible)
                    Button backShowButton = new Button("< Back");
                    backShowButton.getStyleClass().add("search-button");
                    backShowButton.setOnAction(back -> {
                        detailsStage.hide();
                        moviesStage.show();
                    });

                    backShowButton.setLayoutX(20);
                    backShowButton.setLayoutY(450); // Position the back button to remain visible

                    // Layout for Details
                    VBox fieldsVBox = new VBox(15, titleLabel, titleField, releaseYearLabel, releaseYearField,
                            ratingLabel, ratingField, descriptionLabel, descriptionField);
                    fieldsVBox.setPadding(new Insets(20));

                    HBox detailsLayout = new HBox(20, fieldsVBox, posterView);
                    detailsLayout.setPadding(new Insets(20));
                    detailsLayout.setAlignment(Pos.CENTER);

                    // Add Components to the Pre-Created detailsPane
                    detailsPane.getChildren().addAll(detailsLayout, backShowButton);

                } catch (Exception e) {
                    alert.setContentText("An unexpected error occurred while displaying details.");
                    alert.showAndWait();
                    e.printStackTrace();
                }
            });

// Operation Menu Actions
            addOperationMenuItem.setOnAction(event -> {
                try {
                    addStage.show();

                    // Create Labels and Fields
                    Label titleLabel = new Label("Title:");
                    titleLabel.getStyleClass().add("search-label");
                    TextField titleField = new TextField();
                    titleField.setPrefWidth(400);
                    titleField.getStyleClass().add("search-field");

                    Label releaseYearLabel = new Label("Release Year:");
                    releaseYearLabel.getStyleClass().add("search-label");
                    TextField releaseYearField = new TextField();
                    releaseYearField.setPrefWidth(400);
                    releaseYearField.getStyleClass().add("search-field");

                    Label ratingLabel = new Label("Rating:");
                    ratingLabel.getStyleClass().add("search-label");
                    TextField ratingField = new TextField();
                    ratingField.setPrefWidth(400);
                    ratingField.getStyleClass().add("search-field");

                    Label descriptionLabel = new Label("Description:");
                    descriptionLabel.getStyleClass().add("search-label");
                    TextField descriptionField = new TextField();
                    descriptionField.setPrefWidth(400);
                    descriptionField.getStyleClass().add("search-field");

                    Label posterUrlLabel = new Label("Poster URL:");
                    posterUrlLabel.getStyleClass().add("search-label");
                    TextField posterUrlField = new TextField();
                    posterUrlField.setPrefWidth(400);
                    posterUrlField.getStyleClass().add("search-field");

                    // Organize Fields in VBox
                    VBox fieldsVBox = new VBox(15); // Spacing between fields
                    fieldsVBox.getChildren().addAll(
                            titleLabel, titleField,
                            releaseYearLabel, releaseYearField,
                            ratingLabel, ratingField,
                            descriptionLabel, descriptionField,
                            posterUrlLabel, posterUrlField
                    );
                    fieldsVBox.setPadding(new Insets(20)); // Add padding around the VBox

                    // Save Button
                    Button saveButton = new Button("Save");
                    saveButton.getStyleClass().add("search-button");
                    saveButton.setOnAction(save -> {
                        try {
                            String title = titleField.getText().trim();
                            int releaseYear = Integer.parseInt(releaseYearField.getText().trim());
                            double rating = Double.parseDouble(ratingField.getText().trim());
                            String description = descriptionField.getText().trim();
                            String posterUrl = posterUrlField.getText().trim();

                            // Add the new movie to the table
                            movieHashTable.put(new Movie(title, description, releaseYear, rating, posterUrl));
                            fillTable();
                            successPopUp.setContentText("Movie added successfully!");
                            successPopUp.showAndWait();
                            addStage.close();
                        } catch (Exception e) {
                            alert.setContentText("Invalid input. Ensure Release Year is an integer and Rating is a number.");
                            alert.showAndWait();
                        }
                    });

                    VBox layout = new VBox(20, fieldsVBox, saveButton);
                    layout.setAlignment(Pos.CENTER);
                    layout.setPadding(new Insets(20));

                    addPane.getChildren().add(layout);
                    addPane.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
                } catch (Exception e) {
                    alert.setContentText("An unexpected error occurred.");
                    alert.showAndWait();
                }
            });


            updateOperationMenuItem.setOnAction(event -> {
                try {
                    moviesStage.hide();
                    updateStage.show();
                    Movie selectedMovie = tableMovie.getSelectionModel().getSelectedItem();

                    // Create Labels and Fields
                    Label titleLabel = new Label("Title:");
                    titleLabel.getStyleClass().add("search-label");
                    TextField titleField = new TextField(selectedMovie.getTitle());
                    titleField.setPrefWidth(400);
                    titleField.getStyleClass().add("search-field");

                    Label releaseYearLabel = new Label("Release Year:");
                    releaseYearLabel.getStyleClass().add("search-label");
                    TextField releaseYearField = new TextField(String.valueOf(selectedMovie.getReleaseYear()));
                    releaseYearField.setPrefWidth(400);
                    releaseYearField.getStyleClass().add("search-field");

                    Label ratingLabel = new Label("Rating:");
                    ratingLabel.getStyleClass().add("search-label");
                    TextField ratingField = new TextField(String.valueOf(selectedMovie.getRating()));
                    ratingField.setPrefWidth(400);
                    ratingField.getStyleClass().add("search-field");

                    Label descriptionLabel = new Label("Description:");
                    descriptionLabel.getStyleClass().add("search-label");
                    TextField descriptionField = new TextField(selectedMovie.getDescription());
                    descriptionField.setPrefWidth(400);
                    descriptionField.getStyleClass().add("search-field");

                    // Organize Fields in VBox
                    VBox fieldsVBox = new VBox(15); // Spacing between fields
                    fieldsVBox.getChildren().addAll(
                            titleLabel, titleField,
                            releaseYearLabel, releaseYearField,
                            ratingLabel, ratingField,
                            descriptionLabel, descriptionField
                    );
                    fieldsVBox.setPadding(new Insets(20)); // Add padding around the VBox

                    // Back Button
                    Button backUpdateOp = new Button("< Back");
                    backUpdateOp.getStyleClass().add("search-button");
                    backUpdateOp.setOnAction(backUpdate -> {
                        Stage currentStage = (Stage) backUpdateOp.getScene().getWindow();
                        currentStage.hide();
                        moviesStage.show();
                    });


                    //backButton
                    Button backUpOp = new Button("< Back");
                    backUpOp.getStyleClass().add("search-button");
                    backUpOp.setOnAction(backAdd -> {
                        Stage currentStage = (Stage) backButton.getScene().getWindow();
                        currentStage.hide();
                        moviesStage.show();
                    });
                    // Save Button
                    Button updateButton = new Button("Update");
                    updateButton.getStyleClass().add("search-button");
                    updateButton.setOnAction(save -> {
                        try {
                            String oldtitle = selectedMovie.getTitle();

                            selectedMovie.setTitle(titleField.getText().trim());
                            selectedMovie.setReleaseYear(Integer.parseInt(releaseYearField.getText().trim()));
                            selectedMovie.setRating(Double.parseDouble(ratingField.getText().trim()));
                            selectedMovie.setDescription(descriptionField.getText().trim());
                            movieHashTable.remove(oldtitle);
                            movieHashTable.put(selectedMovie);
                            fillTable();
                            successPopUp.setContentText("Movie updated successfully!");
                            successPopUp.showAndWait();
                            movieHashTable.printTable();
                            updateStage.close();
                            moviesStage.show();
                        } catch (Exception e) {
                            alert.setContentText("Invalid input. Ensure Release Year is an integer and Rating is a number.");
                            e.printStackTrace();
                            alert.showAndWait();
                        }
                    });

                    VBox layout = new VBox(20, fieldsVBox, updateButton, backUpOp); // Layout includes fields and button
                    layout.setAlignment(Pos.CENTER); // Center align the layout
                    layout.setPadding(new Insets(20)); // Padding for the layout

                    updatePane.getChildren().add(layout);
                    updatePane.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
                } catch (Exception e) {
                    alert.setContentText("An unexpected error occurred.");
                    alert.showAndWait();
                }
            });


            removeOperationMenuItem.setOnAction(event -> {
                // Remove selected movie
                Movie selectedMovie = tableMovie.getSelectionModel().getSelectedItem();
                if (selectedMovie != null) {

                    movieHashTable.delete(selectedMovie.getTitle());
                    fillTable();
                    successPopUp.setContentText("Movie removed successfully!");
                    successPopUp.showAndWait();
                } else {
                    alert.setContentText("No movie selected for removal.");
                    alert.showAndWait();
                }
            });


            // Place elements at the top, center, and bottom
            VBox topVBox = new VBox(10, moviesMenuBar, searchHBox, actionsHBox);
            moviesPane.setTop(topVBox);


            //file actions
            fileComboBox.setOnAction(event -> {
                String selectedAction = fileComboBox.getSelectionModel().getSelectedItem();

                if (selectedAction != null) {
                    if (selectedAction.equals("Save Movies")) {
                        saveMoviesToFile(moviesStage, tableMovie);  // Save action
                    } else if (selectedAction.equals("Load Movies")) {
                        loadMoviesFromFile(moviesStage, tableMovie);  // Load action
                    }
                }
            });


        });

/******************************************************************************************************************************************************************/


    }


    public void saveMoviesToFile(Stage stage, TableView<Movie> tableView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setTitle("Save Movie File");
        fileChooser.setInitialFileName("movies.txt");
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                for (Movie movie : tableView.getItems()) {
                    writer.println("Title: " + movie.getTitle());
                    writer.println("Description: " + movie.getDescription());
                    writer.println("Release Year: " + movie.getReleaseYear());
                    writer.println("Rating: " + movie.getRating());
                    writer.println();
                }

                successPopUp.setContentText("Movie data saved successfully!");
                successPopUp.showAndWait();

            } catch (IOException e) {
                alert.setContentText("An error occurred while saving the file.");
                alert.showAndWait();
                e.printStackTrace();
            }
        }
    }


   /* public void loadMoviesFromFile(Stage stage, TableView<Movie> tableView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setTitle("Select Movie File");

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try (Scanner scanner = new Scanner(file)) {
                ObservableList<Movie> movies = FXCollections.observableArrayList();

                String title = null, description = null, posterUrl = null;
                int releaseYear = 0;
                double rating = 0.0;

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();

                    if (line.startsWith("Title: ")) {
                        title = line.substring(7).trim();
                    } else if (line.startsWith("Description: ")) {
                        description = line.substring(13).trim();
                    } else if (line.startsWith("Release Year: ")) {
                        try {
                            releaseYear = Integer.parseInt(line.substring(14).trim());
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Invalid release year format: " + line);
                        }
                    } else if (line.startsWith("Rating: ")) {
                        try {
                            rating = Double.parseDouble(line.substring(8).trim());
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Invalid rating format: " + line);
                        }
                    } else if (line.startsWith("Poster URL: ")) {
                        StringBuilder urlBuilder = new StringBuilder(line.substring(12).trim());
                        while (scanner.hasNextLine()) {
                            String nextLine = scanner.nextLine().trim();
                            if (!nextLine.isEmpty() && !nextLine.startsWith("Title: ") && !nextLine.startsWith("Description: ") &&
                                    !nextLine.startsWith("Release Year: ") && !nextLine.startsWith("Rating: ") &&
                                    !nextLine.startsWith("Poster URL: ")) {
                                urlBuilder.append(nextLine);
                            } else {
                                line = nextLine;
                                break;
                            }
                        }
                        posterUrl = urlBuilder.toString();
                    }

                    if (line.isEmpty() && title != null && description != null) {
                        Movie movie = new Movie(title, description, releaseYear, rating, posterUrl);
                        movies.add(movie);

                        // Add the movie to the hash table
                        movieHashTable.put(new Movie(title, description, releaseYear, rating,""));

                        // Reset temporary variables
                        title = null;
                        description = null;
                        posterUrl = null;
                        releaseYear = 0;
                        rating = 0.0;
                    }
                }

                tableView.getItems().clear();
                tableView.setItems(movies);

                successPopUp.setContentText("Movie data loaded successfully!");
                successPopUp.showAndWait();
            } catch (Exception e) {
                alert.setContentText("An error occurred while loading the file: " + e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
            }
        }
    }
*/

    public void loadMoviesFromFile(Stage stage, TableView<Movie> tableView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setTitle("Select Movie File");

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try (Scanner scanner = new Scanner(file)) {
                ObservableList<Movie> movies = FXCollections.observableArrayList();

                String title = null, description = null, posterUrl = null;
                int releaseYear = 0;
                double rating = 0.0;

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();

                    if (line.startsWith("Title: ")) {
                        title = line.substring(7).trim();
                    } else if (line.startsWith("Description: ")) {
                        description = line.substring(13).trim();
                    } else if (line.startsWith("Release Year: ")) {
                        try {
                            releaseYear = Integer.parseInt(line.substring(14).trim());
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Invalid release year format: " + line);
                        }
                    } else if (line.startsWith("Rating: ")) {
                        try {
                            rating = Double.parseDouble(line.substring(8).trim());
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Invalid rating format: " + line);
                        }
                    } else if (line.startsWith("Poster URL: ")) {
                        StringBuilder urlBuilder = new StringBuilder(line.substring(12).trim());
                        while (scanner.hasNextLine()) {
                            String nextLine = scanner.nextLine().trim();
                            if (!nextLine.isEmpty() && !nextLine.startsWith("Title: ") && !nextLine.startsWith("Description: ") &&
                                    !nextLine.startsWith("Release Year: ") && !nextLine.startsWith("Rating: ") &&
                                    !nextLine.startsWith("Poster URL: ")) {
                                urlBuilder.append(nextLine);
                            } else {
                                line = nextLine;
                                break;
                            }
                        }
                        posterUrl = urlBuilder.toString();
                    }

                    if (line.isEmpty() && title != null && description != null) {
                        Movie movie = new Movie(title, description, releaseYear, rating, posterUrl);
                        movies.add(movie);

                        // Add the movie to the hash table
                        movieHashTable.put(new Movie(title, description, releaseYear, rating, ""));

                        // Reset temporary variables
                        title = null;
                        description = null;
                        posterUrl = null;
                        releaseYear = 0;
                        rating = 0.0;
                    }
                }

                tableView.getItems().clear();
                tableView.setItems(movies);

                // Update the global originalMovies list
                originalMovies.clear();
                originalMovies.addAll(movies);

                successPopUp.setContentText("Movie data loaded successfully!");
                successPopUp.showAndWait();
            } catch (Exception e) {
                alert.setContentText("An error occurred while loading the file: " + e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
            }
        }
    }

    public void fillTable() {
        tableMovie.getItems().clear();
        for (int i = 0; i < movieHashTable.getAllMovies().size(); i++) {
            tableMovie.getItems().add(movieHashTable.getAllMovies().get(i));
        }
        tableMovie.refresh();
    }

   /* private void PrintSorted(TableView<Movie> tableMovie, boolean ascending) {
        ObservableList<Movie> movies = tableMovie.getItems();
        movies.sort((movie1, movie2) -> ascending ?
                movie1.getTitle().compareToIgnoreCase(movie2.getTitle()) :
                movie2.getTitle().compareToIgnoreCase(movie1.getTitle()));
        tableMovie.refresh();
    }*/
   private void PrintSorted(TableView<Movie> tableMovie, boolean ascending) {
       ObservableList<Movie> movies = tableMovie.getItems();

       ObservableList<Movie> sortedMovies = FXCollections.observableArrayList(movies);

       for (int i = 0; i < sortedMovies.size(); i++) {
           for (int j = i + 1; j < sortedMovies.size(); j++) {
               boolean condition = ascending
                       ? sortedMovies.get(i).getTitle().compareToIgnoreCase(sortedMovies.get(j).getTitle()) > 0
                       : sortedMovies.get(i).getTitle().compareToIgnoreCase(sortedMovies.get(j).getTitle()) < 0;

               if (condition) {
                   Movie temp = sortedMovies.get(i);
                   sortedMovies.set(i, sortedMovies.get(j));
                   sortedMovies.set(j, temp);
               }
           }
       }

       tableMovie.setItems(sortedMovies);
       tableMovie.refresh();
   }
    private void PrintTopRated(TableView<Movie> tableMovie) {
        ObservableList<Movie> movies = tableMovie.getItems();

        // Find the highest rating
        double highestRating = 0;
        for (Movie movie : movies) {
            if (movie.getRating() > highestRating) {
                highestRating = movie.getRating();
            }
        }

        ObservableList<Movie> topRatedMovies = FXCollections.observableArrayList();

        for (Movie movie : movies) {
            if (movie.getRating() == highestRating) {
                topRatedMovies.add(movie);
            }
        }

        // Update the table to show only top-rated movies
        tableMovie.setItems(topRatedMovies);
        tableMovie.refresh();
        successPopUp.setContentText("Displayed top-rated movies with rating: " + highestRating);
        successPopUp.showAndWait();
    }
    private void PrintLeastRated(TableView<Movie> tableMovie) {
        ObservableList<Movie> movies = tableMovie.getItems();

        // Find the lowest rating
        double lowestRating = 10;
        for (Movie movie : movies) {
            if (movie.getRating() < lowestRating) {
                lowestRating = movie.getRating();
            }
        }

        ObservableList<Movie> leastRatedMovies = FXCollections.observableArrayList();

        for (Movie movie : movies) {
            if (movie.getRating() == lowestRating) {
                leastRatedMovies.add(movie);
            }
        }

        tableMovie.setItems(leastRatedMovies);
        tableMovie.refresh();
        successPopUp.setContentText("Displayed least-rated movies with rating: " + lowestRating);
        successPopUp.showAndWait();
    }


   /* private void PrintTopRated(TableView<Movie> tableMovie) {
        ObservableList<Movie> movies = tableMovie.getItems();
        double highestRating = movies.stream()
                .mapToDouble(Movie::getRating)
                .max()
                .orElse(0);

        ObservableList<Movie> topRatedMovies = FXCollections.observableArrayList(
                movies.stream()
                        .filter(movie -> movie.getRating() == highestRating)
                        .toList()
        );

        tableMovie.setItems(topRatedMovies);
        successPopUp.setContentText("Displayed top-rated movies with rating: " + highestRating);
        successPopUp.showAndWait();
    }
*/
   /* private void PrintLeastRated(TableView<Movie> tableMovie) {
        ObservableList<Movie> movies = tableMovie.getItems();
        double lowestRating = movies.stream()
                .mapToDouble(Movie::getRating)
                .min()
                .orElse(10);

        ObservableList<Movie> leastRatedMovies = FXCollections.observableArrayList(
                movies.stream()
                        .filter(movie -> movie.getRating() == lowestRating)
                        .toList()
        );

        tableMovie.setItems(leastRatedMovies);
        successPopUp.setContentText("Displayed least-rated movies with rating: " + lowestRating);
        successPopUp.showAndWait();
    }*/

    private void PrintAll(TableView<Movie> tableMovie, ObservableList<Movie> originalMovies) {
        if (originalMovies.isEmpty()) {
            alert.setContentText("No movies to display.");
            alert.showAndWait();
            return;
        }

        tableMovie.setItems(FXCollections.observableArrayList(originalMovies));
        tableMovie.refresh();
        successPopUp.setContentText("All movies displayed.");
        successPopUp.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
