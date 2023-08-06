package org.example;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import java.lang.String;
import javax.imageio.IIOException;

public class Controller {
    private int textAreaColCount=32;
    String filePath;
    public List<Character> codeArr = new ArrayList<>();
    public List<Character> decodeArr = new ArrayList<>();
    public List<java.lang.String> decodeTableWalsh = new ArrayList<>();
    public List<java.lang.String> decodeTableBin = new ArrayList<>();
    FileChooser fileChooser = new FileChooser();
    public String startPath;
    public int porogTemp;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button DecButton;

    @FXML
    private TextArea DecTA;

    @FXML
    private TextArea IsxTA;
    @FXML
    private Button button13;
    @FXML
    private TableView<?> table13;

    @FXML
    private TextArea SooTA;

    @FXML
    private TextArea SooqTA;

    @FXML
    private MenuItem menuOpenDec;

    @FXML
    private Menu menuOpenFile;

    @FXML
    private MenuItem menuOpenTable;

    @FXML
    private MenuItem menuSaveResult;

    @FXML
    private AnchorPane myPane;

    @FXML
    private ScrollPane parentPane;
    @FXML
    private CheckBox CheckBox1;

    @FXML
    private Slider slider;
    @FXML
    private ChoiceBox<java.lang.String> choseNumsWalsh;
    @FXML
    private Label sliderLabel;
    @FXML
    private Menu menuHelp;
    @FXML
    private SplitPane superParentPane;
    @FXML
    private VBox vbMenu;
    @FXML
    private TextField lenthTF;
    public TxtOpen txt;
    @FXML
    void initialize() throws IOException {
        //  Window stage = vbMenu.getScene().getWindow();
        lenthTF.setText("32");
        String currentDir = Paths.get("").toAbsolutePath().toString()+"/WalshDecodeTable.xls";
        File tempfile = new File(currentDir);
        decodeTableWalsh=ExcelRead.readFromExcel(tempfile.getAbsolutePath(),0);
        decodeTableBin=ExcelRead.readFromExcel(tempfile.getAbsolutePath(),1);

        parentPane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.INSERT) {
                handleInsertPress();
            }
            if (event.getCode() == KeyCode.DELETE) {
                handleDeletePress();
            }
        });
        IsxTA.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.INSERT) {
                handleInsertPress();
            }
            if (event.getCode() == KeyCode.DELETE) {
                handleDeletePress();
            }
        });
        SooTA.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.INSERT) {
                handleInsertPress();
            }
            if (event.getCode() == KeyCode.DELETE) {
                handleDeletePress();
            }
        });
        SooqTA.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.INSERT) {
                handleInsertPress();
            }
            if (event.getCode() == KeyCode.DELETE) {
                handleDeletePress();
            }
        });
        fileChooser.setTitle("Выберите файл");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file", "*.txt"));
       CheckBox1.setOnAction(event -> {
           if (CheckBox1.isSelected()){
               DecTA.scrollTopProperty().bindBidirectional(SooTA.scrollTopProperty());
               SooqTA.scrollTopProperty().bindBidirectional(DecTA.scrollTopProperty());
               IsxTA.scrollTopProperty().bindBidirectional(SooqTA.scrollTopProperty());
           } else {
               DecTA.scrollTopProperty().unbindBidirectional(SooTA.scrollTopProperty());
               SooqTA.scrollTopProperty().unbindBidirectional(DecTA.scrollTopProperty());
               IsxTA.scrollTopProperty().unbindBidirectional(SooqTA.scrollTopProperty());
           }

       });
        ObservableList<java.lang.String> listOfItemsForWalsh = FXCollections.observableArrayList("64","32", "16","8","4");
       choseNumsWalsh.setItems(listOfItemsForWalsh);
       choseNumsWalsh.setValue("32");
        menuHelp.setOnAction(event -> {
            try {
                Runtime.getRuntime().exec("/full/path/to/project/spr01.doc");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        DecButton.setOnAction(event -> {
            DecTA.clear();
            SooTA.clear();
            SooqTA.clear();
            decodeArr=Decode.fastDecode(codeArr,decodeTableWalsh,decodeTableBin,porogTemp,choseNumsWalsh.getValue());
            for (int i = 1; i < decodeArr.size()+1; i++) {
                DecTA.appendText(Character.toString(decodeArr.get(i-1)));
                if (i%4==0){
                DecTA.appendText("\n");}
            }
            for (int i = 0; i < Decode.decArrWT.size(); i++) {
                SooTA.appendText(Decode.decArrWT.get(i));
                    SooTA.appendText("\n");
            }
            String KK;
            for (int i = 0; i < Decode.decArrWT.size(); i++) {
                KK = Decode.decArrWT.get(i);
                KK=KK.replace('1','0');
                KK=KK.replace('5','4');
                KK=KK.replace('2','0');
                KK=KK.replace('6','4');
                KK=KK.replace('3','0');
                KK=KK.replace('7','4');
                SooqTA.appendText(KK);
                SooqTA.appendText("\n");
            }
        });
        slider.setOnMouseClicked(event -> {
            sliderLabel.setText(Integer.toString((int) slider.getValue()) + "%");
            porogTemp = (int)(Integer.parseInt(choseNumsWalsh.getValue())*((int)slider.getValue())/100);
        });

        menuOpenDec.setOnAction(event -> {
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".txt", "*.txt"));
            File file = fileChooser.showOpenDialog(myPane.getScene().getWindow());
            txt = new TxtOpen(file.getAbsolutePath());
            IsxTA.clear();
            try {
                codeArr = txt.OpenAndRead();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (int i = 1; i < codeArr.size()+1; i++) {
                IsxTA.appendText(Character.toString(codeArr.get(i-1)));
                if (i%textAreaColCount==0){
                    IsxTA.appendText("\n");}
            }

            // fileChooser.setInitialDirectory(file.getPath());


        });
        menuOpenTable.setOnAction(event -> {
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".xls", "*.xls"));
            File file = fileChooser.showOpenDialog(myPane.getScene().getWindow());

            try {
                decodeTableWalsh=ExcelRead.readFromExcel(file.getAbsolutePath(),0);
                decodeTableBin=ExcelRead.readFromExcel(file.getAbsolutePath(),1);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        IsxTA.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != IsxTA
                        && event.getDragboard().hasFiles()) {
                    /* разрешаем получать drag только для файлов */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });
        IsxTA.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    for (File file : db.getFiles()) {
                        filePath = file.getAbsolutePath();
                        Task<Void> task = new Task<Void>() {
                            @Override
                            public Void call() {
                                File file = new File(filePath);
                                txt = new TxtOpen(file.getAbsolutePath());
                                IsxTA.clear();
                                try {
                                    codeArr = txt.OpenAndRead();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                                Platform.runLater(() -> {
                                    IsxTA.clear();
                                    for (int i = 1; i < codeArr.size() + 1; i++) {
                                        IsxTA.appendText(Character.toString(codeArr.get(i - 1)));
                                        if (i % textAreaColCount == 0) {
                                            IsxTA.appendText("\n");
                                        }
                                    }
                                });
                                return null;
                            }
                        }; // Запускаем задачу в новом потоке
                        new Thread(task).start();
                    }
                }
                /*
                 * извещаем источник, что перетаскивание завершилось,
                 * если операция была успешной
                 */
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }
     private char nextChar = 'A';

    private void handleInsertPress() {
        String text = IsxTA.getText().replace("\n", ""); // Удаляем все символы новой строки
        StringBuilder newText = new StringBuilder();
        textAreaColCount++;
        int count = 0;
        for (char ch : text.toCharArray()) {
            newText.append(ch);
            count++;
            if (count == textAreaColCount) {
                newText.append("\n");
                count = 0;
            }
        }
        lenthTF.setText(String.valueOf(textAreaColCount));
        IsxTA.setText(newText.toString());

        String text1 = SooqTA.getText().replace("\n", ""); // Удаляем все символы новой строки
        StringBuilder newText1 = new StringBuilder();
        count = 0;
        for (char ch : text1.toCharArray()) {
            newText1.append(ch);
            count++;
            if (count == textAreaColCount) {
                newText1.append("\n");
                count = 0;
            }
        }
        SooqTA.setText(newText1.toString());
        String text2 = SooTA.getText().replace("\n", ""); // Удаляем все символы новой строки
        StringBuilder newText2 = new StringBuilder();
        count = 0;
        for (char ch : text2.toCharArray()) {
            newText2.append(ch);
            count++;
            if (count == textAreaColCount) {
                newText2.append("\n");
                count = 0;
            }
        }
        SooTA.setText(newText2.toString());
    }
    private void handleDeletePress() {
        String text = IsxTA.getText().replace("\n", ""); // Удаляем все символы новой строки
        StringBuilder newText = new StringBuilder();
        textAreaColCount--;
        int count = 0;
        for (char ch : text.toCharArray()) {
            newText.append(ch);
            count++;
            if (count == textAreaColCount) {
                newText.append("\n");
                count = 0;
            }
        }
        lenthTF.setText(String.valueOf(textAreaColCount));
        IsxTA.setText(newText.toString());

        String text1 = SooqTA.getText().replace("\n", ""); // Удаляем все символы новой строки
        StringBuilder newText1 = new StringBuilder();
        count = 0;
        for (char ch : text1.toCharArray()) {
            newText1.append(ch);
            count++;
            if (count == textAreaColCount) {
                newText1.append("\n");
                count = 0;
            }
        }
        SooqTA.setText(newText1.toString());
        String text2 = SooTA.getText().replace("\n", ""); // Удаляем все символы новой строки
        StringBuilder newText2 = new StringBuilder();
        count = 0;
        for (char ch : text2.toCharArray()) {
            newText2.append(ch);
            count++;
            if (count == textAreaColCount) {
                newText2.append("\n");
                count = 0;
            }
        }
        SooTA.setText(newText2.toString());
    }
}


